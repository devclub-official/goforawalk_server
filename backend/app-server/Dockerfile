# 빌드 스테이지: JDK 17과 함께 Gradle 8.13 사용
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# 필요한 파일들만 복사
COPY gradle gradle
COPY gradlew .
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

# Wrapper 실행 권한 부여
RUN chmod +x ./gradlew

# 빌드 실행
RUN ./gradlew build -x test

# 실행 스테이지
FROM eclipse-temurin:17-jdk

# 실행 환경에서 사용할 작업 디렉토리를 /app으로 설정
WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일을 실행 환경으로 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 컨테이너는 8080 포트 사용
EXPOSE 8080

# 컨테이너 시작 시 실행할 명령
ENTRYPOINT ["java", "-jar", "/app/app.jar"]