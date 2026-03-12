package com.football.controller;

import com.football.entity.TrainingSession;
import com.football.service.TrainingSessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class TrainingSessionController {

    private final TrainingSessionService sessionService;

    public TrainingSessionController(TrainingSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public List<TrainingSession> getAllSessions() {
        return sessionService.getAllSessions();
    }

    @GetMapping("/{id}")
    public TrainingSession getSession(@PathVariable Long id) {
        return sessionService.getSession(id);
    }

    @PostMapping
    public TrainingSession createSession(@RequestBody TrainingSession session) {
        return sessionService.saveSession(session);
    }

    @PutMapping("/{id}")
    public TrainingSession updateSession(@PathVariable Long id, @RequestBody TrainingSession session) {
        session.setId(id);
        return sessionService.saveSession(session);
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
    }
}