# Entity Relationship Diagram (ERD) Documentation

## Football Team Performance Tracking System

### Database Schema Overview

This system implements 6 core entities with proper relationships to track football team performance comprehensively.

### Entities and Relationships

#### 1. Location Entity
```sql
CREATE TABLE locations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    province VARCHAR(100) NOT NULL,
    district VARCHAR(100) NOT NULL,
    stadium_name VARCHAR(200) NOT NULL
);
```

**Relationships:**
- **One-to-One** with Team (bidirectional)
- Primary key: `id`
- Fields: `province`, `district`, `stadiumName`

#### 2. Team Entity
```sql
CREATE TABLE teams (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    coach VARCHAR(100) NOT NULL,
    foundation_date DATE NOT NULL,
    location_id BIGINT,
    FOREIGN KEY (location_id) REFERENCES locations(id)
);
```

**Relationships:**
- **One-to-One** with Location (bidirectional)
- **One-to-Many** with Player
- **One-to-Many** with TrainingSession
- Primary key: `id`
- Foreign key: `location_id`

#### 3. Player Entity
```sql
CREATE TABLE players (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    age INT NOT NULL CHECK (age >= 16 AND age <= 50),
    position VARCHAR(50) NOT NULL,
    jersey_number INT NOT NULL UNIQUE CHECK (jersey_number >= 1 AND jersey_number <= 99),
    join_date DATE NOT NULL,
    team_id BIGINT NOT NULL,
    FOREIGN KEY (team_id) REFERENCES teams(id)
);
```

**Relationships:**
- **One-to-Many** with Team (bidirectional)
- **One-to-One** with PerformanceStat
- **Many-to-Many** with Match
- Primary key: `id`
- Foreign key: `team_id`

#### 4. Match Entity
```sql
CREATE TABLE matches (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    home_team_name VARCHAR(100) NOT NULL,
    away_team_name VARCHAR(100) NOT NULL,
    match_date_time DATETIME NOT NULL,
    home_team_score INT NOT NULL CHECK (home_team_score >= 0),
    away_team_score INT NOT NULL CHECK (away_team_score >= 0),
    match_status VARCHAR(50) NOT NULL
);
```

**Relationships:**
- **Many-to-Many** with Player
- Primary key: `id`

#### 5. PerformanceStat Entity
```sql
CREATE TABLE performance_stats (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    goals_scored INT NOT NULL CHECK (goals_scored >= 0),
    assists INT NOT NULL CHECK (assists >= 0),
    minutes_played INT NOT NULL CHECK (minutes_played >= 0 AND minutes_played <= 120),
    fitness_level INT NOT NULL CHECK (fitness_level >= 1 AND fitness_level <= 10),
    performance_date DATE NOT NULL,
    player_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (player_id) REFERENCES players(id)
);
```

**Relationships:**
- **One-to-One** with Player (bidirectional)
- Primary key: `id`
- Foreign key: `player_id` (UNIQUE)

#### 6. TrainingSession Entity
```sql
CREATE TABLE training_sessions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    training_type VARCHAR(100) NOT NULL,
    training_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    location VARCHAR(200) NOT NULL,
    duration_minutes INT NOT NULL CHECK (duration_minutes >= 30 AND duration_minutes <= 240),
    trainer_name VARCHAR(100) NOT NULL,
    team_id BIGINT NOT NULL,
    FOREIGN KEY (team_id) REFERENCES teams(id)
);
```

**Relationships:**
- **One-to-Many** with Team (bidirectional)
- Primary key: `id`
- Foreign key: `team_id`

### Join Table for Many-to-Many Relationship

#### Player_Match Join Table
```sql
CREATE TABLE player_match (
    player_id BIGINT NOT NULL,
    match_id BIGINT NOT NULL,
    PRIMARY KEY (player_id, match_id),
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE,
    FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE CASCADE
);
```

### Visual ERD Diagram

