package ukim.mk.finki.konstantin.bogdanoski.adb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.BaseEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public interface BaseEntityCrudService<T extends BaseEntity> {

    T save(T entity);

    List<T> save(Iterable<T> entities);

    T saveAndFlush(T entity);

    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    List<T> findAll(Sort sort);

    List<T> findAll(Iterable<Long> ids);

    List<T> findAll(Specification<T> spec);

    Page<T> findAll(Specification<T> spec, Pageable pageable);

    List<T> findAll(Specification<T> spec, Sort sort);

    long count();

    long count(Specification<T> spec);

    Optional<T> findOne(Long id);

    Optional<T> findOne(Specification<T> spec);

    boolean exists(Long id);

    void delete(Long id);

    void delete(T entity);

    void delete(Iterable<T> entities);

    void deleteAll();

}

