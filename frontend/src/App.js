// src/App.js
import React from "react";
import Header from "./components/Header";
import "./App.css";

function App() {
  const userId = 1;
  console.log("App userId:", userId);
  return (
    <div className="App">
      <Header userId={userId} />
      <main>
        <h2>Welcome</h2>
        <p>This is the main content area. Add more features here!</p>
      </main>
    </div>
  );
}

export default App;
