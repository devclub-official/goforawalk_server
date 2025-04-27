plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("plugin.jpa") version "1.9.25"
}

group = "side.flab"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")
extra["springCloudVersion"] = "2024.0.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Spring Security
//    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.jar {
    enabled = false
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// bootJar 태스크 설정 - 문서 복사 로직 직접 포함
tasks.bootJar {
    archiveBaseName.set("app-server")
    archiveVersion.set("")

    // asciidoctor에 의존
    dependsOn(tasks.asciidoctor)

    // JAR 패키징 직전에 문서 복사
    doFirst {
        println("=== 문서를 JAR에 복사합니다 ===")

        // docs 디렉토리 생성
        file("${buildDir}/resources/main/static/docs").mkdirs()

        // 문서 복사
        copy {
            from("${tasks.asciidoctor.get().outputDir}")
            into("${buildDir}/resources/main/static/docs")
        }

        file("${buildDir}/resources/main/static/docs/test.html").writeText(
            "<html><body>테스트 문서 페이지</body></html>"
        )
    }
}

// asciidoctor 태스크 설정
tasks.asciidoctor {
    inputs.dir(project.extra["snippetsDir"]!!)
    dependsOn(tasks.named("generateRestDocs"))

    attributes(
        mapOf(
            "snippets" to project.extra["snippetsDir"],
        )
    )
}

// generateRestDocs 태스크 정의
tasks.register<Test>("generateRestDocs") {
    description = "문서 생성용 테스트만 실행합니다"
    group = "documentation"

    useJUnitPlatform {
        includeTags("restdocs")  // @Tag("restdocs") 붙은 테스트만 실행
    }

    // 스니펫 출력 디렉토리 설정
    outputs.dir(project.extra["snippetsDir"]!!)
}