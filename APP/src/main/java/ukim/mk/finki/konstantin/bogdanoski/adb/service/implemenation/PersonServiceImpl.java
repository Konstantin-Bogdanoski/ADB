package ukim.mk.finki.konstantin.bogdanoski.adb.service.implemenation;

import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Person;
import ukim.mk.finki.konstantin.bogdanoski.adb.repository.PersonRepository;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.PersonService;

import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public class PersonServiceImpl extends BaseEntityCrudServiceImpl<Person, PersonRepository> implements PersonService {

    private final PersonRepository repository;

    public PersonServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    protected PersonRepository getRepository() {
        return repository;
    }

    @Override
    public Optional<Person> findByEmail(String username) {
        return repository.findByEmail(username);
    }
}
