# Viva-Voce Preparation Guide

## Football Team Performance Tracking System

### Project Overview

This Spring Boot application demonstrates all the required database relationships and functionalities as specified in the midterm project requirements.

### 1. Entity Relationship Diagram (ERD) - 6 Tables

**Tables Implemented:**
1. **Location** - Stores province, district, and stadium information
2. **Team** - Football teams with location references
3. **Player** - Team players with performance tracking
4. **Match** - Match details and results
5. **PerformanceStat** - Player performance per match
6. **TrainingSession** - Team training sessions

**Relationships Explained:**

#### One-to-One Relationship: Location ↔ Team
```java
// In Location entity
@OneToOne(mappedBy = "location", cascade = CascadeType.ALL)
private Team team;

// In Team entity  
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "location_id", referencedColumnName = "id")
private Location location;
```

**Logic:** Each team has exactly one location, and each location belongs to exactly one team. This ensures data normalization by avoiding duplication of location data.

#### One-to-Many Relationship: Team → Player
```java
// In Team entity
@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private List<Player> players = new ArrayList<>();

// In Player entity
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "team_id", nullable = false)
private Team team;
```

**Logic:** One team can have many players, but each player belongs to only one team. This reflects real football team structure.

#### One-to-Many Relationship: Team → TrainingSession
```java
// In Team entity
@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private List<TrainingSession> trainingSessions = new ArrayList<>();

// In TrainingSession entity
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "team_id", nullable = false)
private Team team;
```

**Logic:** One team can conduct many training sessions, but each training session belongs to only one team.

#### One-to-One Relationship: Player → PerformanceStat
```java
// In Player entity
@OneToOne(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private PerformanceStat performanceStat;

// In PerformanceStat entity
@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "player_id", nullable = false)
private Player player;
```

**Logic:** Each player has one performance record per match, connected using a shared foreign key. This ensures accurate player performance tracking.

#### Many-to-Many Relationship: Player ↔ Match
```java
// In Player entity
@ManyToMany(fetch = FetchType.LAZY)
@JoinTable(
    name = "player_match",
    joinColumns = @JoinColumn(name = "player_id"),
    inverseJoinColumns = @JoinColumn(name = "match_id")
)
private List<Match> matches = new ArrayList<>();

// In Match entity
@ManyToMany(fetch = FetchType.LAZY)
@JoinTable(
    name = "player_match",
    joinColumns = @JoinColumn(name = "match_id"),
    inverseJoinColumns = @JoinColumn(name = "player_id")
)
private List<Player> players = new ArrayList<>();
```

**Logic:** One player can play many matches, and one match includes many players. The join table `player_match` manages this relationship, reflecting real football participation.

### 2. Location Saving Implementation

**How Location Data is Stored:**
```java
// Location entity stores the actual location data
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String province;
    
    @Column(nullable = false)
    private String district;
    
    @Column(nullable = false)
    private String stadiumName;
}

// Team entity references location via foreign key
@Entity
public class Team {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
}
```

**Logic:** Location is saved first, then Team entity stores the reference via `location_id` foreign key. This avoids duplication of location data and ensures data normalization.

### 3. Sorting Functionality Implementation

**Using Spring Data JPA Sort:**
```java
// Repository method with sorting
List<Team> findByCoachOrderByFoundationDateDesc(String coach, Sort sort);

// Controller usage
@GetMapping("/coach/{coach}/sorted")
public ResponseEntity<List<Team>> getTeamsByCoachSorted(@PathVariable String coach) {
    List<Team> teams = teamRepository.findByCoachOrderByFoundationDateDesc(coach, 
        Sort.by(Sort.Direction.DESC, "foundationDate"));
    return ResponseEntity.ok(teams);
}
```

**Logic:** Sorting is implemented using Spring Data JPA `Sort` object. Data is ordered before display, making reports more readable and useful for coaches.

### 4. Pagination Implementation

**Using Spring Data JPA Pageable:**
```java
// Repository method with pagination
Page<Team> findByProvinceWithPagination(@Param("province") String province, Pageable pageable);

// Controller usage
@GetMapping("/province/{province}/paged")
public ResponseEntity<Page<Team>> getTeamsByProvinceWithPagination(
        @PathVariable String province,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "name") String sort,
        @RequestParam(defaultValue = "ASC") String direction) {
    
    Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
    
    Page<Team> teams = teamRepository.findByProvinceWithPagination(province, pageable);
    return ResponseEntity.ok(teams);
}
```

**Logic:** Data is split into pages (e.g., 10 teams per page), reducing memory usage and improving application performance. Essential for large datasets like match history.

### 5. existsBy() Method Implementation

**Repository Methods:**
```java
// Location repository
boolean existsByProvince(String province);
boolean existsByDistrict(String district);

// Team repository  
boolean existsByName(String name);

// Player repository
boolean existsByJerseyNumber(Integer jerseyNumber);
```

**Logic:** These methods prevent duplicate records and improve data integrity. They use Spring Data JPA auto-generated methods that execute efficient SQL EXISTS queries.

