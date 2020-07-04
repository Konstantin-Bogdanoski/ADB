package ukim.mk.finki.konstantin.bogdanoski.adb.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.PizzaOrder;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Repository
public interface OrderRepository extends JpaSpecificationRepository<PizzaOrder> {
    List<PizzaOrder> findAllByPizza(Pizza pizza);

    @Transactional
    @Modifying
    void deleteAllByPizza(Pizza pizza);

    @Query(nativeQuery = true,
            value = "SELECT p.id as id, address, size, deliverer_id, pz.name AS pizza, pizza_id, date_created, date_updated, p.customer_id as customer_id FROM pizza_order p JOIN pizza pz ON p.pizza_id = pz.id JOIN base_entity be ON p.id = be.id WHERE p.customer_id = :cust_id LIMIT 100")
    List<PizzaOrder> findByCustomerId(@Param("cust_id") Long id);

    @Query(nativeQuery = true, value = "SELECT po.id as id, address, size, date_created, date_updated, customer_id, deliverer_id, pizza_id FROM pizza_order po JOIN base_entity be ON po.id = be.id LIMIT 30")
    List<PizzaOrder> findOrders();

    @Query(nativeQuery = true, value = "SELECT bonus()")
    boolean checkBonus();

    @Query(nativeQuery = true, value = "SELECT report(:id)")
    Long delivererReport(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT COUNT(id) FROM pizza_order po WHERE po.deliverer_id = :delivererId")
    Long delivererOrders(@Param("delivererId") Long id);
}
