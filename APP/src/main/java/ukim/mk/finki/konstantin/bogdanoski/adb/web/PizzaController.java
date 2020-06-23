package ukim.mk.finki.konstantin.bogdanoski.adb.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ukim.mk.finki.konstantin.bogdanoski.adb.exception.IngredientNotFoundException;
import ukim.mk.finki.konstantin.bogdanoski.adb.exception.PizzaAlreadyExistsException;
import ukim.mk.finki.konstantin.bogdanoski.adb.exception.PizzaIngredientNotVeggieException;
import ukim.mk.finki.konstantin.bogdanoski.adb.exception.PizzaNotFoundException;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.PizzaIngredient;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.PizzaIngredientCompositeKey;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.IngredientService;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.OrderService;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.PizzaIngredientService;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.PizzaService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Controller
@RequestMapping(value = "/pizzas")
public class PizzaController {
    private final PizzaService pizzaService;
    private final PizzaIngredientService pizzaIngredientService;
    private final OrderService orderService;
    private final IngredientService ingredientService;
    private final Logger logger = Logger.getLogger(PizzaController.class.getSimpleName());

    public PizzaController(PizzaService pizzaService, PizzaIngredientService pizzaIngredientService, OrderService orderService, IngredientService ingredientService) {
        this.pizzaService = pizzaService;
        this.pizzaIngredientService = pizzaIngredientService;
        this.orderService = orderService;
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ModelAndView addPizza(@ModelAttribute(name = "pizza") Pizza newPizza, @RequestParam(name = "newIngredients") ArrayList<Long> newIngredients) {
        pizzaService.findAll().forEach(pizza1 -> {
            if (pizza1.getName().equals(newPizza.getName()))
                throw new PizzaAlreadyExistsException();
        });
        pizzaService.save(newPizza);
        Pizza pizza = pizzaService.findByName(newPizza.getName());
        List<PizzaIngredient> pizzaIngredients = new ArrayList<>();
        newIngredients.forEach(ing -> {
            if (ingredientService.findOne(ing).isPresent()) {
                PizzaIngredient pizzaIngredient = new PizzaIngredient();
                pizzaIngredient.setPizza(pizza);
                pizzaIngredient.setIngredient(ingredientService.findOne(ing).get());
                PizzaIngredientCompositeKey compositeKey = new PizzaIngredientCompositeKey();
                compositeKey.setPizzaId(pizza.getId());
                compositeKey.setIngredientId(ing);
                pizzaIngredient.setCompositeKey(compositeKey);
                pizzaIngredients.add(pizzaIngredient);
                pizzaIngredientService.save(pizzaIngredient);
            } else
                throw new IngredientNotFoundException();
        });

        pizza.setPizzaIngredients(pizzaIngredients);
        pizzaService.save(pizza);

        if (pizza.isVeggie())
            pizza.getPizzaIngredients().forEach(ingredient -> {
                if (!ingredient.getIngredient().isVeggie()) {
                    pizzaIngredientService.deleteAllByPizza(pizza);
                    pizzaService.delete(pizza.getId());
                    throw new PizzaIngredientNotVeggieException();
                }
            });
        return new ModelAndView("redirect:/admin/pizzas");
    }


    @PutMapping("/{id}")
    public ModelAndView editPizza(@PathVariable(name = "id") Long pizzaId, @ModelAttribute Pizza pizza, @RequestParam(name = "newIngredients") ArrayList<Long> newIngredients) {
        pizzaService.findOne(pizzaId);
        pizza.setId(pizzaId);
        List<PizzaIngredient> pizzaIngredients = new ArrayList<>();
        pizzaIngredientService.deleteAllByPizza(pizza);
        newIngredients.forEach(ing -> {
            if (ingredientService.findOne(ing).isPresent()) {
                if (pizza.isVeggie() && !ingredientService.findOne(ing).get().isVeggie())
                    throw new PizzaIngredientNotVeggieException();
                PizzaIngredient pizzaIngredient = new PizzaIngredient();
                pizzaIngredient.setIngredient(ingredientService.findOne(ing).get());
                pizzaIngredient.setPizza(pizza);
                pizzaIngredient.setAmount(200);
                PizzaIngredientCompositeKey compositeKey = new PizzaIngredientCompositeKey();
                compositeKey.setIngredientId(ing);
                compositeKey.setPizzaId(pizzaId);
                pizzaIngredient.setCompositeKey(compositeKey);
                pizzaIngredients.add(pizzaIngredient);
                pizzaIngredientService.save(pizzaIngredient);
            }
        });
        pizza.setPizzaIngredients(pizzaIngredients);
        pizza.setDateUpdated(LocalDateTime.now());
        pizzaService.save(pizza);
        return new ModelAndView("redirect:/admin/pizzas");
    }

    @DeleteMapping("/{id}")
    public Pizza deletePizza(@PathVariable(name = "id") Long pizzaId) {
        logger.info("\u001B[33mDELETE method CALLED from PizzaController\u001B[0m");
        Pizza pizza = pizzaService.findOne(pizzaId).orElseThrow(PizzaNotFoundException::new);
        pizzaIngredientService.deleteAllByPizza(pizzaService.findOne(pizzaId).orElseThrow(PizzaNotFoundException::new));
        orderService.deleteByPizza(pizza);
        pizzaService.delete(pizzaId);
        return pizza;
    }
}
