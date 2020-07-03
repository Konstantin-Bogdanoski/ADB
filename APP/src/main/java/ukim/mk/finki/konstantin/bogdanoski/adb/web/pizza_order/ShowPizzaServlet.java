package ukim.mk.finki.konstantin.bogdanoski.adb.web.pizza_order;

import lombok.AllArgsConstructor;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.PizzaService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@WebServlet(name = "index", urlPatterns = "")
@AllArgsConstructor
public class ShowPizzaServlet extends HttpServlet {
    private final PizzaService pizzaService;
    private final SpringTemplateEngine springTemplateEngine;
    private final Logger logger = Logger.getLogger(ShowPizzaServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method CALLED from ShowPizza Servlet\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        List<Pizza> pizzas = pizzaService.findAll();
        context.setVariable("username", "Example_User");
        context.setVariable("pizzas", pizzas);
        this.springTemplateEngine.process("listPizzas", context, resp.getWriter());
    }
}
