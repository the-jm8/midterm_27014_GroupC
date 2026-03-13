package com.football.service;

import com.football.entity.PerformanceStat;
import com.football.entity.Player;
import com.football.repository.PerformanceStatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PerformanceStatService {

    private final PerformanceStatRepository performanceStatRepository;

    public PerformanceStatService(PerformanceStatRepository performanceStatRepository) {
        this.performanceStatRepository = performanceStatRepository;
    }

    /**
     * Get all performance stats
     * 
     * @return list of all performance stats
     */
    public List<PerformanceStat> getAllStats() {
        return performanceStatRepository.findAll();
    }

    /**
     * Get performance stat by ID
     * 
     * @param id the performance stat ID
     * @return the performance stat
     * @throws RuntimeException if performance stat not found
     */
    public PerformanceStat getStat(Long id) {
        return performanceStatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Performance stat not found with id: " + id));
    }

    /**
     * Get performance stat by ID (returns PerformanceStat)
     * 
     * @param id the performance stat ID
     * @return the performance stat
     * @throws RuntimeException if performance stat not found
     */
    public PerformanceStat getPerformanceStatById(Long id) {
        return performanceStatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Performance stat not found with id: " + id));
    }

    /**
     * Create a new performance stat
     * 
     * @param stat the performance stat to create
     * @return the created performance stat
     */
    public PerformanceStat createPerformanceStat(PerformanceStat stat) {
        return performanceStatRepository.save(stat);
    }

    /**
     * Save or update a performance stat
     * 
     * @param stat the performance stat to save
     * @return the saved performance stat
     */
    public PerformanceStat saveStat(PerformanceStat stat) {
        return performanceStatRepository.save(stat);
    }

    /**
     * Update an existing performance stat
     * 
     * @param id          the performance stat ID
     * @param statDetails the updated performance stat details
     * @return the updated performance stat
     * @throws RuntimeException if performance stat not found
     */
    public PerformanceStat updatePerformanceStat(Long id, PerformanceStat statDetails) {
        PerformanceStat stat = performanceStatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Performance stat not found with id: " + id));

        stat.setGoalsScored(statDetails.getGoalsScored());
        stat.setAssists(statDetails.getAssists());
        stat.setMinutesPlayed(statDetails.getMinutesPlayed());
        stat.setFitnessLevel(statDetails.getFitnessLevel());
        stat.setPerformanceDate(statDetails.getPerformanceDate());

        if (statDetails.getPlayer() != null) {
            stat.setPlayer(statDetails.getPlayer());
        }

        return performanceStatRepository.save(stat);
    }

    /**
     * Delete a performance stat
     * 
     * @param id the performance stat ID
     * @throws RuntimeException if performance stat not found
     */
    public void deleteStat(Long id) {
        if (!performanceStatRepository.existsById(id)) {
            throw new RuntimeException("Performance stat not found with id: " + id);
        }
        performanceStatRepository.deleteById(id);
    }

    /**
     * Find performance stats by player
     * 
     * @param player the player
     * @return list of performance stats
     */
    public List<PerformanceStat> findByPlayer(Player player) {
        return performanceStatRepository.findByPlayer(player);
    }

    /**
     * Find performance stats by goals scored
     * 
     * @param goalsScored the number of goals scored
     * @return list of performance stats
     */
    public List<PerformanceStat> findByGoalsScored(Integer goalsScored) {
        return performanceStatRepository.findByGoalsScored(goalsScored);
    }

    /**
     * Find performance stats by assists
     * 
     * @param assists the number of assists
     * @return list of performance stats
     */
    public List<PerformanceStat> findByAssists(Integer assists) {
        return performanceStatRepository.findByAssists(assists);
    }

    /**
     * Find performance stats by fitness level
     * 
     * @param fitnessLevel the fitness level
     * @return list of performance stats
     */
    public List<PerformanceStat> findByFitnessLevel(Integer fitnessLevel) {
        return performanceStatRepository.findByFitnessLevel(fitnessLevel);
    }

    /**
     * Find performance stats by date range
     * 
     * @param startDate start date
     * @param endDate   end date
     * @return list of performance stats
     */
    public List<PerformanceStat> findByPerformanceDateBetween(LocalDate startDate, LocalDate endDate) {
        return performanceStatRepository.findByPerformanceDateBetween(startDate, endDate);
    }

    /**
     * Find performance stats by minutes played
     * 
     * @param minutesPlayed the minutes played
     * @return list of performance stats
     */
    public List<PerformanceStat> findByMinutesPlayed(Integer minutesPlayed) {
        return performanceStatRepository.findByMinutesPlayed(minutesPlayed);
    }

    /**
     * Find performance stats by player with sorting
     * 
     * @param player the player
     * @param sort   sorting criteria
     * @return list of performance stats sorted
     */
    public List<PerformanceStat> findByPlayerSorted(Player player, Sort sort) {
        return performanceStatRepository.findByPlayerOrderByPerformanceDateDesc(player, sort);
    }

    /**
     * Find performance stats with high fitness level
     * 
     * @param minFitnessLevel the minimum fitness level
     * @return list of performance stats
     */
    public List<PerformanceStat> findByFitnessLevelGreaterThanEqual(Integer minFitnessLevel) {
        return performanceStatRepository.findByFitnessLevelGreaterThanEqual(minFitnessLevel);
    }

    /**
     * Find performance stats with high goal count
     * 
     * @param minGoals the minimum number of goals
     * @return list of performance stats
     */
    public List<PerformanceStat> findByGoalsScoredGreaterThanEqual(Integer minGoals) {
        return performanceStatRepository.findByGoalsScoredGreaterThanEqual(minGoals);
    }

    /**
     * Find all performance stats with player details
     * 
     * @return list of performance stats with players
     */
    public List<PerformanceStat> findAllWithPlayer() {
        return performanceStatRepository.findAllWithPlayer();
    }

    /**
     * Find performance stats by player name
     * 
     * @param playerName the player's full name
     * @return list of performance stats
     */
    public List<PerformanceStat> findByPlayerName(String playerName) {
        return performanceStatRepository.findByPlayerName(playerName);
    }

    /**
     * Find performance stats by player name with pagination
     * 
     * @param playerName the player's full name
     * @param pageable   pagination info
     * @return page of performance stats
     */
    public Page<PerformanceStat> findByPlayerNameWithPagination(String playerName, Pageable pageable) {
        return performanceStatRepository.findByPlayerNameWithPagination(playerName, pageable);
    }

    /**
     * Find performance stats by team
     * 
     * @param teamName the team name
     * @return list of performance stats
     */
    public List<PerformanceStat> findByTeamName(String teamName) {
        return performanceStatRepository.findByTeamName(teamName);
    }

    /**
     * Find performance stats after a specific date
     * 
     * @param date the date threshold
     * @return list of performance stats
     */
    public List<PerformanceStat> findByPerformanceDateAfter(LocalDate date) {
        return performanceStatRepository.findByPerformanceDateAfter(date);
    }

    /**
     * Find average performance by player
     * 
     * @return list of players with their average performance statistics
     */
    public List<Object[]> findAveragePerformanceByPlayer() {
        return performanceStatRepository.findAveragePerformanceByPlayer();
    }
}
