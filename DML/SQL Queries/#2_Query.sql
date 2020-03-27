-- 2. Extract all the pizzas, which are not spicy (_if the pizza has a spicy ingredient, it is spicy_),
-- while the total weight of the ingredients doesn't pass 460 grams.
-- After you've queried all the pizzas,
-- find the total number of orders which have these pizzas.

-- IDs of spicy pizzas
DROP VIEW IF EXISTS spicy_ids CASCADE;
CREATE VIEW spicy_ids AS
SELECT DISTINCT pizza_id
FROM pizza_ingredient
         JOIN ingredient i ON pizza_ingredient.ingredient_id = i.id
WHERE i.spicy IS TRUE;

-- IDs of pizzas with > 460grams, we will use EXCEPT
DROP VIEW IF EXISTS grams_ids CASCADE;
CREATE VIEW grams_ids AS
SELECT pizza_id, sum
FROM (
         SELECT pizza_id, SUM(pizza_ingredient.amount) AS sum
         FROM pizza_ingredient
         GROUP BY pizza_id
     ) as pis
WHERE sum > 460;

-- IDs of nonspicy pizzas
DROP VIEW IF EXISTS nonspicy_ids CASCADE;
CREATE VIEW nonspicy_ids AS
SELECT id as pizza_id
FROM pizza
    EXCEPT
SELECT pizza_id
FROM spicy_ids;

-- IDs of needed Pizzas (non spicy pizzas with ingredient amount < 460)
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

-- Orders with pizzas
-- Final request - Result is ~ 30% of all orders are cheap pizzas
DROP VIEW IF EXISTS orders CASCADE;
CREATE VIEW orders AS
SELECT COUNT(pizza_order.id)
FROM pizza_order join pizzas p on pizza_order.pizza_id = p.pizza_id;

