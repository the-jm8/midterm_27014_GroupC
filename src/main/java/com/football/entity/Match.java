package com.football.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Match Entity
 * 
 * Represents a football match between two teams.
 * 
 * - Field validation for match details
 * - LocalDateTime for match scheduling
 * 
 * @author Patrick DUSHIMIMANA
 */
@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Home team name is required")
    @Column(nullable = false)
    private String homeTeamName;

    @NotBlank(message = "Away team name is required")
    @Column(nullable = false)
    private String awayTeamName;

    @NotNull(message = "Match date and time is required")
    @Column(nullable = false)
    private LocalDateTime matchDateTime;

    @NotNull(message = "Home team score is required")
    @Min(value = 0, message = "Score cannot be negative")
    @Column(nullable = false)
    private Integer homeTeamScore;

    @NotNull(message = "Away team score is required")
    @Min(value = 0, message = "Score cannot be negative")
    @Column(nullable = false)
    private Integer awayTeamScore;

    @NotBlank(message = "Match status is required")
    @Column(nullable = false)
    private String matchStatus; // e.g., "Scheduled", "In Progress", "Completed"

    // Many-to-Many relationship with Player
    @ManyToMany(mappedBy = "matches", fetch = FetchType.LAZY)
    private List<Player> players = new ArrayList<>();

    // Constructors
    public Match() {
    }

    public Match(String homeTeamName, String awayTeamName, LocalDateTime matchDateTime,
            Integer homeTeamScore, Integer awayTeamScore, String matchStatus) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.matchDateTime = matchDateTime;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        this.matchStatus = matchStatus;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public LocalDateTime getMatchDateTime() {
        return matchDateTime;
    }

    public void setMatchDateTime(LocalDateTime matchDateTime) {
        this.matchDateTime = matchDateTime;
    }

    public Integer getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(Integer homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public Integer getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(Integer awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    // Helper methods for Many-to-Many relationship
    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", homeTeamName='" + homeTeamName + '\'' +
                ", awayTeamName='" + awayTeamName + '\'' +
                ", matchDateTime=" + matchDateTime +
                ", homeTeamScore=" + homeTeamScore +
                ", awayTeamScore=" + awayTeamScore +
                ", matchStatus='" + matchStatus + '\'' +
                ", playersCount=" + players.size() +
                '}';
    }
}
