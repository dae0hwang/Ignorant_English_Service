DROP TABLE IF EXISTS admin_sentence;

CREATE TABLE admin_sentence(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    korean VARCHAR(255) NOT NULL,
    English VARCHAR(255) NOT NULL,
    grammar VARCHAR(255) NOT NULL,
    situation VARCHAR(255) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP NOT NULL
);

DROP TABLE IF EXISTS users;

CREATE TABLE users(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    email_auth TINYINT(1) NOT NULL,
    roles VARCHAR(255) NOT NULL,
    provider VARCHAR(255)
);

DROP TABLE IF EXISTS email_auth;

CREATE TABLE email_auth(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    auth_token VARCHAR(255) NOT NULL,
    expired TINYINT(1) NOT NULL,
    expire_date  TIMESTAMP NOT NULL
);

DROP TABLE IF EXISTS admin_test_check;

CREATE TABLE admin_test_check(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    admin_sentence_id BIGINT NOT NULL,
    test_check VARCHAR(255) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP NOT NULL
);


DROP TABLE IF EXISTS sentence_group;

CREATE TABLE sentence_group(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    sentence_name VARCHAR(255) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP NOT NULL
);

DROP TABLE IF EXISTS user_setence;

CREATE TABLE user_sentence(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sentence_group_id BIGINT NOT NULL,
    korean VARCHAR(255) NOT NULL,
    english VARCHAR(255) NOT NULL
);


DROP TABLE IF EXISTS sentence_subscribe;

CREATE TABLE sentence_subscribe(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sentence_group_id BIGINT NOT NULL,
    subscribed_user_id BIGINT NOT NULL
);


DROP TABLE IF EXISTS alarm;

CREATE TABLE alarm(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    alarm_type VARCHAR(255) NOT NULL,
    alarm_message VARCHAR(255) NOT NULL,
    sentence_group_id BIGINT NOT NULL,
    subscribed_user_id BIGINT NOT NULL
);

