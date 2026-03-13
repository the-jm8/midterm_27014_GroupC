package com.football.repository;

import com.football.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Location Repository
 * 
 * Handles database operations for Location entities.
 * 
 * 
 * @author Patrick DUSHIMIMANA
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    /**
     * Check if a location exists by province name
     * 
     * project.
     * It returns true if a location with the given province name exists, false
     * otherwise.
     * 
     * @param province the province name to check
     * @return true if location exists, false otherwise
     */
    boolean existsByProvince(String province);

    /**
     * Check if a location exists by district name
     * 
     * 
     * @param district the district name to check
     * @return true if location exists, false otherwise
     */
    boolean existsByDistrict(String district);

    /**
     * Find locations by province name
     * 
     * This method retrieves all locations in a specific province.
     * retrieval.
     * 
     * @param province the province name
     * @return list of locations in the specified province
     */
    List<Location> findByProvince(String province);

    /**
     * Find locations by district name
     * 
     * @param district the district name
     * @return list of locations in the specified district
     */
    List<Location> findByDistrict(String district);

    /**
     * Find locations by stadium name
     * 
     * @param stadiumName the stadium name
     * @return list of locations with the specified stadium name
     */
    List<Location> findByStadiumName(String stadiumName);

    /**
     * Custom query to find locations by province with pagination and sorting
     * 
     * It uses JPQL (Java Persistence Query Language) for custom queries.
     * 
     * @param province the province name
     * @param pageable pagination and sorting information
     * @return paginated result of locations in the specified province
     */
    @Query("SELECT l FROM Location l WHERE l.province = :province ORDER BY l.district, l.stadiumName")
    Page<Location> findByProvinceWithPagination(@Param("province") String province, Pageable pageable);

    /**
     * Custom query to find locations by province or district
     * 
     * 
     * @param province the province name
     * @param district the district name
     * @return list of locations matching either province or district
     */
    @Query("SELECT l FROM Location l WHERE l.province = :province OR l.district = :district")
    List<Location> findByProvinceOrDistrict(@Param("province") String province,
            @Param("district") String district);

    /**
     * Find location by province and district (unique combination)
     * 
     * @param province the province name
     * @param district the district name
     * @return optional location if found
     */
    Optional<Location> findByProvinceAndDistrict(String province, String district);
}
