package ukim.mk.finki.konstantin.bogdanoski.adb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pizza_ingredient")
public class PizzaIngredient {
    @EmbeddedId
    PizzaIngredientCompositeKey compositeKey;

    @ManyToOne
    @MapsId("pizza_id")
    @JoinColumn(name = "pizza_id")
    Pizza pizza;

    @ManyToOne
    @MapsId("ingredient_id")
    @JoinColumn(name = "ingredient_id")
    Ingredient ingredient;

    float amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PizzaIngredient that = (PizzaIngredient) o;
        return Float.compare(that.amount, amount) == 0 &&
                compositeKey.equals(that.compositeKey) &&
                pizza.equals(that.pizza) &&
                ingredient.equals(that.ingredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compositeKey, pizza, ingredient, amount);
    }
}
