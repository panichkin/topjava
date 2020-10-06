package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealsData;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MealTo> listOfMealTo = MealsUtil.filteredByStreams(MealsData.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59, 59), 2000);
        req.setAttribute("listofmealto", listOfMealTo);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
