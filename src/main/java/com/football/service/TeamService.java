package com.football.service;

import com.football.entity.Team;
import com.football.repository.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Team Service
 * 
 * Business logic layer for Team entity operations.
 * 
 * This service demonstrates:
 * - CRUD operations
 * - Business validation
 * - Transaction management
 * - Integration with TeamRepository
 * 
 * @author Patrick DUSHIMIMANA
 */
@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

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
     * @return the team
     * @throws RuntimeException if team not found
     */
    public Team getTeam(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
    }

    /**
     * Get team by ID (returns Team)
     * 
     * @param id the team ID
     * @return the team
     * @throws RuntimeException if team not found
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
     * @throws RuntimeException if team name already exists
     */
    public Team createTeam(Team team) {
        if (teamRepository.existsByName(team.getName())) {
            throw new RuntimeException("Team with name " + team.getName() + " already exists");
        }
        return teamRepository.save(team);
    }

    /**
     * Save or update a team
     * 
     * @param team the team to save
     * @return the saved team
     */
    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    /**
     * Update an existing team
     * 
     * @param id          the team ID
     * @param teamDetails the updated team details
     * @return the updated team
     * @throws RuntimeException if team not found
     */
    public Team updateTeam(Long id, Team teamDetails) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));

        team.setName(teamDetails.getName());
        team.setCoach(teamDetails.getCoach());
        team.setFoundationDate(teamDetails.getFoundationDate());

        if (teamDetails.getLocation() != null) {
            team.setLocation(teamDetails.getLocation());
        }

        return teamRepository.save(team);
    }

    /**
     * Delete a team
     * 
     * @param id the team ID
     * @throws RuntimeException if team not found
     */
    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new RuntimeException("Team not found with id: " + id);
        }
        teamRepository.deleteById(id);
    }

    /**
     * Check if team exists by name
     * 
     * @param name the team name
     * @return true if exists, false otherwise
     */
    public boolean existsByName(String name) {
        return teamRepository.existsByName(name);
    }

    /**
     * Find team by name
     * 
     * @param name the team name
     * @return optional team
     */
    public Optional<Team> findByName(String name) {
        return teamRepository.findByNameIgnoreCase(name);
    }

    /**
     * Find teams by coach
     * 
     * @param coach the coach name
     * @return list of teams
     */
    public List<Team> findByCoach(String coach) {
        return teamRepository.findByCoach(coach);
    }

    /**
     * Find teams by coach with sorting
     * 
     * @param coach the coach name
     * @param sort  sorting criteria
     * @return list of teams sorted
     */
    public List<Team> findByCoachSorted(String coach, Sort sort) {
        return teamRepository.findByCoachOrderByFoundationDateDesc(coach, sort);
    }

    /**
     * Find teams by foundation date range
     * 
     * @param startDate start date
     * @param endDate   end date
     * @return list of teams
     */
    public List<Team> findByFoundationDateBetween(LocalDate startDate, LocalDate endDate) {
        return teamRepository.findByFoundationDateBetween(startDate, endDate);
    }

    /**
     * Find all teams with their locations
     * 
     * @return list of teams with locations
     */
    public List<Team> findAllWithLocations() {
        return teamRepository.findAllWithLocations();
    }

    /**
     * Find teams by province
     * 
     * @param province the province name
     * @return list of teams
     */
    public List<Team> findByProvince(String province) {
        return teamRepository.findByProvince(province);
    }

    /**
     * Find teams by province with pagination
     * 
     * @param province the province name
     * @param pageable pagination info
     * @return page of teams
     */
    public Page<Team> findByProvinceWithPagination(String province, Pageable pageable) {
        return teamRepository.findByProvinceWithPagination(province, pageable);
    }

    /**
     * Find teams by district
     * 
     * @param district the district name
     * @return list of teams
     */
    public List<Team> findByDistrict(String district) {
        return teamRepository.findByDistrict(district);
    }

    /**
     * Find teams by stadium name
     * 
     * @param stadiumName the stadium name
     * @return list of teams
     */
    public List<Team> findByStadiumName(String stadiumName) {
        return teamRepository.findByStadiumName(stadiumName);
    }

    /**
     * Find teams founded after a specific date
     * 
     * @param date the foundation date threshold
     * @return list of teams
     */
    public List<Team> findByFoundationDateAfter(LocalDate date) {
        return teamRepository.findByFoundationDateAfter(date);
    }

    /**
     * Find teams with player count greater than specified number
     * 
     * @param playerCount the minimum number of players
     * @return list of teams
     */
    public List<Team> findByPlayerCountGreaterThan(int playerCount) {
        return teamRepository.findByPlayerCountGreaterThan(playerCount);
    }
}
