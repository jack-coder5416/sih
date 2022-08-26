import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";


import Login from "./components/Login/Login";
import Front from "./components/main/Front"
import { RealtimeData } from "./components/Parent/RealtimeData";
import { auth } from "./firebase";
import 'bootstrap/dist/css/bootstrap.min.css';

import "./App.css";

function App() {
  const [userEmail, setUserEmail] = useState("");

  useEffect(() => {
    auth.onAuthStateChanged((user) => {
      if (user) {
        setUserEmail(user.email);
      } else setUserEmail("");
    });
  }, []);

  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/login" element={<Login />} />
          
          <Route path="/parent" element={<RealtimeData email={userEmail}/>}/>

          <Route path="/" element={<Front />} />
        
        
        </Routes>
      </Router>
    </div>
  );
}

export default App;
