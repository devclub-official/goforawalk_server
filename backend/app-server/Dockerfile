# 빌드 스테이지: JDK 17과 함께 Gradle 8.13 사용
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Gradle Wrapper와 의존성 캐싱을 위한 레이어 최적화
COPY gradle/wrapper/gradle-wrapper.jar gradle/wrapper/
COPY gradle/wrapper/gradle-wrapper.properties gradle/wrapper/
COPY gradlew .
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Wrapper 실행 권한 부여
RUN chmod +x ./gradlew

# Gradle 의존성 다운로드만 먼저 실행 (캐싱 효율성 향상)
RUN ./gradlew dependencies --no-daemon

# 소스코드 복사 후 빌드
COPY src src
RUN ./gradlew build --no-daemon -x test

# JAR 파일이 2개 생성되는 문제 해결
RUN find /app/build/libs/ -name "*plain.jar" -delete

# 실행 스테이지
FROM eclipse-temurin:17-jdk

# 실행 환경에서 사용할 작업 디렉토리를 /app으로 설정
WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일을 실행 환경으로 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 애플리케이션 실행을 위한 환경변수 설정
#ENV SPRING_PROFILES_ACTIVE=prod

# 컨테이너는 8080 포트 사용
EXPOSE 8080

# 컨테이너 시작 시 실행할 명령
ENTRYPOINT ["java", "-jar", "/app/app.jar"]