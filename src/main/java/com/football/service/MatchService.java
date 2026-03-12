package com.football.service;

import com.football.entity.Match;
import com.football.repository.MatchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Match Service
 * 
 * Business logic layer for Match entity operations.
 * 
 * This service demonstrates:
 * - CRUD operations
 * - Business validation
 * - Transaction management
 * - Integration with MatchRepository
 * 
 * @author Patrick DUSHIMIMANA
 */
@Service
@Transactional
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    /**
     * Get all matches
     * 
     * @return list of all matches
     */
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    /**
     * Get match by ID
     * 
     * @param id the match ID
     * @return the match
     * @throws RuntimeException if match not found
     */
    public Match getMatch(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + id));
    }

    /**
     * Get match by ID (returns Match)
     * 
     * @param id the match ID
     * @return the match
     * @throws RuntimeException if match not found
     */
    public Match getMatchById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + id));
    }

    /**
     * Create a new match
     * 
     * @param match the match to create
     * @return the created match
     */
    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }

    /**
     * Save or update a match
     * 
     * @param match the match to save
     * @return the saved match
     */
    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

    /**
     * Update an existing match
     * 
     * @param id           the match ID
     * @param matchDetails the updated match details
     * @return the updated match
     * @throws RuntimeException if match not found
     */
    public Match updateMatch(Long id, Match matchDetails) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + id));

        match.setHomeTeamName(matchDetails.getHomeTeamName());
        match.setAwayTeamName(matchDetails.getAwayTeamName());
        match.setMatchDateTime(matchDetails.getMatchDateTime());
        match.setHomeTeamScore(matchDetails.getHomeTeamScore());
        match.setAwayTeamScore(matchDetails.getAwayTeamScore());
        match.setMatchStatus(matchDetails.getMatchStatus());

        return matchRepository.save(match);
    }

    /**
     * Delete a match
     * 
     * @param id the match ID
     * @throws RuntimeException if match not found
     */
    public void deleteMatch(Long id) {
        if (!matchRepository.existsById(id)) {
            throw new RuntimeException("Match not found with id: " + id);
        }
        matchRepository.deleteById(id);
    }

    /**
     * Find matches by home team name
     * 
     * @param homeTeamName the home team name
     * @return list of matches
     */
    public List<Match> findByHomeTeamName(String homeTeamName) {
        return matchRepository.findByHomeTeamName(homeTeamName);
    }

    /**
     * Find matches by away team name
     * 
     * @param awayTeamName the away team name
     * @return list of matches
     */
    public List<Match> findByAwayTeamName(String awayTeamName) {
        return matchRepository.findByAwayTeamName(awayTeamName);
    }

    /**
     * Find matches by match status
     * 
     * @param matchStatus the match status
     * @return list of matches
     */
    public List<Match> findByMatchStatus(String matchStatus) {
        return matchRepository.findByMatchStatus(matchStatus);
    }

    /**
     * Find matches by date range
     * 
     * @param startDate start date and time
     * @param endDate   end date and time
     * @return list of matches
     */
    public List<Match> findByMatchDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return matchRepository.findByMatchDateTimeBetween(startDate, endDate);
    }

    /**
     * Find matches by home team and status
     * 
     * @param homeTeamName the home team name
     * @param matchStatus  the match status
     * @return list of matches
     */
    public List<Match> findByHomeTeamNameAndMatchStatus(String homeTeamName, String matchStatus) {
        return matchRepository.findByHomeTeamNameAndMatchStatus(homeTeamName, matchStatus);
    }

    /**
     * Find matches by away team and status
     * 
     * @param awayTeamName the away team name
     * @param matchStatus  the match status
     * @return list of matches
     */
    public List<Match> findByAwayTeamNameAndMatchStatus(String awayTeamName, String matchStatus) {
        return matchRepository.findByAwayTeamNameAndMatchStatus(awayTeamName, matchStatus);
    }

    /**
     * Find matches by date with sorting
     * 
     * @param matchDateTime the match date and time
     * @param sort          sorting criteria
     * @return list of matches sorted
     */
    public List<Match> findByMatchDateTimeSorted(LocalDateTime matchDateTime, Sort sort) {
        return matchRepository.findByMatchDateTimeOrderByMatchDateTimeDesc(matchDateTime, sort);
    }

    /**
     * Find all matches with players
     * 
     * @return list of matches with players
     */
    public List<Match> findAllWithPlayers() {
        return matchRepository.findAllWithPlayers();
    }

    /**
     * Find matches by team name (either home or away)
     * 
     * @param teamName the team name
     * @return list of matches
     */
    public List<Match> findByTeamName(String teamName) {
        return matchRepository.findByTeamName(teamName);
    }

    /**
     * Find matches by team name with pagination
     * 
     * @param teamName the team name
     * @param pageable pagination info
     * @return page of matches
     */
    public Page<Match> findByTeamNameWithPagination(String teamName, Pageable pageable) {
        return matchRepository.findByTeamNameWithPagination(teamName, pageable);
    }

    /**
     * Find matches by score range
     * 
     * @param minScore minimum score
     * @param maxScore maximum score
     * @return list of matches
     */
    public List<Match> findByTotalScoreBetween(Integer minScore, Integer maxScore) {
        return matchRepository.findByTotalScoreBetween(minScore, maxScore);
    }

    /**
     * Find matches won by home team
     * 
     * @return list of matches
     */
    public List<Match> findHomeTeamWins() {
        return matchRepository.findHomeTeamWins();
    }

    /**
     * Find matches won by away team
     * 
     * @return list of matches
     */
    public List<Match> findAwayTeamWins() {
        return matchRepository.findAwayTeamWins();
    }

    /**
     * Find matches that ended in a draw
     * 
     * @return list of matches
     */
    public List<Match> findDraws() {
        return matchRepository.findDraws();
    }

    /**
     * Find matches after a specific date
     * 
     * @param date the date threshold
     * @return list of matches
     */
    public List<Match> findByMatchDateTimeAfter(LocalDateTime date) {
        return matchRepository.findByMatchDateTimeAfter(date);
    }
}
