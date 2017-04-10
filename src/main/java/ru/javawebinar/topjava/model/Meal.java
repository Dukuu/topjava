package ru.javawebinar.topjava.model;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class Meal extends BaseEntity {
    private Integer userId;

    private Integer id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(LocalDateTime dateTime, String description, int calories, Integer userId) {
        this(userId, null, dateTime, description, calories);
    }

    public Meal(Integer userId, Integer id, LocalDateTime dateTime, String description, int calories) {
        this.userId = userId;
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public Integer getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }

    public boolean isWithinDateRange(LocalDate from, LocalDate to) {
        if (to==null && from==null) return true;
        if (to==null) return !(getDate().isBefore(from));
        if (from==null) return !(getDate().isAfter(to));
        return !(getDate().isBefore(from) || getDate().isAfter(to));
    }

    public boolean isWithinTimeRange(LocalTime from, LocalTime to) {
        if (to==null && from==null) return true;
        if (to==null) return !(getTime().isBefore(from));
        if (from==null) return !(getTime().isAfter(to));
        return !(getTime().isBefore(from) || getTime().isAfter(to));
    }
}
