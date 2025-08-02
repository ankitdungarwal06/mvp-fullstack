import { useState, useEffect } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import { Modal, Box, TextField, Button } from "@mui/material";
import styles from "./DailyNoteModal.module.css";

function DailyNoteModal({ open, onClose, onSave, onUpdate, note, userId }) {
  const [formData, setFormData] = useState({
    id: "",
    content: "",
    date: "",
    tags: "",
  });

  useEffect(() => {
    if (note) {
      setFormData({
        id: note.id || "",
        content: note.content || "",
        date: note.date ? new Date(note.date).toISOString().slice(0, 10) : "",
        tags: note.tags || "",
      });
    } else {
      setFormData({
        id: "",
        content: "",
        date: "",
        tags: "",
      });
    }
  }, [note]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    try {
      if (formData.id) {
        // Update note
        const response = await axios.put(
          `http://localhost:8080/api/daily-note/${formData.id}?userId=${userId}`,
          {
            content: formData.content,
            date: formData.date,
            tags: formData.tags,
          }
        );
        onUpdate(response.data);
      } else {
        // Create note
        const response = await axios.post(
          `http://localhost:8080/api/daily-note?userId=${userId}`,
          {
            content: formData.content,
            date: formData.date,
            tags: formData.tags,
          }
        );
        onSave(response.data);
      }
      onClose();
    } catch (err) {
      console.error("Error saving note:", err.message);
      toast.error("Failed to save note");
    }
  };

  return (
    <Modal open={open} onClose={onClose}>
      <Box className={styles.modal}>
        <TextField
          name="content"
          label="Content"
          value={formData.content}
          onChange={handleChange}
          fullWidth
          margin="normal"
          multiline
        />
        <TextField
          name="date"
          label="Date"
          type="date"
          value={formData.date}
          onChange={handleChange}
          fullWidth
          margin="normal"
        />
        <TextField
          name="tags"
          label="Tags (comma-separated)"
          value={formData.tags}
          onChange={handleChange}
          fullWidth
          margin="normal"
        />
        <div className={styles.buttonContainer}>
          <Button variant="contained" onClick={handleSubmit}>
            Save
          </Button>
        </div>
      </Box>
    </Modal>
  );
}

export default DailyNoteModal;
