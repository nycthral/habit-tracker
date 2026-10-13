package com.nycthral.habittracker.service;

import com.nycthral.habittracker.dto.HabitRequest;
import com.nycthral.habittracker.dto.HabitResponse;
import com.nycthral.habittracker.entity.Habit;
import org.springframework.stereotype.Service;
import com.nycthral.habittracker.repository.HabitRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nycthral.habittracker.exception.HabitNotFoundException;

@Service
public class HabitService {
    private static final Logger log = LoggerFactory.getLogger(HabitService.class);
    private final HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public HabitResponse create(HabitRequest request) {
        log.info("Creating habit with name: {}", request.getName());
        Habit habit = new Habit();
        habit.setName(request.getName());
        habit.setDescription(request.getDescription());
        habit.setTarget(request.getTarget());
        habit.setCreatedAt(LocalDateTime.now());
        Habit saved = habitRepository.save(habit);
        return new HabitResponse(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getTarget(),
                saved.getCreatedAt()
        );
    }

    public HabitResponse getById(Long id) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new HabitNotFoundException(id));
        return new HabitResponse(
                habit.getId(),
                habit.getName(),
                habit.getDescription(),
                habit.getTarget(),
                habit.getCreatedAt()
        );
    }

    public List<HabitResponse> getAll() {
        List<HabitResponse> result = new ArrayList<>();
        for (Habit habit : habitRepository.findAll()) {
            result.add(new HabitResponse(
                    habit.getId(),
                    habit.getName(),
                    habit.getDescription(),
                    habit.getTarget(),
                    habit.getCreatedAt()
            ));
        }
        return result;
    }

    public HabitResponse update(Long id, HabitRequest request) {
        log.info("Updating habit with id: {}", id);
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new HabitNotFoundException(id));
        habit.setName(request.getName());
        habit.setDescription(request.getDescription());
        habit.setTarget(request.getTarget());
        Habit saved = habitRepository.save(habit);
        return new HabitResponse(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getTarget(),
                saved.getCreatedAt()
        );
    }

    public void delete(Long id) {
        log.info("Deleting habit with id: {}", id);
        if (!habitRepository.existsById(id)) {
            throw new HabitNotFoundException(id);
        }
        habitRepository.deleteById(id);
    }
}