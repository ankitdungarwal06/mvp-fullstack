import { useState, useEffect } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import EventModal from "./EventModal";
import axios from "axios";
import { toast } from "react-toastify";

function CalendarView({ userId }) {
  const [events, setEvents] = useState([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedEvent, setSelectedEvent] = useState(null);

  // Color mapping for event categories
  const getEventColor = (category) => {
    switch (category) {
      case "Work":
        return { backgroundColor: "#3788d8", borderColor: "#3788d8" };
      case "Personal":
        return { backgroundColor: "#28a745", borderColor: "#28a745" };
      case "Travel":
        return { backgroundColor: "#fd7e14", borderColor: "#fd7e14" };
      case "Meeting":
        return { backgroundColor: "#6f42c1", borderColor: "#6f42c1" };
      case "Other":
        return { backgroundColor: "#6c757d", borderColor: "#6c757d" };
      default:
        return { backgroundColor: "#3788d8", borderColor: "#3788d8" };
    }
  };

  // Fetch events for the user
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/event/${userId}/fetchEvents`)
      .then((response) => {
        setEvents(
          response.data.map((event) => ({
            id: event.id.toString(),
            title: event.title,
            start: event.start,
            end: event.end,
            ...getEventColor(event.category),
            extendedProps: {
              location: event.location,
              notes: event.notes,
              category: event.category,
              userId: event.userId,
            },
          }))
        );
      })
      .catch((err) => {
        console.error("Error fetching events:", err.message);
        toast.error("Failed to fetch events");
      });
  }, [userId]);

  // Open modal for new event
  const handleDateClick = (arg) => {
    setSelectedEvent(null);
    setModalOpen(true);
  };

  // Open modal for editing event
  const handleEventClick = (arg) => {
    const event = {
      id: arg.event.id,
      title: arg.event.title,
      start: new Date(arg.event.start).toISOString().slice(0, 16),
      end: arg.event.end
        ? new Date(arg.event.end).toISOString().slice(0, 16)
        : "",
      location: arg.event.extendedProps.location || "",
      notes: arg.event.extendedProps.notes || "",
      category: arg.event.extendedProps.category || "Work",
      userId: arg.event.extendedProps.userId,
    };
    setSelectedEvent(event);
    setModalOpen(true);
  };

  // Add new event
  const handleEventAdd = (event) => {
    setEvents([
      ...events,
      {
        id: event.id.toString(),
        title: event.title,
        start: event.start,
        end: event.end,
        ...getEventColor(event.category),
        extendedProps: {
          location: event.location,
          notes: event.notes,
          category: event.category,
          userId: event.userId,
        },
      },
    ]);
    setModalOpen(false);
  };

  // Update existing event
  const handleEventUpdate = (event) => {
    setEvents(
      events.map((e) =>
        e.id === event.id.toString()
          ? {
              id: event.id.toString(),
              title: event.title,
              start: event.start,
              end: event.end,
              ...getEventColor(event.category),
              extendedProps: {
                location: event.location,
                notes: event.notes,
                category: event.category,
                userId: event.userId,
              },
            }
          : e
      )
    );
    setModalOpen(false);
  };

  // Delete event
  const handleEventDelete = (id) => {
    setEvents(events.filter((e) => e.id !== id.toString()));
    setModalOpen(false);
  };

  return (
    <div style={{ maxWidth: "900px", margin: "20px auto", padding: "0 20px" }}>
      <FullCalendar
        plugins={[dayGridPlugin, interactionPlugin]}
        initialView="dayGridMonth"
        events={events}
        dateClick={handleDateClick}
        eventClick={handleEventClick}
        eventDisplay="block"
        eventTimeFormat={{
          hour: "2-digit",
          minute: "2-digit",
          hour12: false,
        }}
        height="auto"
      />
      <EventModal
        open={modalOpen}
        onClose={() => setModalOpen(false)}
        onSave={handleEventAdd}
        onUpdate={handleEventUpdate}
        onDelete={handleEventDelete}
        event={selectedEvent}
        userId={userId}
      />
    </div>
  );
}

export default CalendarView;
