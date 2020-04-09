-- 1. Extract all orders, in which the designated address starts with 16,
-- while the delivered pizzas are Capricciosa and were made from the chef
-- with ID - 1614.

-- I solved the query using the divide-then-conquer method.


-- The original query can be executed without the Indices (just drop the indices) and is in the file
-------------------------------------------------------------------------------------------------
-- All orders with addresses which start with 16

-- Before optimization: Total Cost: 2409.45
-- After optimization:  Total Cost: 211.84
-- Performance gain: 91%

-- Description:
-- Using indices and bypassing the JOIN
-- we get a 99% reduction in Total Cost

-- Original Query
DROP MATERIALIZED VIEW IF EXISTS addresses;
CREATE MATERIALIZED VIEW addresses AS
SELECT pizza_order.id as order_id, pizza_order.pizza_id, pizza_order.customer_id, c.address
FROM pizza_order
         JOIN customer c
              ON pizza_order.customer_id = c.customer_id
WHERE c.address LIKE 'customer_16%';

-- Restructured query + Indices
DROP INDEX IF EXISTS customer_id_index;
DROP INDEX IF EXISTS customer_address_index;
DROP INDEX IF EXISTS pizza_orders_customer_index;
CREATE INDEX IF NOT EXISTS customer_id_index ON customer USING btree (customer_id);
CREATE INDEX IF NOT EXISTS customer_address_index ON customer USING btree (address);
CREATE INDEX IF NOT EXISTS pizza_orders_customer_index ON pizza_order USING btree (customer_id);

DROP MATERIALIZED VIEW IF EXISTS addresses;
CREATE MATERIALIZED VIEW addresses AS
SELECT pizza_order.id as order_id, pizza_order.pizza_id, pizza_order.customer_id
FROM pizza_order
WHERE customer_id IN (
    SELECT customer_id
    FROM customer
    WHERE address LIKE 'customer_16%'
);
-------------------------------------------------------------------------------------------------
-- All orders with Capricciosa pizzas

-- Before optimization: Total Cost: 1395
-- After optimization:  Total Cost: 9.44
-- Performance gain: 99%

-- Description:
-- Using indices and bypassing the JOIN part
-- we get a Total Cost reduction of 99%

-- Original Query
DROP MATERIALIZED VIEW IF EXISTS capricciosa_orders;
CREATE MATERIALIZED VIEW capricciosa_orders AS
SELECT pizza_order.id as order_id, pizza_order.pizza_id, pizza_order.customer_id
FROM pizza_order
         JOIN pizza p
              ON pizza_order.pizza_id = p.id
WHERE p.name = 'Capricciosa';

-- Restructured query + Indices
DROP INDEX IF EXISTS pizza_order_pizza_id_index;
DROP INDEX IF EXISTS pizza_id_name_index;
CREATE INDEX IF NOT EXISTS pizza_order_pizza_id_index ON pizza_order USING btree (pizza_id, customer_id);
CREATE INDEX IF NOT EXISTS pizza_id_name_index ON pizza USING btree (id, name);

DROP MATERIALIZED VIEW IF EXISTS capricciosa_orders;
CREATE MATERIALIZED VIEW capricciosa_orders AS
SELECT pizza_order.id as order_id, pizza_order.pizza_id, pizza_order.customer_id
FROM pizza_order
WHERE id IN (
    SELECT id
    FROM pizza
    WHERE name = 'Capricciosa'
);
-------------------------------------------------------------------------------------------------
-- IDs of chefs above 1614 who created Capricciosas

-- Before optimization: Total Cost: 52.36
-- After optimization:  Total Cost: 52.36
-- Performance gain: 0%

-- Description:
-- No performance gain is noticed because it already uses indices where it can (chef_id)
-- while it cannot use indices for '=' operator.
-- Even if we extract the pizza.name = 'Capricciosa' from the JOIN part
-- we get even worse results with
-- Total Cost: 102.98, that's almost 2x the performance compared to before

DROP INDEX IF EXISTS pizza_id_index;
DROP INDEX IF EXISTS chef_id_index;
CREATE INDEX IF NOT EXISTS pizza_id_index ON pizza USING btree (id);
CREATE INDEX IF NOT EXISTS chef_id_index ON chef USING btree (chef_id);

-- Original Query
DROP MATERIALIZED VIEW IF EXISTS capricciosa_chefs;
CREATE MATERIALIZED VIEW capricciosa_chefs AS
SELECT pizza.id AS pizza_id, pc.chef_id
FROM pizza
         JOIN pizza_chef pc
              ON pizza.id = pc.pizza_id
                  AND pc.chef_id >= 1614
                  AND pizza.name = 'Capricciosa';
-------------------------------------------------------------------------------------------------
-- All orders with ChefID >= 1614 & Pizza = Capricciosa

-- Before optimization: 503509.13
-- After optimization: 419.89
-- Performance gain: 99%

-- Description:
-- If we only add indices, we get the same result in performance.
-- If we restructure the query and avoid the JOIN operation, we get
-- 99% better performance

-- Original Query
DROP MATERIALIZED VIEW IF EXISTS orders;
CREATE MATERIALIZED VIEW orders AS
SELECT DISTINCT capricciosa_orders.order_id, chef_id
FROM capricciosa_orders
         JOIN capricciosa_chefs ON
    capricciosa_orders.pizza_id = capricciosa_chefs.pizza_id;


-- Restructured query + Indices
DROP INDEX IF EXISTS orders_pizza_index;
DROP INDEX IF EXISTS chefs_pizza_index;
CREATE INDEX IF NOT EXISTS orders_pizza_index ON capricciosa_orders USING btree (pizza_id);
CREATE INDEX IF NOT EXISTS chefs_pizza_index ON capricciosa_chefs USING btree (pizza_id);

DROP MATERIALIZED VIEW IF EXISTS orders;
CREATE MATERIALIZED VIEW orders AS
SELECT DISTINCT capricciosa_orders.order_id
FROM capricciosa_orders
WHERE pizza_id IN (
    SELECT pizza_id
    FROM capricciosa_chefs
);

-------------------------------------------------------------------------------------------------
-- Final result

-- Before optimization: 99490.77
-- After optimization: 56977.77
-- Performance gain: 42%

-- Description:
-- If we use indices and avoid the join
-- we get a 42% reduction in the Total Cost of the query

-- Original query
SELECT DISTINCT orders.order_id
FROM orders
         JOIN addresses a on orders.order_id = a.order_id;

-- Restructured query + Indices
DROP INDEX IF EXISTS orders_id_index;
DROP INDEX IF EXISTS addresses_order_id_index;
CREATE INDEX IF NOT EXISTS orders_id_index ON orders USING btree (order_id);
CREATE INDEX IF NOT EXISTS addresses_order_id_index ON addresses USING btree (order_id);

SELECT orders.order_id
FROM orders
WHERE order_id IN (
    SELECT DISTINCT order_id
    FROM addresses
)

-- Note: There are duplicate orders, because of the quantity of chefs, making the pizzas.
-- For example: 100 chefs, make Capricciosas, so it is impossible to filter them out, there could be a
-- constraint meaning OneToOne relationship between Chef & Pizza, or we need to log the work time of the chefs
-- so we can filter them out


-- TL;DR
-- Due to my current knowledge of DB, I am unable to upgrade the performance any further