```
┌─────────────────┐    1:1    ┌─────────────────┐
│                 │◄──────────►│                 │
│   LOCATION      │           │      TEAM       │
│                 │           │                 │
│ - id (PK)       │           │ - id (PK)       │
│ - province      │           │ - name          │
│ - district      │           │ - coach         │
│ - stadiumName   │           │ - foundationDate│
└─────────────────┘           │ - location_id   │
                              │   (FK)          │
                                   │ 1
                                   │
                                   │ N
                              ┌─────────────────┐    1:N    ┌─────────────────┐
                              │                 │◄──────────►│                 │
                              │     PLAYER      │           │ TRAINING_SESSION│
                              │                 │           │                 │
                              │ - id (PK)       │           │ - id (PK)       │
                              │ - firstName     │           │ - trainingType  │
                              │ - lastName      │           │ - trainingDate  │
                              │ - age           │           │ - startTime     │
                              │ - position      │           │ - endTime       │
                              │ - jerseyNumber  │           │ - location      │
                              │ - joinDate      │           │ - durationMins  │
                              │ - team_id (FK)  │           │ - trainerName   │
                              │                 │           │ - team_id (FK)  │
                              └─────────────────┘           └─────────────────┘
                                   │ 1
                                   │
                                   │ 1
                              ┌─────────────────┐
                              │                 │
                              │ PERFORMANCE_STAT│
                              │                 │
                              │ - id (PK)       │
                              │ - goalsScored   │
                              │ - assists       │
                              │ - minutesPlayed │
                              │ - fitnessLevel  │
                              │ - performanceDate│
                              │ - player_id (FK)│
                              │   UNIQUE        │
                              └─────────────────┘

PLAYER ──── N:M ──── MATCH
    │                 │
    │                 │
    ▼                 ▼
┌─────────────────┐ ┌─────────────────┐
│                 │ │                 │
│   PLAYER_MATCH  │ │     MATCH       │
│   (Join Table)  │ │                 │
│                 │ │ - id (PK)       │
│ - player_id (FK)│ │ - homeTeamName  │
│ - match_id (FK) │ │ - awayTeamName  │
│   (Composite    │ │ - matchDateTime │
│    Primary Key) │ │ - homeTeamScore │
│                 │ │ - awayTeamScore │
│                 │ │ - matchStatus   │
└─────────────────┘ └─────────────────┘
```

### Relationship Explanations

#### 1. One-to-One Relationships

**Location ↔ Team:**
- Each team has exactly one location
- Each location belongs to exactly one team
- Implemented with bidirectional mapping
- Location is the owning side (mappedBy)

**Player ↔ PerformanceStat:**
- Each player has one performance record per match
- Each performance stat belongs to one player
- PerformanceStat is the owning side (has foreign key)

#### 2. One-to-Many Relationships

**Team → Player:**
- One team can have many players
- Each player belongs to only one team
- Team is the parent entity
- Cascade.ALL ensures players are deleted when team is deleted

**Team → TrainingSession:**
- One team can have many training sessions
- Each training session belongs to only one team
- Cascade.ALL ensures sessions are deleted when team is deleted

#### 3. Many-to-Many Relationship

**Player ↔ Match:**
- One player can play many matches
- One match includes many players
- Implemented using join table `player_match`
- Composite primary key (player_id, match_id)
- CASCADE DELETE ensures cleanup when entities are removed

### Data Integrity Features

1. **Primary Keys:** All entities have auto-generated primary keys
2. **Foreign Keys:** Proper foreign key constraints maintain referential integrity
3. **Unique Constraints:** 
   - Team name must be unique
   - Player jersey number must be unique
   - PerformanceStat player_id must be unique
4. **Check Constraints:**
   - Age between 16-50
   - Jersey number between 1-99
   - Scores cannot be negative
   - Fitness level between 1-10
   - Minutes played between 0-120
   - Training duration between 30-240 minutes

### Benefits of This Design

1. **Normalization:** Eliminates data redundancy
2. **Data Integrity:** Foreign key constraints prevent orphaned records
3. **Flexibility:** Many-to-many relationship allows complex player-match associations
4. **Performance:** Proper indexing through primary and foreign keys
5. **Maintainability:** Clear relationship mappings make the system easy to understand and modify