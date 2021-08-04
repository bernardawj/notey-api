DROP TABLE note;

CREATE TABLE note
(
    id              SERIAL    NOT NULL,
    title           TEXT      NOT NULL,
    written_content TEXT      NOT NULL,
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP NOT NULL
);

CREATE TABLE "user"
(
    id         SERIAL NOT NULL,
    email      TEXT   NOT NULL,
    password   TEXT   NOT NULL,
    first_name TEXT   NOT NULL,
    last_name  TEXT   NOT NULL
);