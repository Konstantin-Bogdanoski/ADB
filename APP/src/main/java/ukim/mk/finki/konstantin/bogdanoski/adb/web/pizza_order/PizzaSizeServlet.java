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
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/pizzaSize")
@AllArgsConstructor
public class PizzaSizeServlet extends HttpServlet {
    private final PizzaService pizzaService;
    private final SpringTemplateEngine springTemplateEngine;
    private final Logger logger = Logger.getLogger(PizzaSizeServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method CALLED from PizzaSize Servlet\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        Pizza selectedPizza;
        if (req.getParameter("selectedPizza").isEmpty())
            selectedPizza = (Pizza) req.getSession().getAttribute("selectedPizza");
        else
            selectedPizza = pizzaService.findByName(req.getParameter("selectedPizza"));
        req.getSession().setAttribute("selectedPizza", selectedPizza);
        context.setVariable("selectedPizza", selectedPizza);
        this.springTemplateEngine.process("selectPizzaSize.html", context, resp.getWriter());
    }
}
