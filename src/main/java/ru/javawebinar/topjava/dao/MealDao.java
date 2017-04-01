package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.data.MealStorage;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDao implements MealAccess {

    @Override
    public void addMeal(Meal meal) {
        AtomicInteger lastKey = ((TreeMap<AtomicInteger, Meal>)MealStorage.getMealMapStorage()).lastKey();
        lastKey.incrementAndGet();
        MealStorage.getMealMapStorage().put(lastKey, new Meal(lastKey, meal.getDateTime(), meal.getDescription(), meal.getCalories()));
    }

    @Override
    public void deleteMeal(int mealId) {
        if (MealStorage.getMealMapStorage().containsKey(mealId)) MealStorage.getMealMapStorage().remove(mealId);
    }

    @Override
    public void updateMeal(Meal meal) {
        if (MealStorage.getMealMapStorage().containsKey(meal.getId())) {
            MealStorage.getMealMapStorage().put(meal.getId(), new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories()));
        }
    }

    @Override
    public List<Meal> getAllMeals() {
        List<Meal> mealList = new ArrayList<>();
        MealStorage.getMealMapStorage().forEach((integer, meal) -> mealList.add(meal));
        return mealList;
    }

    @Override
    public Meal getMealById(int mealId) {
        return MealStorage.getMealMapStorage().get(mealId);
    }
}
