package ukim.mk.finki.konstantin.bogdanoski.adb.web.pizza_order;

import lombok.AllArgsConstructor;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Pizza;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@WebServlet(urlPatterns = "/PizzaOrder.do")
@AllArgsConstructor
public class PizzaOrderServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final Logger logger = Logger.getLogger(PizzaOrderServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method CALLED from PizzaOrder Servlet\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();

        Pizza pizza = (Pizza) session.getAttribute("selectedPizza");
        context.setVariable("selectedPizza", pizza);

        String size = req.getParameter("size");
        context.setVariable("size", size);
        session.setAttribute("size", size);

        this.springTemplateEngine.process("deliveryInfo.html", context, resp.getWriter());
    }
}
