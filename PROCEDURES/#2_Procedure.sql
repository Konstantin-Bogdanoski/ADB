-- 2. Receive a monthly report for a specific deliverer - how many pizzas has he delivered

CREATE PROCEDURE report(BIGINT)
    LANGUAGE plpgsql
AS
$$
BEGIN
    -- Check if the ID is valid and is in fact a deliverer
    IF $1 IN (SELECT deliverer_id FROM deliverer)
    THEN
        -- Extract orders from last month
        CREATE VIEW last_month_orders AS
        SELECT *
        FROM pizza_order
        WHERE (
                          date_created >= date_trunc('month', CURRENT_DATE) - INTERVAL '1 month' AND
                          date_created < date_trunc('month', CURRENT_TIMESTAMP)
                  );

        -- Count the total orders he delivered from last month
        SELECT SUM(id)
        FROM last_month_orders
        WHERE deliverer_id = $1;
    END IF;
END;
$$;

CALL report(1610)