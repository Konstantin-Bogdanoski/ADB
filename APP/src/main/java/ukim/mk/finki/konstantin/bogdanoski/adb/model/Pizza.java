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
@Table(name = "pizza")
public class Pizza extends BaseEntity implements Comparable<Pizza> {
    private String name;
    private String description;
    private boolean veggie;
    @JsonIgnore
    @OneToMany(mappedBy = "pizza")
    private List<PizzaIngredient> pizzaIngredients;


    @JsonIgnore
    @OneToMany(mappedBy = "pizza")
    private List<PizzaChef> pizzaChefs;

    public Pizza(String name, String description, boolean veggie) {
        this.name = name;
        this.description = description;
        this.veggie = veggie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        return veggie == pizza.veggie &&
                Objects.equals(name, pizza.name) &&
                Objects.equals(description, pizza.description) &&
                Objects.equals(pizzaIngredients, pizza.pizzaIngredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, veggie, pizzaIngredients);
    }

    @Override
    public int compareTo(Pizza pizza) {
        return this.getName().compareTo(pizza.getName());
    }
}
