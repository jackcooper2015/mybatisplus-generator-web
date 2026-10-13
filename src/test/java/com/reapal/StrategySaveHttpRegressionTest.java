package com.reapal;

import com.reapal.dao.DbConfigDao;
import com.reapal.dao.TableStrategyConfigDao;
import com.reapal.model.DbConfig;
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

/** No target database connection: exercises only HTTP and the disposable configuration store. */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:h2:mem:strategy-save-test;MODE=MYSQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "generator.admin.username=admin",
        "generator.admin.password=test-only-password"
})
class StrategySaveHttpRegressionTest {
    @LocalServerPort private int port;
    @Autowired private DbConfigDao databases;
    @Autowired private TableStrategyConfigDao strategies;

    @Test
    void savingTwoTablesAndResavingOnePreservesIndependentStrategyIdentities() throws Exception {
        DbConfig database = new DbConfig();
        database.setDbName("strategy-save-regression");
        Long databaseId = databases.saveAndFlush(database).getId();
        HttpClient client = HttpClient.newBuilder().cookieHandler(new CookieManager()).build();
        try {
            HttpResponse<String> login = post(client, "/login", "username=admin&password=test-only-password");
            assertEquals(200, login.statusCode());
            assertEquals("true", login.body());

            save(client, databaseId, "alpha", "first");
            var alpha = strategies.findByDbIdAndTableName(databaseId, "alpha");
            assertNotNull(alpha);
            Long alphaId = alpha.getId();
            save(client, databaseId, "beta", "second");
            var beta = strategies.findByDbIdAndTableName(databaseId, "beta");
            assertNotNull(beta);
            assertNotEquals(alphaId, beta.getId());

            save(client, databaseId, "alpha", "updated");
            alpha = strategies.findByDbIdAndTableName(databaseId, "alpha");
            var unchangedBeta = strategies.findByDbIdAndTableName(databaseId, "beta");
            assertEquals(alphaId, alpha.getId());
            assertEquals("updated", alpha.getModelName());
            assertEquals(databaseId, alpha.getDbId());
            assertEquals(beta.getId(), unchangedBeta.getId());
            assertEquals("second", unchangedBeta.getModelName());
            assertEquals(databaseId, unchangedBeta.getDbId());
        } finally {
            for (String table : new String[]{"alpha", "beta"}) {
                var strategy = strategies.findByDbIdAndTableName(databaseId, table);
                if (strategy != null) strategies.deleteById(strategy.getId());
            }
            databases.deleteById(databaseId);
        }
    }

    private void save(HttpClient client, Long databaseId, String table, String module) throws Exception {
        // The existing browser sends the data-source ID in the id field, not a strategy ID.
        HttpResponse<String> response = post(client, "/columnsave", "id=" + databaseId
                + "&tableName=" + table + "&modelName=" + module);
        assertEquals(200, response.statusCode(), response.body());
        assertTrue(response.body().contains("\"code\":0"), response.body());
    }

    private HttpResponse<String> post(HttpClient client, String path, String body) throws Exception {
        return client.send(HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + path))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body)).build(), HttpResponse.BodyHandlers.ofString());
    }
}
