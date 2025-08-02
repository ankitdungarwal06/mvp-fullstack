import { useState, useEffect } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import {
  Button,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Paper,
} from "@mui/material";
import TaskModal from "./TaskModal";
import styles from "./TaskModal.module.css";

function Tasks({ userId }) {
  const [tasks, setTasks] = useState([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedTask, setSelectedTask] = useState(null);

  // Fetch tasks
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/task/${userId}/fetchTasks`)
      .then((response) => {
        console.log("Fetched tasks:", response.data);
        setTasks(response.data);
      })
      .catch((err) => {
        console.error("Error fetching tasks:", err.message);
        toast.error("Failed to fetch tasks");
      });
  }, [userId]);

  // Open modal for new task
  const handleCreate = () => {
    setSelectedTask(null);
    setModalOpen(true);
  };

  // Open modal for editing task
  const handleEdit = (task) => {
    setSelectedTask(task);
    setModalOpen(true);
  };

  // Add task
  const handleAdd = (task) => {
    setTasks([...tasks, task]);
    setModalOpen(false);
  };

  // Update task
  const handleUpdate = (updatedTask) => {
    setTasks(tasks.map((t) => (t.id === updatedTask.id ? updatedTask : t)));
    setModalOpen(false);
  };

  // Delete task
  const handleDelete = (id) => {
    setTasks(tasks.filter((t) => t.id !== id));
    setModalOpen(false);
  };

  return (
    <div className={styles.container}>
      <Button
        variant="contained"
        onClick={handleCreate}
        className={styles.createButton}
      >
        Create Task
      </Button>
      {tasks.length === 0 ? (
        <p>No tasks found.</p>
      ) : (
        <Paper className={styles.tableContainer}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Description</TableCell>
                <TableCell>Priority</TableCell>
                <TableCell>Status</TableCell>
                <TableCell>Follow-Up Date</TableCell>
                <TableCell>Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {tasks.map((task) => (
                <TableRow key={task.id}>
                  <TableCell>{task.description || "N/A"}</TableCell>
                  <TableCell>{task.priority || "N/A"}</TableCell>
                  <TableCell>{task.status || "N/A"}</TableCell>
                  <TableCell>
                    {task.followUpDate
                      ? new Date(task.followUpDate).toLocaleDateString()
                      : "N/A"}
                  </TableCell>
                  <TableCell>
                    <Button onClick={() => handleEdit(task)}>Edit</Button>
                    <Button
                      color="error"
                      onClick={() => {
                        if (window.confirm("Delete task?")) {
                          axios
                            .delete(
                              `http://localhost:8080/api/task/${task.id}?userId=${userId}`
                            )
                            .then(() => handleDelete(task.id))
                            .catch((err) =>
                              toast.error("Failed to delete task")
                            );
                        }
                      }}
                    >
                      Delete
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Paper>
      )}
      <TaskModal
        open={modalOpen}
        onClose={() => setModalOpen(false)}
        onSave={handleAdd}
        onUpdate={handleUpdate}
        task={selectedTask}
        userId={userId}
        discussionId={null}
      />
    </div>
  );
}

export default Tasks;
