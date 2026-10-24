package com.nycthral.habittracker.dto;

public class HabitDailyStatus {
    private Long id;
    private String name;
    private boolean completed;

    public HabitDailyStatus(Long id, String name, boolean completed) {
        this.id = id;
        this.name = name;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getName() {
        return name;
    }
}
