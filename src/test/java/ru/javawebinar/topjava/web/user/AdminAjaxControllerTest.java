package ru.javawebinar.topjava.web.user;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class AdminAjaxControllerTest extends AbstractControllerTest {

    private static final String REST_URL_AJAX = AdminAjaxController.REST_URL_AJAX + '/';

    @Test
    public void testUpdateNotValidByAjax() throws Exception {
        UserTo updatedTo = UserUtil.asTo(USER);
        updatedTo.setCaloriesPerDay(9);
        mockMvc.perform(post(REST_URL_AJAX + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .with(csrf())
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testDuplicateEmail() throws Exception {
        UserTo updatedTo = UserUtil.asTo(USER);
        updatedTo.setEmail("admin@gmail.com");
        mockMvc.perform(post(REST_URL_AJAX + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andExpect(content().string(""));
    }
}