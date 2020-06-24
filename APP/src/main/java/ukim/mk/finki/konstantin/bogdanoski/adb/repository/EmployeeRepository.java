package ukim.mk.finki.konstantin.bogdanoski.adb.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Employee;

import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Repository
public interface EmployeeRepository extends JpaSpecificationRepository<Employee> {
    @Query(nativeQuery = true,
            value = "SELECT e.id as id, pay, email, password, date_created, date_updated from employee e join person p on e.id = p.id join base_entity be on p.id = be.id limit 100")
    List<Employee> findTop100();
}
