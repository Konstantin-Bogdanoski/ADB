package ukim.mk.finki.konstantin.bogdanoski.adb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
@Table(name = "pizza_order")
public class PizzaOrder extends BaseEntity {
    @OneToOne
    @JoinColumn
    private Pizza pizza;

    private String size;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Deliverer deliverer;

    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PizzaOrder that = (PizzaOrder) o;
        return Objects.equals(pizza, that.pizza) &&
                Objects.equals(size, that.size) &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pizza, size, customer, address);
    }

    @PrePersist
    public void updateAddres() {
        this.address = customer.getAddress();
    }

    @PreUpdate
    public void updateAddressOnUpdate() {
        this.updateAddres();
    }
}
