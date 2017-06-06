package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by AleZin on 01.06.2017.
 */
public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + "/";

    @Test
    public void testGet() throws Exception {
    mockMvc.perform(get(REST_URL + MEAL1_ID))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print());
        MATCHER.assertCollectionEquals(mealService.getAll(USER_ID), MEALS.subList(0, 5));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(MealRestController.REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(EXCEED_MATCHER.contentListMatcher(MealsUtil.getWithExceeded(MEALS, userService.get(USER_ID).getCaloriesPerDay())));
    }

    @Test
    public void testCreateWithLocation() throws Exception {
        Meal expected = new Meal(LocalDateTime.now(), "TEST breakfast", 500);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        Meal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        List<Meal> expectedList = new ArrayList<>(MEALS);
        expectedList.add(0, expected);
        MATCHER.assertCollectionEquals(expectedList, mealService.getAll(USER_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories());
        updated.setCalories(555);
        updated.setDescription("Завтрак555");
        updated.setId(MEAL1.getId());
        mockMvc.perform(post(REST_URL + updated.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, mealService.get(MEAL1.getId(), USER_ID));
    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "sorted?from=" + LocalDateTime.of(2015, 5, 30, 10, 0).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "&to=" + LocalDateTime.of(2015, 5, 30, 11, 0).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(EXCEED_MATCHER.contentListMatcher(MealsUtil.createWithExceed(MEAL1, false)));
    }
}