package com.nycthral.habittracker.dto;

public class StatsResponse {
    private int currentStreak;
    private int bestStreak;
    private double completionRate;
    private long totalCompletions;

    public StatsResponse(int currentStreak, int bestStreak, double completionRate, long totalCompletions) {
        this.currentStreak = currentStreak;
        this.bestStreak = bestStreak;
        this.completionRate = completionRate;
        this.totalCompletions = totalCompletions;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getBestStreak() {
        return bestStreak;
    }

    public double getCompletionRate() {
        return completionRate;
    }

    public long getTotalCompletions() {
        return totalCompletions;
    }
}