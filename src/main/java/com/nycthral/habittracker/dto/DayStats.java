package com.nycthral.habittracker.dto;

import java.time.LocalDate;

public class DayStats {
    private LocalDate date;
    private int completedCount;
    private int totalCount;

    public DayStats(LocalDate date, int completedCount, int totalCount) {
        this.date = date;
        this.completedCount = completedCount;
        this.totalCount = totalCount;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public int getTotalCount() {
        return totalCount;
    }
}