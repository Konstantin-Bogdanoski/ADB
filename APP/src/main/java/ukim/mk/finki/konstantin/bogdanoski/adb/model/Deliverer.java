package ukim.mk.finki.konstantin.bogdanoski.adb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
public class Deliverer extends Employee {
    @JsonIgnore
    @OneToMany(mappedBy = "deliverer")
    private List<PizzaOrder> orderList;
}
