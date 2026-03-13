package com.football.controller;

import com.football.entity.Location;
import com.football.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getLocationById(id));
    }

    @PostMapping
    public Location createLocation(@RequestBody Location location) {
        return locationService.createLocation(location);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(
            @PathVariable Long id,
            @RequestBody Location locationDetails) {

        return ResponseEntity.ok(locationService.updateLocation(id, locationDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteLocation(@PathVariable Long id) {

        locationService.deleteLocation(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists/province/{province}")
    public ResponseEntity<Map<String, Object>> existsByProvince(@PathVariable String province) {

        boolean exists = locationService.existsByProvince(province);

        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        response.put("province", province);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists/district/{district}")
    public ResponseEntity<Map<String, Object>> existsByDistrict(@PathVariable String district) {

        boolean exists = locationService.existsByDistrict(district);

        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        response.put("district", district);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/province/{province}")
    public ResponseEntity<List<Location>> getLocationsByProvince(@PathVariable String province) {
        return ResponseEntity.ok(locationService.findByProvince(province));
    }

    @GetMapping("/district/{district}")
    public ResponseEntity<List<Location>> getLocationsByDistrict(@PathVariable String district) {
        return ResponseEntity.ok(locationService.findByDistrict(district));
    }

    @GetMapping("/stadium/{stadiumName}")
    public ResponseEntity<List<Location>> getLocationsByStadium(@PathVariable String stadiumName) {
        return ResponseEntity.ok(locationService.findByStadiumName(stadiumName));
    }

    @GetMapping("/province/{province}/paged")
    public ResponseEntity<Page<Location>> getLocationsByProvinceWithPagination(
            @PathVariable String province,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "district") String sort,
            @RequestParam(defaultValue = "ASC") String direction) {

        Sort.Direction sortDirection =
                direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));

        return ResponseEntity.ok(
                locationService.findByProvinceWithPagination(province, pageable)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<Location>> searchLocations(
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String district) {

        if (province == null && district == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Location> locations;

        if (province != null && district != null) {
            locations = locationService.findByProvinceOrDistrict(province, district);
        } else if (province != null) {
            locations = locationService.findByProvince(province);
        } else {
            locations = locationService.findByDistrict(district);
        }

        return ResponseEntity.ok(locations);
    }
}
