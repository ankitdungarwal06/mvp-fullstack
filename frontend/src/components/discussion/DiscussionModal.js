import { useState, useEffect } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import { Modal, Box, TextField, Button, MenuItem } from "@mui/material";
import styles from "./DiscussionModal.module.css";

function DiscussionModal({
  open,
  onClose,
  onSave,
  onUpdate,
  discussion,
  userId,
}) {
  const [formData, setFormData] = useState({
    id: "",
    question: "",
    status: "OPEN",
    summary: "",
    eventId: "",
  });
  const [events, setEvents] = useState([]);

  // Fetch events for dropdown
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/event/${userId}/fetchEvents`)
      .then((response) => {
        setEvents(response.data);
      })
      .catch((err) => {
        console.error("Error fetching events:", err.message);
        toast.error("Failed to fetch events");
      });
  }, [userId]);

  // Set form data for editing
  useEffect(() => {
    if (discussion) {
      setFormData({
        id: discussion.id || "",
        question: discussion.question || "",
        status: discussion.status || "OPEN",
        summary: discussion.summary || "",
        eventId: discussion.eventId || "",
      });
    } else {
      setFormData({
        id: "",
        question: "",
        status: "OPEN",
        summary: "",
        eventId: "",
      });
    }
  }, [discussion]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    try {
      if (formData.id) {
        // Update discussion
        const response = await axios.put(
          `http://localhost:8080/api/discussion/${formData.id}?userId=${userId}`,
          {
            question: formData.question,
            status: formData.status,
            summary: formData.summary,
            eventId: formData.eventId || null,
          }
        );
        onUpdate(response.data);
      } else {
        // Create discussion
        const response = await axios.post(
          `http://localhost:8080/api/discussion?userId=${userId}&eventId=${
            formData.eventId || ""
          }`,
          {
            question: formData.question,
            status: formData.status,
            summary: formData.summary,
          }
        );
        onSave(response.data);
      }
      onClose();
    } catch (err) {
      console.error("Error saving discussion:", err.message);
      toast.error("Failed to save discussion");
    }
  };

  return (
    <Modal open={open} onClose={onClose}>
      <Box className={styles.modal}>
        <TextField
          name="question"
          label="Question"
          value={formData.question}
          onChange={handleChange}
          fullWidth
          margin="normal"
          multiline
        />
        <TextField
          name="status"
          label="Status"
          value={formData.status}
          onChange={handleChange}
          select
          fullWidth
          margin="normal"
        >
          <MenuItem value="OPEN">Open</MenuItem>
          <MenuItem value="CLOSED">Closed</MenuItem>
        </TextField>
        <TextField
          name="summary"
          label="Summary"
          value={formData.summary}
          onChange={handleChange}
          fullWidth
          margin="normal"
          multiline
        />
        <TextField
          name="eventId"
          label="Event (Optional)"
          value={formData.eventId}
          onChange={handleChange}
          select
          fullWidth
          margin="normal"
        >
          <MenuItem value="">None</MenuItem>
          {events.map((event) => (
            <MenuItem key={event.id} value={event.id}>
              {event.title}
            </MenuItem>
          ))}
        </TextField>
        <div className={styles.buttonContainer}>
          <Button variant="contained" onClick={handleSubmit}>
            Save
          </Button>
        </div>
      </Box>
    </Modal>
  );
}

export default DiscussionModal;
