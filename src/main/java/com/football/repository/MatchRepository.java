package com.football.repository;

import com.football.entity.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Match Repository
 * 
 * Handles database operations for Match entities.
 * 
 * This repository demonstrates:
 * - Basic CRUD operations through JpaRepository
 * - Many-to-Many relationship management
 * - Custom queries for match scheduling and results
 * - Sorting and pagination for match history
 * 
 * @author Patrick DUSHIMIMANA
 */
@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    /**
     * Find matches by home team name
     * 
     * @param homeTeamName the home team name
     * @return list of matches where the specified team is the home team
     */
    List<Match> findByHomeTeamName(String homeTeamName);

    /**
     * Find matches by away team name
     * 
     * @param awayTeamName the away team name
     * @return list of matches where the specified team is the away team
     */
    List<Match> findByAwayTeamName(String awayTeamName);

    /**
     * Find matches by match status
     * 
     * @param matchStatus the match status
     * @return list of matches with the specified status
     */
    List<Match> findByMatchStatus(String matchStatus);

    /**
     * Find matches by date range
     * 
     * @param startDate the start date and time
     * @param endDate   the end date and time
     * @return list of matches within the specified date range
     */
    List<Match> findByMatchDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find matches by home team and status
     * 
     * @param homeTeamName the home team name
     * @param matchStatus  the match status
     * @return list of matches for the specified home team with the status
     */
    List<Match> findByHomeTeamNameAndMatchStatus(String homeTeamName, String matchStatus);

    /**
     * Find matches by away team and status
     * 
     * @param awayTeamName the away team name
     * @param matchStatus  the match status
     * @return list of matches for the specified away team with the status
     */
    List<Match> findByAwayTeamNameAndMatchStatus(String awayTeamName, String matchStatus);

    /**
     * Find matches by date with sorting
     * 
     * This method demonstrates sorting functionality.
     * Matches are sorted by date and time in descending order (newest first).
     * 
     * @param matchDateTime the match date and time
     * @param sort          sorting criteria
     * @return list of matches sorted by date and time
     */
    List<Match> findByMatchDateTimeOrderByMatchDateTimeDesc(LocalDateTime matchDateTime, Sort sort);

    /**
     * Custom query to find matches with players
     * 
     * This method demonstrates JOIN operations for Many-to-Many relationships.
     * It retrieves matches along with the players who participated.
     * 
     * @return list of matches with player details
     */
    @Query("SELECT m FROM Match m LEFT JOIN FETCH m.players")
    List<Match> findAllWithPlayers();

    /**
     * Find matches by team name (either home or away)
     * 
     * This method demonstrates complex querying with OR conditions.
     * 
     * @param teamName the team name
     * @return list of matches where the specified team is either home or away
     */
    @Query("SELECT m FROM Match m WHERE m.homeTeamName = :teamName OR m.awayTeamName = :teamName")
    List<Match> findByTeamName(@Param("teamName") String teamName);

    /**
     * Find matches by team name with pagination and sorting
     * 
     * This method demonstrates advanced querying with pagination and sorting.
     * Matches are sorted by date and time in descending order.
     * 
     * @param teamName the team name
     * @param pageable pagination and sorting information
     * @return paginated result of matches for the specified team
     */
    @Query("SELECT m FROM Match m WHERE m.homeTeamName = :teamName OR m.awayTeamName = :teamName ORDER BY m.matchDateTime DESC")
    Page<Match> findByTeamNameWithPagination(@Param("teamName") String teamName, Pageable pageable);

    /**
     * Find matches by score range
     * 
     * @param minScore the minimum score
     * @param maxScore the maximum score
     * @return list of matches with scores within the specified range
     */
    @Query("SELECT m FROM Match m WHERE (m.homeTeamScore + m.awayTeamScore) BETWEEN :minScore AND :maxScore")
    List<Match> findByTotalScoreBetween(@Param("minScore") Integer minScore, @Param("maxScore") Integer maxScore);

    /**
     * Find matches won by home team
     * 
     * @return list of matches won by the home team
     */
    @Query("SELECT m FROM Match m WHERE m.homeTeamScore > m.awayTeamScore")
    List<Match> findHomeTeamWins();

    /**
     * Find matches won by away team
     * 
     * @return list of matches won by the away team
     */
    @Query("SELECT m FROM Match m WHERE m.awayTeamScore > m.homeTeamScore")
    List<Match> findAwayTeamWins();

    /**
     * Find matches that ended in a draw
     * 
     * @return list of matches that ended in a draw
     */
    @Query("SELECT m FROM Match m WHERE m.homeTeamScore = m.awayTeamScore")
    List<Match> findDraws();

    /**
     * Find matches after a specific date
     * 
     * @param date the date threshold
     * @return list of matches played after the specified date
     */
    List<Match> findByMatchDateTimeAfter(LocalDateTime date);
}