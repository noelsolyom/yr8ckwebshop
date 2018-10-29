CREATE TABLE product(
    id bigint unsigned NOT NULL auto_increment,
    number bigint unsigned NOT NULL UNIQUE,
    name varchar(255) NOT NULL,
    url varchar(255) NOT NULL UNIQUE,
    manufacturer varchar(255) NOT NULL,
    current_price bigint unsigned NOT NULL,
    deleted tinyint(1) unsigned NOT NULL DEFAULT 0,
    CONSTRAINT pk_product PRIMARY KEY (id)
)

ENGINE=InnoDB;

truncate TABLE product;

insert into product(id, number, name, url, manufacturer, current_price) values
    (1,1, 'Galaxy S8', 'galaxy-s8', 'Samsung', 500),
    (2,2, 'Iphone 8', 'iphone-8', 'Apple', 400),
    (3,3, 'Y7 Prime (2018)', 'y7-prime-2018', 'Huawei', 300),
    (4,4, 'Galaxy S8 2', 'galaxy-s8-2', 'Samsung', 500),
    (5,5, 'Iphone 8 2', 'iphone-8-2-2', 'Apple', 400),
    (6,6, 'Y7 Prime (2018) 2', 'y7-prime-2018-2', 'Huawei', 300);