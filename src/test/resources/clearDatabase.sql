delete from order_item;
delete from orders;
delete from basket;
delete from user;
delete from product;

insert into user (id, user_name, password, role, family_name, given_name) values
(1,"admin", "$2a$10$7C3KaLAXgXwfM8NboaXj8OryT7a2dbllcvVog8kpwdZ2DSh2jdRxS", "ADMIN", "Doe", "John"),
(2,"user", "$2a$10$MOdYtjJPIympQN8484STP.LQGRDZHQTNb5/MXvdsWuz5ZVEpNY7Se", "USER", "Doe", "Jane");

insert into product(id, number, name, url, manufacturer, current_price) values
    (1,1, 'Galaxy S8', 'galaxy-s8', 'Samsung', 500),
    (2,2, 'Iphone 8', 'iphone-8', 'Apple', 400),
    (3,3, 'Y7 Prime (2018)', 'y7-prime-2018', 'Huawei', 300),
    (4,4, 'Galaxy S8 2', 'galaxy-s8-2', 'Samsung', 500),
    (5,5, 'Iphone 8 2', 'iphone-8-2-2', 'Apple', 400),
    (6,6, 'Y7 Prime (2018) 2', 'y7-prime-2018-2', 'Huawei', 300);

insert into basket(id, user_id, product_id) values
    (1,1,4),
    (2,1,5),
    (3,2,6);

insert into orders(id, user_id) values
    (1,1),
    (2,2),
    (3,1);

insert into order_item (id, orders_id, product_id) values
    (1,1,1),
    (2,1,2),
    (3,1,3),
    (4,2,1),
    (5,3,4);