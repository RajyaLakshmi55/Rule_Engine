import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './CombineRule.css'; // Ensure you have your CSS file

function CombineRule() {
  const [availableRules, setAvailableRules] = useState([]);
  const [selectedRules, setSelectedRules] = useState([]);
  const [treeData, setTreeData] = useState(null);
  const [jsonData, setJsonData] = useState(null);

  useEffect(() => {
    // Fetch available rules from the backend
    const fetchAvailableRules = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/rules'); // Adjust the endpoint accordingly
        setAvailableRules(response.data);
      } catch (error) {
        console.error('Error fetching rules:', error);
      }
    };

    fetchAvailableRules();
  }, []);

  const handleRuleChange = (rule) => {
    if (selectedRules.includes(rule)) {
      setSelectedRules(selectedRules.filter(r => r !== rule));
    } else {
      setSelectedRules([...selectedRules, rule]);
    }
  };

  const handleCombineRules = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/rules/combine', {
        rules: selectedRules,
      });
      setTreeData(response.data);
      setJsonData(JSON.stringify(response.data, null, 2));
    } catch (error) {
      console.error('Error combining rules:', error);
    }
  };

  const renderNode = (node) => {
    if (!node) return null;

    return (
      <div className={`tree-node ${node.type}`}>
        <div>{node.value}</div>
        <div className="children">
          <div className="child">{renderNode(node.left)}</div>
          <div className="child">{renderNode(node.right)}</div>
        </div>
      </div>
    );
  };

  return (
    <div className="container">
      <h1 className="text-center mt-4">Combine Rules</h1>

      <h4>Select Rules to Combine</h4>
      <div className="scrollable-rule-list">
        {availableRules.length > 0 ? (
          availableRules.map((rule, index) => (
            <div key={index} className="form-check">
              <input
                type="checkbox"
                className="form-check-input"
                id={`rule-${index}`}
                checked={selectedRules.includes(rule)}
                onChange={() => handleRuleChange(rule)}
              />
              <label className="form-check-label" htmlFor={`rule-${index}`}>
                {rule}
              </label>
            </div>
          ))
        ) : (
          <p>Loading rules...</p>
        )}
      </div>

      <button className="btn btn-primary mt-3" onClick={handleCombineRules}>
        Combine Rules
      </button>

      
          <h4>Combined Rules Tree (AST)</h4>
          <div className="tree-visualization">
            {treeData ? renderNode(treeData) : <p>No tree to display</p>}
          </div>
        

        <div className="col-md-6">
          <h4>JSON Representation</h4>
          <pre className="json-output">{jsonData || 'No JSON data to display'}</pre>
        </div>
      </div>
    
  );
}

export default CombineRule;
