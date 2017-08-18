package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static ru.javawebinar.topjava.util.converter.DateTimeFormatters.*;

@RestController
@RequestMapping("ajax/user/meals")
public class MealAjaxController extends AbstractMealController {

    @Autowired
    public MealAjaxController(MealService service) {
        super(service);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@RequestParam("id") Integer id,
                               @RequestParam("dateTime") String dateTime,
                               @RequestParam("description") String description,
                               @RequestParam("calories") Integer calories) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        Meal meal = new Meal(id, localDateTime, description, calories);
        if (meal.isNew()) {
            super.create(meal);
        }
        else {
            super.update(meal, id);
        }
    }

    @RequestMapping(value = "/filter", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> filter(
            @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endDate", required = false) String endDate, @RequestParam(value = "endTime", required = false) String endTime) throws ParseException {
        LocalDateFormatter localDateFormatter = new LocalDateFormatter();
        LocalTimeFormatter localTimeFormatter = new LocalTimeFormatter();
        Locale locale = Locale.ENGLISH;
        return super.getBetween(localDateFormatter.parse(startDate, locale), localTimeFormatter.parse(startTime, locale), localDateFormatter.parse(endDate, locale), localTimeFormatter.parse(endTime, locale));
    }
}
