import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import CalendarView from "./components/calendar/CalendarView";
import Discussions from "../src/components/discussion/Discussions";
import Tasks from "./components/task/Tasks";
import DailyNotes from "./components/dailynotes/DailyNotes";

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
            <Route
              path="/discussions"
              element={<Discussions userId={userId} />}
            />
            <Route path="/tasks" element={<Tasks userId={userId} />} />
            <Route path="/journal" element={<DailyNotes userId={userId} />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
