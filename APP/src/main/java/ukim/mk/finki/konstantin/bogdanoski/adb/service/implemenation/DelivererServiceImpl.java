package ukim.mk.finki.konstantin.bogdanoski.adb.service.implemenation;

import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Deliverer;
import ukim.mk.finki.konstantin.bogdanoski.adb.repository.DelivererRepository;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.DelivererService;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public class DelivererServiceImpl extends BaseEntityCrudServiceImpl<Deliverer, DelivererRepository> implements DelivererService {
    private final DelivererRepository repository;

    public DelivererServiceImpl(DelivererRepository repository) {
        this.repository = repository;
    }

    @Override
    protected DelivererRepository getRepository() {
        return repository;
    }
}
