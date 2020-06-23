package ukim.mk.finki.konstantin.bogdanoski.adb.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Customer;

import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Repository
public interface CustomerRepository extends JpaSpecificationRepository<Customer> {
    @Query(nativeQuery = true,
            value = "SELECT c.id as id, address, email, password, date_created, date_updated from customer c join person p on c.id = p.id join base_entity be on p.id = be.id limit 100")
    List<Customer> findTop100();
}
