-- 1. Additional bonus of 5% for the employees if the profit from the current week is at least 10% greater than last week

CREATE OR REPLACE PROCEDURE bonus()
    LANGUAGE plpgsql
AS
$$
BEGIN
    -- Get profit from last week (price is stored in pizza)
    CREATE VIEW last_week_profit AS
    SELECT sum(price) AS profit
    FROM pizza
    WHERE id IN (
        SELECT pizza_id
        FROM pizza_order
        WHERE (
                          date_created >= date_trunc('week', CURRENT_TIMESTAMP - INTERVAL '2 week') AND
                          date_created < date_trunc('week', CURRENT_TIMESTAMP - INTERVAL '1 week')
                  )
    );

    -- Get profit from this week
    CREATE VIEW this_week_pizzas AS
    SELECT sum(price) AS profit
    FROM pizza
    WHERE id IN (
        SELECT pizza_id
        FROM pizza_order
        WHERE (
                          date_created >= date_trunc('week', CURRENT_TIMESTAMP - INTERVAL '1 week') AND
                          date_created < date_trunc('week', CURRENT_TIMESTAMP)
                  )
    );

    -- Update employees pay
    IF this_week_pizzas.profit > (last_week_profit.profit * 1.10)
    THEN
        UPDATE employee
        SET pay = pay * 1.05;
    END IF;

    COMMIT;
END;
$$