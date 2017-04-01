package ru.javawebinar.topjava.data;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MealStorage {
    private static final List<Meal> meals = Arrays.asList(
            new Meal(new AtomicInteger(1), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(new AtomicInteger(2), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(new AtomicInteger(3), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(new AtomicInteger(4), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(new AtomicInteger(5), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(new AtomicInteger(6), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    private static Map<AtomicInteger, Meal> mealMapStorage = new TreeMap<>();
    //static final MealStorage mealStorage = new MealStorage();

    static {
        meals.forEach(meal -> mealMapStorage.put(meal.getId(), meal));
    }

    public static Map<AtomicInteger, Meal> getMealMapStorage() {
        return mealMapStorage;
    }
}
