import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import CalendarView from "./components/calendar/CalendarView";

function App() {
  const userId = 253;
  console.log("App userId:", userId);
  return (
    <Router>
      <div className="App">
        <Header userId={userId} />
        <main>
          <Routes>
            <Route
              path="/"
              element={
                <>
                  <h2>Welcome</h2>
                  <p>This is the main content area. Add more features here!</p>
                </>
              }
            />
            <Route
              path="/calendar"
              element={<CalendarView userId={userId} />}
            />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
