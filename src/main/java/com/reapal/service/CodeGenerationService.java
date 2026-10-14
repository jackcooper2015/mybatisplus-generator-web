package com.reapal.service;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.reapal.conf.CustomerVelocityTemplateEngine;
import com.reapal.conf.DbColumnTypeExt;
import com.reapal.model.DbConfig;
import com.reapal.model.TableStrategyConfig;
import com.reapal.model.Template;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/** One isolated workspace per request, shared by single-table and batch generation. */
@Service
public class CodeGenerationService {
    private static final Set<String> TEMPLATE_NAMES = Set.of("controller.java.vm", "entity.java.vm",
            "mapper.java.vm", "mapper.xml.vm", "service.java.vm", "serviceImpl.java.vm",
            "page.html.vm", "page.vue.vm", "page.js.vm");

    public byte[] generateZip(DbConfig database, TableStrategyConfig options, List<Template> savedTemplates)
            throws IOException {
        if (options == null) {
            throw new IllegalArgumentException("Save the table generation settings before generating code");
        }
        validateOptions(options);
        Path directory = Files.createTempDirectory("mybatisplus-generator-");
        try {
            generate(database, options, savedTemplates, directory);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (ZipOutputStream zip = new ZipOutputStream(bytes); var paths = Files.walk(directory)) {
                for (Path file : paths.filter(Files::isRegularFile).sorted().toList()) {
                    zip.putNextEntry(new ZipEntry(directory.relativize(file).toString().replace('\\', '/')));
                    Files.copy(file, zip);
                    zip.closeEntry();
                }
            }
            return bytes.toByteArray();
        } finally {
            try (var paths = Files.walk(directory)) {
                for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) {
                    Files.deleteIfExists(path);
                }
            }
        }
    }

    private void generate(DbConfig database, TableStrategyConfig options, List<Template> savedTemplates,
                          Path output) {
        DataSourceConfig.Builder source = new DataSourceConfig.Builder(database.getUrl(),
                database.getUsername(), database.getPassword());
        if (hasText(database.getSchema())) {
            source.schema(database.getSchema());
        }
        // Preserve the application's historical date/time mapping under the new JDBC metadata API.
        source.typeConvertHandler((global, registry, column) -> {
            int type = column.getJdbcType().TYPE_CODE;
            if (type == Types.DATE || type == Types.TIME || type == Types.TIMESTAMP
                    || type == Types.TIME_WITH_TIMEZONE || type == Types.TIMESTAMP_WITH_TIMEZONE) {
                return DbColumnTypeExt.OFFSET_DATE_TIME;
            }
            return registry.getColumnType(column);
        });
        StrategyConfig.Builder strategy = new StrategyConfig.Builder()
                .addInclude(tableNames(options.getTableName()));
        if (hasText(options.getPrefix())) {
            strategy.addTablePrefix(options.getPrefix());
        }
        var entity = strategy.entityBuilder().naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel).logicDeleteColumnName("is_delete")
                .enableActiveRecord().enableLombok().enableChainModel()
                .javaTemplate("entity.java.vm");
        if (hasText(options.getEntityName())) entity.formatFileName(options.getEntityName());
        var mapper = strategy.mapperBuilder().enableBaseResultMap()
                .mapperTemplate("mapper.java.vm").mapperXmlTemplate("mapper.xml.vm");
        if (hasText(options.getMapperName())) mapper.formatMapperFileName(options.getMapperName());
        if (hasText(options.getXmlName())) mapper.formatXmlFileName(options.getXmlName());
        var service = strategy.serviceBuilder().serviceTemplate("service.java.vm")
                .serviceImplTemplate("serviceImpl.java.vm");
        if (hasText(options.getServiceName())) service.formatServiceFileName(options.getServiceName());
        if (hasText(options.getServiceImplName())) service.formatServiceImplFileName(options.getServiceImplName());
        var controller = strategy.controllerBuilder().enableRestStyle().template("controller.java.vm");
        if (hasText(options.getControllerName())) controller.formatFileName(options.getControllerName());

        PackageConfig.Builder packages = new PackageConfig.Builder().parent("com")
                .moduleName(valueOr(options.getModelName(), ""))
                .entity(valueOr(options.getEntityPackage(), "entity"))
                .mapper(valueOr(options.getMapperPackage(), "mapper"))
                .service(valueOr(options.getServicePackage(), "service"))
                .serviceImpl(valueOr(options.getServiceImplPackage(), "service.impl"))
                .controller(valueOr(options.getControllerPackage(), "controller"));
        List<CustomFile> files = new ArrayList<>();
        files.add(customFile(output, "resource/templates/views", ".html", "page.html.vm"));
        files.add(customFile(output, "resource/templates/views", ".vue", "page.vue.vm"));
        files.add(customFile(output, "resource/static/js", ".js", "page.js.vm"));
        Map<String, String> templates = new HashMap<>();
        for (Template template : savedTemplates) {
            if (!TEMPLATE_NAMES.contains(template.getTemplateName())) {
                throw new IllegalArgumentException("Unknown template name");
            }
            if (template.getContent() == null) throw new IllegalArgumentException("Template content is required");
            templates.put(template.getTemplateName(), template.getContent());
        }
        DataSourceConfig dataSource = source.build();
        GlobalConfig global = new GlobalConfig.Builder().outputDir(output.toString()).disableOpenDir()
                .author(valueOr(options.getAuthor(), "generator")).build();
        InjectionConfig injection = new InjectionConfig.Builder()
                .customMap(Map.of("entityLombokModel", true, "restControllerStyle", true))
                .customFile(files).build();
        ConfigBuilder config = new ConfigBuilder(packages.build(), dataSource, strategy.build(), null, global, injection);
        if (config.getTableInfoList().size() != tableNames(options.getTableName()).size()) {
            throw new IllegalArgumentException("One or more selected tables were not found");
        }
        new AutoGenerator(dataSource).config(config).execute(new CustomerVelocityTemplateEngine(templates));
    }

    private static CustomFile customFile(Path root, String directory, String extension, String template) {
        return new CustomFile.Builder().filePath(root.resolve(directory).toString()).fileName(extension)
                .formatNameFunction(table -> table.getName().replace('_', '-').toLowerCase(Locale.ROOT))
                .templatePath(template).build();
    }

    private static List<String> tableNames(String names) {
        if (!hasText(names)) throw new IllegalArgumentException("Select at least one table");
        Set<String> tables = new LinkedHashSet<>();
        for (String name : names.split(",", -1)) {
            String table = name.trim();
            if (!table.matches("[A-Za-z_][A-Za-z0-9_$]*")) {
                throw new IllegalArgumentException("Invalid table name");
            }
            tables.add(table);
        }
        return new ArrayList<>(tables);
    }

    private static void validateOptions(TableStrategyConfig options) {
        List<String> tables = tableNames(options.getTableName());
        for (String value : new String[]{options.getModelName(), options.getEntityPackage(),
                options.getMapperPackage(), options.getServicePackage(), options.getServiceImplPackage(),
                options.getControllerPackage()}) {
            if (hasText(value) && !value.matches("[A-Za-z_$][A-Za-z0-9_$]*(\\.[A-Za-z_$][A-Za-z0-9_$]*)*")) {
                throw new IllegalArgumentException("Invalid Java package name");
            }
        }
        for (String format : new String[]{options.getEntityName(), options.getMapperName(), options.getXmlName(),
                options.getServiceName(), options.getServiceImplName(), options.getControllerName()}) {
            if (!hasText(format)) continue;
            if (!format.replace("%s", "Entity").matches("[A-Za-z_$][A-Za-z0-9_$]*")
                    || format.indexOf("%s") != format.lastIndexOf("%s")
                    || (tables.size() > 1 && !format.contains("%s"))) {
                throw new IllegalArgumentException("Use a Java class-name pattern with one %s for batch generation");
            }
        }
    }

    private static boolean hasText(String value) { return value != null && !value.isBlank(); }
    private static String valueOr(String value, String fallback) { return hasText(value) ? value : fallback; }
}
