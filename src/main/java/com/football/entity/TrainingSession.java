package com.football.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * TrainingSession Entity
 * 
 * Represents a football team training session.
 * 
 * - One-to-Many relationship with Team (bidirectional)
 * - Field validation for training details
 * - Date and time tracking for training sessions
 * 
 * @author Patrick DUSHIMIMANA
 */
@Entity
@Table(name = "training_sessions")
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Training type is required")
    @Column(nullable = false)
    private String trainingType; // e.g., "Fitness", "Tactics", "Shooting"

    @NotNull(message = "Training date is required")
    @Column(nullable = false)
    private LocalDate trainingDate;

    @NotNull(message = "Start time is required")
    @Column(nullable = false)
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    @Column(nullable = false)
    private LocalTime endTime;

    @NotBlank(message = "Location is required")
    @Column(nullable = false)
    private String location;

    @NotNull(message = "Duration in minutes is required")
    @Min(value = 30, message = "Training session must be at least 30 minutes")
    @Max(value = 240, message = "Training session cannot exceed 240 minutes")
    @Column(nullable = false)
    private Integer durationMinutes;

    @NotBlank(message = "Trainer name is required")
    @Column(nullable = false)
    private String trainerName;

    // One-to-Many relationship with Team
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    // Constructors
    public TrainingSession() {
    }

    public TrainingSession(String trainingType, LocalDate trainingDate, LocalTime startTime,
            LocalTime endTime, String location, Integer durationMinutes, String trainerName) {
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.durationMinutes = durationMinutes;
        this.trainerName = trainerName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public LocalDate getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "TrainingSession{" +
                "id=" + id +
                ", trainingType='" + trainingType + '\'' +
                ", trainingDate=" + trainingDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", location='" + location + '\'' +
                ", durationMinutes=" + durationMinutes +
                ", trainerName='" + trainerName + '\'' +
                ", team=" + (team != null ? team.getName() : "No team") +
                '}';
    }
}
