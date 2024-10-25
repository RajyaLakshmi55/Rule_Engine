package com.ats.rule_engine.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "metadata")
public class Metadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "version", nullable = false)
    private String version; // Version of the rules engine

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated; // Last updated timestamp

    @Column(name = "description")
    private String description; // Description of the rules engine

    @OneToMany(mappedBy = "metadata", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rule> rules = new ArrayList<>(); // List of rules associated with this metadata

    public Metadata() {
        this.lastUpdated = LocalDateTime.now(); // Default to current time
    }

    public Metadata(Long id, String version, String description) {
        this.id = id;
        this.version = version;
        this.description = description;
        this.lastUpdated = LocalDateTime.now(); // Default to current time
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}
