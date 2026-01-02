package org.cachewrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.UUID;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.cachewrapper")
@ConfigurationPropertiesScan(basePackages = "org.cachewrapper")
public class AuthSpringApplication {

    static void main(String[] args) {
        SpringApplication.run(AuthSpringApplication.class, args);
    }
}
