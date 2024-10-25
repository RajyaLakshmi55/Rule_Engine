import React, { useEffect, useState } from 'react';
import axios from 'axios';

function EvaluateRule() {
  const [availableRules, setAvailableRules] = useState([]);
  const [ruleString, setRuleString] = useState('Select Rule');
  const [userAttributes, setUserAttributes] = useState({
    age: '',
    department: '',
    income: ''
  });
  const [evaluationResult, setEvaluationResult] = useState(null);

  useEffect(() => {
    const fetchAvailableRules = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/rules');
        setAvailableRules(response.data);
      } catch (error) {
        console.error('Error fetching rules:', error);
      }
    };
    fetchAvailableRules();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUserAttributes({
      ...userAttributes,
      [name]: value
    });
  };

  const handleRuleChange = (e) => {
    setRuleString(e.target.value);
  };

  const handleEvaluate = async () => {
    // Validate if ruleString is provided
    console.log(ruleString)
    if (!ruleString) {
      alert('Please enter a rule to evaluate.');
      return;
    }

    // Log the user attributes to ensure they're correct
    console.log("User Attributes:", {
      age: parseInt(userAttributes.age),
      department: userAttributes.department,
      salary: parseFloat(userAttributes.income)
    });

    try {
      const response = await axios.post('http://localhost:8080/api/rules/evaluate', {
        ruleString: ruleString, // Send user-defined rule string
        userAttributes: {
          age: parseInt(userAttributes.age), // Ensure age is an integer
          department: userAttributes.department,
          salary: parseFloat(userAttributes.income) // Ensure salary is a float
        }
      });

      console.log("Response from API:", response.data); // Log the response from the API
      setEvaluationResult(response.data); // Store the evaluation result
    } catch (error) {
      console.error('Error evaluating rule:', error);
      setEvaluationResult('Error evaluating rule');
    }
  };

  return (
    <div className="container">
      <h1>Evaluate Rule</h1>

      {/* User-Defined Rule Input */}
      <div className="form-group">
        <label>Enter Rule</label>
        
        <select
          type="text"
          value={ruleString}
          onChange={handleRuleChange}
          className="form-control"
          
        >
          <option disabled >{ruleString}</option>
          {
            availableRules.map((rule)=>(
              <option key={rule.id} value={rule}>{rule}</option>
            ))
          }
        </select>
      </div>

      {/* User Attributes Form */}
      <div className="form-group">
        <label>Age</label>
        <input
          type="text"
          name="age"
          value={userAttributes.age}
          onChange={handleInputChange}
          className="form-control"
        />
      </div>
      <div className="form-group">
        <label>Department</label>
        <input
          type="text"
          name="department"
          value={userAttributes.department}
          onChange={handleInputChange}
          className="form-control"
        />
      </div>
      <div className="form-group">
        <label>Income</label>
        <input
          type="text"
          name="income"
          value={userAttributes.income}
          onChange={handleInputChange}
          className="form-control"
        />
      </div>

      {/* Button to Trigger Evaluation */}
      <button className="btn btn-primary mt-3" onClick={handleEvaluate}>
        Evaluate Rule
      </button>

      {/* Display Evaluation Result */}
      {evaluationResult !== null && (
        <div className="mt-4">
          <h3>Evaluation Result: {evaluationResult ? 'True' : 'False'}</h3>
        </div>
      )}
    </div>
  );
}

export default EvaluateRule;
