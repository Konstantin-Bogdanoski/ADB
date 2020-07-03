package ukim.mk.finki.konstantin.bogdanoski.adb.service;

import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Deliverer;

import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public interface DelivererService extends BaseEntityCrudService<Deliverer> {
    List<Deliverer> findTop100();
}
