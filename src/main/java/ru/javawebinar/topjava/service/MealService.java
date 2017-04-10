package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {

    Meal save(Meal meal);

    void delete(int mealId, int userId) throws NotFoundException;

    Meal get(int mealId, int userId) throws NotFoundException;

    List<Meal> getAll(int userId);

    List<Meal> sortByDate(List<Meal> meals, LocalDate dateFrom, LocalDate dateTo);

    List<Meal> sortByTime(List<Meal> meals, LocalTime timeFrom, LocalTime timeTo);
}