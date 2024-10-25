import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import CreateRule from './components/CreateRule';
import CombineRules from './components/CombineRules';
import EvaluateRule from './components/EvaluateRule';
import Navbar from './components/Navbar';

function App() {
    return (
        <Router>
            <div>
              <Navbar />
                <Routes>
                    <Route path="/" exact Component={Home} />
                    <Route path="/create-rule" Component={CreateRule} />
                    <Route path="/combine-rule" Component={CombineRules} />
                    <Route path="/evaluate-rule" Component={EvaluateRule} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
