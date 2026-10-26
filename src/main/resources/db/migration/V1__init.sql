CREATE TABLE users (
                       id            BIGSERIAL PRIMARY KEY,
                       username      VARCHAR(100) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE project_boards (
                                id         BIGSERIAL PRIMARY KEY,
                                owner_id   BIGINT       NOT NULL REFERENCES users (id) ON DELETE CASCADE,
                                name       VARCHAR(100) NOT NULL,
                                created_at TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE tasks (
                       id               BIGSERIAL PRIMARY KEY,
                       project_board_id BIGINT       NOT NULL REFERENCES project_boards (id) ON DELETE CASCADE,
                       title            VARCHAR(100) NOT NULL,
                       description      VARCHAR(300),
                       status           VARCHAR(20)  NOT NULL DEFAULT 'BACKLOG'
);

CREATE INDEX idx_project_boards_owner ON project_boards (owner_id);
CREATE INDEX idx_tasks_project_board ON tasks (project_board_id);