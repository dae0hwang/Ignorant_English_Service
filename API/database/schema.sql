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