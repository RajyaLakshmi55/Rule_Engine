package com.ats.rule_engine.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ats.rule_engine.dto.CombineRulesRequest;
import com.ats.rule_engine.dto.EvaluateRequest;
import com.ats.rule_engine.model.Node;
import com.ats.rule_engine.service.RuleServiceInterface;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rules")
@CrossOrigin( origins = "http://localhost:3000")
public class RuleController {

    @Autowired
    private RuleServiceInterface ruleService;

    @PostMapping("/create")
    public ResponseEntity<Node> createRule(@RequestBody Map<String, String> request) {
        String ruleString = request.get("ruleString");
        String description = request.get("description"); // Extract description from request

        if (ruleString == null || description == null) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request if missing fields
        }

        Node rootNode = ruleService.createRule(ruleString, description);
        return ResponseEntity.ok(rootNode); // Return 200 OK with the root node
    }


    @PostMapping("/combine")
    public ResponseEntity<Node> combineRules(@RequestBody CombineRulesRequest request) {
        List<String> rules = request.getRules();
        Node combinedNode = ruleService.combineRules(rules);
        return ResponseEntity.ok(combinedNode);
    }
    
    @GetMapping
    public ResponseEntity<List<String>> getAllRules() {
        List<String> rules = ruleService.getAllRules(); // Fetch rules from the database
        return ResponseEntity.ok(rules);
    }


    @PostMapping("/evaluate")
    public ResponseEntity<Boolean> evaluateRule(@RequestBody EvaluateRequest evaluateRequest) {
        Node ruleNode = ruleService.createRule(evaluateRequest.getRuleString());
        boolean result = ruleService.evaluateRule(ruleNode, evaluateRequest.getUserAttributes());
        return ResponseEntity.ok(result);
    }
}
