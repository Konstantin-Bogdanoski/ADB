package ukim.mk.finki.konstantin.bogdanoski.adb.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ukim.mk.finki.konstantin.bogdanoski.adb.exception.IngredientNotFoundException;
import ukim.mk.finki.konstantin.bogdanoski.adb.exception.OrderNotExistsException;
import ukim.mk.finki.konstantin.bogdanoski.adb.exception.PersonNotFoundException;
import ukim.mk.finki.konstantin.bogdanoski.adb.exception.PizzaNotFoundException;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.*;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PersonService personService;
    private final PizzaService pizzaService;
    private final IngredientService ingredientService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final OrderService orderService;
    private final ChefService chefService;
    private final DelivererService delivererService;
    private final Logger logger = Logger.getLogger(AdminController.class.getSimpleName());

    public AdminController(PersonService personService, PizzaService pizzaService, IngredientService ingredientService, CustomerService customerService, EmployeeService employeeService, OrderService orderService, ChefService chefService, DelivererService delivererService) {
        this.personService = personService;
        this.pizzaService = pizzaService;
        this.ingredientService = ingredientService;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.orderService = orderService;
        this.chefService = chefService;
        this.delivererService = delivererService;
    }

    @GetMapping
    public ModelAndView getAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for ADMIN PAGE CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("orders", orderService.findOrders());
        modelAndView.setViewName("master-admin");
        modelAndView.addObject("bodyContent", "body-orders");
        return modelAndView;
    }

    @GetMapping("/ingredients")
    public ModelAndView getIngredients(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for GET INGREDIENTS CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        ModelAndView modelAndView = new ModelAndView("master-admin");
        modelAndView.addObject("ingredients", ingredientService.findAll());
        modelAndView.addObject("bodyContent", "body-ingredients");
        return modelAndView;
    }

    @GetMapping("/addIngredient")
    public ModelAndView addIngredient(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for ADD INGREDIENT CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        ModelAndView modelAndView = new ModelAndView("master-admin");
        modelAndView.addObject("ingredient", new Ingredient());
        modelAndView.addObject("bodyContent", "body-add-ingredient");
        return modelAndView;
    }

    @GetMapping("/editIngredient/{id}")
    public ModelAndView editIngredient(HttpServletRequest req, HttpServletResponse resp, @PathVariable(name = "id") Long ingredientID) throws IOException {
        logger.info("\u001B[33mGET method for EDIT INGREDIENT CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        Ingredient oldIngredient;
        oldIngredient = ingredientService.findOne(ingredientID).orElseThrow(IngredientNotFoundException::new);
        ModelAndView modelAndView = new ModelAndView("master-admin");
        modelAndView.addObject("ingredient", oldIngredient);
        modelAndView.addObject("bodyContent", "body-edit-ingredient");
        return modelAndView;
    }

    @GetMapping("/pizzas")
    public ModelAndView getPizzas(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for GET PIZZAS CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        ModelAndView modelAndView = new ModelAndView("master-admin");
        modelAndView.addObject("pizzas", pizzaService.findAll().stream().sorted().collect(Collectors.toList()));
        modelAndView.addObject("bodyContent", "body-pizzas");
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView getUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for GET USERS CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        ModelAndView modelAndView = new ModelAndView("master-admin");
        modelAndView.addObject("users", customerService.findTop100());
        modelAndView.addObject("bodyContent", "body-users");
        return modelAndView;
    }

    @GetMapping("/employees")
    public ModelAndView getEmpployees(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for GET EMPLOYEES CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        ModelAndView modelAndView = new ModelAndView("master-admin");
        modelAndView.addObject("employees", employeeService.findTop100());
        modelAndView.addObject("bodyContent", "body-employees");
        return modelAndView;
    }

    @GetMapping("/chefs")
    public ModelAndView getChefs(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for GET CHEFS CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        ModelAndView modelAndView = new ModelAndView("master-admin");
        modelAndView.addObject("chefs", chefService.findTop100());
        modelAndView.addObject("bodyContent", "body-chefs");
        return modelAndView;
    }

    @GetMapping("/deliverers")
    public ModelAndView getDeliverers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for GET DELIVERERS CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        ModelAndView modelAndView = new ModelAndView("master-admin");
        modelAndView.addObject("deliverers", delivererService.findTop100());
        modelAndView.addObject("bodyContent", "body-deliverers");
        return modelAndView;
    }

    @GetMapping("/editPizza/{id}")
    public ModelAndView editPizza(@PathVariable(name = "id") Long pizzaID, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for EDIT PIZZA CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        ModelAndView modelAndView = new ModelAndView("master-admin");
        Pizza oldPizza;
        oldPizza = pizzaService.findOne(pizzaID).orElseThrow(PizzaNotFoundException::new);
        modelAndView.addObject("pizza", oldPizza);
        modelAndView.addObject("ingredientList", ingredientService.findAll().stream().sorted().collect(Collectors.toList()));
        modelAndView.addObject("newIngredients", new ArrayList<Long>());
        modelAndView.addObject("oldIngredients", oldPizza.getPizzaIngredients().stream().map(pizzaIngredient -> pizzaIngredient.getIngredient().getId()).collect(Collectors.toList()));
        modelAndView.addObject("bodyContent", "body-edit-pizza");
        return modelAndView;
    }

    @GetMapping("/editUser/{id}")
    public ModelAndView editUser(@PathVariable(name = "id") Long userID, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for EDIT USER CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        Person oldUser;
        oldUser = personService.findOne(userID).orElseThrow(PersonNotFoundException::new);
        ModelAndView modelAndView = new ModelAndView("master-admin");
        modelAndView.addObject("user", oldUser);
        modelAndView.addObject("bodyContent", "body-edit-user");
        return modelAndView;
    }

    @GetMapping("/addPizza")
    public ModelAndView addPizza(@ModelAttribute Pizza pizza, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for ADD PIZZA CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        ModelAndView modelAndView = new ModelAndView("master-admin");
        modelAndView.addObject("pizza", new Pizza());
        modelAndView.addObject("ingredientList", ingredientService.findAll().stream().sorted().collect(Collectors.toList()));
        modelAndView.addObject("newIngredients", new ArrayList<Long>());
        modelAndView.addObject("bodyContent", "body-add-pizza");
        return modelAndView;
    }

    @DeleteMapping("/orders/{userid}/{orderid}")
    public ModelAndView deleteOrderOfUser(@PathVariable(name = "orderid") Long orderID, @PathVariable(name = "userid") Long userID, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for DELETE ORDER BY USER CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        PizzaOrder order = orderService.findOne(orderID).orElseThrow(OrderNotExistsException::new);
        orderService.delete(order);
        return new ModelAndView("redirect:/admin/orders/" + userID);
    }

    @DeleteMapping("/orders/{orderid}")
    public ModelAndView deleteOrder(@PathVariable(name = "orderid") Long orderID, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for DELETE ORDER CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        PizzaOrder order = orderService.findOne(orderID).orElseThrow(OrderNotExistsException::new);
        orderService.delete(order);
        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/orders/{id}")
    public ModelAndView getOrdersFromUser(@PathVariable(name = "id") Long userID, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method for GET ORDERS BY USER CALLED from Admin Controller\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        Customer customer = customerService.findOne(userID).orElseThrow(PersonNotFoundException::new);
        ModelAndView modelAndView = new ModelAndView("master-admin");
        List<PizzaOrder> ordersFromUser = orderService.findByCustomerId(customer.getId());
        modelAndView.addObject("orders", ordersFromUser);
        modelAndView.addObject("user", customer);
        modelAndView.addObject("bodyContent", "body-user-orders");
        return modelAndView;
    }
}
