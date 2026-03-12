package com.football.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * PerformanceStat Entity
 * 
 * Represents player performance statistics for a specific match.
 * 
 * This entity demonstrates:
 * - One-to-One relationship with Player (bidirectional)
 * - Field validation for performance metrics
 * - Date tracking for performance records
 * 
 * @author Patrick DUSHIMIMANA
 */
@Entity
@Table(name = "performance_stats")
public class PerformanceStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Goals scored is required")
    @Min(value = 0, message = "Goals scored cannot be negative")
    @Column(nullable = false)
    private Integer goalsScored;

    @NotNull(message = "Assists is required")
    @Min(value = 0, message = "Assists cannot be negative")
    @Column(nullable = false)
    private Integer assists;

    @NotNull(message = "Minutes played is required")
    @Min(value = 0, message = "Minutes played cannot be negative")
    @Max(value = 120, message = "Minutes played cannot exceed 120")
    @Column(nullable = false)
    private Integer minutesPlayed;

    @NotNull(message = "Fitness level is required")
    @Min(value = 1, message = "Fitness level must be at least 1")
    @Max(value = 10, message = "Fitness level must be at most 10")
    @Column(nullable = false)
    private Integer fitnessLevel;

    @NotNull(message = "Performance date is required")
    @Column(nullable = false)
    private LocalDate performanceDate;

    // One-to-One relationship with Player
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    // Constructors
    public PerformanceStat() {
    }

    public PerformanceStat(Integer goalsScored, Integer assists, Integer minutesPlayed,
            Integer fitnessLevel, LocalDate performanceDate) {
        this.goalsScored = goalsScored;
        this.assists = assists;
        this.minutesPlayed = minutesPlayed;
        this.fitnessLevel = fitnessLevel;
        this.performanceDate = performanceDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(Integer goalsScored) {
        this.goalsScored = goalsScored;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(Integer minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }

    public Integer getFitnessLevel() {
        return fitnessLevel;
    }

    public void setFitnessLevel(Integer fitnessLevel) {
        this.fitnessLevel = fitnessLevel;
    }

    public LocalDate getPerformanceDate() {
        return performanceDate;
    }

    public void setPerformanceDate(LocalDate performanceDate) {
        this.performanceDate = performanceDate;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "PerformanceStat{" +
                "id=" + id +
                ", goalsScored=" + goalsScored +
                ", assists=" + assists +
                ", minutesPlayed=" + minutesPlayed +
                ", fitnessLevel=" + fitnessLevel +
                ", performanceDate=" + performanceDate +
                ", player=" + (player != null ? player.getFullName() : "No player") +
                '}';
    }
}