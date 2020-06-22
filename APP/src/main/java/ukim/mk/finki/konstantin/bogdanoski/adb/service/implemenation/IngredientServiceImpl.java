package ukim.mk.finki.konstantin.bogdanoski.adb.service.implemenation;

import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Ingredient;
import ukim.mk.finki.konstantin.bogdanoski.adb.repository.IngredientRepository;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.IngredientService;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public class IngredientServiceImpl extends BaseEntityCrudServiceImpl<Ingredient, IngredientRepository> implements IngredientService {
    private final IngredientRepository repository;

    public IngredientServiceImpl(IngredientRepository repository) {
        this.repository = repository;
    }

    @Override
    protected IngredientRepository getRepository() {
        return repository;
    }
}
