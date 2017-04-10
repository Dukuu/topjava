package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private MealService service;

    @Autowired
    public void setService(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        LOG.info("create " + meal);
        return service.save(meal);
    }

    public void delete(int mealId, int userId) {
        LOG.info("delete meal with id " + mealId);
        service.delete(mealId, userId);
    }

    public Meal get(int mealId, int userId) {
        LOG.info("get meal with id " + mealId);
        return service.get(mealId, userId);
    }

    public List<Meal> getAll(int userId) {
        LOG.info("get all meals for user with id " + userId);
        return service.getAll(userId);
    }

    public List<Meal> sortByDateTime(List<Meal> meals, LocalDate dateFrom, LocalDate dateTo, LocalTime timeFrom, LocalTime timeTo) {
        return (service.sortByTime(service.sortByDate(meals, dateFrom, dateTo), timeFrom, timeTo));
    }
}