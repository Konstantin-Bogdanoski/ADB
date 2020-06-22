package ukim.mk.finki.konstantin.bogdanoski.adb.service;

import ukim.mk.finki.konstantin.bogdanoski.adb.model.Person;

import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public interface PersonService extends BaseEntityCrudService<Person> {
    Optional<Person> findByEmail(String email);
}
