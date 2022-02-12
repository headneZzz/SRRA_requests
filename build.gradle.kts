plugins {
    java
    `maven-publish`
    id("org.springframework.boot").version("2.6.3")
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
    implementation("org.modelmapper:modelmapper:2.4.5")
    runtimeOnly("org.postgresql:postgresql:42.3.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springVersion")
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
}

group = "ru.gosarhro"
version = "1.0.0"
description = "SRRA_requests"
java.sourceCompatibility = JavaVersion.VERSION_1_9

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
