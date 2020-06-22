package ukim.mk.finki.konstantin.bogdanoski.adb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
@Table(name = "customer")
public class Customer extends Person {
    private String address;

    @OneToMany(mappedBy = "customer")
    List<PizzaOrder> orderList;
}
