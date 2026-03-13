package com.football.controller;

import com.football.entity.PerformanceStat;
import com.football.service.PerformanceStatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class PerformanceStatController {

    private final PerformanceStatService statService;

    public PerformanceStatController(PerformanceStatService statService) {
        this.statService = statService;
    }

    @GetMapping
    public List<PerformanceStat> getAllStats() {
        return statService.getAllStats();
    }

    @GetMapping("/{id}")
    public PerformanceStat getStat(@PathVariable Long id) {
        return statService.getStat(id);
    }

    @PostMapping
    public PerformanceStat createStat(@RequestBody PerformanceStat stat) {
        return statService.saveStat(stat);
    }

    @PutMapping("/{id}")
    public PerformanceStat updateStat(@PathVariable Long id, @RequestBody PerformanceStat stat) {
        stat.setId(id);
        return statService.saveStat(stat);
    }

    @DeleteMapping("/{id}")
    public void deleteStat(@PathVariable Long id) {
        statService.deleteStat(id);
    }
}
