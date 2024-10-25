package com.ats.rule_engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ats.rule_engine.model.Metadata;
import com.ats.rule_engine.model.Rule;
import com.ats.rule_engine.repository.RuleRepository;
import com.ats.rule_engine.repository.MetadataRepository; // Import MetadataRepository
import com.ats.rule_engine.model.Node; // Ensure to import your Node class

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@Service
public class RuleService implements RuleServiceInterface {

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private MetadataRepository metadataRepository; // Inject MetadataRepository

    public Node createRule(String ruleString, String description) {
        // Parse the rule string to create the AST
        Node rootNode = parseRuleString(ruleString);
        
        // Create and save metadata
        Metadata metadata = new Metadata();
        metadata.setVersion("1.0"); // Set appropriate version
        metadata.setDescription(description); // Use the provided description
        metadata.setLastUpdated(LocalDateTime.now()); // Set the current time
        
        // Save metadata
        Metadata savedMetadata = metadataRepository.save(metadata);

        // Create a new rule with the parsed string and associated metadata
        Rule rule = new Rule();
        rule.setRuleString(ruleString); // Set the rule string
        rule.setMetadata(savedMetadata); // Link the rule to the saved metadata
        
        // Save the rule
        ruleRepository.save(rule);

        return rootNode; // Return the root node of the rule
    }
    
    // Handle rule creation without description
    public Node createRule(String ruleString) {
        return parseRuleString(ruleString);
    }

    public Node combineRules(List<String> rules) {
        if (rules == null || rules.isEmpty()) {
            return null; // Handle empty input
        }

        Node combinedRoot = createRule(rules.get(0), "Combined Rule"); // Start with the first rule

        for (int i = 1; i < rules.size(); i++) {
            Node currentNode = createRule(rules.get(i), "Combined Rule");
            combinedRoot = new Node("operator", combinedRoot, currentNode, "OR"); // Combine using OR
        }

        return combinedRoot;
    }

    public boolean evaluateRule(Node rootNode, Map<String, Object> userAttributes) {
        return evaluateNode(rootNode, userAttributes);
    }

    private Node parseRuleString(String ruleString) {
        // Clean the input rule string
        ruleString = ruleString.replaceAll("\\s+", " ").trim(); // Normalize whitespace

        // Split the input string into tokens, including parentheses
        String[] tokens = ruleString.split("(?=[()])|(?<=[()])|\\s+");
        Stack<Node> operands = new Stack<>();
        Stack<String> operators = new Stack<>();

        // This StringBuilder will help build complete operands
        StringBuilder currentOperand = new StringBuilder();

        for (String token : tokens) {
            token = token.trim();

            if (token.equals("AND") || token.equals("OR")) {
                // Handle operator precedence and push current operand
                if (currentOperand.length() > 0) {
                    operands.push(new Node("operand", null, null, currentOperand.toString()));
                    currentOperand.setLength(0); // Reset the current operand
                }
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token)) {
                    Node rightOperand = operands.pop();
                    Node leftOperand = operands.pop();
                    String operator = operators.pop();
                    operands.push(new Node("operator", leftOperand, rightOperand, operator));
                }
                operators.push(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                if (currentOperand.length() > 0) {
                    operands.push(new Node("operand", null, null, currentOperand.toString()));
                    currentOperand.setLength(0); // Reset the current operand
                }
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    Node rightOperand = operands.pop();
                    Node leftOperand = operands.pop();
                    String operator = operators.pop();
                    operands.push(new Node("operator", leftOperand, rightOperand, operator));
                }
                operators.pop(); // Pop the '('
            } else {
                // Build the current operand
                if (currentOperand.length() > 0) {
                    currentOperand.append(" "); // Separate from previous part
                }
                currentOperand.append(token);
            }
        }

        // Handle any remaining operand
        if (currentOperand.length() > 0) {
            operands.push(new Node("operand", null, null, currentOperand.toString()));
        }

        // Process remaining operators
        while (!operators.isEmpty()) {
            Node rightOperand = operands.pop();
            Node leftOperand = operands.pop();
            String operator = operators.pop();
            operands.push(new Node("operator", leftOperand, rightOperand, operator));
        }

        return operands.pop(); // The root of the expression
    }

    // Helper method to determine operator precedence
    private int precedence(String operator) {
        if (operator.equals("AND")) {
            return 1;
        } else if (operator.equals("OR")) {
            return 0;
        }
        return -1; // For anything else
    }

    private boolean evaluateNode(Node rootNode, Map<String, Object> userAttributes) {
        if (rootNode.getType().equals("operand")) {
            String[] parts = rootNode.getValue().split(" ");
            if (parts.length < 3) return false; // Ensure there are enough parts

            String attribute = parts[0]; // Left side of the condition
            String operator = parts[1]; // Operator (>, <, =, etc.)
            String value = parts[2].replace("'", ""); // Right side value, handle quotes

            Object userValue = userAttributes.get(attribute); // Get the user attribute

            if (userValue instanceof Number && value.matches("\\d+")) {
                return evaluateNumericCondition((Number) userValue, operator, Integer.parseInt(value));
            } else {
                return evaluateStringCondition(userValue != null ? userValue.toString() : null, operator, value);
            }
        } else if (rootNode.getType().equals("operator")) {
            boolean leftResult = evaluateNode(rootNode.getLeft(), userAttributes);
            boolean rightResult = evaluateNode(rootNode.getRight(), userAttributes);
            return rootNode.getValue().equals("AND") ? leftResult && rightResult : leftResult || rightResult;
        }
        return false;
    }

    private boolean evaluateNumericCondition(Number userValue, String operator, int value) {
        switch (operator) {
            case ">":
                return userValue.doubleValue() > value;
            case "<":
                return userValue.doubleValue() < value;
            case "=":
                return userValue.doubleValue() == value;
            default:
                return false;
        }
    }

    private boolean evaluateStringCondition(String userValue, String operator, String value) {
        if (userValue == null) return false; // Handle null case
        switch (operator) {
            case "=":
                return userValue.equals(value);
            case "!=":
                return !userValue.equals(value);
            default:
                return false;
        }
    }

    public List<String> getAllRules() {
        // Fetch rule strings from the database
        return ruleRepository.findAll().stream()
            .map(Rule::getRuleString)
            .toList(); // Convert to list of rule strings
    }
}
