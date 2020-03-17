-- Table representing the BaseEntity
drop table if exists base_entity cascade;
create table base_entity
(
    id           bigint    not null unique,
    date_created timestamp not null,
    date_updated timestamp not null,
    primary key (id)
);

-- Table representing the Pizza entity
drop table if exists pizza cascade;
create table pizza
(
    name     varchar(50) not null,
    price    float4      not null,
    spicy    bool,
    veggie   bool,
    primary key (id),
    constraint price_notnegative check ( price >= 0 )
) inherits (base_entity);

-- Table representing the Ingredient entity
drop table if exists ingredient cascade;
create table ingredient
(
    name          varchar(50) not null,
    spicy         bool,
    veggie        bool,
    primary key (id)
) inherits (base_entity);

-- Table representing the connection between pizzas and ingredients
-- Which pizza has which ingredients with specific amount
drop table if exists pizza_ingredient cascade;
create table pizza_ingredient
(
    pizza_id      bigint not null,
    ingredient_id bigint not null,
    amount        int,
    constraint pizza_key foreign key (pizza_id)
        references pizza (id)
        on update cascade
        on delete cascade,
    constraint ingredient_key foreign key (ingredient_id)
        references ingredient (id)
        on update cascade
        on delete cascade,
    primary key (pizza_id, ingredient_id),
    constraint amount_notnegative check ( amount >= 0 )
);

-- Table represeting the Person entity
drop table if exists person cascade;
create table person
(
    email     varchar(255) not null,
    password  varchar(255) not null,
    primary key (id)
) inherits (base_entity);

-- Table representing the Employee entity, which inherits Person
drop table if exists employee cascade;
create table employee
(
    employee_id bigint not null,
    pay         float8 not null,
    primary key (employee_id),
    constraint p_key foreign key (employee_id)
        references person (id)
        on update cascade
        on delete cascade,
    constraint pay_notnegative check ( pay >= 0 )
);

-- Table representing the Chef entity, which inherits from Employee
drop table if exists chef cascade;
create table chef
(
    chef_id bigint not null,
    primary key (chef_id),
    constraint p_key foreign key (chef_id)
        references employee (employee_id)
        on update cascade
        on delete cascade
);

-- Table representing the Pizza_Chef entity
-- It shows which chef makes which pizzas
drop table if exists pizza_chef cascade;
create table pizza_chef
(
    pizza_id bigint not null,
    chef_id  bigint not null,
    primary key (pizza_id, chef_id),
    constraint pizza_key foreign key (pizza_id)
        references pizza (id)
        on update cascade,
    constraint chef_key foreign key (chef_id)
        references chef (chef_id)
        on update cascade
);

-- Table representing the Deliverer entity, which inherits from Employee
drop table if exists deliverer cascade;
create table deliverer
(
    deliverer_id bigint not null,
    primary key (deliverer_id),
    constraint p_key foreign key (deliverer_id)
        references employee (employee_id)
        on update cascade
        on delete cascade
);

-- Table representing the Customer entity, which inherits Person
drop table if exists customer cascade;
create table customer
(
    customer_id bigint       not null,
    address     varchar(255) not null,
    primary key (customer_id),
    constraint p_key foreign key (customer_id)
        references person (id)
        on update cascade
        on delete cascade
);

-- Table representing the Order entity
drop table if exists pizza_order cascade;
create table pizza_order
(
    pizza_id     bigint not null,
    deliverer_id bigint not null,
    customer_id  bigint not null,
    primary key (id),
    constraint pizza_key foreign key (pizza_id)
        references pizza (id)
        on update cascade,
    constraint deliverer_key foreign key (deliverer_id)
        references deliverer (deliverer_id)
        on update cascade,
    constraint customer_key foreign key (customer_id)
        references customer (customer_id)
        on update cascade
) inherits (base_entity);

-- Sequience for IDs
drop sequence if exists main_sequence;
create sequence main_sequence
    start 1
    increment 1;
