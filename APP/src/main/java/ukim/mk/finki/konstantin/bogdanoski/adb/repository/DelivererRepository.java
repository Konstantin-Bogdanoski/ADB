package ukim.mk.finki.konstantin.bogdanoski.adb.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Deliverer;

import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Repository
public interface DelivererRepository extends JpaSpecificationRepository<Deliverer> {
    @Query(nativeQuery = true,
            value = "SELECT d.id AS id, pay, email, password, date_created, date_updated FROM deliverer d JOIN employee e on d.id = e.id JOIN person p on e.id = p.id JOIN base_entity be on p.id = be.id LIMIT 100")
    List<Deliverer> findTop100();
}
