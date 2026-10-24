package com.nycthral.habittracker.dto;

import java.time.LocalDate;
import java.util.List;

public class DailyStatsResponse {
    private LocalDate date;
    private List<HabitDailyStatus> habits;

    public DailyStatsResponse(LocalDate date, List<HabitDailyStatus> habits) {
        this.date = date;
        this.habits = habits;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<HabitDailyStatus> getHabits() {
        return habits;
    }
}
