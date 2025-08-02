// src/components/Header.js
import React from "react";
import UpcomingDates from "./UpcomingDates";
import "./Header.css";

const Header = ({ userId }) => {
  console.log("Header userId:", userId);
  return (
    <header className="header">
      <div className="header-brand">
        <h1>Complete Application</h1>
      </div>
      <nav className="header-nav">
        <ul>
          <li>
            <a href="/">Home</a>
          </li>
          <li>
            <a href="/about">About</a>
          </li>
          <li>
            <a href="/calendar">Calendar</a>
          </li>
          <li>
            <a href="/tasks">Tasks</a>
          </li>
          <li>
            <a href="/discussions">Discussions</a>
          </li>
          <li>
            <a href="/DailyNotes">DailyNotes</a>
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
