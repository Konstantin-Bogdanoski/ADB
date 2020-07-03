package ukim.mk.finki.konstantin.bogdanoski.adb.service.implemenation;

import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Chef;
import ukim.mk.finki.konstantin.bogdanoski.adb.repository.ChefRepository;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.ChefService;

import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public class ChefServiceImpl extends BaseEntityCrudServiceImpl<Chef, ChefRepository> implements ChefService {
    private final ChefRepository repository;

    public ChefServiceImpl(ChefRepository repository) {
        this.repository = repository;
    }

    @Override
    protected ChefRepository getRepository() {
        return repository;
    }

    @Override
    public List<Chef> findTop100() {
        return getRepository().findTop100();
    }
}
