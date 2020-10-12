package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal get(int id, int authUserId) {
        Meal m = repository.get(id, authUserId);
        if(m == null) throw new NotFoundException("get()");
        return m;
    }

    public List<Meal> getAll(int authUserId) {
        return repository.getAll(authUserId);
    }

    public List<Meal> getAll(int authUserId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return repository.getAll(authUserId, startDate, endDate, startTime, endTime);
    }

    public Meal create(Meal meal, int authUserId) {
        Meal m = repository.save(meal, authUserId);
        if(m == null) throw new NotFoundException("create()");
        return m;
    }

    public void delete(int id, int authUserId) {
        if(!repository.delete(id, authUserId))
            throw new NotFoundException("delete()");
    }

    public void update(Meal meal, int authUserId) {
        Meal m =  repository.save(meal, authUserId);
        if(m == null) throw new NotFoundException("update()");
    }

}