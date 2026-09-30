package com.nycthral.habittracker.exception;

public class HabitNotFoundException extends RuntimeException {
    public HabitNotFoundException(Long id) {
        super("Habit with id " + id + " not found");
    }
}
