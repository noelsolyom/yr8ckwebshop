CREATE TABLE order_item(
    id bigint unsigned auto_increment,
    product_id bigint unsigned not null,
    orders_id bigint unsigned not null,
    FOREIGN KEY (orders_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT pk_order_item PRIMARY KEY (id)
)

ENGINE=InnoDB;

insert into order_item (id, orders_id, product_id) values
    (1,1,1),
    (2,1,2),
    (3,1,3),
    (4,2,1),
    (5,3,4);