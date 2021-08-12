DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS project_users;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id         SERIAL NOT NULL UNIQUE,
    email      TEXT   NOT NULL UNIQUE,
    password   TEXT   NOT NULL,
    first_name TEXT   NOT NULL,
    last_name  TEXT   NOT NULL
);

CREATE TABLE projects
(
    id          SERIAL    NOT NULL UNIQUE,
    name        TEXT      NOT NULL,
    description TEXT      NOT NULL,
    start_at    TIMESTAMP NOT NULL,
    end_at      TIMESTAMP NOT NULL,
    accessed_at TIMESTAMP NOT NULL,
    manager_id  INTEGER   NOT NULL,
    CONSTRAINT fk_manager_id FOREIGN KEY (manager_id) REFERENCES users (id)
);

CREATE TABLE project_users
(
    project_id   INTEGER NOT NULL,
    user_id      INTEGER NOT NULL,
    has_accepted BOOLEAN NOT NULL,
    CONSTRAINT pk_project_users PRIMARY KEY (project_id, user_id),
    CONSTRAINT fk_project_id FOREIGN KEY (project_id) REFERENCES projects (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE tasks
(
    id           SERIAL    NOT NULL,
    name         TEXT      NOT NULL,
    description  TEXT      NOT NULL,
    type         TEXT      NOT NULL,
    is_completed BOOLEAN   NOT NULL,
    created_at   TIMESTAMP NOT NULL,
    ended_at     TIMESTAMP NOT NULL,
    project_id   INTEGER   NOT NULL,
    user_id      INTEGER   NOT NULL,
    CONSTRAINT fk_project_id_user_id FOREIGN KEY (project_id, user_id) REFERENCES project_users (project_id, user_id)
);

CREATE TABLE notes
(
    id              SERIAL    NOT NULL UNIQUE,
    title           TEXT      NOT NULL,
    written_content TEXT      NOT NULL,
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP NOT NULL,
    user_id         INTEGER   NOT NULL,
    project_id      INTEGER   NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_project_id FOREIGN KEY (project_id) REFERENCES projects (id)
);

INSERT INTO users (email, password, first_name, last_name)
VALUES ('ben@email.com', 'ben123', 'Ben', 'Tan');
INSERT INTO users (email, password, first_name, last_name)
VALUES ('carly@email.com', 'carly123', 'Carly', 'Lee');
INSERT INTO users (email, password, first_name, last_name)
VALUES ('daniel@email.com', 'daniel123', 'Daniel', 'Wong');