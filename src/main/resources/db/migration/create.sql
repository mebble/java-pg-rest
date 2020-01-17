CREATE TYPE availability AS ENUM ('present', 'discontinued');

CREATE TABLE item (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    quantity INT DEFAULT 0,
    state availability DEFAULT 'present'
);

INSERT INTO item (name, quantity) VALUES ('ABC', 10);
INSERT INTO item (name, quantity) VALUES ('DEF', 20);
INSERT INTO item (name, quantity) VALUES ('GHI', 30);
