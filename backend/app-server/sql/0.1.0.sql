create table sample
(
    id   bigint auto_increment,
    name varchar(10) not null,
    primary key (id)
);

create table users
(
    id                bigint auto_increment,
    created_at        timestamp(6) not null,
    updated_at        timestamp(6) not null,
    provider_username varchar(30)  not null,
    created_by        varchar(50),
    email             varchar(50),
    nickname          varchar(50)  not null,
    updated_by        varchar(50),
    provider          enum('APPLE', 'KAKAO') not null,
    role              enum('ADMIN', 'USER') not null,
    primary key (id),
    constraint uk_provider_provider_username unique (provider, provider_username),
    constraint uk_nickname unique (nickname)
);