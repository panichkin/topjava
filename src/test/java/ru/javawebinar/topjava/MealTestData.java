package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class MealTestData {
    public static List<Meal> meals1 = new ArrayList<>(); // Meal for user 100000
    public static List<Meal> meals2 = new ArrayList<>(); // Meal for user 100001

    static {
        meals1.add(new Meal(100002, LocalDateTime.parse("2020-01-30T10:00:00"), "Завтрак", 500));
        meals1.add(new Meal(100003, LocalDateTime.parse("2020-01-30T13:00:00"), "Обед", 1000));
        meals1.add(new Meal(100004, LocalDateTime.parse("2020-01-30T20:00:00"), "Ужин", 500));
        meals1.add(new Meal(100005, LocalDateTime.parse("2020-01-31T00:00:00"), "Еда на граничное значение", 100));
        meals1.add(new Meal(100006, LocalDateTime.parse("2020-01-31T10:00:00"), "Завтрак", 1000));
        meals1.add(new Meal(100007, LocalDateTime.parse("2020-01-31T13:00:00"), "Обед", 1000));
        meals1.add(new Meal(100008, LocalDateTime.parse("2020-01-31T20:00:00"), "Ужин", 500));

        meals2.add(new Meal(100009, LocalDateTime.parse("2020-01-30T00:00:00"), "AЕда на граничное значение", 100));
        meals2.add(new Meal(100010, LocalDateTime.parse("2020-01-30T10:00:00"), "АЗавтрак", 500));
        meals2.add(new Meal(100011, LocalDateTime.parse("2020-01-30T13:00:00"), "АОбед", 500));
        meals2.add(new Meal(100012, LocalDateTime.parse("2020-01-30T20:00:00"), "АУжин", 410));
        meals2.add(new Meal(100013, LocalDateTime.parse("2020-01-31T10:00:00"), "AЗавтрак", 1000));
        meals2.add(new Meal(100014, LocalDateTime.parse("2020-01-31T13:00:00"), "AОбед", 500));
        meals2.add(new Meal(100015, LocalDateTime.parse("2020-01-31T20:00:00"), "AУжин", 410));
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
