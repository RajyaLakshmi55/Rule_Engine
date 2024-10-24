import React from 'react';
import { Link } from 'react-router-dom';
import "./Navbar.css";

const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
        <div className="container-fluid">
          <Link className="navbar-brand" to="/">Rule Engine</Link>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link className="nav-link" to="/">Home</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/create-rule">Create Rule</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/combine-rule">Combine Rule</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/evaluate-rule">Evaluate Rule</Link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
  );
};

export default Navbar;
