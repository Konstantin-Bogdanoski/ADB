-- 3. Find all the clients, who ordered at least 15 pizzas which contain
-- the ingredient Sausage and the delivery was made by employees which id starts with 614.

-- The original query can be executed without the Indices (just drop the indices) and is in the file
-------------------------------------------------------------------------------------------------
-- IDs of pizzas containing ingredient Sausage

-- Before optimization: Total Cost: 2.68
-- After optimization:  Total Cost: 2.68
-- Performance gain: 0%

-- Description:
-- No possible performance gain, due to small dataset and a filter for only one entity

-- Original query
DROP VIEW IF EXISTS sausage_ids CASCADE;
CREATE VIEW sausage_ids AS
SELECT DISTINCT pizza_id
FROM pizza_ingredient
         JOIN ingredient i ON pizza_ingredient.ingredient_id = i.id
WHERE i.name = 'sausage';

-- Restructure query + Indices
DROP INDEX IF EXISTS ingredient_id_index;
DROP INDEX IF EXISTS pizza_ingredient_index;
CREATE INDEX IF NOT EXISTS ingredient_id_index ON ingredient USING btree(id);
CREATE INDEX IF NOT EXISTS pizza_ingredient_index ON pizza_ingredient USING btree(pizza_id);

DROP MATERIALIZED VIEW IF EXISTS sausage_ids CASCADE;
CREATE MATERIALIZED VIEW sausage_ids AS
SELECT DISTINCT pizza_id
FROM pizza_ingredient
WHERE ingredient_id IN (
    SELECT id
    FROM ingredient
    WHERE name = 'sausage'
    );

-------------------------------------------------------------------------------------------------
-- IDs of orders with deliverer_id > 6140 & pizza.contains('sausage')

-- Before optimization: Total Cost: 1354.4
-- After optimization:  Total Cost: 1352.73
-- Performance gain: 0.1%

-- Description:
-- Due to great filtering and Indexing, we updated the performance for only 0.1%

-- Original query
DROP VIEW IF EXISTS orders CASCADE;
CREATE VIEW orders AS
SELECT id as order_id, customer_id, deliverer_id
FROM pizza_order
         JOIN sausage_ids si ON pizza_order.pizza_id = si.pizza_id
WHERE deliverer_id >= 6140;

-- Restructured query + Indices
DROP INDEX IF EXISTS sausage_ids_index;
DROP INDEX IF EXISTS pizza_order_index;
CREATE INDEX IF NOT EXISTS sausage_ids_index ON sausage_ids USING btree(pizza_id);
CREATE INDEX IF NOT EXISTS pizza_order_index ON pizza_order USING btree(pizza_id);

DROP MATERIALIZED VIEW IF EXISTS orders CASCADE;
CREATE MATERIALIZED VIEW orders AS
SELECT id as order_id, customer_id, deliverer_id
FROM pizza_order
WHERE pizza_id IN (
    SELECT pizza_id
    FROM sausage_ids
    ) AND deliverer_id >= 6140;
-------------------------------------------------------------------------------------------------
-- Sum of orders with customers and deliverers and pizzas.contain('sausage')
-- Before optimization: Total Cost: 2911.50
-- After optimization:  Total Cost: 539.28
-- Performance gain: 81%

-- Description:
-- The index on ids don't do too much, but avoiding the JOIN does

-- Original query
DROP VIEW IF EXISTS sum_orders CASCADE;
CREATE VIEW sum_orders AS
SELECT c.customer_id, count(po) as num_orders
FROM orders po
         JOIN customer c ON po.customer_id = c.customer_id
GROUP BY c.customer_id;

-- Restructured query + Indices
DROP INDEX IF EXISTS orders_index;
DROP INDEX IF EXISTS pizza_order_pizza_id_index;
DROP INDEX IF EXISTS customer_id_index;
CREATE INDEX IF NOT EXISTS orders_index ON orders USING btree(customer_id);
CREATE INDEX IF NOT EXISTS pizza_order_pizza_id_index ON pizza_order USING btree(pizza_id);
CREATE INDEX IF NOT EXISTS customer_id_index ON customer USING btree(customer_id);

DROP MATERIALIZED VIEW IF EXISTS sum_orders CASCADE;
CREATE MATERIALIZED VIEW sum_orders AS
SELECT customer_id, count(po) as num_orders
FROM orders po
WHERE customer_id IN (
    SELECT customer_id
    FROM customer
    )
GROUP BY customer_id;

-------------------------------------------------------------------------------------------------
-- Final result
-- Customers with num_orders > 15

-- Before optimization: Total Cost: 104.24
-- After optimization:  Total Cost: 16.6
-- Performance gain: 84%

-- Description:
-- Using the Indices for the summary of prices and addresses helps the query a lot

-- Original query
SELECT *
FROM customer
         natural join sum_orders
WHERE num_orders > 15;

-- Restructured query + Indices
DROP INDEX IF EXISTS customer_address_index;
DROP INDEX IF EXISTS sum_orders_index;
CREATE INDEX IF NOT EXISTS customer_address_index ON customer USING btree(address);
CREATE INDEX IF NOT EXISTS sum_orders_index ON sum_orders USING btree(num_orders);

SELECT *
FROM customer
         natural join sum_orders
WHERE num_orders > 15;