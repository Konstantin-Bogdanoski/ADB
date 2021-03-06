package ukim.mk.finki.konstantin.bogdanoski.adb.service;

import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Person;

import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public interface PersonService extends BaseEntityCrudService<Person> {
    Optional<Person> findByEmail(String email);
}
