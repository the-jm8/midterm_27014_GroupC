package com.football.service;

import com.football.entity.Player;
import com.football.entity.Team;
import com.football.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Player Service
 * 
 * Business logic layer for Player entity operations.
 * 
 * This service demonstrates:
 * - CRUD operations
 * - Business validation
 * - Transaction management
 * - Integration with PlayerRepository
 * 
 * @author Patrick DUSHIMIMANA
 */
@Service
@Transactional
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Get all players
     * 
     * @return list of all players
     */
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    /**
     * Get player by ID
     * 
     * @param id the player ID
     * @return the player
     * @throws RuntimeException if player not found
     */
    public Player getPlayer(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));
    }

    /**
     * Get player by ID (returns Optional)
     * 
     * @param id the player ID
     * @return optional player
     */
    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    /**
     * Create a new player
     * 
     * @param player the player to create
     * @return the created player
     * @throws RuntimeException if jersey number already exists
     */
    public Player createPlayer(Player player) {
        if (playerRepository.existsByJerseyNumber(player.getJerseyNumber())) {
            throw new RuntimeException("Player with jersey number " + player.getJerseyNumber() + " already exists");
        }
        return playerRepository.save(player);
    }

    /**
     * Save or update a player
     * 
     * @param player the player to save
     * @return the saved player
     */
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    /**
     * Update an existing player
     * 
     * @param id            the player ID
     * @param playerDetails the updated player details
     * @return the updated player
     * @throws RuntimeException if player not found
     */
    public Player updatePlayer(Long id, Player playerDetails) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));

        player.setFirstName(playerDetails.getFirstName());
        player.setLastName(playerDetails.getLastName());
        player.setAge(playerDetails.getAge());
        player.setPosition(playerDetails.getPosition());
        player.setJerseyNumber(playerDetails.getJerseyNumber());
        player.setJoinDate(playerDetails.getJoinDate());

        if (playerDetails.getTeam() != null) {
            player.setTeam(playerDetails.getTeam());
        }

        return playerRepository.save(player);
    }

    /**
     * Delete a player
     * 
     * @param id the player ID
     * @throws RuntimeException if player not found
     */
    public void deletePlayer(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new RuntimeException("Player not found with id: " + id);
        }
        playerRepository.deleteById(id);
    }

    /**
     * Check if player exists by jersey number
     * 
     * @param jerseyNumber the jersey number
     * @return true if exists, false otherwise
     */
    public boolean existsByJerseyNumber(Integer jerseyNumber) {
        return playerRepository.existsByJerseyNumber(jerseyNumber);
    }

    /**
     * Find player by jersey number
     * 
     * @param jerseyNumber the jersey number
     * @return optional player
     */
    public Optional<Player> findByJerseyNumber(Integer jerseyNumber) {
        return playerRepository.findByJerseyNumber(jerseyNumber);
    }

    /**
     * Find players by team
     * 
     * @param team the team
     * @return list of players
     */
    public List<Player> findByTeam(Team team) {
        return playerRepository.findByTeam(team);
    }

    /**
     * Find players by position
     * 
     * @param position the position
     * @return list of players
     */
    public List<Player> findByPosition(String position) {
        return playerRepository.findByPosition(position);
    }

    /**
     * Find players by position with sorting
     * 
     * @param position the position
     * @param sort     sorting criteria
     * @return list of players sorted
     */
    public List<Player> findByPositionSorted(String position, Sort sort) {
        return playerRepository.findByPositionOrderByAgeAsc(position, sort);
    }

    /**
     * Find players by age range
     * 
     * @param minAge minimum age
     * @param maxAge maximum age
     * @return list of players
     */
    public List<Player> findByAgeBetween(Integer minAge, Integer maxAge) {
        return playerRepository.findByAgeBetween(minAge, maxAge);
    }

    /**
     * Find players by join date range
     * 
     * @param startDate start date
     * @param endDate   end date
     * @return list of players
     */
    public List<Player> findByJoinDateBetween(LocalDate startDate, LocalDate endDate) {
        return playerRepository.findByJoinDateBetween(startDate, endDate);
    }

    /**
     * Find players by first name
     * 
     * @param firstName the first name
     * @return list of players
     */
    public List<Player> findByFirstName(String firstName) {
        return playerRepository.findByFirstNameIgnoreCase(firstName);
    }

    /**
     * Find players by last name
     * 
     * @param lastName the last name
     * @return list of players
     */
    public List<Player> findByLastName(String lastName) {
        return playerRepository.findByLastNameIgnoreCase(lastName);
    }

    /**
     * Find all players with their performance stats
     * 
     * @return list of players with stats
     */
    public List<Player> findAllWithPerformanceStats() {
        return playerRepository.findAllWithPerformanceStats();
    }

    /**
     * Find players by team name
     * 
     * @param teamName the team name
     * @return list of players
     */
    public List<Player> findByTeamName(String teamName) {
        return playerRepository.findByTeamName(teamName);
    }

    /**
     * Find players by team name with pagination
     * 
     * @param teamName the team name
     * @param pageable pagination info
     * @return page of players
     */
    public Page<Player> findByTeamNameWithPagination(String teamName, Pageable pageable) {
        return playerRepository.findByTeamNameWithPagination(teamName, pageable);
    }

    /**
     * Find players by position and team
     * 
     * @param position the position
     * @param team     the team
     * @return list of players
     */
    public List<Player> findByPositionAndTeam(String position, Team team) {
        return playerRepository.findByPositionAndTeam(position, team);
    }

    /**
     * Find players older than specified age
     * 
     * @param age the age threshold
     * @return list of players
     */
    public List<Player> findByAgeGreaterThan(Integer age) {
        return playerRepository.findByAgeGreaterThan(age);
    }

    /**
     * Find players who joined after a specific date
     * 
     * @param date the join date threshold
     * @return list of players
     */
    public List<Player> findByJoinDateAfter(LocalDate date) {
        return playerRepository.findByJoinDateAfter(date);
    }

    /**
     * Find top goal scorers
     * 
     * @param pageable pagination info
     * @return list of top scorers
     */
    public List<Object[]> findTopGoalScorers(Pageable pageable) {
        return playerRepository.findTopGoalScorers(pageable);
    }
}
