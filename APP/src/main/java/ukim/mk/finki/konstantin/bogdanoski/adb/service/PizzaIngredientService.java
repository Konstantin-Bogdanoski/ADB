package ukim.mk.finki.konstantin.bogdanoski.adb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.PizzaIngredient;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.PizzaIngredientCompositeKey;
import ukim.mk.finki.konstantin.bogdanoski.adb.repository.PizzaIngredientRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public interface PizzaIngredientService {

    PizzaIngredient save(PizzaIngredient entity);

    PizzaIngredientRepository getRepository();

    List<PizzaIngredient> save(Iterable<PizzaIngredient> entities);


    PizzaIngredient saveAndFlush(PizzaIngredient entity);


    List<PizzaIngredient> findAll();


    Page<PizzaIngredient> findAll(Pageable pageable);

    List<PizzaIngredient> findAll(Sort sort);

    long count();


    Optional<PizzaIngredient> findOne(PizzaIngredientCompositeKey id);

    boolean exists(PizzaIngredientCompositeKey id);

    void delete(PizzaIngredientCompositeKey id);

    void delete(PizzaIngredient entity);

    void delete(Iterable<PizzaIngredient> entities);

    void deleteAllByPizza(Pizza pizza);

    void deleteAll();
}
