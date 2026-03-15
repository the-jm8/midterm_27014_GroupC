package com.football.service;

import com.football.entity.Team;
import com.football.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Team Service
 * 
 * Business logic layer for Team operations with comprehensive
 * sorting and pagination functionality.
 * 
 * @author Patrick DUSHIMIMANA
 */
@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    /**
     * Get all teams
     * 
     * @return list of all teams
     */
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    /**
     * Get team by ID
     * 
     * @param id the team ID
     * @return the team if found
     */
    public Team getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
    }

    /**
     * Create a new team
     * 
     * @param team the team to create
     * @return the created team
     */
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    /**
     * Update an existing team
     * 
     * @param id the team ID
     * @param teamDetails the updated team details
     * @return the updated team
     */
    public Team updateTeam(Long id, Team teamDetails) {
        Team team = getTeamById(id);
        team.setName(teamDetails.getName());
        team.setCoach(teamDetails.getCoach());
        team.setFoundationDate(teamDetails.getFoundationDate());
        team.setLocation(teamDetails.getLocation());
        return teamRepository.save(team);
    }

    /**
     * Delete a team by ID
     * 
     * @param id the team ID
     */
    public void deleteTeam(Long id) {
        Team team = getTeamById(id);
        teamRepository.delete(team);
    }

    /**
     * Check if team exists by name
     * 
     * @param name the team name
     * @return true if team exists, false otherwise
     */
    public boolean existsByName(String name) {
        return teamRepository.existsByName(name);
    }

    /**
     * Find team by name
     * 
     * @param name the team name
     * @return optional team if found
     */
    public Optional<Team> findByName(String name) {
        return teamRepository.findByName(name);
    }

    /**
     * Find teams by coach with sorting
     * 
     * This method demonstrates sorting functionality using Spring Data JPA Sort object.
     * Teams are sorted by foundation date in descending order (newest first).
     * 
     * @param coach the coach name
     * @return list of teams sorted by foundation date
     */
    public List<Team> getTeamsByCoachSorted(String coach) {
        Sort sort = Sort.by(Sort.Direction.DESC, "foundationDate");
        return teamRepository.findByCoachOrderByFoundationDateDesc(coach, sort);
    }

    /**
     * Find teams by foundation date range
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of teams founded within the date range
     */
    public List<Team> getTeamsByFoundationDateRange(LocalDate startDate, LocalDate endDate) {
        return teamRepository.findByFoundationDateBetween(startDate, endDate);
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
    public List<Team> getTeamsByProvince(String province) {
        return teamRepository.findByLocationProvince(province);
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
    public Page<Team> getTeamsByProvinceWithPagination(
            String province, 
            int page, 
            int size, 
            String sortField, 
            String sortDirection) {
        
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return teamRepository.findByLocationProvinceWithPagination(province, pageable);
    }

    /**
     * Find teams by location district
     * 
     * @param district the district name
     * @return list of teams in the specified district
     */
    public List<Team> getTeamsByDistrict(String district) {
        return teamRepository.findByLocationDistrict(district);
    }

    /**
     * Find teams by location stadium name
     * 
     * @param stadiumName the stadium name
     * @return list of teams using the specified stadium
     */
    public List<Team> getTeamsByStadium(String stadiumName) {
        return teamRepository.findByLocationStadiumName(stadiumName);
    }

    /**
     * Find teams with their locations (eager loading)
     * 
     * This method demonstrates eager loading to avoid N+1 query problems.
     * It fetches teams along with their location data in a single query.
     * 
     * @return list of teams with their locations
     */
    public List<Team> getTeamsWithLocations() {
        return teamRepository.findAllWithLocations();
    }

    /**
     * Find teams with their players (eager loading)
     * 
     * This method demonstrates eager loading for One-to-Many relationships.
     * It fetches teams along with their players in a single query.
     * 
     * @return list of teams with their players
     */
    public List<Team> getTeamsWithPlayers() {
        return teamRepository.findAllWithPlayers();
    }

    /**
     * Find teams with their training sessions (eager loading)
     * 
     * This method demonstrates eager loading for One-to-Many relationships.
     * It fetches teams along with their training sessions in a single query.
     * 
     * @return list of teams with their training sessions
     */
    public List<Team> getTeamsWithTrainingSessions() {
        return teamRepository.findAllWithTrainingSessions();
    }

    /**
     * Find teams with all related data (eager loading)
     * 
     * This method demonstrates comprehensive eager loading to fetch
     * teams with their locations, players, and training sessions.
     * 
     * @return list of teams with all related data
     */
    public List<Team> getTeamsWithAllData() {
        return teamRepository.findAllWithAllData();
    }

    /**
     * Find teams by name pattern (case insensitive)
     * 
     * @param namePattern the name pattern (supports % wildcards)
     * @return list of teams matching the name pattern
     */
    public List<Team> getTeamsByNamePattern(String namePattern) {
        return teamRepository.findByNameLikeIgnoreCase(namePattern);
    }

    /**
     * Find teams by coach pattern (case insensitive)
     * 
     * @param coachPattern the coach pattern (supports % wildcards)
     * @return list of teams with coaches matching the pattern
     */
    public List<Team> getTeamsByCoachPattern(String coachPattern) {
        return teamRepository.findByCoachLikeIgnoreCase(coachPattern);
    }

    /**
     * Find teams founded after a specific date
     * 
     * @param date the foundation date threshold
     * @return list of teams founded after the specified date
     */
    public List<Team> getTeamsFoundedAfter(LocalDate date) {
        return teamRepository.findByFoundationDateAfter(date);
    }

    /**
     * Find teams founded before a specific date
     * 
     * @param date the foundation date threshold
     * @return list of teams founded before the specified date
     */
    public List<Team> getTeamsFoundedBefore(LocalDate date) {
        return teamRepository.findByFoundationDateBefore(date);
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
    public Page<Team> getTeamsByCriteria(
            String province,
            String coach,
            LocalDate minFoundationDate,
            LocalDate maxFoundationDate,
            int page,
            int size,
            String sortField,
            String sortDirection) {
        
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return teamRepository.findTeamsByCriteria(
                province, coach, minFoundationDate, maxFoundationDate, pageable);
    }

    /**
     * Find the oldest team (founded earliest)
     * 
     * @return the oldest team
     */
    public Optional<Team> getOldestTeam() {
        return teamRepository.findOldestTeam();
    }

    /**
     * Find the newest team (founded latest)
     * 
     * @return the newest team
     */
    public Optional<Team> getNewestTeam() {
        return teamRepository.findNewestTeam();
    }

    /**
     * Get teams with player count statistics
     * 
     * This method demonstrates aggregate queries with JOIN operations.
     * It returns teams along with their player count for statistical analysis.
     * 
     * @return list of teams with player counts
     */
    public List<Object[]> getTeamsWithPlayerCount() {
        return teamRepository.findTeamsWithPlayerCount();
    }

    /**
     * Get teams with training session count statistics
     * 
     * This method demonstrates aggregate queries with JOIN operations.
     * It returns teams along with their training session count for statistical analysis.
     * 
     * @return list of teams with training session counts
     */
    public List<Object[]> getTeamsWithTrainingSessionCount() {
        return teamRepository.findTeamsWithTrainingSessionCount();
    }
}