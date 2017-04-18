package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class MealTestData {

    public static final int mealId = START_SEQ + 2;

    public static final Meal MEAL = new Meal(LocalDateTime.now(), "обед", 250);
    public static final Meal MEAL1 = new Meal(LocalDateTime.now(), "обед2", 200);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getCalories(), actual.getCalories())
                    && Objects.equals(expected.getDateTime().truncatedTo(ChronoUnit.SECONDS), actual.getDateTime().truncatedTo(ChronoUnit.SECONDS))
                    && Objects.equals(expected.getDescription(), actual.getDescription())
                    && Objects.equals(expected.getId(), actual.getId())
                    )
    );
}
