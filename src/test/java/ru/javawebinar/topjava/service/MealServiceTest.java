package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.meals1;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void testGetById() {
        Meal meal = service.get(100002, 100000);
        assertMatch(meal, meal);
    }

    @Test
    public void testSomeoneMeal() {
        assertThrows(NotFoundException.class, () -> service.get(100002, 100001));
    }

    @Test
    public void testNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(10, 100000));
    }

    @Test
    public void delete() {
        service.delete(100004, 100000);
        assertThrows(NotFoundException.class, ()->service.get(100004, 100000));
    }

    @Test
    public void deleteUnexisting() {
        assertThrows(NotFoundException.class, ()->service.delete(100099, 100000));
    }

    @Test
    public void deleteSomeoneMeal() {
        assertThrows(NotFoundException.class, ()->service.delete(100002, 100001));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> expected = MealTestData.meals1.stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());

        List<Meal> actual = service.getBetweenInclusive(LocalDate.parse("2020-01-30"), LocalDate.parse("2020-01-30"), 100000);
        assertMatch(actual, expected);
    }

    @Test
    public void getAll() {
        List<Meal> expected = MealTestData.meals1.stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        List<Meal> actual = service.getAll(100000);
        assertMatch(actual, expected);
    }

    @Test
    public void update() {
        Integer userId = 100001;
        Integer mealId = 100010;
        Meal updated = service.get(mealId, userId);
        updated.setCalories(111);
        service.update(updated, userId);
        assertMatch(service.get(mealId, userId), updated);
    }

    @Test
    public void updateSomeone() {
        Integer userId2 = 100000;
        Integer userId1 = 100001;
        Integer mealId = 100010;
        Meal updated = service.get(mealId, userId1);
        updated.setCalories(111);
        assertThrows(NotFoundException.class, ()->service.update(updated, userId2));

    }

    @Test
    public void create() {
        Integer userId = 100001;
        Meal newMeal = new Meal(meals1.get(3));
        newMeal.setId(null);
        Meal created = service.create(newMeal, userId);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, userId), newMeal);
    }
}
