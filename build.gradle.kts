plugins {
    id("java")
    id("com.microsoft.azure.azurefunctions") version "1.16.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.5.16")
    implementation("com.microsoft.azure.functions:azure-functions-java-library:3.1.0")
    implementation("com.azure:azure-messaging-servicebus:7.17.9")
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
