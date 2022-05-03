create table PRODUCTS(
product_id bigint primary key auto_increment,
product_name varchar(20) not null,
stock int not null default 0,
price bigint not null,
description varchar(100) default null
);

Create table ORDERS(
order_id bigint primary key auto_increment,
email varchar(30) not null,
address varchar(50) not null,
order_status varchar(20) not null,
created_at datetime(6) not null,
updated_at datetime(6) not null
);

Create table ORDER_ITEMS(
seq bigint primary key auto_increment,
order_id bigint not null,
product_id bigint not null,
price bigint not null,
quantity int not null,
index(order_id),
CONSTRAINT fk_order_items_to_order FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE,
CONSTRAINT fk_order_items_to_product FOREIGN KEY (product_id) REFERENCES products (product_id)
);
