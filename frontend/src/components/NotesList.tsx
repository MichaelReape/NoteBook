import type { NoteDTO } from "../types/note";
import { useState } from "react";

type NotesListProps = {
  onOpen: (note: NoteDTO) => void;
  notes: NoteDTO[];
};

const NotesList = ({ onOpen, notes }: NotesListProps) => {
  const [selectedNote, setSelectedNote] = useState<NoteDTO | null>(null);

  // need to display a list of notes from the notes prop, when one is seleceted then onOpen passes it back to app.tsx to be loaded into the editor

  return (
    <div>
      {notes.map((note) => (
        <div
          key={note.noteId}
          onClick={() => setSelectedNote(note)}
          style={{
            backgroundColor:
              selectedNote?.noteId === note.noteId ? "#ddd" : "transparent",
            cursor: "pointer",
          }}
        >
          <h3>{note.note}</h3>
        </div>
      ))}
      <button onClick={() => selectedNote && onOpen(selectedNote)}>
        Open Note
      </button>
    </div>
  );
};

export default NotesList;
