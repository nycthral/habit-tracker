package com.nycthral.habittracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nycthral.habittracker.dto.RecordResponse;
import com.nycthral.habittracker.service.RecordService;
import java.util.List;

@RestController
@RequestMapping("/api/habits")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("/{id}/records")
    public ResponseEntity<RecordResponse> create(@PathVariable Long id) {
        RecordResponse created = recordService.create(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}/records")
    public ResponseEntity<List<RecordResponse>> getByHabitId(@PathVariable Long id) {
        List<RecordResponse> records = recordService.getByHabitId(id);
        return ResponseEntity.ok(records);
    }
}