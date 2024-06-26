plugins {
    val springBootVersion = "2.7.13"
    val kotlinVersion = "1.9.24"
    val dependencyVersion = "1.1.4"
    val lombokVersion = "8.1.0"

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version dependencyVersion

    kotlin("jvm") version kotlinVersion // Kotlin을 JVM 바이트코드로 컴파일하는데 필요
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.lombok") version kotlinVersion // Lombok을 Kotlin에서 사용가능하도록 도와줌
    id("io.freefair.lombok") version lombokVersion // Lombok을 프로젝트에 쉽게 통합할 수 있도록 도와줌
    id("jacoco")
}

group = "io.junseok"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {

    //query dsl
    val querydslVersion = "5.0.0"
    implementation("com.querydsl:querydsl-jpa:$querydslVersion")
    kapt("com.querydsl:querydsl-apt:$querydslVersion:jpa")

    //jpa
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")

    //security
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
	implementation ("org.springframework.boot:spring-boot-starter-security")

    //test
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("org.springframework.security:spring-security-test")

    //etc
    implementation ("org.springframework.boot:spring-boot-starter-web")
    compileOnly ("org.projectlombok:lombok")
    kapt("org.projectlombok:lombok")
    implementation ("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.jetbrains.kotlin:kotlin-reflect") // blog

    //mysql
    runtimeOnly ("com.mysql:mysql-connector-j")

    //swagger
	implementation("io.springfox:springfox-swagger-ui:2.9.2")
	implementation("io.springfox:springfox-swagger2:2.9.2")

	// s3
	implementation("com.amazonaws:aws-java-sdk-s3:1.12.624")
	implementation("com.slack.api:slack-api-client:1.29.0")

}

//  kapt가 Java 애노테이션 프로세서를 유지
kapt {
    keepJavacAnnotationProcessors = true
}

tasks {
    test {
        useJUnitPlatform()
    }
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

