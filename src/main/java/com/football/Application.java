package com.football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Football Team Performance Tracking System
 * 
 * This Spring Boot application demonstrates:
 * - Entity Relationship Diagram with 6 tables
 * - Location saving with proper relationships
 * - Sorting and pagination functionality
 * - One-to-One, One-to-Many, and Many-to-Many relationships
 * - existsBy() method implementation
 * - Province-based data retrieval
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}