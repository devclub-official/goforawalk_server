#!/bin/bash
# docker-run.sh

# 스크립트 실패 시 즉시 중단
set -e

echo "===== 애플리케이션 서버 컨테이너 배포 시작 ====="

echo ">>>>> 기존 이미지 정리 중..."
docker image prune -f

# 기존 컨테이너 중지 및 제거 (compose와 직접 명령 모두 시도)
echo ">>>>> 기존 컨테이너 정리 중..."
docker compose down || true
docker stop app-server-container || true
docker rm app-server-container || true

# Docker 이미지 빌드
echo ">>>>> Docker 이미지 빌드 중..."
docker compose build

# 컨테이너 시작
echo ">>>>> 애플리케이션 컨테이너 시작 중..."
docker compose up -d

# 컨테이너 상태 확인
echo ">>>>> 애플리케이션 컨테이너 상태 확인 중..."
docker ps | grep app-server-container || echo "컨테이너가 실행되지 않았습니다. 로그를 확인해주세요."

echo "===== 애플리케이션 서버 컨테이너 배포 완료 ====="