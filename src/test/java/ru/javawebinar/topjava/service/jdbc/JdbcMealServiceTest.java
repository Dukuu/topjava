package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assume;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {
    @Autowired
    private Environment environment;

    @Override
    public void testValidation() throws Exception {
        String[] profiles = environment.getActiveProfiles();
        for (String s : profiles) {
            Assume.assumeFalse(s.equalsIgnoreCase("jdbc"));
        }
        super.testValidation();
    }
}