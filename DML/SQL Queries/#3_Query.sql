-- 3. Find all the clients, who ordered at least 15 pizzas which contain
-- the ingredient Sausage and the delivery was made by employees which id starts with 614.

-- IDs of pizzas containing ingredient Sausage
DROP VIEW IF EXISTS sausage_ids CASCADE;
CREATE VIEW sausage_ids AS
SELECT DISTINCT pizza_id
FROM pizza_ingredient
         JOIN ingredient i ON pizza_ingredient.ingredient_id = i.id
WHERE i.name = 'sausage';

-- IDs of orders with deliverer_id > 6140 & pizza.contains('sausage')
DROP VIEW IF EXISTS orders CASCADE;
CREATE VIEW orders AS
SELECT id as order_id, customer_id, deliverer_id
FROM pizza_order
         JOIN sausage_ids si ON pizza_order.pizza_id = si.pizza_id
WHERE deliverer_id >= 6140;

-- Sum of orders with customers and deliverers and pizzas.contain('sausage')
DROP VIEW IF EXISTS sum_orders CASCADE;
CREATE VIEW sum_orders AS
SELECT c.customer_id, count(po) as num_orders
FROM orders po
         JOIN customer c ON po.customer_id = c.customer_id
GROUP BY c.customer_id;

-- Final result
-- Customers with num_orders > 15
SELECT *
FROM customer
         natural join sum_orders
WHERE num_orders > 15;