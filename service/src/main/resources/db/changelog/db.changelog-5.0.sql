--liquibase formatted sql

-- changeset example:1
ALTER TABLE idk_user
ADD COLUMN  password VARCHAR(128) DEFAULT '{noop}123';


