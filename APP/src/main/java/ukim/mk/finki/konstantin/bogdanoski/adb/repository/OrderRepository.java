package ukim.mk.finki.konstantin.bogdanoski.adb.repository;

import org.springframework.data.jpa.repository.Modifying;
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
    public List<PizzaOrder> findAllByPizza(Pizza pizza);

    @Transactional
    @Modifying
    public void deleteAllByPizza(Pizza pizza);
}
