package ukim.mk.finki.konstantin.bogdanoski.adb.repository;

import org.springframework.stereotype.Repository;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Person;

import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Repository
public interface PersonRepository extends JpaSpecificationRepository<Person> {
    Optional<Person> findByEmail(String email);
}
