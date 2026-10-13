package com.nycthral.habittracker.service;

import com.nycthral.habittracker.dto.HabitRequest;
import com.nycthral.habittracker.dto.HabitResponse;
import com.nycthral.habittracker.exception.HabitNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HabitServiceTest {

    @Autowired
    private HabitService habitService;

    @Test
    void testCreate() {
        HabitRequest request = new HabitRequest();
        request.setName("Drink water");
        request.setDescription("2 liters");
        request.setTarget(7);

        HabitResponse response = habitService.create(request);

        assertNotNull(response.getId());
        assertEquals("Drink water", response.getName());
        assertEquals(7, response.getTarget());
    }

    @Test
    void testGetById() {
        HabitRequest request = new HabitRequest();
        request.setName("Read book");
        request.setDescription("30 pages");
        request.setTarget(5);
        HabitResponse created = habitService.create(request);

        HabitResponse found = habitService.getById(created.getId());

        assertEquals(created.getId(), found.getId());
        assertEquals("Read book", found.getName());
    }

    @Test
    void testGetByIdNotFound() {
        assertThrows(HabitNotFoundException.class,
                () -> habitService.getById(99999L));
    }
}