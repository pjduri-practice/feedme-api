plugins {
	java
	id("org.springframework.boot") version "3.0.1"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "FeedMe.Auth"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.hibernate:hibernate-core:6.1.6.Final")
	implementation("org.springframework.security:spring-security-crypto")

	// json web tokens
	implementation("io.jsonwebtoken:jjwt:0.9.1")

	// needed for jjwt to work in Java 12+
	implementation("com.sun.activation:javax.activation:1.2.0")
	implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
	runtimeOnly("org.glassfish.jaxb:jaxb-runtime")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
