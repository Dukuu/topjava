package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(mealId, USER_ID);
        MEAL.setId(100002);
        MATCHER.assertEquals(MEAL, meal);
    }

    @Test
    public void delete() throws Exception {
        service.delete(mealId, USER_ID);
        MEAL1.setId(100003);
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL1), service.getAll(USER_ID+1));
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> mealList = service.getAll(USER_ID);
        MEAL.setId(100002);
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL), mealList);
    }

    @Test
    public void update() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.now(), "обед", 210);
        newMeal.setId(100002);
        service.update(newMeal, USER_ID);
        MATCHER.assertEquals(newMeal, service.get(mealId, USER_ID));
    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.now(), "breakfast3", 1100);
        service.save(newMeal, USER_ID);
        MATCHER.assertEquals(newMeal, service.get(mealId+2, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnother() throws Exception {
        service.delete(mealId, ADMIN_ID);
    }
    @Test(expected = NotFoundException.class)
    public void getAnother() throws Exception {
        service.get(mealId, ADMIN_ID);
    }
    @Test(expected = NotFoundException.class)
    public void updateAnother() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.now(), "обеддддд", 230);
        newMeal.setId(100002);
        service.update(newMeal, ADMIN_ID);
    }
}