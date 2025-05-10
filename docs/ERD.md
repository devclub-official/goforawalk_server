```mermaid
erDiagram
%% UK: Means Unique Index
%% *모든 컬럼은 기본적으로 NOT NULL. Nullable인 경우 "Nullable" 코멘트로 표기
%% 모든 Date, Timestamp는 전부 UTC 기준
    direction LR

%% relationship
    users 1 .. 1+ footstep: "has"
%% entities
    users {
        bigint id PK
        varchar(50) nickname UK "UK(nickname)"
        varchar(10) provider UK "UK(provider, provider_username)"
        varchar(50) provider_username UK "UK(provider, provider_username)"
        varchar email "Nullable"
        varchar timezone "Default('Asia/Seoul') | 회원가입 시점 기준 IANA 타임존"
        varchar(50) created_by
        timestamp created_at
        varchar(50) updated_by
        timestamp updated_at
    }

    footstep {
        bigint id PK
        bigint user_id FK, UK "UK(user_id, local_date)"
        date local_date UK "UK(user_id, local_date) | footstep을 남기는 시점의 로컬 날짜"
        varchar(50) content "Nullable"
        varchar image_url
        varchar(50) created_by
        timestamp created_at
        varchar(50) updated_by
        timestamp updated_at
    }  
```
