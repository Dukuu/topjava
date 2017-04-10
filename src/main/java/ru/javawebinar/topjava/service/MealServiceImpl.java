package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public void setRepository(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int mealId, int userId) throws NotFoundException {
        Meal meal = repository.get(mealId);
        if (meal == null) throw new NotFoundException("Meal with id=" + mealId + " not found. Cannot delete!");
        else if (meal.getUserId() != userId) throw new NotFoundException("Meal with id=" + mealId + " belongs to another user. Cannot delete!");
        else repository.delete(mealId);
    }

    @Override
    public Meal get(int mealId, int userId) throws NotFoundException {
        Meal meal = repository.get(mealId);
        if (meal == null) throw new NotFoundException("Meal with id=" + mealId + " not found");
        else if (meal.getUserId() != userId) throw new NotFoundException("Meal with id=" + mealId + " belongs to another user. Access denied!");
        else return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> sortByDate(List<Meal> meals, LocalDate dateFrom, LocalDate dateTo) {
        return meals.stream()
                .filter(meal -> meal.isWithinDateRange(dateFrom, dateTo))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> sortByTime(List<Meal> meals, LocalTime timeFrom, LocalTime timeTo) {
        return meals.stream()
                .filter(meal -> meal.isWithinTimeRange(timeFrom, timeTo))
                .collect(Collectors.toList());
    }
}