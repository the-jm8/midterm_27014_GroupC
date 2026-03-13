package com.football.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Location Entity
 * 
 * Represents geographical location information for football teams.
 * Stores province, district, and stadium name.
 * 
 * - Primary key generation strategy
 * - Basic field validation
 * - One-to-One relationship with Team
 * 
 * @author Patrick DUSHIMIMANA
 */
@Entity
@Table(name = "locations")
public class Location {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Province name is required")
    @Column(nullable = false)
    private String province;
    
    @NotBlank(message = "District name is required")
    @Column(nullable = false)
    private String district;
    
    @NotBlank(message = "Stadium name is required")
    @Column(nullable = false)
    private String stadiumName;
    
    // One-to-One relationship with Team
    @OneToOne(mappedBy = "location", cascade = CascadeType.ALL)
    private Team team;
    
    // Constructors
    public Location() {}
    
    public Location(String province, String district, String stadiumName) {
        this.province = province;
        this.district = district;
        this.stadiumName = stadiumName;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getProvince() {
        return province;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }
    
    public String getDistrict() {
        return district;
    }
    
    public void setDistrict(String district) {
        this.district = district;
    }
    
    public String getStadiumName() {
        return stadiumName;
    }
    
    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }
    
    public Team getTeam() {
        return team;
    }
    
    public void setTeam(Team team) {
        this.team = team;
    }
    
    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", province='" + province + '\'' +
                ", district='" + district + '\'' +
                ", stadiumName='" + stadiumName + '\'' +
                '}';
    }
}
