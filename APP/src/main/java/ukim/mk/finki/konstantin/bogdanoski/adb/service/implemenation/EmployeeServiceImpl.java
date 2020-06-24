package ukim.mk.finki.konstantin.bogdanoski.adb.service.implemenation;

import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Employee;
import ukim.mk.finki.konstantin.bogdanoski.adb.repository.EmployeeRepository;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.EmployeeService;

import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public class EmployeeServiceImpl extends BaseEntityCrudServiceImpl<Employee, EmployeeRepository> implements EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    protected EmployeeRepository getRepository() {
        return repository;
    }

    @Override
    public List<Employee> findTop100() {
        return getRepository().findTop100();
    }
}
