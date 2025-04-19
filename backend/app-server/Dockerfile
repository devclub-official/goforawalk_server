# 1단계: 컴파일 (Compiler)
FROM eclipse-temurin:17-jdk as compiler

# 프로젝트의 모든 파일을 복사
COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew bootJar

# 2단계: 빌더 및 Layered JAR 추출 (Builder & Extractor)
FROM eclipse-temurin:17-jdk as builder

# 컴파일 단계에서 빌드된 JAR 파일을 복사
COPY --from=compiler build/libs/app-server.jar ./

# Layered JAR에서 레이어별로 파일을 추출
RUN java -Djarmode=layertools -jar app-server.jar extract

# 3단계: 최종 Docker 이미지 생성 (Final Image)
# 추출된 레이어들과 필요한 기본 환경을 설정하여 최종 이미지를 Todtjd
FROM eclipse-temurin:17-jdk

# 애플리케이션 실행을 위한 사용자 및 작업 디렉토리 생성/설정
RUN useradd -m app
USER app
WORKDIR /app

COPY --chown=app --from=builder dependencies/ ./
COPY --chown=app --from=builder snapshot-dependencies/ ./
COPY --chown=app --from=builder spring-boot-loader/ ./
COPY --chown=app --from=builder application/ ./

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]