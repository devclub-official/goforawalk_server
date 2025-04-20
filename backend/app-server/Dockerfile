# Dockerfile (소스 저장소 내 위치. Cloud Build 직접 빌드용)

# --- 1단계: 컴파일러 (Compiler) - 빌드 파일/소스 분리 및 캐시 마운트 적용 ---
FROM eclipse-temurin:17-jdk as compiler
WORKDIR /app
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle.kts ./
COPY settings.gradle.kts ./
COPY src ./src
# BuildKit 캐시 마운트 및 --no-daemon 옵션 사용
RUN --mount=type=cache,target=/root/.gradle/caches ./gradlew bootJar --no-daemon

# --- 2단계: 빌더 (Builder) - Layered JAR 추출 ---
FROM eclipse-temurin:17-jdk as builder
WORKDIR /app
COPY --from=compiler /app/build/libs/app-server.jar ./app-server.jar
RUN java -Djarmode=layertools -jar app-server.jar extract

# --- 3단계: 최종 이미지 (Final Image) ---
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/dependencies/ ./dependencies/
COPY --from=builder /app/spring-boot-loader/ ./spring-boot-loader/
COPY --from=builder /app/snapshot-dependencies/ ./snapshot-dependencies/
COPY --from=builder /app/application/ ./application/
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]