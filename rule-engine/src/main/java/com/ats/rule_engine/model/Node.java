package com.ats.rule_engine.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // This will exclude null fields from serialization
public class Node {
    private String type; // Can be "operator" or "operand"
    private String value; // The operator or operand value
    private Node left; // Left child node
    private Node right; // Right child node

    // Constructor for operator nodes
    public Node(String type, Node left, Node right, String value) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.value = value;
    }

    // Constructor for operand nodes
    public Node(String type, String value) {
        this.type = type;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        // Customize the output format for debugging
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("    \"type\": \"").append(type).append("\",\n");
        if (value != null) {
            sb.append("    \"value\": \"").append(value).append("\",\n");
        }
        if (left != null) {
            sb.append("    \"left\": ").append(left.toString()).append(",\n");
        }
        if (right != null) {
            sb.append("    \"right\": ").append(right.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
