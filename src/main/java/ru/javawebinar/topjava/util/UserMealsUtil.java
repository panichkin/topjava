package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import static java.util.stream.Collectors.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static UserMealWithExcess makeUserMealWithExcess(UserMeal userMeal, boolean excess) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> userMealsCaloriesPerDay = new HashMap<>();
        meals.forEach(um -> {
            LocalDate key = um.getLocalDate();
            userMealsCaloriesPerDay.put(key, userMealsCaloriesPerDay.getOrDefault(key, 0) + um.getCalories());
        });

        List<UserMealWithExcess> result = new ArrayList<>();
        meals.forEach(um -> {
            if (TimeUtil.isBetweenHalfOpen(um.getLocalTime(), startTime, endTime)) {
                result.add(makeUserMealWithExcess(um, userMealsCaloriesPerDay.get(um.getLocalDate()) > caloriesPerDay));
            }
        });

        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> userMealsCaloriesPerDay =
                meals.stream().collect(groupingBy(UserMeal::getLocalDate, summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(um -> TimeUtil.isBetweenHalfOpen(um.getLocalTime(), startTime, endTime))
                .map(um -> makeUserMealWithExcess(um, userMealsCaloriesPerDay.get(um.getLocalDate()) > caloriesPerDay))
                .collect(toList());
    }
}
