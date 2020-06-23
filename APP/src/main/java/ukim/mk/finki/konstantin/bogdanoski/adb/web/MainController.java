package ukim.mk.finki.konstantin.bogdanoski.adb.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Employee;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Person;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.PizzaOrder;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.*;

import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@RestController
@RequestMapping("/")
public class MainController {
    private final OrderService orderService;
    private final PersonService personService;
    private final EmployeeService employeeService;
    private final PizzaService pizzaService;

    public MainController(OrderService orderService,
                          PersonService personService,
                          EmployeeService employeeService, PizzaIngredientService pizzaIngredientService,
                          PizzaService pizzaService) {
        this.orderService = orderService;
        this.personService = personService;
        this.employeeService = employeeService;
        this.pizzaService = pizzaService;
    }

    @RequestMapping(value = "orders", produces = "application/json")
    public List<PizzaOrder> getOrders() {
        return orderService.findAll();
    }

    @RequestMapping(value = "persons", produces = "application/json")
    public List<Person> getPersons() {
        return personService.findAll();
    }

    @RequestMapping(value = "employees", produces = "application/json")
    public List<Employee> getEmployees() {
        return employeeService.findAll();
    }

    @RequestMapping(value = "pizzas", produces = "application/json")
    public List<Pizza> getPizzas() {
        return pizzaService.findAll();
    }
}
