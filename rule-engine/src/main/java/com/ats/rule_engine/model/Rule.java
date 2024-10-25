package com.ats.rule_engine.model;

import jakarta.persistence.*;

@Entity
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rule_string", nullable = false)
    private String ruleString; // The rule as a string

    @ManyToOne(fetch = FetchType.LAZY) // Many rules can belong to one metadata
    @JoinColumn(name = "metadata_id") // Foreign key in the Rule table
    private Metadata metadata;

    public Rule() {
    }

    public Rule(Long id, String ruleString, Metadata metadata) {
        this.id = id;
        this.ruleString = ruleString;
        this.metadata = metadata;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleString() {
        return ruleString;
    }

    public void setRuleString(String ruleString) {
        this.ruleString = ruleString;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
