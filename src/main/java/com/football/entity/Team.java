package com.football.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Team Entity
 * 
 * Represents a football team with its location and players.
 * 
 * - One-to-One relationship with Location (bidirectional)
 * - One-to-Many relationship with Player
 * - One-to-Many relationship with TrainingSession
 * - Primary key generation and field validation
 * 
 * @author Patrick DUSHIMIMANA
 */
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Team name is required")
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Team coach is required")
    @Column(nullable = false)
    private String coach;

    @NotNull(message = "Foundation date is required")
    @Column(nullable = false)
    private LocalDate foundationDate;

    // One-to-One relationship with Location
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    // One-to-Many relationship with Player
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Player> players = new ArrayList<>();

    // One-to-Many relationship with TrainingSession
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TrainingSession> trainingSessions = new ArrayList<>();

    // Constructors
    public Team() {
    }

    public Team(String name, String coach, LocalDate foundationDate) {
        this.name = name;
        this.coach = coach;
        this.foundationDate = foundationDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public LocalDate getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(LocalDate foundationDate) {
        this.foundationDate = foundationDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        if (location != null) {
            location.setTeam(this);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<TrainingSession> getTrainingSessions() {
        return trainingSessions;
    }

    public void setTrainingSessions(List<TrainingSession> trainingSessions) {
        this.trainingSessions = trainingSessions;
    }

    // Helper methods for bidirectional relationships
    public void addPlayer(Player player) {
        players.add(player);
        player.setTeam(this);
    }

    public void removePlayer(Player player) {
        players.remove(player);
        player.setTeam(null);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coach='" + coach + '\'' +
                ", foundationDate=" + foundationDate +
                ", location=" + location +
                '}';
    }
}
