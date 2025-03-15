DROP TABLE IF EXISTS notifications;
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
    created_at  TIMESTAMP NOT NULL,
    manager_id  INTEGER   NOT NULL,
    CONSTRAINT projects_manager_id_fk FOREIGN KEY (manager_id) REFERENCES users (id),
    CONSTRAINT projects_max_name_length_check CHECK (length(name) <= 50),
    CONSTRAINT projects_max_description_length_check CHECK (length(description) <= 255)
);

CREATE TABLE project_users
(
    project_id   INTEGER NOT NULL,
    user_id      INTEGER NOT NULL,
    has_accepted BOOLEAN NOT NULL,
    CONSTRAINT project_users_pk PRIMARY KEY (project_id, user_id),
    CONSTRAINT project_users_project_id_fk FOREIGN KEY (project_id) REFERENCES projects (id),
    CONSTRAINT project_users_user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE tasks
(
    id           SERIAL    NOT NULL,
    name         TEXT      NOT NULL,
    description  TEXT      NOT NULL,
    type         TEXT      NOT NULL,
    is_completed BOOLEAN   NOT NULL,
    start_at     TIMESTAMP NOT NULL,
    end_at       TIMESTAMP NOT NULL,
    created_at   TIMESTAMP NOT NULL,
    project_id   INTEGER   NOT NULL,
    user_id      INTEGER   NULL,
    CONSTRAINT tasks_project_id_fk FOREIGN KEY (project_id) REFERENCES projects (id),
    CONSTRAINT tasks_user_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT tasks_max_name_length_check CHECK (length(name) <= 50),
    CONSTRAINT tasks_max_description_length_check CHECK (length(description) <= 255),
    CONSTRAINT tasks_type_check CHECK (type IN ('URGENT', 'NON_URGENT'))
);

CREATE TABLE notifications
(
    id           SERIAL    NOT NULL,
    message      TEXT      NOT NULL,
    type         TEXT      NOT NULL,
    created_at   TIMESTAMP NOT NULL,
    user_id      INTEGER   NOT NULL,
    from_user_id INTEGER   NOT NULL,
    CONSTRAINT notifications_user_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT notifications_from_user_id_fk FOREIGN KEY (from_user_id) REFERENCES users (id),
    CONSTRAINT notifications_max_message_length_check CHECK (length(message) <= 100),
    CONSTRAINT notifications_type_check CHECK (type IN ('PROJECT_INVITATION', 'PROJECT_REMOVAL', 'PROJECT_ACCEPTANCE',
                                                        'PROJECT_REJECTION', 'TASK_ALLOCATION',
                                                        'TASK_ALLOCATION_REMOVAL',
                                                        'TASK_MARK_COMPLETED', 'TASK_MARK_INCOMPLETE'))
);

insert into users (email, password, first_name, last_name) values ('ben@email.com', '$2a$10$hzhxq9Y/EduQwv2RNHgsXedN1Og2wRhevUOE/dj/IVKvd8ElMgSN2', 'Ben', 'Tan');
insert into users (email, password, first_name, last_name) values ('emma@email.com', '$2a$10$avJJkp0Yz/rzH.sUMBziF.CqUYH5Q63wJR5IHWs7yKlOLpLozNCLm',	'Emma', 'Kang');
insert into users (email, password, first_name, last_name) values ('daniel@email.com', '$2a$10$zc2MzCXGzn8lJ8vb69H4q.EpHVz/PqetDbfZAunUXNVs1ansq2i.y', 'Daniel', 'Wong');
insert into users (email, password, first_name, last_name) values ('carly@email.com', '$2a$10$VUbwt5Ihr06BiSeC9A1gJeAmXBH.SZVZQ1RFUc2cIKxRtk0x8./vK', 'Carly', 'Rae');