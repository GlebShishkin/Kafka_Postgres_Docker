--??? create schema if not exists storage;
CREATE TYPE status_new AS ENUM ('IN_PROGRESS','CANCELED', 'APPROVED');
CREATE CAST (varchar AS status_new) WITH INOUT AS IMPLICIT;

create table if not exists orders
(
    id serial primary key,
    product_name varchar(255) not null,
    bar_code varchar(255) not null,
    quantity INT not null,
    price numeric(19, 2),
    amount numeric(19, 2),
    order_date timestamp not null,
    status status_new not null
);

