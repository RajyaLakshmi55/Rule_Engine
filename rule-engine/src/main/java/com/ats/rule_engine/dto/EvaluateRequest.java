package com.ats.rule_engine.dto;

import java.util.Map;

public class EvaluateRequest {
    private String ruleString;
    private Map<String, Object> userAttributes;

    // Getters and Setters
    public String getRuleString() {
        return ruleString;
    }

    public void setRuleString(String ruleString) {
        this.ruleString = ruleString;
    }

    public Map<String, Object> getUserAttributes() {
        return userAttributes;
    }

    public void setUserAttributes(Map<String, Object> userAttributes) {
        this.userAttributes = userAttributes;
    }
}
