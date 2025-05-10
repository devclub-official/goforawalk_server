```mermaid
erDiagram
%% UK: Means Unique Index
%% *모든 컬럼은 기본적으로 NOT NULL. Nullable인 경우 "Nullable" 코멘트로 표기
    direction LR

    %% relationship
    users 1 .. 1+ footstep : "has"

    %% entities
    users {
        bigint id PK
        varchar nickname UK "UK(nickname)"
        varchar provider UK "UK(provider, provider_username)"
        varchar provider_username UK "UK(provider, provider_username)"
        varchar email "Nullable"
        varchar created_by
        timestamp created_at
        varchar updated_by
        timestamp updated_at
    }

    footstep {
        bigint id PK
        bigint user_id FK
        varchar image_url
        varchar created_by
        timestamp created_at
        varchar updated_by
        timestamp updated_at
    }  
```
