-- liquibase formatted sql
-- changeset sombriks:2025-05-01-initial-schema.sql
create table products(
  id serial primary key,
  description text not null
);

-- rollback drop table products;
