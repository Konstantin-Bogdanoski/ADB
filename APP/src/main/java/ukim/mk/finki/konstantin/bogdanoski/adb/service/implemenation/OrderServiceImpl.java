package ukim.mk.finki.konstantin.bogdanoski.adb.service.implemenation;

import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.PizzaOrder;
import ukim.mk.finki.konstantin.bogdanoski.adb.repository.OrderRepository;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.OrderService;

import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public class OrderServiceImpl extends BaseEntityCrudServiceImpl<PizzaOrder, OrderRepository> implements OrderService {

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    protected OrderRepository getRepository() {
        return repository;
    }

    @Override
    public List<PizzaOrder> findByPizza(Pizza pizza) {
        return repository.findAllByPizza(pizza);
    }

    @Override
    public void deleteByPizza(Pizza pizza) {
        repository.deleteAllByPizza(pizza);
    }

    @Override
    public List<PizzaOrder> findByCustomerId(Long id) {
        return getRepository().findByCustomerId(id);
    }

    @Override
    public List<PizzaOrder> findOrders() {
        return getRepository().findOrders();
    }
}
