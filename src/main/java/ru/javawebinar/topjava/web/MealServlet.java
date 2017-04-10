package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try (ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            mealRestController = applicationContext.getBean(MealRestController.class);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(AuthorizedUser.getId(), id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        mealRestController.create(meal);

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                LOG.info("Delete {}", id);
                mealRestController.delete(id, AuthorizedUser.getId());
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = action.equals("create") ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, AuthorizedUser.getId()) :
                        mealRestController.get(getId(request), AuthorizedUser.getId());
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            case "all":
            default:
                LOG.info("getAll (sorted)");
                String dateSortFrom = request.getParameter("dateSortFrom");
                String dateSortTo = request.getParameter("dateSortTo");
                String timeSortFrom = request.getParameter("timeSortFrom");
                String timeSortTo = request.getParameter("timeSortTo");
                //List<String> pString = Arrays.asList(dateSortFrom, dateSortTo, timeSortFrom, timeSortTo);
                //DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
                //DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH-mm");
                LocalDate dFrom = (dateSortFrom!=null && !dateSortFrom.equalsIgnoreCase("")) ? LocalDate.parse(dateSortFrom) : null;
                LocalDate dTo = (dateSortTo!=null && !dateSortTo.equalsIgnoreCase("")) ? LocalDate.parse(dateSortTo) : null;
                LocalTime tFrom = (timeSortFrom!=null && !timeSortFrom.equalsIgnoreCase("")) ? LocalTime.parse(timeSortFrom) : null;
                LocalTime tTo = (timeSortTo!=null && !timeSortTo.equalsIgnoreCase("")) ? LocalTime.parse(timeSortTo) : null;

                request.setAttribute("meals",
                        MealsUtil.getWithExceeded(mealRestController.sortByDateTime(mealRestController.getAll(AuthorizedUser.getId()),
                                dFrom, dTo, tFrom, tTo),
                                MealsUtil.DEFAULT_CALORIES_PER_DAY));

                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}