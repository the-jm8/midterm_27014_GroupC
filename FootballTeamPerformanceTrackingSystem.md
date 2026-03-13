# Football Team Performance Tracking System

## Project Overview

A comprehensive Spring Boot application for tracking football team performance, implementing all required database relationships and functionalities as specified in the midterm project requirements.

## Database Schema

### Tables (5+ required)

1. **Location** - Stores province, district, and stadium information
2. **Team** - Football teams with location references
3. **Player** - Team players with performance tracking
4. **Match** - Match details and results
5. **PerformanceStat** - Player performance per match
6. **TrainingSession** - Team training sessions

### Relationships

- **One-to-One**: Player → PerformanceStat
- **One-to-Many**: Team → Player, Team → TrainingSession
- **Many-to-Many**: Player ↔ Match (via join table)

## Implementation Features

✅ Entity Relationship Diagram with 6 tables
✅ Location saving with proper relationships
✅ Sorting functionality using Spring Data JPA
✅ Pagination implementation
✅ All required relationship mappings
✅ existsBy() method implementation
✅ Province-based data retrieval

## Technology Stack

- Spring Boot 3.x
- Spring Data JPA
- H2 Database (for development)
- REST API endpoints
- Maven build system