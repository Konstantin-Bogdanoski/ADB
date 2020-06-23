package ukim.mk.finki.konstantin.bogdanoski.adb.service.implemenation;

import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Customer;
import ukim.mk.finki.konstantin.bogdanoski.adb.repository.CustomerRepository;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.CustomerService;

import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public class CustomerServiceImpl extends BaseEntityCrudServiceImpl<Customer, CustomerRepository> implements CustomerService {
    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CustomerRepository getRepository() {
        return repository;
    }

    @Override
    public List<Customer> findTop100() {
        return getRepository().findTop100();
    }
}
