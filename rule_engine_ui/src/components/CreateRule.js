import React, { useState } from 'react';
import axios from 'axios';
import './CreateRule.css'; // Updated custom styles here

function CreateRule() {
  const [ruleString, setRuleString] = useState('');
  const [description, setDescription] = useState('');
  const [treeData, setTreeData] = useState(null);
  const [jsonData, setJsonData] = useState(null);

  const handleCreateRule = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/rules/create', {
        ruleString,
        description
      });
      setTreeData(response.data); // Tree node structure
      setJsonData(JSON.stringify(response.data, null, 2)); // JSON data
    } catch (error) {
      console.error('Error creating rule:', error);
    }
  };

  // Recursive function to render AST nodes
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
      <h1 className="text-center mt-4">Create Rule</h1>

      <div className="form-group mb-3">
        <label htmlFor="ruleString">Rule String</label>
        <input
          type="text"
          id="ruleString"
          className="form-control"
          value={ruleString}
          onChange={(e) => setRuleString(e.target.value)}
        />
      </div>

      <div className="form-group mb-3">
        <label htmlFor="description">Description</label>
        <input
          type="text"
          id="description"
          className="form-control"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />
      </div>

      <button className="btn btn-primary" onClick={handleCreateRule}>
        Create Rule
      </button>

     
          <h4>Abstract Syntax Tree (AST)</h4>
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

export default CreateRule;
