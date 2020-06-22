package ukim.mk.finki.konstantin.bogdanoski.adb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Entity
@Getter
@Setter
@Table(name = "employee")
public class Employee extends Person {
    private Float pay;
}
