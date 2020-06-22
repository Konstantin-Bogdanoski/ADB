package ukim.mk.finki.konstantin.bogdanoski.adb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PizzaChefCompositeKey implements Serializable {
    @Column(name = "pizza_id")
    Long pizzaId;

    @Column(name = "chef_id")
    Long chefId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PizzaChefCompositeKey that = (PizzaChefCompositeKey) o;
        return Objects.equals(pizzaId, that.pizzaId) &&
                Objects.equals(chefId, that.chefId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pizzaId, chefId);
    }
}
