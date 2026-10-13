package com.nycthral.habittracker.controller;

import com.nycthral.habittracker.dto.HabitRequest;
import com.nycthral.habittracker.dto.HabitResponse;
import com.nycthral.habittracker.service.HabitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
public class HabitController {
    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @PostMapping
    public ResponseEntity<HabitResponse> create(@Valid @RequestBody HabitRequest request) {
        HabitResponse created = habitService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitResponse> getById(@PathVariable Long id) {
        HabitResponse habit = habitService.getById(id);
        return ResponseEntity.ok(habit);
    }

    @GetMapping
    public ResponseEntity<List<HabitResponse>> getAll() {
        List<HabitResponse> habits = habitService.getAll();
        return ResponseEntity.ok(habits);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitResponse> update(@PathVariable Long id, @RequestBody HabitRequest request) {
        HabitResponse updated = habitService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        habitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