**Example Usage:**
```java
@GetMapping("/exists/name/{name}")
public ResponseEntity<Map<String, Object>> existsByName(@PathVariable String name) {
    boolean exists = teamRepository.existsByName(name);
    Map<String, Object> response = new HashMap<>();
    response.put("exists", exists);
    response.put("teamName", name);
    return ResponseEntity.ok(response);
}
```

### 6. Province-Based Data Retrieval

**Implementation:**
```java
// Repository method with JOIN
@Query("SELECT t FROM Team t JOIN t.location l WHERE l.province = :province")
List<Team> findByProvince(@Param("province") String province);

// With pagination and sorting
@Query("SELECT t FROM Team t JOIN t.location l WHERE l.province = :province ORDER BY t.name")
Page<Team> findByProvinceWithPagination(@Param("province") String province, Pageable pageable);
```

**Logic:** Efficient filtering using JOIN queries. Province is stored in the Location table, so we use JOIN to retrieve teams based on province. Supports both simple retrieval and advanced pagination/sorting.

### 7. Viva-Voce Theory Questions

#### Q1: Why were these specific relationships chosen?

**A:** The relationships were chosen to reflect real football operations:
- **One-to-One (Location-Team):** Each team has one home location/stadium
- **One-to-Many (Team-Player):** Teams have multiple players
- **One-to-Many (Team-TrainingSession):** Teams conduct multiple training sessions
- **One-to-One (Player-PerformanceStat):** Each player has performance stats per match
- **Many-to-Many (Player-Match):** Players participate in multiple matches, matches have multiple players

#### Q2: How does pagination improve performance?

**A:** Pagination improves performance by:
- **Memory Efficiency:** Only loads required data instead of entire datasets
- **Faster Response Times:** Reduces database query time and network transfer
- **Better User Experience:** Prevents overwhelming users with too much data at once
- **Scalability:** Allows the application to handle large datasets efficiently

#### Q3: What is the difference between One-to-One, One-to-Many, and Many-to-Many relationships?

**A:**
- **One-to-One:** Each record in Table A relates to exactly one record in Table B (e.g., Team-Location)
- **One-to-Many:** Each record in Table A relates to multiple records in Table B (e.g., Team-Player)
- **Many-to-Many:** Records in Table A can relate to multiple records in Table B, and vice versa (e.g., Player-Match)

#### Q4: Why is existsBy() important for data integrity?

**A:** existsBy() methods are important because they:
- **Prevent Duplicates:** Check if records already exist before creating new ones
- **Enforce Constraints:** Help maintain unique constraints at the application level
- **Improve Performance:** Use efficient EXISTS SQL queries instead of SELECT COUNT
- **User Experience:** Provide immediate feedback about data availability

#### Q5: How does normalization improve the database?

**A:** Normalization improves the database by:
- **Reducing Redundancy:** Eliminates duplicate data storage
- **Improving Data Integrity:** Reduces update anomalies
- **Optimizing Storage:** Uses space more efficiently
- **Simplifying Maintenance:** Easier to update and maintain data

#### Q6: What are the benefits of using Spring Data JPA?

**A:** Spring Data JPA provides:
- **Reduced Boilerplate:** Automatic implementation of common CRUD operations
- **Type Safety:** Compile-time checking of query methods
- **Database Abstraction:** Easy switching between different databases
- **Query Methods:** Declarative query creation using method names
- **Pagination Support:** Built-in pagination and sorting capabilities

### 8. Key Technologies Used

- **Spring Boot 3.x:** Application framework
- **Spring Data JPA:** Database abstraction layer
- **H2 Database:** In-memory database for development
- **REST API:** HTTP endpoints for data access
- **Maven:** Build and dependency management

### 9. Testing Strategy

The application includes comprehensive tests covering:
- **Entity Relationships:** Verification of all relationship mappings
- **existsBy() Methods:** Testing duplicate prevention functionality
- **Sorting:** Verification of data ordering
- **Pagination:** Testing page-based data retrieval
- **Province-based Retrieval:** Testing location-based queries
- **Data Integrity:** Testing constraints and validation

### 10. Project Structure

```
src/
├── main/java/com/football/
│   ├── Application.java                    # Main application class
│   ├── entity/                            # JPA entities (6 tables)
│   │   ├── Location.java
│   │   ├── Team.java
│   │   ├── Player.java
│   │   ├── PerformanceStat.java
│   │   ├── Match.java
│   │   └── TrainingSession.java
│   ├── repository/                        # Spring Data JPA repositories
│   │   ├── LocationRepository.java
│   │   ├── TeamRepository.java
│   │   ├── PlayerRepository.java
│   │   ├── MatchRepository.java
│   │   ├── PerformanceStatRepository.java
│   │   └── TrainingSessionRepository.java
│   └── controller/                        # REST API controllers
│       ├── LocationController.java
│       └── TeamController.java
├── main/resources/
│   └── application.properties            # Application configuration
└── test/java/com/football/
    └── FootballTeamPerformanceTrackingApplicationTests.java
```

This comprehensive implementation demonstrates all required features and provides a solid foundation for understanding Spring Boot, JPA, and database relationship concepts.