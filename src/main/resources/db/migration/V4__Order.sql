CREATE TABLE orders(
    id bigint unsigned auto_increment,
    user_id bigint unsigned,
    date TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    CONSTRAINT FOREIGN KEY fk_orders (user_id) REFERENCES user(id),
    CONSTRAINT pk_orders PRIMARY KEY (id)
)

ENGINE=InnoDB;

insert into orders(id, user_id) values
    (1,1),
    (2,2),
    (3,1);