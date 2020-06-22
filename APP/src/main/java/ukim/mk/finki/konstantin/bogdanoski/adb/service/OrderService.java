package ukim.mk.finki.konstantin.bogdanoski.adb.service;

import ukim.mk.finki.konstantin.bogdanoski.adb.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.PizzaOrder;

import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public interface OrderService extends BaseEntityCrudService<PizzaOrder> {
    List<PizzaOrder> findByPizza(Pizza pizza);

    void deleteByPizza(Pizza pizza);
}
