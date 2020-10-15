package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal get(int id, int userId) {
        Meal m = repository.get(id, userId);
        if (m == null) throw new NotFoundException("get()");
        return m;
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<Meal> getAll(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        return repository.getAll(startDate, endDate, startTime, endTime, userId);
    }

    public Meal create(Meal meal, int userId) {
        Meal m = repository.save(meal, userId);
        return m;
    }

    public void delete(int id, int userId) {
        if (! repository.delete(id, userId))
            throw new NotFoundException("delete()");
    }

    public void update(Meal meal, int userId) {
        Meal m = repository.save(meal, userId);
        if (m == null) throw new NotFoundException("update()");
    }
}