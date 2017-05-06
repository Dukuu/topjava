package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by AleZin on 06.05.2017.
 */

@ActiveProfiles(value = {Profiles.ACTIVE_DB, Profiles.DATAJPA}, inheritProfiles = false)
public class UserServiceDataJpaTest extends UserServiceTest {
}
