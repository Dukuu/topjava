package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private MealDao mealDao;
    private static String LIST = "meals.jsp";


    public MealServlet() {
        super();
        mealDao = new MealDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("forward to meals");

        String forward = LIST;
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(req.getParameter("id"));
            mealDao.deleteMeal(mealId);
            req.setAttribute("mealList", MealsUtil.getExceededMealList(mealDao.getAllMeals(),2000));
        }
        else if (action.equalsIgnoreCase("edit")) {
            forward = "meal.jsp";
            int mealId = Integer.parseInt(req.getParameter("id"));
            Meal meal = mealDao.getMealById(mealId);
            req.setAttribute("mealEdit", meal);
        }
        else req.setAttribute("mealList", MealsUtil.getExceededMealList(mealDao.getAllMeals(),2000));
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("new meal or edit meal");

        String mealIdString = req.getParameter("id");
        String mealDescr = req.getParameter("descr");
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.parse(req.getParameter("date")), LocalTime.parse(req.getParameter("time")));
        Integer cal = Integer.parseInt(req.getParameter("cal"));

        if (mealIdString == null || mealIdString.isEmpty()) {
            Meal meal = new Meal(-1, localDateTime, mealDescr, cal);
            mealDao.addMeal(meal);
        }
        else {
            Integer mealId = Integer.parseInt(mealIdString);
            Meal meal = new Meal(mealId, localDateTime, mealDescr, cal);
            mealDao.updateMeal(meal);
        }
        req.getRequestDispatcher(LIST).forward(req, resp);
    }
}
