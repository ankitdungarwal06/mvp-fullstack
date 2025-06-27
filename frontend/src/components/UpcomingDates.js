// src/components/UpcomingDates.js
import React, { useState, useEffect } from "react";
import axios from "axios";
import "./UpcomingDates.css";

const UpcomingDates = ({ userId }) => {
  const [dates, setDates] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    console.log("UpcomingDates mounted with userId:", userId); // Debug mount
    if (!userId) {
      console.warn("userId is undefined or null");
      setError("Invalid user ID");
      setLoading(false);
      return;
    }

    const fetchDates = async () => {
      const url = `http://localhost:8080/api/users/${userId}/upcoming-dates`;
      console.log("Sending request to:", url); // Debug URL
      try {
        const response = await axios.get(url, {
          headers: {
            "Content-Type": "application/json",
          },
        });
        console.log("Response received:", response.data); // Debug response
        setDates(response.data);
        setLoading(false);
      } catch (err) {
        const errorMessage = err.response
          ? `Error ${err.response.status}: ${JSON.stringify(err.response.data)}`
          : `Network error: ${err.message}`;
        console.error("Axios error:", errorMessage); // Detailed error logging
        setError(errorMessage);
        setLoading(false);
      }
    };

    fetchDates();
  }, [userId]);

  if (loading) return <div className="dates-loading">Loading...</div>;
  if (error) return <div className="dates-error">{error}</div>;

  const isScrolling = dates.length > 3; // Enable scrolling if >3 rows

  return (
    <div className="upcoming-dates">
      <h3>Upcoming Dates</h3>
      <div className={`dates-container ${isScrolling ? "scrolling" : ""}`}>
        <ul className="dates-list">
          {dates.map((date) => (
            <li key={date.id} className="date-item">
              <span>{date.occasion}</span> - <span>{date.date}</span>
              {date.restricted && (
                <span className="restricted"> (Restricted)</span>
              )}
            </li>
          ))}
          {isScrolling &&
            dates.map((date) => (
              <li key={`${date.id}-duplicate`} className="date-item">
                <span>{date.occasion}</span> - <span>{date.date}</span>
                {date.restricted && (
                  <span className="restricted"> (Restricted)</span>
                )}
              </li>
            ))}
        </ul>
      </div>
    </div>
  );
};

export default UpcomingDates;
