# Dockerfile.remote
# 목적: 원격 GCE 인스턴스에서 'docker compose build' 시 사용될 Dockerfile (Gradle 빌드 없음)

# --- 1단계: 빌더 (Builder) - 사전 빌드된 JAR에서 레이어 추출 ---
FROM eclipse-temurin:17-jdk as builder
WORKDIR /app

# Cloud Build에서 scp로 복사된 JAR 파일을 이미지 안으로 복사
# 이 JAR 파일은 빌드 컨텍스트 루트(docker-compose.yml 위치)에 있다고 가정
COPY ./app-server.jar ./app-server.jar

# Layered JAR 모드를 사용하여 레이어 추출
RUN java -Djarmode=layertools -jar app-server.jar extract

# --- 2단계: 최종 이미지 (Final Image) ---
FROM eclipse-temurin:17-jdk
WORKDIR /app
# 1단계(builder)에서 추출된 레이어들을 순서대로 복사
COPY --from=builder /app/dependencies/ ./dependencies/
COPY --from=builder /app/spring-boot-loader/ ./spring-boot-loader/
COPY --from=builder /app/snapshot-dependencies/ ./snapshot-dependencies/ # 있다면 복사
COPY --from=builder /app/application/ ./application/
EXPOSE 8080
# Layered JAR 실행을 위한 권장 Entrypoint
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]