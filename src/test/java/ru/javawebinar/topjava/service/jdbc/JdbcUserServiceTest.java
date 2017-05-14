package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assume;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

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