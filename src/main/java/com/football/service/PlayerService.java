package com.football.service;

import com.football.entity.Player;
import com.football.repository.PlayerRepository;
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
 * Player Service
 * 
 * Business logic layer for Player operations with comprehensive
 * functionality including province-based retrieval.
 * 
 * @author Patrick DUSHIMIMANA
 */
@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

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
     * @return the player if found
     */
    public Player getPlayer(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));
    }

    /**
     * Create a new player
     * 
     * @param player the player to create
     * @return the created player
     */
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    /**
     * Update an existing player
     * 
     * @param id the player ID
     * @param playerDetails the updated player details
     * @return the updated player
     */
    public Player updatePlayer(Long id, Player playerDetails) {
        Player player = getPlayer(id);
        player.setFirstName(playerDetails.getFirstName());
        player.setLastName(playerDetails.getLastName());
        player.setAge(playerDetails.getAge());
        player.setPosition(playerDetails.getPosition());
        player.setJerseyNumber(playerDetails.getJerseyNumber());
        player.setJoinDate(playerDetails.getJoinDate());
        player.setTeam(playerDetails.getTeam());
        return playerRepository.save(player);
    }

    /**
     * Delete a player by ID
     * 
     * @param id the player ID
     */
    public void deletePlayer(Long id) {
        Player player = getPlayer(id);
        playerRepository.delete(player);
    }

    /**
     * Check if player exists by jersey number
     * 
     * This method demonstrates the existsBy() functionality for players.
     * It returns true if a player with the given jersey number exists, false otherwise.
     * 
     * @param jerseyNumber the jersey number to check
     * @return true if player exists, false otherwise
     */
    public boolean existsByJerseyNumber(Integer jerseyNumber) {
        return playerRepository.existsByJerseyNumber(jerseyNumber);
    }

    /**
     * Find player by jersey number
     * 
     * @param jerseyNumber the jersey number
     * @return optional player if found
     */
    public Optional<Player> findByJerseyNumber(Integer jerseyNumber) {
        return playerRepository.findByJerseyNumber(jerseyNumber);
    }

    /**
     * Find players by team
     * 
     * @param team the team
     * @return list of players in the specified team
     */
    public List<Player> getPlayersByTeam(com.football.entity.Team team) {
        return playerRepository.findByTeam(team);
    }

    /**
     * Find players by position
     * 
     * @param position the player position
     * @return list of players in the specified position
     */
    public List<Player> getPlayersByPosition(String position) {
        return playerRepository.findByPosition(position);
    }

    /**
     * Find players by position with sorting by age
     * 
     * This method demonstrates sorting functionality using Spring Data JPA Sort object.
     * Players are sorted by age in ascending order.
     * 
     * @param position the player position
     * @return list of players sorted by age
     */
    public List<Player> getPlayersByPositionSortedByAge(String position) {
        Sort sort = Sort.by(Sort.Direction.ASC, "age");
        return playerRepository.findByPositionOrderByAgeAsc(position, sort);
    }

    /**
     * Find players by age range
     * 
     * @param minAge the minimum age
     * @param maxAge the maximum age
     * @return list of players within the age range
     */
    public List<Player> getPlayersByAgeRange(Integer minAge, Integer maxAge) {
        return playerRepository.findByAgeBetween(minAge, maxAge);
    }

    /**
     * Find players by join date range
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of players who joined within the date range
     */
    public List<Player> getPlayersByJoinDateRange(LocalDate startDate, LocalDate endDate) {
        return playerRepository.findByJoinDateBetween(startDate, endDate);
    }

    /**
     * Find players by first name (case insensitive)
     * 
     * @param firstName the first name
     * @return list of players with the specified first name
     */
    public List<Player> getPlayersByFirstName(String firstName) {
        return playerRepository.findByFirstNameIgnoreCase(firstName);
    }

    /**
     * Find players by last name (case insensitive)
     * 
     * @param lastName the last name
     * @return list of players with the specified last name
     */
    public List<Player> getPlayersByLastName(String lastName) {
        return playerRepository.findByLastNameIgnoreCase(lastName);
    }

    /**
     * Find players with their performance stats (eager loading)
     * 
     * This method demonstrates eager loading to avoid N+1 query problems.
     * It fetches players along with their performance statistics in a single query.
     * 
     * @return list of players with performance stats
     */
    public List<Player> getPlayersWithPerformanceStats() {
        return playerRepository.findAllWithPerformanceStats();
    }

    /**
     * Find players by team name
     * 
     * This method demonstrates JOIN operations for retrieving players
     * based on their team name.
     * 
     * @param teamName the team name
     * @return list of players in the specified team
     */
    public List<Player> getPlayersByTeamName(String teamName) {
        return playerRepository.findByTeamName(teamName);
    }

    /**
     * Find players by team name with pagination and sorting
     * 
     * This method demonstrates comprehensive pagination and sorting functionality.
     * It uses Spring Data JPA Pageable for pagination and sorting support.
     * 
     * @param teamName the team name
     * @param page the page number (0-based)
     * @param size the page size
     * @param sortField the field to sort by
     * @param sortDirection the sort direction (ASC or DESC)
     * @return paginated result of players in the specified team
     */
    public Page<Player> getPlayersByTeamNameWithPagination(
            String teamName,
            int page,
            int size,
            String sortField,
            String sortDirection) {
        
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return playerRepository.findByTeamNameWithPagination(teamName, pageable);
    }

    /**
     * Find players by position and team
     * 
     * @param position the player position
     * @param team the team
     * @return list of players with the specified position in the team
     */
    public List<Player> getPlayersByPositionAndTeam(String position, com.football.entity.Team team) {
        return playerRepository.findByPositionAndTeam(position, team);
    }

    /**
     * Find players older than specified age
     * 
     * @param age the age threshold
     * @return list of players older than the specified age
     */
    public List<Player> getPlayersOlderThan(Integer age) {
        return playerRepository.findByAgeGreaterThan(age);
    }

    /**
     * Find players who joined after a specific date
     * 
     * @param date the join date threshold
     * @return list of players who joined after the specified date
     */
    public List<Player> getPlayersJoinedAfter(LocalDate date) {
        return playerRepository.findByJoinDateAfter(date);
    }

    /**
     * Find top goal scorers with pagination
     * 
     * This method demonstrates aggregate queries with JOIN operations.
     * It returns players with their total goals scored, sorted in descending order.
     * 
     * @param page the page number (0-based)
     * @param size the page size
     * @return paginated result of top goal scorers with their total goals
     */
    public Page<Object[]> getTopGoalScorers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return playerRepository.findTopGoalScorers(pageable);
    }

    /**
     * Find players by province (NEW FUNCTIONALITY)
     * 
     * This method retrieves all players from a given province using province code OR province name.
     * It demonstrates complex JOIN operations across multiple entities:
     * Player → Team → Location
     * 
     * @param province the province name or code
     * @return list of players from the specified province
     */
    public List<Player> getPlayersByProvince(String province) {
        // This would require a custom query in the repository
        // For now, we'll implement it using existing methods
        // In a real implementation, this would be a JPQL query like:
        // "SELECT p FROM Player p JOIN p.team t JOIN t.location l WHERE l.province = :province"
        
        // For demonstration purposes, we'll return an empty list
        // The actual implementation would be in the repository
        return playerRepository.findPlayersByProvince(province);
    }

    /**
     * Find players by province with pagination and sorting (NEW FUNCTIONALITY)
     * 
     * This method retrieves all players from a given province with pagination and sorting.
     * It demonstrates comprehensive functionality for the assessment requirement.
     * 
     * @param province the province name or code
     * @param page the page number (0-based)
     * @param size the page size
     * @param sortField the field to sort by
     * @param sortDirection the sort direction (ASC or DESC)
     * @return paginated result of players from the specified province
     */
    public Page<Player> getPlayersByProvinceWithPagination(
            String province,
            int page,
            int size,
            String sortField,
            String sortDirection) {
        // This would require a custom query in the repository with pagination support
        // For demonstration purposes, we'll return an empty page
        // The actual implementation would be in the repository
        return playerRepository.findPlayersByProvinceWithPagination(province, page, size, sortField, sortDirection);
    }

    /**
     * Find players by district (NEW FUNCTIONALITY)
     * 
     * This method retrieves all players from a given district.
     * It demonstrates JOIN operations across multiple entities.
     * 
     * @param district the district name
     * @return list of players from the specified district
     */
    public List<Player> getPlayersByDistrict(String district) {
        return playerRepository.findPlayersByDistrict(district);
    }

    /**
     * Find players by stadium (NEW FUNCTIONALITY)
     * 
     * This method retrieves all players who play at a specific stadium.
     * It demonstrates JOIN operations across multiple entities.
     * 
     * @param stadiumName the stadium name
     * @return list of players who play at the specified stadium
     */
    public List<Player> getPlayersByStadium(String stadiumName) {
        return playerRepository.findPlayersByStadium(stadiumName);
    }
}