plugins {
    java
    id("com.microsoft.azure.azurefunctions") version "1.16.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.5.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.5.0")
    implementation("io.micrometer:micrometer-tracing-bridge-otel:1.5.0")
    implementation("com.microsoft.azure.functions:azure-functions-java-library:3.1.0")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

azurefunctions {
//    resourceGroup = "rg-test1"
    appName = "func-test1"
}
