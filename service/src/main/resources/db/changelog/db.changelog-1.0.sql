--liquibase formatted sql


-- changeset example:1
CREATE TABLE IF NOT EXISTS  movie
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR   NOT NULL UNIQUE,
    producer     VARCHAR   NOT NULL,
    actor        VARCHAR,
    country      VARCHAR ,
    release_date TIMESTAMP NOT NULL,
    genre        VARCHAR   NOT NULL

);

-- changeset example:2
CREATE TABLE IF NOT EXISTS  actor
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(128) NOT NULL,
    surname  VARCHAR(128) NOT NULL,
    birth_date TIMESTAMP    NOT NULL,
    movie_id INT REFERENCES movie (id)

    );

-- changeset example:3
CREATE TABLE IF NOT EXISTS  idk_user
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(128) NOT NULL,
    surname  VARCHAR(128) NOT NULL,
    role     VARCHAR      NOT NULL,
    password VARCHAR(128) NOT NULL UNIQUE,
    email    VARCHAR      NOT NULL UNIQUE

    );

-- changeset example:4
CREATE TABLE IF NOT EXISTS  review
(
    id       BIGSERIAL PRIMARY KEY,
    text     VARCHAR                      NOT NULL,
    grade    DOUBLE     PRECISION                  NOT NULL,
    movie_id BIGINT REFERENCES movie (id) NOT NULL,
    user_id  BIGINT REFERENCES idk_user (id)
    );

-- changeset example:5
CREATE TABLE IF NOT EXISTS  movie_actor
(
    id         BIGSERIAL PRIMARY KEY,
    movie_id   BIGINT REFERENCES movie (id) ON DELETE CASCADE,
    actor_id   BIGINT REFERENCES actor (id) ON DELETE CASCADE,
    created_at TIMESTAMP ,
    created_by VARCHAR(128)

    );
