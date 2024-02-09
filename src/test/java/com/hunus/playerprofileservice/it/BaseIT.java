package com.hunus.playerprofileservice.it;

import static com.hunus.playerprofileservice.it.TestContainers.playerProfileServiceContainer;
import static com.hunus.playerprofileservice.it.TestContainers.postgresContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseIT {
    protected static Connection connection;

    @BeforeAll
    static void init() throws Exception {
        TestContainers.start();

        RestAssured.baseURI = getBaseURI();
        WireMock.configureFor(
            TestContainers.httpServerContainer.getHost(),
            TestContainers.httpServerContainer.getMappedPort(8080)
        );
        connection = DriverManager.getConnection(getJdbcUrl(), "playerprofileservice", "playerprofileservice");
    }

    protected void executeSql(String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    protected void clearDb() throws SQLException {
        executeSql("truncate table device, player_profile, clan;");
    }

    private static String getBaseURI() {
        return String.format("http://%s:%d/rest",
            playerProfileServiceContainer.getHost(),
            playerProfileServiceContainer.getMappedPort(8888)
        );
    }

    private static String getJdbcUrl() {
        return "jdbc:postgresql://localhost:" + postgresContainer.getFirstMappedPort() + "/playerprofileservicedb";
    }
}
