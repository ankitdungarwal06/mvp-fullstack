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
import DiscussionModal from "./DiscussionModal";
import styles from "./Discussions.module.css";

function Discussions({ userId }) {
  const [discussions, setDiscussions] = useState([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedDiscussion, setSelectedDiscussion] = useState(null);

  // Fetch discussions
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/discussion/${userId}/fetchDiscussions`)
      .then((response) => {
        setDiscussions(response.data);
      })
      .catch((err) => {
        console.error("Error fetching discussions:", err.message);
        toast.error("Failed to fetch discussions");
      });
  }, [userId]);

  // Open modal for new discussion
  const handleCreate = () => {
    setSelectedDiscussion(null);
    setModalOpen(true);
  };

  // Open modal for editing discussion
  const handleEdit = (discussion) => {
    setSelectedDiscussion(discussion);
    setModalOpen(true);
  };

  // Add discussion
  const handleAdd = (discussion) => {
    setDiscussions([...discussions, discussion]);
    setModalOpen(false);
  };

  // Update discussion
  const handleUpdate = (updatedDiscussion) => {
    setDiscussions(
      discussions.map((d) =>
        d.id === updatedDiscussion.id ? updatedDiscussion : d
      )
    );
    setModalOpen(false);
  };

  // Delete discussion
  const handleDelete = (id) => {
    setDiscussions(discussions.filter((d) => d.id !== id));
    setModalOpen(false);
  };

  return (
    <div className={styles.container}>
      <Button
        variant="contained"
        onClick={handleCreate}
        className={styles.createButton}
      >
        Create Discussion
      </Button>
      <Paper className={styles.tableContainer}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Question</TableCell>
              <TableCell>Status</TableCell>
              <TableCell>Summary</TableCell>
              <TableCell>Event</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {discussions.map((discussion) => (
              <TableRow key={discussion.id}>
                <TableCell>{discussion.question}</TableCell>
                <TableCell>{discussion.status}</TableCell>
                <TableCell>{discussion.summary || "N/A"}</TableCell>
                <TableCell>
                  {discussion.event ? discussion.event.title : "None"}
                </TableCell>
                <TableCell>
                  <Button onClick={() => handleEdit(discussion)}>Edit</Button>
                  <Button
                    color="error"
                    onClick={() => {
                      if (window.confirm("Delete discussion?")) {
                        axios
                          .delete(
                            `http://localhost:8080/api/discussion/${discussion.id}?userId=${userId}`
                          )
                          .then(() => handleDelete(discussion.id))
                          .catch((err) =>
                            toast.error("Failed to delete discussion")
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
      <DiscussionModal
        open={modalOpen}
        onClose={() => setModalOpen(false)}
        onSave={handleAdd}
        onUpdate={handleUpdate}
        discussion={selectedDiscussion}
        userId={userId}
      />
    </div>
  );
}

export default Discussions;
