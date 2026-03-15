package com.football.repository;

import com.football.entity.Player;
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
 * Player Repository
 * 
 * Handles database operations for Player entities.
 * 
 * 
 * @author Patrick DUSHIMIMANA
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    /**
     * Check if a player exists by jersey number
     * 
     * It prevents duplicate jersey numbers within the system.
     * 
     * @param jerseyNumber the jersey number to check
     * @return true if player exists, false otherwise
     */
    boolean existsByJerseyNumber(Integer jerseyNumber);

    /**
     * Find player by jersey number
     * 
     * @param jerseyNumber the jersey number
     * @return optional player if found
     */
    Optional<Player> findByJerseyNumber(Integer jerseyNumber);

    /**
     * Find players by team
     * 
     * @param team the team
     * @return list of players in the specified team
     */
    List<Player> findByTeam(com.football.entity.Team team);

    /**
     * Find players by position
     * 
     * @param position the player position
     * @return list of players in the specified position
     */
    List<Player> findByPosition(String position);

    /**
     * Find players by position with sorting
     * 
     * Players are sorted by age in ascending order.
     * 
     * @param position the player position
     * @param sort     sorting criteria
     * @return list of players sorted by age
     */
    List<Player> findByPositionOrderByAgeAsc(String position, Sort sort);

    /**
     * Find players by age range
     * 
     * @param minAge the minimum age
     * @param maxAge the maximum age
     * @return list of players within the age range
     */
    List<Player> findByAgeBetween(Integer minAge, Integer maxAge);

    /**
     * Find players by join date range
     * 
     * @param startDate the start date
     * @param endDate   the end date
     * @return list of players who joined within the date range
     */
    List<Player> findByJoinDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find players by first name (case insensitive)
     * 
     * @param firstName the first name
     * @return list of players with the specified first name
     */
    List<Player> findByFirstNameIgnoreCase(String firstName);

    /**
     * Find players by last name (case insensitive)
     * 
     * @param lastName the last name
     * @return list of players with the specified last name
     */
    List<Player> findByLastNameIgnoreCase(String lastName);

    /**
     * Custom query to find players with their performance stats
     * 
     * It retrieves players along with their performance statistics.
     * 
     * @return list of players with performance stats
     */
    @Query("SELECT p FROM Player p LEFT JOIN FETCH p.performanceStat")
    List<Player> findAllWithPerformanceStats();

    /**
     * Custom query to find players by team name
     * 
     * 
     * @param teamName the team name
     * @return list of players in the specified team
     */
    @Query("SELECT p FROM Player p JOIN p.team t WHERE t.name = :teamName")
    List<Player> findByTeamName(@Param("teamName") String teamName);

    /**
     * Find players by team name with pagination and sorting
     * 
     * Players are sorted by jersey number in ascending order.
     * 
     * @param teamName the team name
     * @param pageable pagination and sorting information
     * @return paginated result of players in the specified team
     */
    @Query("SELECT p FROM Player p JOIN p.team t WHERE t.name = :teamName ORDER BY p.jerseyNumber")
    Page<Player> findByTeamNameWithPagination(@Param("teamName") String teamName, Pageable pageable);

    /**
     * Find players by position and team
     * 
     * @param position the player position
     * @param team     the team
     * @return list of players with the specified position in the team
     */
    List<Player> findByPositionAndTeam(String position, com.football.entity.Team team);

    /**
     * Find players older than specified age
     * 
     * @param age the age threshold
     * @return list of players older than the specified age
     */
    List<Player> findByAgeGreaterThan(Integer age);

    /**
     * Find players who joined after a specific date
     * 
     * @param date the join date threshold
     * @return list of players who joined after the specified date
     */
    List<Player> findByJoinDateAfter(LocalDate date);

    /**
     * Custom query to find top goal scorers
     * 
     * It retrieves players with their total goals scored, sorted in descending
     * order.
     * 
     * @param pageable pagination information
     * @return paginated result of top goal scorers with their total goals
     */
    @Query("SELECT p, SUM(ps.goalsScored) as totalGoals " +
            "FROM Player p LEFT JOIN p.performanceStat ps " +
            "GROUP BY p.id, p.firstName, p.lastName " +
            "ORDER BY totalGoals DESC")
    Page<Object[]> findTopGoalScorers(Pageable pageable);

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
    @Query("SELECT p FROM Player p JOIN p.team t JOIN t.location l WHERE l.province = :province")
    List<Player> findPlayersByProvince(@Param("province") String province);

    /**
     * Find players by province with pagination and sorting (NEW FUNCTIONALITY)
     * 
     * This method retrieves all players from a given province with pagination and sorting.
     * It demonstrates comprehensive functionality for the assessment requirement.
     * 
     * @param province the province name or code
     * @param pageable pagination and sorting information
     * @return paginated result of players from the specified province
     */
    @Query("SELECT p FROM Player p JOIN p.team t JOIN t.location l WHERE l.province = :province ORDER BY p.firstName, p.lastName")
    Page<Player> findPlayersByProvinceWithPagination(@Param("province") String province, Pageable pageable);

    /**
     * Find players by district (NEW FUNCTIONALITY)
     * 
     * This method retrieves all players from a given district.
     * It demonstrates JOIN operations across multiple entities.
     * 
     * @param district the district name
     * @return list of players from the specified district
     */
    @Query("SELECT p FROM Player p JOIN p.team t JOIN t.location l WHERE l.district = :district")
    List<Player> findPlayersByDistrict(@Param("district") String district);

    /**
     * Find players by stadium (NEW FUNCTIONALITY)
     * 
     * This method retrieves all players who play at a specific stadium.
     * It demonstrates JOIN operations across multiple entities.
     * 
     * @param stadiumName the stadium name
     * @return list of players who play at the specified stadium
     */
    @Query("SELECT p FROM Player p JOIN p.team t JOIN t.location l WHERE l.stadiumName = :stadiumName")
    List<Player> findPlayersByStadium(@Param("stadiumName") String stadiumName);
}
