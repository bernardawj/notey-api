CREATE TABLE note
(
    id         SERIAL    NOT NULL,
    title      TEXT      NOT NULL,
    content    TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);