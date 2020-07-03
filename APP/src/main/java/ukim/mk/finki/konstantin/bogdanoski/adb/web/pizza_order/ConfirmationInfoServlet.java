package ukim.mk.finki.konstantin.bogdanoski.adb.web.pizza_order;

import lombok.AllArgsConstructor;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ukim.mk.finki.konstantin.bogdanoski.adb.exception.PersonNotFoundException;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.adb.model.PizzaOrder;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.CustomerService;
import ukim.mk.finki.konstantin.bogdanoski.adb.service.OrderService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@WebServlet(urlPatterns = "/ConfirmationInfo.do")
@AllArgsConstructor
public class ConfirmationInfoServlet extends HttpServlet {
    private final OrderService orderService;
    private final CustomerService customerService;
    private final SpringTemplateEngine springTemplateEngine;
    private final Logger logger = Logger.getLogger(ConfirmationInfoServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("\u001B[33mGET method CALLED from ConfirmationInfo Servlet\u001B[0m");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = context.getSession();
        String address = req.getParameter("address");
        session.setAttribute("address", address);
        context.setVariable("address", address);
        PizzaOrder order = new PizzaOrder();

        order.setAddress(address);
        order.setCustomer(customerService.findOne(10017L).orElseThrow(PersonNotFoundException::new));
        order.setPizza((Pizza) session.getAttribute("selectedPizza"));
        order.setSize((String) session.getAttribute("size"));
        order.setDateCreated(LocalDateTime.now());
        orderService.save(order);

        Pizza selectedPizza = (Pizza) session.getAttribute("selectedPizza");
        req.setAttribute("selectedPizza", selectedPizza);
        req.setAttribute("address", address);
        req.setAttribute("order", order);
        req.setAttribute("size", session.getAttribute("size"));
        req.setAttribute("browser", "Firefox");
        req.setAttribute("ip", req.getRemoteAddr());

        this.springTemplateEngine.process("confirmationInfo.html", context, resp.getWriter());
    }
}
