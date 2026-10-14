package com.reapal;

import com.reapal.controller.LoginController;
import com.reapal.dao.DbConfigDao;
import com.reapal.dao.TemplateDao;
import com.reapal.dao.TableStrategyConfigDao;
import com.reapal.model.TableStrategyConfig;
import com.reapal.dao.TemplateSetDao;
import com.reapal.model.DbConfig;
import com.reapal.model.Template;
import com.reapal.model.TemplateSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:h2:mem:runtime-test;MODE=MYSQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "generator.admin.username=admin",
        "generator.admin.password=test-only-password"
})
class CodeGeneratorApplicationTests {
    @LocalServerPort
    private int port;
    @Autowired
    private DbConfigDao dbConfigs;
    @Autowired
    private TemplateSetDao templateSets;
    @Autowired
    private TemplateDao templates;
    @Autowired
    private TableStrategyConfigDao strategies;

    @Test
    void freshSchemaSupportsIdentityColumnsAndLongTemplates() {
        DbConfig config = new DbConfig();
        config.setDbName("runtime-test");
        config.setSchema("PUBLIC");
        config.setCatalog("runtime-test");
        config = dbConfigs.saveAndFlush(config);
        assertNotNull(config.getId());
        assertEquals("PUBLIC", dbConfigs.findById(config.getId()).orElseThrow().getSchema());

        TemplateSet set = new TemplateSet();
        set.setSetName("Runtime test");
        set.setDesc("Reserved SQL keyword uses a safe physical column");
        set = templateSets.saveAndFlush(set);
        assertNotNull(set.getId());
        assertEquals(set.getDesc(), templateSets.findById(set.getId()).orElseThrow().getDesc());

        Template template = new Template();
        template.setTemplateSetId(set.getId());
        template.setTemplateName("large.java.vm");
        template.setContent("x".repeat(20_000));
        template = templates.saveAndFlush(template);
        assertEquals(20_000, templates.findById(template.getId()).orElseThrow().getContent().length());
        TableStrategyConfig strategy = new TableStrategyConfig();
        strategy.setDbId(config.getId());
        strategy.setTableName("sample_table");
        strategy = strategies.saveAndFlush(strategy);
        assertNotNull(strategy.getId());
        strategies.deleteById(strategy.getId());
        templates.deleteById(template.getId());
        templateSets.deleteById(set.getId());
        dbConfigs.deleteById(config.getId());
    }

    @Test
    void loginAndJacksonApiWorkWithoutLegacyConverters() throws Exception {
        HttpClient client = HttpClient.newBuilder().cookieHandler(new CookieManager()).build();
        HttpResponse<String> protectedRoute = client.send(HttpRequest.newBuilder(uri("/template-set/to-page"))
                .GET().build(), HttpResponse.BodyHandlers.ofString());
        assertEquals(302, protectedRoute.statusCode());
        assertEquals("/login", URI.create(protectedRoute.headers().firstValue("location").orElseThrow()).getPath());

        HttpResponse<String> page = client.send(HttpRequest.newBuilder(uri("/login")).GET().build(),
                HttpResponse.BodyHandlers.ofString());
        assertEquals(200, page.statusCode());
        assertTrue(page.body().contains("<html"));

        HttpResponse<String> wrongLogin = client.send(loginRequest("admin"), HttpResponse.BodyHandlers.ofString());
        assertEquals("false", wrongLogin.body());
        HttpResponse<String> login = client.send(loginRequest("test-only-password"), HttpResponse.BodyHandlers.ofString());
        assertEquals(200, login.statusCode());
        assertEquals("true", login.body());

        HttpResponse<String> api = client.send(HttpRequest.newBuilder(uri("/template-set/list"))
                .POST(HttpRequest.BodyPublishers.noBody()).build(), HttpResponse.BodyHandlers.ofString());
        assertEquals(200, api.statusCode());
        assertTrue(api.headers().firstValue("content-type").orElse("").contains("application/json"));
        assertTrue(api.body().contains("\"code\":0"));
        assertFalse(api.headers().firstValue("access-control-allow-origin").isPresent());

        HttpResponse<String> templatePage = client.send(HttpRequest.newBuilder(uri("/template-set/to-page"))
                .GET().build(), HttpResponse.BodyHandlers.ofString());
        assertEquals(200, templatePage.statusCode());
        assertTrue(templatePage.body().contains("/js/"));

        HttpResponse<String> console = client.send(HttpRequest.newBuilder(uri("/h2-console/"))
                .GET().build(), HttpResponse.BodyHandlers.ofString());
        assertEquals(404, console.statusCode());
        HttpResponse<String> scriptEndpoint = client.send(HttpRequest.newBuilder(uri("/god/run-script"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"script\":\"return 1\"}"))
                .build(), HttpResponse.BodyHandlers.ofString());
        assertEquals(404, scriptEndpoint.statusCode());
    }

    @Test
    void blankAdminPasswordIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new LoginController("admin", ""));
    }

    private HttpRequest loginRequest(String password) {
        return HttpRequest.newBuilder(uri("/login"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("username=admin&password=" + password)).build();
    }

    private URI uri(String path) {
        return URI.create("http://127.0.0.1:" + port + path);
    }
}
