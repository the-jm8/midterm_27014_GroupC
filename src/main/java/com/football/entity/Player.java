package com.football.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * Player Entity
 * 
 * Represents a football player with performance tracking.
 * 
 * - One-to-Many relationship with Team (bidirectional)
 * - One-to-One relationship with PerformanceStat
 * - Comprehensive field validation
 * 
 * @author Patrick DUSHIMIMANA
 */
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(nullable = false)
    private String lastName;

    @NotNull(message = "Age is required")
    @Min(value = 16, message = "Player must be at least 16 years old")
    @Max(value = 50, message = "Player must be at most 50 years old")
    @Column(nullable = false)
    private Integer age;

    @NotBlank(message = "Position is required")
    @Column(nullable = false)
    private String position;

    @NotNull(message = "Jersey number is required")
    @Min(value = 1, message = "Jersey number must be at least 1")
    @Max(value = 99, message = "Jersey number must be at most 99")
    @Column(nullable = false, unique = true)
    private Integer jerseyNumber;

    @NotNull(message = "Join date is required")
    @Column(nullable = false)
    private LocalDate joinDate;

    // One-to-Many relationship with Team
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    // One-to-One relationship with PerformanceStat
    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PerformanceStat performanceStat;

    // Constructors
    public Player() {
    }

    public Player(String firstName, String lastName, Integer age, String position,
            Integer jerseyNumber, LocalDate joinDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.position = position;
        this.jerseyNumber = jerseyNumber;
        this.joinDate = joinDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(Integer jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public PerformanceStat getPerformanceStat() {
        return performanceStat;
    }

    public void setPerformanceStat(PerformanceStat performanceStat) {
        this.performanceStat = performanceStat;
        if (performanceStat != null) {
            performanceStat.setPlayer(this);
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", position='" + position + '\'' +
                ", jerseyNumber=" + jerseyNumber +
                ", joinDate=" + joinDate +
                ", team=" + (team != null ? team.getName() : "No team") +
                '}';
    }
}
