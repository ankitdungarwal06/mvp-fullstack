// src/components/Header.js
import React from "react";
import UpcomingDates from "./UpcomingDates";
import "./Header.css";

const Header = ({ userId }) => {
  console.log("Header userId:", userId);
  return (
    <header className="header">
      <div className="header-brand">
        <h1>My Application</h1>
      </div>
      <nav className="header-nav">
        <ul>
          <li>
            <a href="/">Home</a>
          </li>
          <li>
            <a href="/about">About</a>
          </li>
        </ul>
      </nav>
      <div className="header-dates">
        <UpcomingDates userId={userId} />
      </div>
    </header>
  );
};

export default Header;
