package com.football.service;

import com.football.entity.Location;
import com.football.repository.LocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Get all locations
     * 
     * @return list of all locations
     */
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    /**
     * Get location by ID
     * 
     * @param id the location ID
     * @return the location
     * @throws RuntimeException if location not found
     */
    public Location getLocation(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
    }

    /**
     * Get location by ID (returns Location)
     * 
     * @param id the location ID
     * @return the location
     * @throws RuntimeException if location not found
     */
    public Location getLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
    }

    /**
     * Create a new location
     * 
     * @param location the location to create
     * @return the created location
     */
    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    /**
     * Save or update a location
     * 
     * @param location the location to save
     * @return the saved location
     */
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    /**
     * Update an existing location
     * 
     * @param id              the location ID
     * @param locationDetails the updated location details
     * @return the updated location
     * @throws RuntimeException if location not found
     */
    public Location updateLocation(Long id, Location locationDetails) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));

        location.setProvince(locationDetails.getProvince());
        location.setDistrict(locationDetails.getDistrict());
        location.setStadiumName(locationDetails.getStadiumName());

        return locationRepository.save(location);
    }

    /**
     * Delete a location
     * 
     * @param id the location ID
     * @throws RuntimeException if location not found
     */
    public void deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new RuntimeException("Location not found with id: " + id);
        }
        locationRepository.deleteById(id);
    }

    /**
     * Check if location exists by province
     * 
     * @param province the province name
     * @return true if exists, false otherwise
     */
    public boolean existsByProvince(String province) {
        return locationRepository.existsByProvince(province);
    }

    /**
     * Check if location exists by district
     * 
     * @param district the district name
     * @return true if exists, false otherwise
     */
    public boolean existsByDistrict(String district) {
        return locationRepository.existsByDistrict(district);
    }

    /**
     * Find locations by province
     * 
     * @param province the province name
     * @return list of locations
     */
    public List<Location> findByProvince(String province) {
        return locationRepository.findByProvince(province);
    }

    /**
     * Find locations by district
     * 
     * @param district the district name
     * @return list of locations
     */
    public List<Location> findByDistrict(String district) {
        return locationRepository.findByDistrict(district);
    }

    /**
     * Find locations by stadium name
     * 
     * @param stadiumName the stadium name
     * @return list of locations
     */
    public List<Location> findByStadiumName(String stadiumName) {
        return locationRepository.findByStadiumName(stadiumName);
    }

    /**
     * Find locations by province with pagination
     * 
     * @param province the province name
     * @param pageable pagination info
     * @return page of locations
     */
    public Page<Location> findByProvinceWithPagination(String province, Pageable pageable) {
        return locationRepository.findByProvinceWithPagination(province, pageable);
    }

    /**
     * Find locations by province or district
     * 
     * @param province the province name
     * @param district the district name
     * @return list of locations
     */
    public List<Location> findByProvinceOrDistrict(String province, String district) {
        return locationRepository.findByProvinceOrDistrict(province, district);
    }

    /**
     * Find location by province and district
     * 
     * @param province the province name
     * @param district the district name
     * @return optional location
     */
    public Optional<Location> findByProvinceAndDistrict(String province, String district) {
        return locationRepository.findByProvinceAndDistrict(province, district);
    }
}
