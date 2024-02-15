package com.example.foodapplication.Model;

import java.util.List;

public class WeeklyMealPlan {
    private List<Day> days;

    public WeeklyMealPlan(List<Day> days) {
        this.days = days;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}
