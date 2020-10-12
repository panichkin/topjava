package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.meals) {
            save(meal, meal.getUserId());
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }

        if(meal.getUserId() != userId) return null;

        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal m = repository.get(id);
        if(m == null || m.getUserId() != userId) return false;
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal m = repository.get(id);
        if(m == null || m.getUserId() != userId) return null;
        return m;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream().filter(meal -> meal.getUserId() == userId)
              .sorted((m1, m2) -> m2.getDate().compareTo(m1.getDate())).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAll(int authUserId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == authUserId)
                .filter(meal -> meal.getTime().equals(startTime) || meal.getTime().isAfter(startTime))
                .filter(meal -> meal.getTime().isBefore(endTime))
                .filter(meal -> meal.getDate().equals(startDate) || meal.getDate().isAfter(startDate))
                .filter(meal -> meal.getDate().isBefore(endDate))
                .sorted((m1, m2) -> m2.getDate().compareTo(m1.getDate())).collect(Collectors.toList());
    }


}

