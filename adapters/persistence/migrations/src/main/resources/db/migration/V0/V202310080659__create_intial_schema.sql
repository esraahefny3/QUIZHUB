
-- create roles table
CREATE TABLE IF NOT EXISTS roles (
                                     id SERIAL NOT NULL CONSTRAINT roles_pk PRIMARY KEY,
                                     name varchar (20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
    );

-- create users table
CREATE TABLE IF NOT EXISTS users (
                                     sid UUID NOT NULL CONSTRAINT users_pk PRIMARY KEY,
                                     username varchar (255) NOT NULL,
    email varchar (100) NOT NULL UNIQUE,
    password varchar (255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
    );

--Fill roles table with base roles
INSERT INTO roles(id, name,created_at,updated_at)
VALUES
    (1, 'ROLE_USER', now(), now());
INSERT INTO roles(id, name,created_at,updated_at)
VALUES
    (2, 'ROLE_MODERATOR', now(),now());
INSERT INTO roles(id, name,created_at,updated_at)
VALUES
    (3, 'ROLE_ADMIN', now(), now());

-- create user_roles table
CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id UUID REFERENCES users (sid),
    role_id bigint REFERENCES roles (id),
    CONSTRAINT pk_user_role primary key (user_id, role_id)
    );


-- Quiz Table
CREATE TABLE quizzes (
                         id SERIAL PRIMARY KEY,
                         external_id UUID NOT NULL ,
                         title VARCHAR(255) UNIQUE NOT NULL,
                         author_id UUID REFERENCES users(sid),
                         published BOOLEAN DEFAULT FALSE,
                         created_at TIMESTAMP NOT NULL,
                         updated_at TIMESTAMP NOT NULL
);


CREATE TABLE questions (
                           id SERIAL PRIMARY KEY,
                           external_id UUID NOT NULL,
                           quiz_id bigint,
                           question VARCHAR NOT NULL,
                           type VARCHAR(10) NOT NULL CHECK (type IN ('boolean', 'multiple')),
                           difficulty VARCHAR NOT NULL CHECK (difficulty IN ('easy', 'medium', 'hard')),
                           category VARCHAR NOT NULL,
                           correct_answer VARCHAR NOT NULL,
                           incorrect_answers VARCHAR[] NOT NULL,
                           created_at TIMESTAMP NOT NULL,
                           updated_at TIMESTAMP NOT NULL,
                           CONSTRAINT fk_quiz FOREIGN KEY (quiz_id) REFERENCES quizzes (id)
);

CREATE TABLE quiz_result (
                              id SERIAL PRIMARY KEY,
                              external_id UUID NOT NULL,
                              quiz_id BIGINT REFERENCES quizzes(id),
                              user_id UUID REFERENCES users(sid),
                              score NUMERIC NOT NULL,
                              created_at TIMESTAMPTZ NOT NULL,
                              updated_at TIMESTAMPTZ NOT NULL

);

CREATE TABLE question_answer (
                           id SERIAL PRIMARY KEY,
                           external_id UUID NOT NULL,
                           question_id BIGINT REFERENCES questions(id),
                           quiz_result_id BIGINT REFERENCES quiz_result(id),
                           selected_answer VARCHAR NOT NULL,
                           question_score INTEGER NOT NULL,
                           created_at TIMESTAMPTZ NOT NULL,
                           updated_at TIMESTAMPTZ NOT NULL
);

