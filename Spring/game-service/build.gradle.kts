plugins {
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.cachewrapper"
version = "1.0"

dependencies {
    implementation(project(":Spring:common"))
}