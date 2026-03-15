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
 * Handles database operations for Team entities with comprehensive
 * sorting and pagination functionality.
 * 
 * @author Patrick DUSHIMIMANA
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    /**
     * Check if a team exists by name
     * 
     * @param name the team name to check
     * @return true if team exists, false otherwise
     */
    boolean existsByName(String name);

    /**
     * Find team by name
     * 
     * @param name the team name
     * @return optional team if found
     */
    Optional<Team> findByName(String name);

    /**
     * Find teams by coach name
     * 
     * @param coach the coach name
     * @return list of teams coached by the specified coach
     */
    List<Team> findByCoach(String coach);

    /**
     * Find teams by coach with sorting by foundation date (descending)
     * 
     * This method demonstrates sorting functionality using Spring Data JPA.
     * Teams are sorted by foundation date in descending order (newest first).
     * 
     * @param coach the coach name
     * @param sort sorting criteria
     * @return list of teams sorted by foundation date
     */
    List<Team> findByCoachOrderByFoundationDateDesc(String coach, Sort sort);

    /**
     * Find teams by foundation date range
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of teams founded within the date range
     */
    List<Team> findByFoundationDateBetween(java.time.LocalDate startDate, java.time.LocalDate endDate);

    /**
     * Find teams by location province
     * 
     * This method demonstrates JOIN operations for retrieving teams
     * based on their location's province.
     * 
     * @param province the province name
     * @return list of teams in the specified province
     */
    @Query("SELECT t FROM Team t JOIN t.location l WHERE l.province = :province")
    List<Team> findByLocationProvince(@Param("province") String province);

    /**
     * Find teams by location province with pagination and sorting
     * 
     * This method demonstrates comprehensive pagination and sorting functionality.
     * It uses JPQL for custom queries with pagination support.
     * 
     * @param province the province name
     * @param pageable pagination and sorting information
     * @return paginated result of teams in the specified province
     */
    @Query("SELECT t FROM Team t JOIN t.location l WHERE l.province = :province ORDER BY t.name")
    Page<Team> findByLocationProvinceWithPagination(@Param("province") String province, Pageable pageable);

    /**
     * Find teams by location district
     * 
     * @param district the district name
     * @return list of teams in the specified district
     */
    @Query("SELECT t FROM Team t JOIN t.location l WHERE l.district = :district")
    List<Team> findByLocationDistrict(@Param("district") String district);

    /**
     * Find teams by location stadium name
     * 
     * @param stadiumName the stadium name
     * @return list of teams using the specified stadium
     */
    @Query("SELECT t FROM Team t JOIN t.location l WHERE l.stadiumName = :stadiumName")
    List<Team> findByLocationStadiumName(@Param("stadiumName") String stadiumName);

    /**
     * Find teams with their locations (eager loading)
     * 
     * This method demonstrates eager loading to avoid N+1 query problems.
     * It fetches teams along with their location data in a single query.
     * 
     * @return list of teams with their locations
     */
    @Query("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.location")
    List<Team> findAllWithLocations();

    /**
     * Find teams with their players (eager loading)
     * 
     * This method demonstrates eager loading for One-to-Many relationships.
     * It fetches teams along with their players in a single query.
     * 
     * @return list of teams with their players
     */
    @Query("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.players")
    List<Team> findAllWithPlayers();

    /**
     * Find teams with their training sessions (eager loading)
     * 
     * This method demonstrates eager loading for One-to-Many relationships.
     * It fetches teams along with their training sessions in a single query.
     * 
     * @return list of teams with their training sessions
     */
    @Query("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.trainingSessions")
    List<Team> findAllWithTrainingSessions();

    /**
     * Find teams with all related data (eager loading)
     * 
     * This method demonstrates comprehensive eager loading to fetch
     * teams with their locations, players, and training sessions.
     * 
     * @return list of teams with all related data
     */
    @Query("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.location LEFT JOIN FETCH t.players LEFT JOIN FETCH t.trainingSessions")
    List<Team> findAllWithAllData();

    /**
     * Find teams by name pattern (case insensitive)
     * 
     * @param namePattern the name pattern (supports % wildcards)
     * @return list of teams matching the name pattern
     */
    @Query("SELECT t FROM Team t WHERE LOWER(t.name) LIKE LOWER(:namePattern)")
    List<Team> findByNameLikeIgnoreCase(@Param("namePattern") String namePattern);

    /**
     * Find teams by coach pattern (case insensitive)
     * 
     * @param coachPattern the coach pattern (supports % wildcards)
     * @return list of teams with coaches matching the pattern
     */
    @Query("SELECT t FROM Team t WHERE LOWER(t.coach) LIKE LOWER(:coachPattern)")
    List<Team> findByCoachLikeIgnoreCase(@Param("coachPattern") String coachPattern);

    /**
     * Find teams founded after a specific date
     * 
     * @param date the foundation date threshold
     * @return list of teams founded after the specified date
     */
    List<Team> findByFoundationDateAfter(java.time.LocalDate date);

    /**
     * Find teams founded before a specific date
     * 
     * @param date the foundation date threshold
     * @return list of teams founded before the specified date
     */
    List<Team> findByFoundationDateBefore(java.time.LocalDate date);

    /**
     * Custom query to find teams with player count
     * 
     * This method demonstrates aggregate queries with JOIN operations.
     * It returns teams along with their player count.
     * 
     * @return list of teams with player counts
     */
    @Query("SELECT t, COUNT(p) as playerCount FROM Team t LEFT JOIN t.players p GROUP BY t.id, t.name, t.coach, t.foundationDate, t.location ORDER BY playerCount DESC")
    List<Object[]> findTeamsWithPlayerCount();

    /**
     * Custom query to find teams with training session count
     * 
     * This method demonstrates aggregate queries with JOIN operations.
     * It returns teams along with their training session count.
     * 
     * @return list of teams with training session counts
     */
    @Query("SELECT t, COUNT(ts) as sessionCount FROM Team t LEFT JOIN t.trainingSessions ts GROUP BY t.id, t.name, t.coach, t.foundationDate, t.location ORDER BY sessionCount DESC")
    List<Object[]> findTeamsWithTrainingSessionCount();

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
     * @param pageable pagination and sorting information
     * @return paginated result of teams matching the criteria
     */
    @Query("SELECT t FROM Team t LEFT JOIN t.location l WHERE " +
           "(:province IS NULL OR l.province = :province) AND " +
           "(:coach IS NULL OR t.coach = :coach) AND " +
           "(:minFoundationDate IS NULL OR t.foundationDate >= :minFoundationDate) AND " +
           "(:maxFoundationDate IS NULL OR t.foundationDate <= :maxFoundationDate) " +
           "ORDER BY t.name")
    Page<Team> findTeamsByCriteria(
            @Param("province") String province,
            @Param("coach") String coach,
            @Param("minFoundationDate") java.time.LocalDate minFoundationDate,
            @Param("maxFoundationDate") java.time.LocalDate maxFoundationDate,
            Pageable pageable
    );

    /**
     * Find the oldest team (founded earliest)
     * 
     * @return the oldest team
     */
    @Query("SELECT t FROM Team t ORDER BY t.foundationDate ASC")
    Optional<Team> findOldestTeam();

    /**
     * Find the newest team (founded latest)
     * 
     * @return the newest team
     */
    @Query("SELECT t FROM Team t ORDER BY t.foundationDate DESC")
    Optional<Team> findNewestTeam();
}