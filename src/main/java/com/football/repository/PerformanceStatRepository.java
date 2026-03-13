package com.football.repository;

import com.football.entity.PerformanceStat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * PerformanceStat Repository
 * 
 * Handles database operations for PerformanceStat entities.
 * 
 * - One-to-One relationship management
 * 
 * @author Patrick DUSHIMIMANA
 */
@Repository
public interface PerformanceStatRepository extends JpaRepository<PerformanceStat, Long> {

    /**
     * Find performance stats by player
     * 
     * @param player the player
     * @return list of performance stats for the specified player
     */
    List<PerformanceStat> findByPlayer(com.football.entity.Player player);

    /**
     * Find performance stats by goals scored
     * 
     * @param goalsScored the number of goals scored
     * @return list of performance stats with the specified goals scored
     */
    List<PerformanceStat> findByGoalsScored(Integer goalsScored);

    /**
     * Find performance stats by assists
     * 
     * @param assists the number of assists
     * @return list of performance stats with the specified assists
     */
    List<PerformanceStat> findByAssists(Integer assists);

    /**
     * Find performance stats by fitness level
     * 
     * @param fitnessLevel the fitness level
     * @return list of performance stats with the specified fitness level
     */
    List<PerformanceStat> findByFitnessLevel(Integer fitnessLevel);

    /**
     * Find performance stats by date range
     * 
     * @param startDate the start date
     * @param endDate   the end date
     * @return list of performance stats within the specified date range
     */
    List<PerformanceStat> findByPerformanceDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find performance stats by minutes played
     * 
     * @param minutesPlayed the minutes played
     * @return list of performance stats with the specified minutes played
     */
    List<PerformanceStat> findByMinutesPlayed(Integer minutesPlayed);

    /**
     * Find performance stats by player with sorting
     * 
     * Performance stats are sorted by performance date in descending order (newest
     * first).
     * 
     * @param player the player
     * @param sort   sorting criteria
     * @return list of performance stats sorted by date
     */
    List<PerformanceStat> findByPlayerOrderByPerformanceDateDesc(com.football.entity.Player player, Sort sort);

    /**
     * Find performance stats with high fitness level
     * 
     * @param minFitnessLevel the minimum fitness level
     * @return list of performance stats with fitness level above the threshold
     */
    List<PerformanceStat> findByFitnessLevelGreaterThanEqual(Integer minFitnessLevel);

    /**
     * Find performance stats with high goal count
     * 
     * @param minGoals the minimum number of goals
     * @return list of performance stats with goals above the threshold
     */
    List<PerformanceStat> findByGoalsScoredGreaterThanEqual(Integer minGoals);

    /**
     * Custom query to find performance stats with player details
     * 
     * It retrieves performance stats along with player information.
     * 
     * @return list of performance stats with player details
     */
    @Query("SELECT ps FROM PerformanceStat ps LEFT JOIN FETCH ps.player")
    List<PerformanceStat> findAllWithPlayer();

    /**
     * Find performance stats by player name
     * 
     * 
     * @param playerName the player's full name
     * @return list of performance stats for the specified player
     */
    @Query("SELECT ps FROM PerformanceStat ps JOIN ps.player p WHERE p.firstName || ' ' || p.lastName = :playerName")
    List<PerformanceStat> findByPlayerName(@Param("playerName") String playerName);

    /**
     * Find performance stats by player name with pagination and sorting
     * 
     * Performance stats are sorted by performance date in descending order.
     * 
     * @param playerName the player's full name
     * @param pageable   pagination and sorting information
     * @return paginated result of performance stats for the specified player
     */
    @Query("SELECT ps FROM PerformanceStat ps JOIN ps.player p WHERE p.firstName || ' ' || p.lastName = :playerName ORDER BY ps.performanceDate DESC")
    Page<PerformanceStat> findByPlayerNameWithPagination(@Param("playerName") String playerName, Pageable pageable);

    /**
     * Find performance stats by team
     * 
     * 
     * @param teamName the team name
     * @return list of performance stats for players in the specified team
     */
    @Query("SELECT ps FROM PerformanceStat ps JOIN ps.player p JOIN p.team t WHERE t.name = :teamName")
    List<PerformanceStat> findByTeamName(@Param("teamName") String teamName);

    /**
     * Find performance stats after a specific date
     * 
     * @param date the date threshold
     * @return list of performance stats recorded after the specified date
     */
    List<PerformanceStat> findByPerformanceDateAfter(LocalDate date);

    /**
     * Custom query to find average performance by player
     * 
     * It calculates average goals, assists, and fitness level for each player.
     * 
     * @return list of players with their average performance statistics
     */
    @Query("SELECT p, AVG(ps.goalsScored), AVG(ps.assists), AVG(ps.fitnessLevel) " +
            "FROM PerformanceStat ps JOIN ps.player p " +
            "GROUP BY p.id, p.firstName, p.lastName")
    List<Object[]> findAveragePerformanceByPlayer();
}
