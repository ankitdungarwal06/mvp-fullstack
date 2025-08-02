import { useState, useEffect } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import { Modal, Box, TextField, Button, MenuItem } from "@mui/material";
import styles from "./TaskModal.module.css";

function TaskModal({
  open,
  onClose,
  onSave,
  onUpdate,
  task,
  userId,
  discussionId,
}) {
  const [formData, setFormData] = useState({
    id: "",
    description: "",
    discussionDate: "",
    notes: "",
    priority: "MEDIUM",
    followUpDate: "",
    status: "OPEN",
  });

  useEffect(() => {
    if (task) {
      setFormData({
        id: task.id || "",
        description: task.description || "",
        discussionDate: task.discussionDate
          ? new Date(task.discussionDate).toISOString().slice(0, 16)
          : "",
        notes: task.notes || "",
        priority: task.priority || "MEDIUM",
        followUpDate: task.followUpDate
          ? new Date(task.followUpDate).toISOString().slice(0, 16)
          : "",
        status: task.status || "OPEN",
      });
    } else {
      setFormData({
        id: "",
        description: "",
        discussionDate: "",
        notes: "",
        priority: "MEDIUM",
        followUpDate: "",
        status: "OPEN",
      });
    }
  }, [task]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    try {
      if (formData.id) {
        // Update task
        const response = await axios.put(
          `http://localhost:8080/api/task/${formData.id}?userId=${userId}`,
          {
            description: formData.description,
            discussionDate: formData.discussionDate,
            notes: formData.notes,
            priority: formData.priority,
            followUpDate: formData.followUpDate,
            status: formData.status,
          }
        );
        onUpdate(response.data);
      } else {
        // Create task
        const response = await axios.post(
          `http://localhost:8080/api/task?userId=${userId}`,
          {
            description: formData.description,
            discussionDate: formData.discussionDate,
            notes: formData.notes,
            priority: formData.priority,
            followUpDate: formData.followUpDate,
            status: formData.status,
          }
        );
        onSave(response.data);
      }
      onClose();
    } catch (err) {
      console.error("Error saving task:", err.message);
      toast.error("Failed to save task");
    }
  };

  return (
    <Modal open={open} onClose={onClose}>
      <Box className={styles.modal}>
        <TextField
          name="description"
          label="Description"
          value={formData.description}
          onChange={handleChange}
          fullWidth
          margin="normal"
          multiline
        />
        <TextField
          name="discussionDate"
          label="Discussion Date"
          type="datetime-local"
          value={formData.discussionDate}
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
          name="priority"
          label="Priority"
          value={formData.priority}
          onChange={handleChange}
          select
          fullWidth
          margin="normal"
        >
          <MenuItem value="LOW">Low</MenuItem>
          <MenuItem value="MEDIUM">Medium</MenuItem>
          <MenuItem value="HIGH">High</MenuItem>
        </TextField>
        <TextField
          name="followUpDate"
          label="Follow-Up Date"
          type="datetime-local"
          value={formData.followUpDate}
          onChange={handleChange}
          fullWidth
          margin="normal"
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
        <div className={styles.buttonContainer}>
          <Button variant="contained" onClick={handleSubmit}>
            Save
          </Button>
        </div>
      </Box>
    </Modal>
  );
}

export default TaskModal;
