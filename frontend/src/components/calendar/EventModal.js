import { Modal, Box, TextField, Button, MenuItem } from "@mui/material";
import { useState, useEffect } from "react";
import axios from "axios";
import { toast } from "react-toastify";

function EventModal({
  open,
  onClose,
  onSave,
  onUpdate,
  onDelete,
  event,
  userId,
}) {
  const [formData, setFormData] = useState({
    id: "",
    title: "",
    start: "",
    end: "",
    location: "",
    notes: "",
    category: "Work",
    userId,
  });

  // Update formData when event prop changes
  useEffect(() => {
    if (event) {
      setFormData({
        id: event.id || "",
        title: event.title || "",
        start: event.start || "",
        end: event.end || "",
        location: event.location || "",
        notes: event.notes || "",
        category: event.category || "Work",
        userId: event.userId || userId,
      });
    } else {
      setFormData({
        id: "",
        title: "",
        start: "",
        end: "",
        location: "",
        notes: "",
        category: "Work",
        userId,
      });
    }
  }, [event, userId]);

  // Update form when typing
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // Save or update event
  const handleSubmit = async () => {
    try {
      if (formData.id) {
        // Update existing event
        const response = await axios.put(
          `http://localhost:8080/api/event/${formData.id}?userId=${userId}`,
          {
            title: formData.title,
            start: formData.start,
            end: formData.end,
            location: formData.location,
            notes: formData.notes,
            category: formData.category,
          }
        );
        onUpdate(response.data);
      } else {
        // Create new event
        const response = await axios.post(
          `http://localhost:8080/api/event?userId=${userId}`,
          {
            title: formData.title,
            start: formData.start,
            end: formData.end,
            location: formData.location,
            notes: formData.notes,
            category: formData.category,
          }
        );
        onSave(response.data);
      }
      onClose();
    } catch (err) {
      console.error("Error saving event:", err.message);
      toast.error("Failed to save event");
    }
  };

  // Delete event
  const handleDelete = async () => {
    if (formData.id) {
      try {
        await axios.delete(
          `http://localhost:8080/api/event/${formData.id}?userId=${userId}`
        );
        onDelete(formData.id);
        onClose();
      } catch (err) {
        console.error("Error deleting event:", err.message);
        toast.error("Failed to delete event");
      }
    }
  };

  return (
    <Modal open={open} onClose={onClose}>
      <Box
        sx={{
          p: 3,
          backgroundColor: "white",
          margin: "auto",
          top: "15%",
          left: "50%",
          transform: "translate(-50%, 0)",
          width: { xs: "80%", sm: 350 },
          maxHeight: "70vh",
          overflowY: "auto",
          borderRadius: 2,
          boxShadow: 24,
        }}
      >
        <TextField
          name="title"
          label="Title"
          value={formData.title}
          onChange={handleChange}
          fullWidth
          margin="normal"
        />
        <TextField
          name="start"
          label="Start"
          type="datetime-local"
          value={formData.start}
          onChange={handleChange}
          fullWidth
          margin="normal"
        />
        <TextField
          name="end"
          label="End"
          type="datetime-local"
          value={formData.end}
          onChange={handleChange}
          fullWidth
          margin="normal"
        />
        <TextField
          name="location"
          label="Location"
          value={formData.location}
          onChange={handleChange}
          fullWidth
          margin="normal"
        />
        <TextField
          name="notes"
          label="Notes"
          value={formData.notes}
          onChange={handleChange}
          fullWidth
          margin="normal"
          multiline
        />
        <TextField
          name="category"
          label="Category"
          value={formData.category}
          onChange={handleChange}
          select
          fullWidth
          margin="normal"
        >
          <MenuItem value="Work">Work</MenuItem>
          <MenuItem value="Personal">Personal</MenuItem>
          <MenuItem value="Travel">Travel</MenuItem>
          <MenuItem value="Meeting">Meeting</MenuItem>
          <MenuItem value="Other">Other</MenuItem>
        </TextField>
        <Box
          sx={{ mt: 2, display: "flex", gap: 1, justifyContent: "flex-end" }}
        >
          <Button onClick={handleSubmit} variant="contained">
            Save
          </Button>
          {formData.id && (
            <Button onClick={handleDelete} variant="outlined" color="error">
              Delete
            </Button>
          )}
        </Box>
      </Box>
    </Modal>
  );
}

export default EventModal;
