package ukim.mk.finki.konstantin.bogdanoski.adb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pizza_chef")
public class PizzaChef {
    @EmbeddedId
    PizzaChefCompositeKey compositeKey;

    @ManyToOne
    @MapsId("pizza_id")
    @JoinColumn(name = "pizza_id")
    Pizza pizza;

    @ManyToOne
    @MapsId("chef_id")
    @JoinColumn(name = "chef_id")
    Chef chef;
}
