package ukim.mk.finki.konstantin.bogdanoski.adb.service;

import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Employee;

import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public interface EmployeeService extends BaseEntityCrudService<Employee> {
    List<Employee> findTop100();
}
