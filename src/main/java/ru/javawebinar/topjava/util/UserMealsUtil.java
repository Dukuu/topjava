package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate, Integer> caloriesSumByDates = new HashMap<>();
        List<UserMealWithExceed> exceedList = new ArrayList<>();
        mealList.forEach(userMeal -> caloriesSumByDates.put(userMeal.getDateTime().toLocalDate(), caloriesSumByDates.getOrDefault(userMeal.getDateTime().toLocalDate(), 0) + userMeal.getCalories()));

        mealList.stream()
                .filter(userMeal -> userMeal.getDateTime().toLocalTime().isAfter(startTime))
                .filter(userMeal -> userMeal.getDateTime().toLocalTime().isBefore(endTime))
                .forEach(userMeal -> exceedList.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                        caloriesSumByDates.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay)));
        return exceedList;
    }
}
