package com.ats.rule_engine.service;

import com.ats.rule_engine.model.Node;
import java.util.List;
import java.util.Map;

public interface RuleServiceInterface {

    /**
     * Creates a new rule with the specified rule string and description.
     *
     * @param ruleString the rule string to be parsed into a rule
     * @param description the description for the rule
     * @return the root node of the abstract syntax tree (AST)
     */
    Node createRule(String ruleString, String description);

    /**
     * Creates a new rule with the specified rule string.
     *
     * @param ruleString the rule string to be parsed into a rule
     * @return the root node of the abstract syntax tree (AST)
     */
    Node createRule(String ruleString);

    /**
     * Combines multiple rules into a single rule node.
     *
     * @param rules a list of rule strings to combine
     * @return the root node of the combined rule
     */
    Node combineRules(List<String> rules);

    /**
     * Evaluates a rule against user attributes.
     *
     * @param rootNode the root node of the rule to evaluate
     * @param userAttributes a map of user attributes to evaluate the rule against
     * @return true if the rule evaluates to true, false otherwise
     */
    boolean evaluateRule(Node rootNode, Map<String, Object> userAttributes);

    /**
     * Fetches all rules from the database.
     *
     * @return a list of all rule strings
     */
    List<String> getAllRules();
}
