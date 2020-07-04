-- To complete the optional task, I have decided to use Partitioning as the theme of the task.
-- The goal is because there are a lot of orders in our DB (greater than 1,000,000), we can
-- partition the table based on the date_created attribute and separate the orders into different partitions
-- represented by the year in which they were created. As an example, I have created 3 partitions for the years
-- 2013, 2014 and 2015 respectively.

CREATE TABLE pizza_order
(
    address      varchar(255),
    size         varchar(255),
    date_created timestamp,
    date_updated timestamp,
    id           bigint not null
        constraint pizza_order_pkey
            primary key
        constraint fk6dgn2goqkdkmbmoxgmh0m7leg
            references base_entity,
    customer_id  bigint
        constraint fk5m09ybythg18t66kfao4vblr
            references customer,
    deliverer_id bigint
        constraint fk3cj8kpo8ercsugwwcep5kiajv
            references deliverer,
    pizza_id     bigint
        constraint fkqo4nnh4h7iula92gq0wlmx1w3
            references pizza
)
    PARTITION BY RANGE (date_created);

CREATE TABLE year_2013 PARTITION OF pizza_order
    FOR VALUES FROM ('2013-01-01 00:00:00') TO ('2014-01-01 00:00:00');

CREATE TABLE year_2014 PARTITION OF pizza_order
    FOR VALUES FROM ('2014-01-01 00:00:00') TO ('2015-01-01 00:00:00');

CREATE TABLE year_2015 PARTITION OF pizza_order
    FOR VALUES FROM ('2015-01-01 00:00:00') TO ('2016-01-01 00:00:00');


