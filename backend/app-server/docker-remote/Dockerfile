FROM eclipse-temurin:17-jre
WORKDIR /app

# 이미 빌드된 JAR 파일 복사
COPY ./app-server.jar app.jar

# 포트 설정
EXPOSE 8080

# JAR 실행
ENTRYPOINT ["java", "-jar", "app.jar"]