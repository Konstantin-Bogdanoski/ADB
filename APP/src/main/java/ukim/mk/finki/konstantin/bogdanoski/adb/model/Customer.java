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
@Table(name = "customer")
public class Customer extends Person implements Comparable<Customer> {
    private String address;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    List<PizzaOrder> orderList;

    @Override
    public int compareTo(Customer customer) {
        return this.getEmail().compareTo(customer.getEmail());
    }
}
