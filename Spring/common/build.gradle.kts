plugins {
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.cachewrapper"
version = "1.0"

dependencies {
    api("org.springframework.boot:spring-boot-starter-web:4.0.1")
    api("org.springframework.boot:spring-boot-flyway:4.0.1")
    api("org.flywaydb:flyway-database-postgresql:11.20.0")
    api("org.springframework.boot:spring-boot-starter-data-jpa:4.0.1")
    api("org.springframework.boot:spring-boot-starter-security:4.0.1")
    api("org.springframework.kafka:spring-kafka:4.0.1")
    api("org.springframework.data:spring-data-redis:4.0.1")
    api("redis.clients:jedis:7.2.0")

    api("org.postgresql:postgresql:42.7.8")
}