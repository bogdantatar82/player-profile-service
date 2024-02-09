package com.hunus.playerprofileservice.it;

import java.time.Duration;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.DockerHealthcheckWaitStrategy;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

public class TestContainers {
    private static final Logger log = LoggerFactory.getLogger(TestContainers.class);
    private static final String WIREMOCK_IMAGE_NAME = "wiremock/wiremock:latest";
    private static final String POSTGRES_IMAGE_NAME = "postgres:14.10";
    private static final String SERVICE_IMAGE_NAME = "hunus/player-profile-service:latest";

    static GenericContainer<?> httpServerContainer;
    static PostgreSQLContainer<?> postgresContainer;
    static GenericContainer<?> playerProfileServiceContainer;
    private static Network network;

    public static void start() {
        log.info("Starting integration test containers");
        createHttpServerContainer();
        createPostgresContainer();
        createPlayerProfileServiceContainer();
        log.info("Integration test containers started");
    }

    private static GenericContainer createHttpServerContainer() {
        if (httpServerContainer != null) {
            return httpServerContainer;
        }
        log.info("Starting httpServer wiremock container");
        httpServerContainer = new GenericContainer<>(WIREMOCK_IMAGE_NAME)
            .withFileSystemBind("./src/test/resources/integration-tests-data/mappings",
                "/home/wiremock/mappings")
            .withFileSystemBind("./src/test/resources/integration-tests-data/__files",
                "/home/wiremock/__files")
            .withCommand("--global-response-templating",
                "--print-all-network-traffic",
                "--enable-browser-proxying")
            .withExposedPorts(8080)
            .withEnv(getEnvironment())
            .withNetwork(getTestNetwork())
            .withNetworkAliases("httpserver")
            .waitingFor(getWiremockStartupCheck());
        httpServerContainer.start();
        return httpServerContainer;
    }

    static PostgreSQLContainer<?> createPostgresContainer() {
        if (postgresContainer != null) {
            return postgresContainer;
        }
        log.info("Starting Postgres container");
        postgresContainer = new PostgreSQLContainer<>(POSTGRES_IMAGE_NAME)
            .withDatabaseName("playerprofileservicedb")
            .withUsername("playerprofileservice")
            .withPassword("playerprofileservice")
            .withExposedPorts(5432)
            .withEnv(getEnvironment())
            .withNetwork(getTestNetwork())
            .withNetworkAliases("player-profile-service-database");
        postgresContainer.start();
        return postgresContainer;
    }

    private static GenericContainer createPlayerProfileServiceContainer() {
        if (playerProfileServiceContainer != null) {
            return playerProfileServiceContainer;
        }
        log.info("Starting PlayerProfileService container");
        // If running in intellij and local changes, make sure to manually regenerate docker image!
        playerProfileServiceContainer = new GenericContainer<>(SERVICE_IMAGE_NAME)
            .withExposedPorts(8888, 8787)
            .withEnv(getEnvironment())
            .withNetwork(getTestNetwork())
            .withNetworkAliases("player-profile-service")
            .waitingFor(new DockerHealthcheckWaitStrategy().withStartupTimeout(Duration.ofSeconds(500)));
        playerProfileServiceContainer.start();
        return playerProfileServiceContainer;
    }

    private static WaitStrategy getWiremockStartupCheck() {
        return new HttpWaitStrategy()
            .forPath("/__admin/")
            .forStatusCode(200)
            .withStartupTimeout(Duration.ofSeconds(20));
    }
    private static Network getTestNetwork() {
        if (network == null) {
            network = Network.newNetwork();
        }
        return network;
    }

    private static Map<String, String> getEnvironment() {
        return Map.of("SPRING_PROFILES_ACTIVE", "it");
    }
}
