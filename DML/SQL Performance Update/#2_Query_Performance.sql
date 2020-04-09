-- 2. Extract all the pizzas, which are not spicy (_if the pizza has a spicy ingredient, it is spicy_),
-- while the total weight of the ingredients doesn't pass 460 grams.
-- After you've queried all the pizzas,
-- find the total number of orders which have these pizzas.

-- The original query can be executed without the Indices (just drop the indices) and is in the file
-------------------------------------------------------------------------------------------------
-- IDs of spicy pizzas

-- Before optimization: Total Cost: 53.27
-- After optimization:  Total Cost: 2.9
-- Performance gain: 94%

-- Description:
-- Bypassing the JOIN and using indices we get
-- a 94% reduction in Total Cost

-- Original query
DROP VIEW IF EXISTS spicy_ids CASCADE;
CREATE VIEW spicy_ids AS
SELECT DISTINCT pizza_id
FROM pizza_ingredient
         JOIN ingredient i ON pizza_ingredient.ingredient_id = i.id
WHERE i.spicy IS TRUE;

-- Restructure query + Indices
DROP INDEX IF EXISTS ingredient_id_index;
DROP INDEX IF EXISTS pizza_ingr_ingredient_id_index;
CREATE INDEX IF NOT EXISTS ingredient_id_index ON ingredient USING btree (id);
CREATE INDEX IF NOT EXISTS pizza_ingr_ingredient_id_index ON pizza_ingredient USING btree (ingredient_id);

DROP MATERIALIZED VIEW IF EXISTS spicy_ids CASCADE;
CREATE MATERIALIZED VIEW spicy_ids AS
SELECT DISTINCT pizza_id
FROM pizza_ingredient
WHERE ingredient_id IN (
    SELECT id
    FROM ingredient
    WHERE spicy IS TRUE
);
-------------------------------------------------------------------------------------------------
-- IDs of pizzas with > 460grams

-- Before optimization: Total Cost: 1.9
-- After optimization:  Total Cost: 1.9
-- Performance gain: 0%

-- Description:
-- We're not doing anything complicated and we can't speed up the SUM from this point
-- We can speed up the SUM by adding a TRIGGER so when each time a new ingredient is added,
-- we have another attribute which contains the total amount in grams

-- Original query
DROP MATERIALIZED VIEW IF EXISTS grams_ids CASCADE;
CREATE MATERIALIZED VIEW grams_ids AS
SELECT pizza_id, sum
FROM (
         SELECT pizza_id, SUM(pizza_ingredient.amount) AS sum
         FROM pizza_ingredient
         GROUP BY pizza_id
     ) as pis
WHERE sum > 460;

-- Restructure query + Indices
DROP INDEX IF EXISTS pizza_id_index;
CREATE INDEX IF NOT EXISTS pizza_id_index ON pizza_ingredient USING btree (pizza_id);

DROP MATERIALIZED VIEW IF EXISTS grams_ids CASCADE;
CREATE MATERIALIZED VIEW grams_ids AS
SELECT pizza_id, sum
FROM (
         SELECT pizza_id, SUM(pizza_ingredient.amount) AS sum
         FROM pizza_ingredient
         GROUP BY pizza_id
     ) as pis
WHERE sum > 460;

-------------------------------------------------------------------------------------------------
-- IDs of nonspicy pizzas

-- Before optimization: Total Cost: 4.56
-- After optimization:  Total Cost: 4.2
-- Performance gain: 7%

-- Description:
-- Again nothing complicated, we just reformatted the query
-- and got a slight performance gain

-- Original query
DROP VIEW IF EXISTS nonspicy_ids CASCADE;
CREATE VIEW nonspicy_ids AS
SELECT id as pizza_id
FROM pizza
    EXCEPT
SELECT pizza_id
FROM spicy_ids;

-- Restructured query + Indices
DROP INDEX IF EXISTS pizza_id_index;
DROP INDEX IF EXISTS spicy_pizza_id_index;
CREATE INDEX IF NOT EXISTS pizza_id_index ON pizza USING btree (id);
CREATE INDEX IF NOT EXISTS spicy_pizza_id_index ON spicy_ids USING btree (pizza_id);

SELECT id as pizza_id
FROM pizza
WHERE id NOT IN (
    SELECT pizza_id as id
    FROM spicy_ids
);
-------------------------------------------------------------------------------------------------
-- IDs of needed Pizzas (non spicy pizzas with ingredient amount < 460)

-- Before optimization: Total Cost: 134.48
-- After optimization:  Total Cost: 74.81
-- Performance gain: 44%

-- Description:
-- From the practice we can clearly see that in general
-- avoiding JOINs, yields better performance

-- Original query
DROP VIEW IF EXISTS pizzas CASCADE;
CREATE VIEW pizzas AS
SELECT id as pizza_id
FROM pizza
    EXCEPT
SELECT pizza_id
FROM grams_ids
    EXCEPT
SELECT pizza_id
FROM spicy_ids;

-- Restructured query + Indices
DROP INDEX IF EXISTS grams_index;
DROP MATERIALIZED VIEW IF EXISTS pizzas CASCADE;

CREATE MATERIALIZED VIEW pizzas AS
SELECT id as pizza_id
FROM pizza
WHERE id NOT IN (
    SELECT pizza_id
    FROM grams_ids
    WHERE pizza_id NOT IN (
        SELECT pizza_id
        FROM spicy_ids));

-------------------------------------------------------------------------------------------------
-- Orders with pizzas
-- Final request - Result is ~ 30% of all orders are cheap pizzas

-- Before optimization: Total Cost: 26962.03
-- After optimization:  Total Cost: 2311.07
-- Performance gain: 91%

-- Description:
-- Same result as before, avoiding the JOINs and adding indices yields better performance

-- Original query
DROP VIEW IF EXISTS orders CASCADE;
CREATE VIEW orders AS
SELECT COUNT(pizza_order.id)
FROM pizza_order
         join pizzas p on pizza_order.pizza_id = p.pizza_id;

-- Restructured query + Indices
DROP INDEX IF EXISTS pizza_order_pizza_id_index;
DROP INDEX IF EXISTS pizza_order_index;
DROP INDEX IF EXISTS pizzas_index;
CREATE INDEX IF NOT EXISTS pizza_order_pizza_id_index ON pizza_order USING btree (pizza_id);
CREATE INDEX IF NOT EXISTS pizza_order_index ON pizza_order USING btree(id);
CREATE INDEX IF NOT EXISTS pizzas_index ON pizzas USING btree(pizza_id);

DROP VIEW IF EXISTS orders CASCADE;
CREATE VIEW orders AS
SELECT COUNT(pizza_order.id)
FROM pizza_order
WHERE pizza_id NOT IN (
    SELECT pizza_id
    FROM pizzas
    );