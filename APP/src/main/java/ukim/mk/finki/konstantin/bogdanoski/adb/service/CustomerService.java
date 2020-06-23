package ukim.mk.finki.konstantin.bogdanoski.adb.service;

import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Customer;

import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public interface CustomerService extends BaseEntityCrudService<Customer> {
    List<Customer> findTop100();
}
