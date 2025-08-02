import { useState, useEffect } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import {
  Button,
  TextField,
  List,
  ListItem,
  ListItemText,
  Paper,
} from "@mui/material";
import DailyNoteModal from "./DailyNoteModal";
import styles from "./DailyNotes.module.css";

function DailyNotes({ userId }) {
  const [notes, setNotes] = useState([]);
  const [searchTag, setSearchTag] = useState("");
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedNote, setSelectedNote] = useState(null);

  // Fetch notes
  useEffect(() => {
    const url = searchTag
      ? `http://localhost:8080/api/daily-note/${userId}/search?tag=${searchTag}`
      : `http://localhost:8080/api/daily-note/${userId}/fetchNotes`;
    axios
      .get(url)
      .then((response) => {
        console.log("Fetched notes:", response.data);
        setNotes(response.data);
      })
      .catch((err) => {
        console.error("Error fetching notes:", err.message);
        toast.error("Failed to fetch notes");
      });
  }, [userId, searchTag]);

  // Open modal for new note
  const handleCreate = () => {
    setSelectedNote(null);
    setModalOpen(true);
  };

  // Open modal for editing note
  const handleEdit = (note) => {
    setSelectedNote(note);
    setModalOpen(true);
  };

  // Add note
  const handleAdd = (note) => {
    setNotes([...notes, note]);
    setModalOpen(false);
  };

  // Update note
  const handleUpdate = (updatedNote) => {
    setNotes(notes.map((n) => (n.id === updatedNote.id ? updatedNote : n)));
    setModalOpen(false);
  };

  // Delete note
  const handleDelete = (id) => {
    setNotes(notes.filter((n) => n.id !== id));
    setModalOpen(false);
  };

  return (
    <div className={styles.container}>
      <div className={styles.controls}>
        <TextField
          label="Search by Tag"
          value={searchTag}
          onChange={(e) => setSearchTag(e.target.value)}
          className={styles.search}
        />
        <Button
          variant="contained"
          onClick={handleCreate}
          className={styles.createButton}
        >
          Create Note
        </Button>
      </div>
      {notes.length === 0 ? (
        <p>No notes found.</p>
      ) : (
        <Paper className={styles.listContainer}>
          <List>
            {notes.map((note) => (
              <ListItem key={note.id} className={styles.listItem}>
                <ListItemText
                  primary={`${note.content} (${note.tags || "No tags"})`}
                  secondary={
                    note.date ? new Date(note.date).toLocaleDateString() : "N/A"
                  }
                />
                <Button onClick={() => handleEdit(note)}>Edit</Button>
                <Button
                  color="error"
                  onClick={() => {
                    if (window.confirm("Delete note?")) {
                      axios
                        .delete(
                          `http://localhost:8080/api/daily-note/${note.id}?userId=${userId}`
                        )
                        .then(() => handleDelete(note.id))
                        .catch((err) => toast.error("Failed to delete note"));
                    }
                  }}
                >
                  Delete
                </Button>
              </ListItem>
            ))}
          </List>
        </Paper>
      )}
      <DailyNoteModal
        open={modalOpen}
        onClose={() => setModalOpen(false)}
        onSave={handleAdd}
        onUpdate={handleUpdate}
        note={selectedNote}
        userId={userId}
      />
    </div>
  );
}

export default DailyNotes;
