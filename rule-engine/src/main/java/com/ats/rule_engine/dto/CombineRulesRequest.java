package com.ats.rule_engine.dto;

import java.util.List;

public class CombineRulesRequest {
    private List<String> rules;

    // Getters and Setters
    public List<String> getRules() {
        return rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }
}
