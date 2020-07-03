package ukim.mk.finki.konstantin.bogdanoski.adb.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Chef;

import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Repository
public interface ChefRepository extends JpaSpecificationRepository<Chef> {
    @Query(nativeQuery = true,
            value = "SELECT c.id AS id, date_created, date_updated FROM chef c JOIN person p ON c.id=p.id JOIN base_entity be on c.id = be.id LIMIT 100")
    List<Chef> findTop100();
}
