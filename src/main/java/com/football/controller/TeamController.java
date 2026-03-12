package com.football.controller;

import com.football.entity.Team;
import com.football.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @PostMapping
    public Team createTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(
            @PathVariable Long id,
            @RequestBody Team teamDetails) {

        return ResponseEntity.ok(teamService.updateTeam(id, teamDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteTeam(@PathVariable Long id) {

        teamService.deleteTeam(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists/name/{name}")
    public ResponseEntity<Map<String, Object>> existsByName(@PathVariable String name) {

        boolean exists = teamService.existsByName(name);

        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        response.put("teamName", name);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/coach/{coach}")
    public ResponseEntity<List<Team>> getTeamsByCoach(@PathVariable String coach) {
        return ResponseEntity.ok(teamService.findByCoach(coach));
    }

    @GetMapping("/coach/{coach}/sorted")
    public ResponseEntity<List<Team>> getTeamsByCoachSorted(@PathVariable String coach) {

        Sort sort = Sort.by(Sort.Direction.DESC, "foundationDate");

        return ResponseEntity.ok(
                teamService.findByCoachSorted(coach, sort)
        );
    }

    @GetMapping("/founded-between")
    public ResponseEntity<List<Team>> getTeamsByFoundationDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return ResponseEntity.ok(
                teamService.findByFoundationDateBetween(start, end)
        );
    }

    @GetMapping("/with-locations")
    public ResponseEntity<List<Team>> getTeamsWithLocations() {
        return ResponseEntity.ok(teamService.findAllWithLocations());
    }

    @GetMapping("/province/{province}")
    public ResponseEntity<List<Team>> getTeamsByProvince(@PathVariable String province) {
        return ResponseEntity.ok(teamService.findByProvince(province));
    }

    @GetMapping("/province/{province}/paged")
    public ResponseEntity<Page<Team>> getTeamsByProvinceWithPagination(
            @PathVariable String province,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "ASC") String direction) {

        Sort.Direction sortDirection =
                direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));

        return ResponseEntity.ok(
                teamService.findByProvinceWithPagination(province, pageable)
        );
    }

    @GetMapping("/district/{district}")
    public ResponseEntity<List<Team>> getTeamsByDistrict(@PathVariable String district) {
        return ResponseEntity.ok(teamService.findByDistrict(district));
    }

    @GetMapping("/stadium/{stadiumName}")
    public ResponseEntity<List<Team>> getTeamsByStadium(@PathVariable String stadiumName) {
        return ResponseEntity.ok(teamService.findByStadiumName(stadiumName));
    }

    @GetMapping("/founded-after/{date}")
    public ResponseEntity<List<Team>> getTeamsFoundedAfter(@PathVariable String date) {

        LocalDate foundationDate = LocalDate.parse(date);

        return ResponseEntity.ok(
                teamService.findByFoundationDateAfter(foundationDate)
        );
    }

    @GetMapping("/with-more-players/{count}")
    public ResponseEntity<List<Team>> getTeamsWithMorePlayers(@PathVariable int count) {
        return ResponseEntity.ok(teamService.findByPlayerCountGreaterThan(count));
    }
}