package com.zidiogroup9.expensemanagement;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainerConfiguration {
    @Bean
    @ServiceConnection
    MySQLContainer<?> mySQLContainer(){
        return new MySQLContainer<>(DockerImageName.parse("mysql:latest"));
    }
}
