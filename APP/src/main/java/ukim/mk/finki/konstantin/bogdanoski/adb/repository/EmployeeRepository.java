package ukim.mk.finki.konstantin.bogdanoski.adb.repository;

import org.springframework.stereotype.Repository;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Employee;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Repository
public interface EmployeeRepository extends JpaSpecificationRepository<Employee> {
}
