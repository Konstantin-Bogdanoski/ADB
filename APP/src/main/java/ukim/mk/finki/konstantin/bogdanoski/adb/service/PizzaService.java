package ukim.mk.finki.konstantin.bogdanoski.adb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Pizza;

import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public interface PizzaService extends BaseEntityCrudService<Pizza> {
    Pizza findByName(String name);

    Page<Pizza> findPaginated(Pageable pageable);

    List<Pizza> findByPizzaNameLike(String searchTerm);
}
