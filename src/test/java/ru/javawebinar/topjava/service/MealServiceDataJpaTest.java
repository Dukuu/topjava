package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by AleZin on 06.05.2017.
 */

@ActiveProfiles(value = {Profiles.ACTIVE_DB, Profiles.DATAJPA}, inheritProfiles = false)
public class MealServiceDataJpaTest extends MealServiceTest {

    @Autowired
    private MealService mealService;

    @Autowired
    private UserService userService;

    @Test
    public void getUserWithMeals() {
        User user = userService.get(USER_ID);
        List<Meal> userMealsExpected = user.getMeals();
        List<Meal> userMealsActual = mealService.getAll(USER_ID);
        MATCHER.assertCollectionEquals(userMealsExpected, userMealsActual);
    }
}
