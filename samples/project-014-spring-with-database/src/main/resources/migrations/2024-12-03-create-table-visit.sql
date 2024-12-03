-- liquibase formatted sql
-- changeset sombriks:2024-12-03-create-table-visit.sql
create table visit
(
    id      identity primary key,
    created timestamp
);
