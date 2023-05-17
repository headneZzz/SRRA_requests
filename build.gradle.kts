plugins {
    java
    `maven-publish`
    id("org.springframework.boot").version("2.6.3")
    kotlin("jvm") version "1.8.21"
}

repositories {
    maven { url = uri("https://repo.spring.io/release") }
    mavenCentral()
}


val springVersion = "2.6.3"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springVersion")
    implementation("org.springframework.boot:spring-boot-configuration-processor:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:$springVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springVersion")

    implementation("org.modelmapper:modelmapper:2.4.5")
    runtimeOnly("org.postgresql:postgresql:42.3.8")
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")

    implementation("org.webjars:bootstrap:5.2.3")

    implementation("jakarta.xml.bind:jakarta.xml.bind-api:2.3.2")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.2")
    implementation(kotlin("stdlib-jdk8"))
}

group = "ru.gosarhro"
version = "2.0.0"
description = "SRRA_requests"
java.sourceCompatibility = JavaVersion.VERSION_17

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
