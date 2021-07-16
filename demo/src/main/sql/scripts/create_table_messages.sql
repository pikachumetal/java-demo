drop table IF EXISTS questions;

create TABLE questions(
    id char(36) ,
    author_id CHAR(36) NOT NULL,
    title VARCHAR(128) NOT NULL,
    mesage VARCHAR(512) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT (UTC_TIMESTAMP),
    updated_at TIMESTAMP NOT NULL DEFAULT (UTC_TIMESTAMP),
    deleted_at TIMESTAMP NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_authors_id FOREIGN KEY(author_id) REFERENCES authors(id)
);
