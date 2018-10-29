CREATE TABLE user(
    id bigint unsigned NOT NULL auto_increment,
    user_name VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(255) default 'USER',
    family_name VARCHAR(255),
    given_name VARCHAR(255),
    deleted tinyint(1) unsigned NOT NULL DEFAULT 0,
    CONSTRAINT pk_user PRIMARY KEY (id)
)

ENGINE=InnoDB;

truncate TABLE user;

insert into user (id, user_name, password, role, family_name, given_name) values
(1,"admin", "$2a$10$7C3KaLAXgXwfM8NboaXj8OryT7a2dbllcvVog8kpwdZ2DSh2jdRxS", "ADMIN", "Doe", "John"),
(2,"user", "$2a$10$MOdYtjJPIympQN8484STP.LQGRDZHQTNb5/MXvdsWuz5ZVEpNY7Se", "USER", "Doe", "Jane");