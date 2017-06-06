package ru.javawebinar.topjava.web;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class RootControllerTest extends AbstractControllerTest {

    @Test
    public void testUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(USER.getName()))
                        )
                )));
    }

    @Test
    public void testMeals() throws Exception {
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", hasSize(6)))
                .andExpect(model().attribute("meals",
                        hasItems(
                                allOf(
                                hasProperty("id", is(MEAL1_ID)),
                                hasProperty("description", is(MEAL1.getDescription())),
                                hasProperty("dateTime", is(MEAL1.getDateTime())),
                                hasProperty("calories", is(MEAL1.getCalories()))
                        ),
                                allOf(
                                hasProperty("id", is(MEAL1_ID+1)),
                                hasProperty("description", is(MEAL2.getDescription())),
                                hasProperty("dateTime", is(MEAL2.getDateTime())),
                                hasProperty("calories", is(MEAL2.getCalories()))
                        ),
                                allOf(
                                hasProperty("id", is(MEAL1_ID+2)),
                                hasProperty("description", is(MEAL3.getDescription())),
                                hasProperty("dateTime", is(MEAL3.getDateTime())),
                                hasProperty("calories", is(MEAL3.getCalories()))
                        ),
                                allOf(
                                hasProperty("id", is(MEAL1_ID+3)),
                                hasProperty("description", is(MEAL4.getDescription())),
                                hasProperty("dateTime", is(MEAL4.getDateTime())),
                                hasProperty("calories", is(MEAL4.getCalories()))
                        ),
                                allOf(
                                hasProperty("id", is(MEAL1_ID+4)),
                                hasProperty("description", is(MEAL5.getDescription())),
                                hasProperty("dateTime", is(MEAL5.getDateTime())),
                                hasProperty("calories", is(MEAL5.getCalories()))
                        ),
                                allOf(
                                hasProperty("id", is(MEAL1_ID+5)),
                                hasProperty("description", is(MEAL6.getDescription())),
                                hasProperty("dateTime", is(MEAL6.getDateTime())),
                                hasProperty("calories", is(MEAL6.getCalories()))
                        )

                )));
    }
}