--liquibase formatted sql

-- changeset example:1
ALTER TABLE movie
ADD COLUMN image VARCHAR(64)

