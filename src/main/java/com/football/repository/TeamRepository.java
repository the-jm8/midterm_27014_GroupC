package com.football.repository;

import com.football.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Team Repository
 * 
 * Handles database operations for Team entities.
 * 
 * 
 * @author Patrick DUSHIMIMANA
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    /**
     * Check if a team exists by name
     * 
     * It prevents duplicate team names in the database.
     * 
     * @param name the team name to check
     * @return true if team exists, false otherwise
     */
    boolean existsByName(String name);

    /**
     * Find team by name (case insensitive)
     * 
     * @param name the team name
     * @return optional team if found
     */
    Optional<Team> findByNameIgnoreCase(String name);

    /**
     * Find teams by coach name
     * 
     * @param coach the coach name
     * @return list of teams coached by the specified coach
     */
    List<Team> findByCoach(String coach);

    /**
     * Find teams by coach name with sorting
     * 
     * Teams are sorted by foundation date in descending order (newest first).
     * 
     * @param coach the coach name
     * @param sort  sorting criteria
     * @return list of teams sorted by foundation date
     */
    List<Team> findByCoachOrderByFoundationDateDesc(String coach, Sort sort);

    /**
     * Find teams by foundation date range
     * 
     * @param startDate the start date
     * @param endDate   the end date
     * @return list of teams founded within the date range
     */
    List<Team> findByFoundationDateBetween(java.time.LocalDate startDate, java.time.LocalDate endDate);

    /**
     * Custom query to find teams with their locations
     * 
     * It retrieves teams along with their location information.
     * 
     * @return list of teams with location details
     */
    @Query("SELECT t FROM Team t JOIN FETCH t.location")
    List<Team> findAllWithLocations();

    /**
     * Custom query to find teams by province
     * 
     * It retrieves teams located in a specific province.
     * 
     * @param province the province name
     * @return list of teams in the specified province
     */
    @Query("SELECT t FROM Team t JOIN t.location l WHERE l.province = :province")
    List<Team> findByProvince(@Param("province") String province);

    /**
     * Find teams by province with pagination and sorting
     * 
     * It's particularly useful for the province-based retrieval requirement.
     * 
     * @param province the province name
     * @param pageable pagination and sorting information
     * @return paginated result of teams in the specified province
     */
    @Query("SELECT t FROM Team t JOIN t.location l WHERE l.province = :province ORDER BY t.name")
    Page<Team> findByProvinceWithPagination(@Param("province") String province, Pageable pageable);

    /**
     * Find teams by district
     * 
     * @param district the district name
     * @return list of teams in the specified district
     */
    @Query("SELECT t FROM Team t JOIN t.location l WHERE l.district = :district")
    List<Team> findByDistrict(@Param("district") String district);

    /**
     * Find teams by stadium name
     * 
     * @param stadiumName the stadium name
     * @return list of teams using the specified stadium
     */
    @Query("SELECT t FROM Team t JOIN t.location l WHERE l.stadiumName = :stadiumName")
    List<Team> findByStadiumName(@Param("stadiumName") String stadiumName);

    /**
     * Find teams founded after a specific date
     * 
     * @param date the foundation date threshold
     * @return list of teams founded after the specified date
     */
    List<Team> findByFoundationDateAfter(java.time.LocalDate date);

    /**
     * Find teams with player count greater than specified number
     * 
     * 
     * @param playerCount the minimum number of players
     * @return list of teams with more players than the specified count
     */
    @Query("SELECT t FROM Team t WHERE SIZE(t.players) > :playerCount")
    List<Team> findByPlayerCountGreaterThan(@Param("playerCount") int playerCount);
}
