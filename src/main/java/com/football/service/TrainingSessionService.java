package com.football.service;

import com.football.entity.TrainingSession;
import com.football.entity.Team;
import com.football.repository.TrainingSessionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * TrainingSession Service
 * 
 * Business logic layer for TrainingSession entity operations.
 * 
 * 
 * @author Patrick DUSHIMIMANA
 */
@Service
@Transactional
public class TrainingSessionService {

    private final TrainingSessionRepository trainingSessionRepository;

    public TrainingSessionService(TrainingSessionRepository trainingSessionRepository) {
        this.trainingSessionRepository = trainingSessionRepository;
    }

    /**
     * Get all training sessions
     * 
     * @return list of all training sessions
     */
    public List<TrainingSession> getAllSessions() {
        return trainingSessionRepository.findAll();
    }

    /**
     * Get training session by ID
     * 
     * @param id the training session ID
     * @return the training session
     * @throws RuntimeException if training session not found
     */
    public TrainingSession getSession(Long id) {
        return trainingSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Training session not found with id: " + id));
    }

    /**
     * Get training session by ID (returns TrainingSession)
     * 
     * @param id the training session ID
     * @return the training session
     * @throws RuntimeException if training session not found
     */
    public TrainingSession getTrainingSessionById(Long id) {
        return trainingSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Training session not found with id: " + id));
    }

    /**
     * Create a new training session
     * 
     * @param session the training session to create
     * @return the created training session
     */
    public TrainingSession createTrainingSession(TrainingSession session) {
        return trainingSessionRepository.save(session);
    }

    /**
     * Save or update a training session
     * 
     * @param session the training session to save
     * @return the saved training session
     */
    public TrainingSession saveSession(TrainingSession session) {
        return trainingSessionRepository.save(session);
    }

    /**
     * Update an existing training session
     * 
     * @param id             the training session ID
     * @param sessionDetails the updated training session details
     * @return the updated training session
     * @throws RuntimeException if training session not found
     */
    public TrainingSession updateTrainingSession(Long id, TrainingSession sessionDetails) {
        TrainingSession session = trainingSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Training session not found with id: " + id));

        session.setTrainingType(sessionDetails.getTrainingType());
        session.setTrainingDate(sessionDetails.getTrainingDate());
        session.setStartTime(sessionDetails.getStartTime());
        session.setEndTime(sessionDetails.getEndTime());
        session.setLocation(sessionDetails.getLocation());
        session.setDurationMinutes(sessionDetails.getDurationMinutes());
        session.setTrainerName(sessionDetails.getTrainerName());

        if (sessionDetails.getTeam() != null) {
            session.setTeam(sessionDetails.getTeam());
        }

        return trainingSessionRepository.save(session);
    }

    /**
     * Delete a training session
     * 
     * @param id the training session ID
     * @throws RuntimeException if training session not found
     */
    public void deleteSession(Long id) {
        if (!trainingSessionRepository.existsById(id)) {
            throw new RuntimeException("Training session not found with id: " + id);
        }
        trainingSessionRepository.deleteById(id);
    }

    /**
     * Find training sessions by team
     * 
     * @param team the team
     * @return list of training sessions
     */
    public List<TrainingSession> findByTeam(Team team) {
        return trainingSessionRepository.findByTeam(team);
    }

    /**
     * Find training sessions by training type
     * 
     * @param trainingType the training type
     * @return list of training sessions
     */
    public List<TrainingSession> findByTrainingType(String trainingType) {
        return trainingSessionRepository.findByTrainingType(trainingType);
    }

    /**
     * Find training sessions by trainer name
     * 
     * @param trainerName the trainer name
     * @return list of training sessions
     */
    public List<TrainingSession> findByTrainerName(String trainerName) {
        return trainingSessionRepository.findByTrainerName(trainerName);
    }

    /**
     * Find training sessions by location
     * 
     * @param location the training location
     * @return list of training sessions
     */
    public List<TrainingSession> findByLocation(String location) {
        return trainingSessionRepository.findByLocation(location);
    }

    /**
     * Find training sessions by date range
     * 
     * @param startDate start date
     * @param endDate   end date
     * @return list of training sessions
     */
    public List<TrainingSession> findByTrainingDateBetween(LocalDate startDate, LocalDate endDate) {
        return trainingSessionRepository.findByTrainingDateBetween(startDate, endDate);
    }

    /**
     * Find training sessions by duration range
     * 
     * @param minDuration minimum duration in minutes
     * @param maxDuration maximum duration in minutes
     * @return list of training sessions
     */
    public List<TrainingSession> findByDurationMinutesBetween(Integer minDuration, Integer maxDuration) {
        return trainingSessionRepository.findByDurationMinutesBetween(minDuration, maxDuration);
    }

    /**
     * Find training sessions by start time range
     * 
     * @param startTime start time
     * @param endTime   end time
     * @return list of training sessions
     */
    public List<TrainingSession> findByStartTimeBetween(LocalTime startTime, LocalTime endTime) {
        return trainingSessionRepository.findByStartTimeBetween(startTime, endTime);
    }

    /**
     * Find training sessions by team with sorting
     * 
     * @param team the team
     * @param sort sorting criteria
     * @return list of training sessions sorted
     */
    public List<TrainingSession> findByTeamSorted(Team team, Sort sort) {
        return trainingSessionRepository.findByTeamOrderByTrainingDateDesc(team, sort);
    }

    /**
     * Find training sessions by training type and team
     * 
     * @param trainingType the training type
     * @param team         the team
     * @return list of training sessions
     */
    public List<TrainingSession> findByTrainingTypeAndTeam(String trainingType, Team team) {
        return trainingSessionRepository.findByTrainingTypeAndTeam(trainingType, team);
    }

    /**
     * Find all training sessions with team details
     * 
     * @return list of training sessions with teams
     */
    public List<TrainingSession> findAllWithTeam() {
        return trainingSessionRepository.findAllWithTeam();
    }

    /**
     * Find training sessions by team name
     * 
     * @param teamName the team name
     * @return list of training sessions
     */
    public List<TrainingSession> findByTeamName(String teamName) {
        return trainingSessionRepository.findByTeamName(teamName);
    }

    /**
     * Find training sessions by team name with pagination
     * 
     * @param teamName the team name
     * @param pageable pagination info
     * @return page of training sessions
     */
    public Page<TrainingSession> findByTeamNameWithPagination(String teamName, Pageable pageable) {
        return trainingSessionRepository.findByTeamNameWithPagination(teamName, pageable);
    }

    /**
     * Find training sessions by trainer and date
     * 
     * @param trainerName the trainer name
     * @param date        the training date
     * @return list of training sessions
     */
    public List<TrainingSession> findByTrainerNameAndTrainingDate(String trainerName, LocalDate date) {
        return trainingSessionRepository.findByTrainerNameAndTrainingDate(trainerName, date);
    }

    /**
     * Find training sessions after a specific date
     * 
     * @param date the date threshold
     * @return list of training sessions
     */
    public List<TrainingSession> findByTrainingDateAfter(LocalDate date) {
        return trainingSessionRepository.findByTrainingDateAfter(date);
    }

    /**
     * Find training session statistics by team
     * 
     * @return list of teams with their training session statistics
     */
    public List<Object[]> findTrainingStatisticsByTeam() {
        return trainingSessionRepository.findTrainingStatisticsByTeam();
    }
}
