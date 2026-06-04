import type { NoteDTO } from "../types/note";
import { useState } from "react";

type NotesListProps = {
  onOpen: (note: NoteDTO) => void;
  notes: NoteDTO[];
  selectedNote: NoteDTO | null;
};

const NotesList = ({ onOpen, notes, selectedNote }: NotesListProps) => {
  // need to display a list of notes from the notes prop, when one is seleceted then onOpen passes it back to app.tsx to be loaded into the editor

  return (
    <div>
      {notes.map((note) => (
        <div
          key={note.noteId}
          onClick={() => onOpen(note)}
          style={{
            backgroundColor:
              selectedNote?.noteId === note.noteId ? "#ddd" : "transparent",
            cursor: "pointer",
          }}
        >
          <h3>{note.note}</h3>
        </div>
      ))}
    </div>
  );
};

export default NotesList;
