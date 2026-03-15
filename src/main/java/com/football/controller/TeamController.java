package com.football.controller;

import com.football.entity.Team;
import com.football.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    /**
     * Get all teams
     * 
     * @return list of all teams
     */
    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    /**
     * Get team by ID
     * 
     * @param id the team ID
     * @return the team if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        Team team = teamService.getTeamById(id);
        return ResponseEntity.ok(team);
    }

    /**
     * Create a new team
     * 
     * @param team the team to create
     * @return the created team
     */
    @PostMapping
    public Team createTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }

    /**
     * Update an existing team
     * 
     * @param id the team ID
     * @param teamDetails the updated team details
     * @return the updated team
     */
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(
            @PathVariable Long id,
            @RequestBody Team teamDetails) {
        Team updatedTeam = teamService.updateTeam(id, teamDetails);
        return ResponseEntity.ok(updatedTeam);
    }

    /**
     * Delete a team by ID
     * 
     * @param id the team ID
     * @return success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return ResponseEntity.ok(response);
    }

    /**
     * Check if team exists by name
     * 
     * This method demonstrates the existsBy() functionality.
     * It returns true if a team with the given name exists, false otherwise.
     * 
     * @param name the team name to check
     * @return existence status and team name
     */
    @GetMapping("/exists/name/{name}")
    public ResponseEntity<Map<String, Object>> existsByName(@PathVariable String name) {
        boolean exists = teamService.existsByName(name);
        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        response.put("teamName", name);
        return ResponseEntity.ok(response);
    }

    /**
     * Find team by name
     * 
     * @param name the team name
     * @return optional team if found
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<Optional<Team>> getTeamByName(@PathVariable String name) {
        Optional<Team> team = teamService.findByName(name);
        return ResponseEntity.ok(team);
    }

    /**
     * Find teams by coach with sorting
     * 
     * This method demonstrates sorting functionality using Spring Data JPA.
     * Teams are sorted by foundation date in descending order (newest first).
     * 
     * @param coach the coach name
     * @return list of teams sorted by foundation date
     */
    @GetMapping("/coach/{coach}/sorted")
    public ResponseEntity<List<Team>> getTeamsByCoachSorted(@PathVariable String coach) {
        List<Team> teams = teamService.getTeamsByCoachSorted(coach);
        return ResponseEntity.ok(teams);
    }

    /**
     * Find teams by foundation date range
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of teams founded within the date range
     */
    @GetMapping("/foundation-date-range")
    public ResponseEntity<List<Team>> getTeamsByFoundationDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<Team> teams = teamService.getTeamsByFoundationDateRange(startDate, endDate);
        return ResponseEntity.ok(teams);
    }

    /**
     * Find teams by location province
     * 
     * This method demonstrates JOIN operations for retrieving teams
     * based on their location's province.
     * 
     * @param province the province name
     * @return list of teams in the specified province
     */
    @GetMapping("/province/{province}")
    public ResponseEntity<List<Team>> getTeamsByProvince(@PathVariable String province) {
        List<Team> teams = teamService.getTeamsByProvince(province);
        return ResponseEntity.ok(teams);
    }

    /**
     * Find teams by location province with pagination and sorting
     * 
     * This method demonstrates comprehensive pagination and sorting functionality.
     * It uses Spring Data JPA Pageable for pagination and sorting support.
     * 
     * @param province the province name
     * @param page the page number (0-based)
     * @param size the page size
     * @param sortField the field to sort by
     * @param sortDirection the sort direction (ASC or DESC)
     * @return paginated result of teams in the specified province
     */
    @GetMapping("/province/{province}/paged")
    public ResponseEntity<Page<Team>> getTeamsByProvinceWithPagination(
            @PathVariable String province,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        
        Page<Team> teams = teamService.getTeamsByProvinceWithPagination(
                province, page, size, sortField, sortDirection);
        return ResponseEntity.ok(teams);
    }

    /**
     * Find teams by location district
     * 
     * @param district the district name
     * @return list of teams in the specified district
     */
    @GetMapping("/district/{district}")
    public ResponseEntity<List<Team>> getTeamsByDistrict(@PathVariable String district) {
        List<Team> teams = teamService.getTeamsByDistrict(district);
        return ResponseEntity.ok(teams);
    }

    /**
     * Find teams by location stadium name
     * 
     * @param stadiumName the stadium name
     * @return list of teams using the specified stadium
     */
    @GetMapping("/stadium/{stadiumName}")
    public ResponseEntity<List<Team>> getTeamsByStadium(@PathVariable String stadiumName) {
        List<Team> teams = teamService.getTeamsByStadium(stadiumName);
        return ResponseEntity.ok(teams);
    }

    /**
     * Find teams with their locations (eager loading)
     * 
     * This method demonstrates eager loading to avoid N+1 query problems.
     * It fetches teams along with their location data in a single query.
     * 
     * @return list of teams with their locations
     */
    @GetMapping("/with-locations")
    public ResponseEntity<List<Team>> getTeamsWithLocations() {
        List<Team> teams = teamService.getTeamsWithLocations();
        return ResponseEntity.ok(teams);
    }

    /**
     * Find teams with their players (eager loading)
     * 
     * This method demonstrates eager loading for One-to-Many relationships.
     * It fetches teams along with their players in a single query.
     * 
     * @return list of teams with their players
     */
    @GetMapping("/with-players")
    public ResponseEntity<List<Team>> getTeamsWithPlayers() {
        List<Team> teams = teamService.getTeamsWithPlayers();
        return ResponseEntity.ok(teams);
    }

    /**
     * Find teams with their training sessions (eager loading)
     * 
     * This method demonstrates eager loading for One-to-Many relationships.
     * It fetches teams along with their training sessions in a single query.
     * 
     * @return list of teams with their training sessions
     */
    @GetMapping("/with-training-sessions")
    public ResponseEntity<List<Team>> getTeamsWithTrainingSessions() {
        List<Team> teams = teamService.getTeamsWithTrainingSessions();
        return ResponseEntity.ok(teams);
    }

    /**
     * Find teams with all related data (eager loading)
     * 
     * This method demonstrates comprehensive eager loading to fetch
     * teams with their locations, players, and training sessions.
     * 
     * @return list of teams with all related data
     */
    @GetMapping("/with-all-data")
    public ResponseEntity<List<Team>> getTeamsWithAllData() {
        List<Team> teams = teamService.getTeamsWithAllData();
        return ResponseEntity.ok(teams);
    }

    /**
     * Find teams by name pattern (case insensitive)
     * 
     * @param namePattern the name pattern (supports % wildcards)
     * @return list of teams matching the name pattern
     */
    @GetMapping("/name-pattern/{namePattern}")
    public ResponseEntity<List<Team>> getTeamsByNamePattern(@PathVariable String namePattern) {
        List<Team> teams = teamService.getTeamsByNamePattern(namePattern);
        return ResponseEntity.ok(teams);
    }

    /**
     * Find teams by coach pattern (case insensitive)
     * 
     * @param coachPattern the coach pattern (supports % wildcards)
     * @return list of teams with coaches matching the pattern
     */
    @GetMapping("/coach-pattern/{coachPattern}")
    public ResponseEntity<List<Team>> getTeamsByCoachPattern(@PathVariable String coachPattern) {
        List<Team> teams = teamService.getTeamsByCoachPattern(coachPattern);
        return ResponseEntity.ok(teams);
    }

    /**
     * Find teams founded after a specific date
     * 
     * @param date the foundation date threshold
     * @return list of teams founded after the specified date
     */
    @GetMapping("/founded-after/{date}")
    public ResponseEntity<List<Team>> getTeamsFoundedAfter(@PathVariable LocalDate date) {
        List<Team> teams = teamService.getTeamsFoundedAfter(date);
        return ResponseEntity.ok(teams);
    }

    /**
     * Find teams founded before a specific date
     * 
     * @param date the foundation date threshold
     * @return list of teams founded before the specified date
     */
    @GetMapping("/founded-before/{date}")
    public ResponseEntity<List<Team>> getTeamsFoundedBefore(@PathVariable LocalDate date) {
        List<Team> teams = teamService.getTeamsFoundedBefore(date);
        return ResponseEntity.ok(teams);
    }

    /**
     * Find teams by multiple criteria with pagination
     * 
     * This method demonstrates complex querying with multiple criteria
     * and pagination support.
     * 
     * @param province the province (optional)
     * @param coach the coach name (optional)
     * @param minFoundationDate minimum foundation date (optional)
     * @param maxFoundationDate maximum foundation date (optional)
     * @param page the page number (0-based)
     * @param size the page size
     * @param sortField the field to sort by
     * @param sortDirection the sort direction (ASC or DESC)
     * @return paginated result of teams matching the criteria
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Team>> getTeamsByCriteria(
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String coach,
            @RequestParam(required = false) LocalDate minFoundationDate,
            @RequestParam(required = false) LocalDate maxFoundationDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        
        Page<Team> teams = teamService.getTeamsByCriteria(
                province, coach, minFoundationDate, maxFoundationDate,
                page, size, sortField, sortDirection);
        return ResponseEntity.ok(teams);
    }

    /**
     * Find the oldest team (founded earliest)
     * 
     * @return the oldest team
     */
    @GetMapping("/oldest")
    public ResponseEntity<Optional<Team>> getOldestTeam() {
        Optional<Team> team = teamService.getOldestTeam();
        return ResponseEntity.ok(team);
    }

    /**
     * Find the newest team (founded latest)
     * 
     * @return the newest team
     */
    @GetMapping("/newest")
    public ResponseEntity<Optional<Team>> getNewestTeam() {
        Optional<Team> team = teamService.getNewestTeam();
        return ResponseEntity.ok(team);
    }

    /**
     * Get teams with player count statistics
     * 
     * This method demonstrates aggregate queries with JOIN operations.
     * It returns teams along with their player count for statistical analysis.
     * 
     * @return list of teams with player counts
     */
    @GetMapping("/with-player-count")
    public ResponseEntity<List<Object[]>> getTeamsWithPlayerCount() {
        List<Object[]> teamsWithCount = teamService.getTeamsWithPlayerCount();
        return ResponseEntity.ok(teamsWithCount);
    }

    /**
     * Get teams with training session count statistics
     * 
     * This method demonstrates aggregate queries with JOIN operations.
     * It returns teams along with their training session count for statistical analysis.
     * 
     * @return list of teams with training session counts
     */
    @GetMapping("/with-training-session-count")
    public ResponseEntity<List<Object[]>> getTeamsWithTrainingSessionCount() {
        List<Object[]> teamsWithCount = teamService.getTeamsWithTrainingSessionCount();
        return ResponseEntity.ok(teamsWithCount);
    }
}