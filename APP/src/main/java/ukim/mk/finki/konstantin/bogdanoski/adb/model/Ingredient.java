package ukim.mk.finki.konstantin.bogdanoski.adb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ingredient")
public class Ingredient extends BaseEntity implements Comparable<Ingredient> {
    private String name;
    private boolean spicy;
    private boolean veggie;
    @OneToMany(mappedBy = "ingredient")
    @JsonIgnore
    private List<PizzaIngredient> pizzaIngredients;

    public Ingredient(String name, boolean spicy, boolean veggie) {
        this.name = name;
        this.spicy = spicy;
        this.veggie = veggie;
    }

    @Override
    public int compareTo(Ingredient ingredient) {
        return this.name.compareTo(ingredient.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return spicy == that.spicy &&
                veggie == that.veggie &&
                Objects.equals(name, that.name) &&
                Objects.equals(pizzaIngredients, that.pizzaIngredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, spicy, veggie, pizzaIngredients);
    }
}
