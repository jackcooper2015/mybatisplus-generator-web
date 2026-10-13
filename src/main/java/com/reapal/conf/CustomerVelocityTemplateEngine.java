package com.reapal.conf;

import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

/** Renders built-in and saved templates without writing executable templates to the web root. */
public class CustomerVelocityTemplateEngine extends AbstractTemplateEngine {
    private final Map<String, String> templates;
    private final java.util.Set<java.nio.file.Path> outputPaths = new java.util.HashSet<>();
    private final VelocityEngine velocity = new VelocityEngine();

    public CustomerVelocityTemplateEngine(Map<String, String> templates) {
        this.templates = Map.copyOf(templates);
        velocity.setProperty("introspector.uberspect.class", "org.apache.velocity.util.introspection.SecureUberspector");
        velocity.init();
    }

    @Override
    public CustomerVelocityTemplateEngine init(ConfigBuilder configBuilder) {
        return this;
    }

    @Override
    public String writer(Map<String, Object> objectMap, String templateName, String templateString) {
        StringWriter writer = new StringWriter();
        velocity.evaluate(new VelocityContext(objectMap), writer, templateName, templateString);
        return writer.toString();
    }

    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, File outputFile) throws IOException {
        String name = templatePath.substring(templatePath.lastIndexOf('/') + 1);
        String content = templates.get(name);
        if (content == null) {
            try (InputStream input = getClass().getResourceAsStream("/templates/gen-template/" + name)) {
                if (input == null) {
                    throw new IOException("Missing generation template: " + name);
                }
                content = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            }
        }
        Files.writeString(outputFile.toPath(), writer(objectMap, name, content), StandardCharsets.UTF_8);
    }

    @Override
    protected void outputFile(File file, Map<String, Object> values, String template, boolean override) {
        if (!outputPaths.add(file.toPath().toAbsolutePath().normalize())) {
            throw new IllegalArgumentException("Selected tables produce duplicate output paths; change the naming strategy");
        }
        super.outputFile(file, values, template, override);
    }

    @Override
    public String templateFilePath(String filePath) {
        return filePath.endsWith(".vm") ? filePath : filePath + ".vm";
    }
}
