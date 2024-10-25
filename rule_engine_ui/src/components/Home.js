import React from 'react';
import { Link } from 'react-router-dom';
import './Home.css';

function Home() {
  return (
    <div>
      {/* Navigation Bar */}
      

      {/* Hero Section */}
      <div className="hero-section text-center">
        <h1>Welcome to the Rule Engine</h1>
        <p>Create, combine, and evaluate rules dynamically with ease.</p>
        <Link to="/create-rule" className="hero-button">Get Started</Link>
      </div>

      {/* Features Section */}
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <div className="card card-hover card-gradient">
              <div className="card-body">
                <h5 className="card-title">Create Rules</h5>
                <p className="card-text">Define rules with simple conditions.</p>
                <Link to="/create-rule" className="btn btn-outline-light">Create</Link>
              </div>
            </div>
          </div>
          <div className="col-md-4">
            <div className="card card-hover card-gradient-secondary">
              <div className="card-body">
                <h5 className="card-title">Combine Rules</h5>
                <p className="card-text">Merge multiple rules with AND/OR conditions.</p>
                <Link to="/combine-rule" className="btn btn-outline-light">Combine</Link>
              </div>
            </div>
          </div>
          <div className="col-md-4">
            <div className="card card-hover card-gradient-tertiary">
              <div className="card-body">
                <h5 className="card-title">Evaluate Rules</h5>
                <p className="card-text">Run the rules on user attributes.</p>
                <Link to="/evaluate-rule" className="btn btn-outline-light">Evaluate</Link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
