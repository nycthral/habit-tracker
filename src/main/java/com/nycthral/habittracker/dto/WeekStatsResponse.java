package com.nycthral.habittracker.dto;

import java.util.List;

public class WeekStatsResponse {
    private List<DayStats> week;

    public WeekStatsResponse(List<DayStats> week) {
        this.week = week;
    }

    public List<DayStats> getWeek() {
        return week;
    }
}