package com.football.repository;

import com.football.entity.TrainingSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * TrainingSession Repository
 * 
 * Handles database operations for TrainingSession entities.
 * 
 * This repository demonstrates:
 * - Basic CRUD operations through JpaRepository
 * - One-to-Many relationship management
 * - Custom queries for training session management
 * - Sorting and pagination for training history
 * 
 * @author Patrick DUSHIMIMANA
 */
@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    /**
     * Find training sessions by team
     * 
     * @param team the team
     * @return list of training sessions for the specified team
     */
    List<TrainingSession> findByTeam(com.football.entity.Team team);

    /**
     * Find training sessions by training type
     * 
     * @param trainingType the training type
     * @return list of training sessions with the specified type
     */
    List<TrainingSession> findByTrainingType(String trainingType);

    /**
     * Find training sessions by trainer name
     * 
     * @param trainerName the trainer name
     * @return list of training sessions conducted by the specified trainer
     */
    List<TrainingSession> findByTrainerName(String trainerName);

    /**
     * Find training sessions by location
     * 
     * @param location the training location
     * @return list of training sessions held at the specified location
     */
    List<TrainingSession> findByLocation(String location);

    /**
     * Find training sessions by date range
     * 
     * @param startDate the start date
     * @param endDate   the end date
     * @return list of training sessions within the specified date range
     */
    List<TrainingSession> findByTrainingDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find training sessions by duration range
     * 
     * @param minDuration the minimum duration in minutes
     * @param maxDuration the maximum duration in minutes
     * @return list of training sessions within the specified duration range
     */
    List<TrainingSession> findByDurationMinutesBetween(Integer minDuration, Integer maxDuration);

    /**
     * Find training sessions by start time range
     * 
     * @param startTime the start time
     * @param endTime   the end time
     * @return list of training sessions within the specified time range
     */
    List<TrainingSession> findByStartTimeBetween(LocalTime startTime, LocalTime endTime);

    /**
     * Find training sessions by team with sorting
     * 
     * This method demonstrates sorting functionality.
     * Training sessions are sorted by training date in descending order (newest
     * first).
     * 
     * @param team the team
     * @param sort sorting criteria
     * @return list of training sessions sorted by date
     */
    List<TrainingSession> findByTeamOrderByTrainingDateDesc(com.football.entity.Team team, Sort sort);

    /**
     * Find training sessions by training type and team
     * 
     * @param trainingType the training type
     * @param team         the team
     * @return list of training sessions with the specified type for the team
     */
    List<TrainingSession> findByTrainingTypeAndTeam(String trainingType, com.football.entity.Team team);

    /**
     * Custom query to find training sessions with team details
     * 
     * This method demonstrates JOIN operations in JPQL.
     * It retrieves training sessions along with team information.
     * 
     * @return list of training sessions with team details
     */
    @Query("SELECT ts FROM TrainingSession ts LEFT JOIN FETCH ts.team")
    List<TrainingSession> findAllWithTeam();

    /**
     * Find training sessions by team name
     * 
     * This method demonstrates complex querying with JOINs.
     * 
     * @param teamName the team name
     * @return list of training sessions for the specified team
     */
    @Query("SELECT ts FROM TrainingSession ts JOIN ts.team t WHERE t.name = :teamName")
    List<TrainingSession> findByTeamName(@Param("teamName") String teamName);

    /**
     * Find training sessions by team name with pagination and sorting
     * 
     * This method demonstrates advanced querying with pagination and sorting.
     * Training sessions are sorted by training date in descending order.
     * 
     * @param teamName the team name
     * @param pageable pagination and sorting information
     * @return paginated result of training sessions for the specified team
     */
    @Query("SELECT ts FROM TrainingSession ts JOIN ts.team t WHERE t.name = :teamName ORDER BY ts.trainingDate DESC")
    Page<TrainingSession> findByTeamNameWithPagination(@Param("teamName") String teamName, Pageable pageable);

    /**
     * Find training sessions by trainer and date
     * 
     * @param trainerName the trainer name
     * @param date        the training date
     * @return list of training sessions conducted by the trainer on the specified
     *         date
     */
    List<TrainingSession> findByTrainerNameAndTrainingDate(String trainerName, LocalDate date);

    /**
     * Find training sessions after a specific date
     * 
     * @param date the date threshold
     * @return list of training sessions held after the specified date
     */
    List<TrainingSession> findByTrainingDateAfter(LocalDate date);

    /**
     * Custom query to find training session statistics by team
     * 
     * This method demonstrates complex querying with aggregation.
     * It calculates average duration and session count for each team.
     * 
     * @return list of teams with their training session statistics
     */
    @Query("SELECT t, COUNT(ts), AVG(ts.durationMinutes) " +
            "FROM TrainingSession ts JOIN ts.team t " +
            "GROUP BY t.id, t.name")
    List<Object[]> findTrainingStatisticsByTeam();
}