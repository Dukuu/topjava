package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.AuthorizedUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class UserServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to users");
        request.getRequestDispatcher("/users.jsp").forward(request, response);

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("Users login");
        String user = request.getParameter("userselector");

        if (user.equalsIgnoreCase("1")) {
            LOG.info("user "+ user +" logged in");
            AuthorizedUser.setId(Integer.parseInt(user));
        }
        else {
            LOG.info("user "+ user +" logged in");
            AuthorizedUser.setId(Integer.parseInt(user));
        }
        request.getRequestDispatcher("/").forward(request, response);
    }
}
