plugins {
    id("java")
    id("java-library")
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains:annotations:26.0.2-1")
        implementation("jakarta.validation:jakarta.validation-api:4.0.0-M1")
        implementation("com.fasterxml.jackson.core:jackson-core:2.20.1")
        implementation("com.fasterxml.jackson.core:jackson-annotations:3.0-rc5")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.20.1")

        compileOnly("org.projectlombok:lombok:1.18.42")
        annotationProcessor("org.projectlombok:lombok:1.18.42")
    }
}