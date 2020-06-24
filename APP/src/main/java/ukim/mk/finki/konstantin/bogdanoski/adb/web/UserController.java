package ukim.mk.finki.konstantin.bogdanoski.adb.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ukim.mk.finki.konstantin.bogdanoski.adb.exception.PersonNotFoundException;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Customer;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.CustomerService;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.PersonService;

import java.time.LocalDateTime;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final PersonService personService;
    private final CustomerService customerService;

    public UserController(PersonService personService, CustomerService customerService) {
        this.personService = personService;
        this.customerService = customerService;
    }

    @DeleteMapping("/{id}")
    public ModelAndView deleteUser(@PathVariable Long id) {
        personService.delete(id);
        return new ModelAndView("redirect:/admin/users");
    }

    @PatchMapping("/{id}")
    public ModelAndView editUser(@PathVariable Long id, @ModelAttribute(name = "newUser") Customer newUser) {
        newUser.setId(id);
        Customer person = customerService.findOne(id).orElseThrow(PersonNotFoundException::new);
        newUser.setPassword(person.getPassword());
        newUser.setOrderList(person.getOrderList());
        newUser.setDateCreated(person.getDateCreated());
        newUser.setEmail(person.getEmail());
        newUser.setDateUpdated(LocalDateTime.now());
        personService.save(newUser);
        return new ModelAndView("redirect:/admin/users");
    }
}
