-- 1. Extract all orders, in which the designated address starts with 16,
-- while the delivered pizzas are Capricciosa and were made from the chef
-- with ID - 1614.

-- I solved the query using the divide-then-conquer method.

-- All orders with addresses which start with 16
DROP VIEW IF EXISTS addresses;
CREATE VIEW addresses AS
SELECT pizza_order.id as order_id, pizza_order.pizza_id, pizza_order.customer_id, c.address
FROM pizza_order
         JOIN customer c
              ON pizza_order.customer_id = c.customer_id
                  AND c.address LIKE 'customer_16%';

-- All orders with Capricciosa pizzas
DROP VIEW IF EXISTS capricciosa_orders;
CREATE VIEW capricciosa_orders AS
SELECT pizza_order.id as order_id, pizza_order.pizza_id, pizza_order.customer_id
FROM pizza_order
         JOIN pizza p
              ON pizza_order.pizza_id = p.id
                  AND p.name LIKE 'Capricciosa';

-- IDs of chefs above 1614 who created Capricciosas
DROP VIEW IF EXISTS capricciosa_chefs;
CREATE VIEW capricciosa_chefs AS
SELECT pizza.id AS pizza_id, pc.chef_id
FROM pizza
         JOIN pizza_chef pc
              ON pizza.id = pc.pizza_id
                  AND pc.chef_id >= 1614
                  AND pizza.name LIKE 'Capricciosa';

-- All orders with ChefID >= 1614 & Pizza = Capricciosa
DROP VIEW IF EXISTS orders;
CREATE VIEW orders AS
SELECT DISTINCT capricciosa_orders.order_id, chef_id
FROM capricciosa_orders
         JOIN capricciosa_chefs ON
    capricciosa_orders.pizza_id = capricciosa_chefs.pizza_id;

-- Final result
SELECT DISTINCT order_id
FROM orders
         NATURAL JOIN addresses;

-- Note: There are duplicate orders, because of the quantity of chefs, making the pizzas.
-- For example: 100 chefs, make Capricciosas, so it is impossible to filter them out, there could be a
-- constraint meaning OneToOne relationship between Chef & Pizza, or we need to log the work time of the chefs
-- so we can filter them out