CREATE TABLE basket(
    id bigint unsigned NOT NULL auto_increment,
    user_id bigint unsigned NOT NULL,
    product_id bigint unsigned NOT NULL,
    FOREIGN KEY fk_user (user_id) REFERENCES user(id),
    FOREIGN KEY fk_produc (product_id) REFERENCES product(id),
    CONSTRAINT pk_basket PRIMARY KEY (id)
)

ENGINE=InnoDB;

insert into basket(id, user_id, product_id) values
    (1,1,4),
    (2,1,5),
    (3,2,6);