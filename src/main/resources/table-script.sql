DROP TABLE IF EXISTS note;
DROP TABLE IF EXISTS project_users;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS "user";

CREATE TABLE "user"
(
    id         SERIAL NOT NULL UNIQUE,
    email      TEXT   NOT NULL,
    password   TEXT   NOT NULL,
    first_name TEXT   NOT NULL,
    last_name  TEXT   NOT NULL
);

CREATE TABLE project
(
    id          SERIAL    NOT NULL UNIQUE,
    name        TEXT      NOT NULL,
    description TEXT      NOT NULL,
    start_at    TIMESTAMP NOT NULL,
    end_at      TIMESTAMP NOT NULL,
    manager_id  INTEGER   NOT NULL,
    CONSTRAINT fk_manager_id FOREIGN KEY (manager_id) REFERENCES "user" (id)
);

CREATE TABLE project_users
(
    project_id INTEGER NOT NULL,
    user_id    INTEGER NOT NULL,
    CONSTRAINT pk_project_users PRIMARY KEY (project_id, user_id),
    CONSTRAINT fk_project_id FOREIGN KEY (project_id) REFERENCES project (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE note
(
    id              SERIAL    NOT NULL UNIQUE,
    title           TEXT      NOT NULL,
    written_content TEXT      NOT NULL,
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP NOT NULL,
    user_id         INTEGER   NOT NULL,
    project_id      INTEGER   NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES "user" (id),
    CONSTRAINT fk_project_id FOREIGN KEY (project_id) REFERENCES project (id)
);

INSERT INTO "user" (email, password, first_name, last_name) VALUES ('ben@email.com', 'ben123', 'Ben', 'Tan');
INSERT INTO "user" (email, password, first_name, last_name) VALUES ('carly@email.com', 'carly123', 'Carly', 'Lee');
INSERT INTO "user" (email, password, first_name, last_name) VALUES ('daniel@email.com', 'daniel123', 'Daniel', 'Wong');