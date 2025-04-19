# 빌드 스테이지: JDK 17과 함께 Gradle 사용
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Gradle 관련 파일만 먼저 복사하여 의존성 다운로드 레이어의 캐싱 활용
COPY gradle gradle/
COPY gradlew .
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle.properties .

# Wrapper 실행 권한 부여
RUN chmod +x ./gradlew

# 의존성만 먼저 다운로드 (이 레이어가 캐싱됨. gradle 관련 파일 변경 시에만 다시 실행)
# --mount=type=cache 옵션을 사용하여 빌드 간 /root/.gradle 디렉토리를 캐싱
RUN --mount=type=cache,target=/root/.gradle ./gradlew dependencies --no-daemon

# 소스 코드 복사 (소스 코드 변경 시 이 레이어 이후는 캐시가 무효화됨)
COPY src src/

# 빌드 실행 (메모리 및 성능 최적화 옵션 적용)
# src가 변경된 경우에만 이 레이어가 다시 실행됨
# 빌드 중에도 .gradle 캐시가 필요하므로 동일하게 캐시 마운트 사용
RUN --mount=type=cache,target=/root/.gradle ./gradlew build -x test --no-daemon \
-Dorg.gradle.jvmargs="-Xmx2g -XX:MaxMetaspaceSize=512m -XX:+UseParallelGC" \
-Dkotlin.incremental=false \
-Dkotlin.compiler.execution.strategy=in-process

# 실행 스테이지
FROM eclipse-temurin:17-jre

# 실행 환경에서 사용할 작업 디렉토리를 /app으로 설정
WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일을 실행 환경으로 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 컨테이너는 8080 포트 사용
EXPOSE 8080

# 컨테이너 시작 시 실행할 명령
ENTRYPOINT ["java", "-jar", "/app/app.jar"]