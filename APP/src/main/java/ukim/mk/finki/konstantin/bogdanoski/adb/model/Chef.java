package ukim.mk.finki.konstantin.bogdanoski.adb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "chef")
public class Chef extends BaseEntity {
    @OneToMany(mappedBy = "chef")
    @JsonIgnore
    List<PizzaChef> pizzaChefs;
}
