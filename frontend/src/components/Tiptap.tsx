// src/Tiptap.tsx
import { useEditor, EditorContent } from "@tiptap/react";
import { FloatingMenu, BubbleMenu } from "@tiptap/react/menus";
import StarterKit from "@tiptap/starter-kit";
import type { NoteDTO } from "../types/note.ts";
import { useEffect, useState } from "react";

const Tiptap = ({
  loadedNote,
  onNoteSaved,
}: {
  loadedNote: NoteDTO | null;
  onNoteSaved: () => void;
}) => {
  const editor = useEditor({
    extensions: [StarterKit], // define your extension array
    content: "<p>Enter your note here</p>", // initial content
  });

  const [note, setNote] = useState<NoteDTO | null>(null);
  const saveNote = async () => {
    if (!loadedNote) {
      try {
        console.log("Saving notes");
        const content = editor.getHTML();
        const response = await fetch(`http://localhost:8080/api/notes`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          credentials: "include",
          body: JSON.stringify({
            note: content,
          }),
        });
        // idk if this is necessary or not
        // REVIEW LATER
        const data = await response.json();
        setNote(data);
        console.log("Saved ", data.noteId, " at ", data.createdAt);
      } catch (error) {
        console.log(error);
      }
    } else {
      try {
        console.log("updating note at the start of the saveNote function");
        const content = editor.getHTML();
        const response = await fetch(`http://localhost:8080/api/notes`, {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          credentials: "include",
          body: JSON.stringify({
            noteId: loadedNote.noteId,
            book: loadedNote.book,
            chapter: loadedNote.chapter,
            note: content,
            createdAt: loadedNote.createdAt,
          }),
        });
        const data = await response.json();
        setNote(data);
        console.log("Updated ", data.noteId);
        console.log(data.note);
      } catch (error) {
        console.log(error);
      }
    }
    onNoteSaved();
  };

  useEffect(() => {
    if (loadedNote?.note && editor) {
      editor.commands.setContent(loadedNote.note);
    }
  }, [loadedNote, editor]);
  return (
    <>
      <EditorContent editor={editor} />
      <FloatingMenu editor={editor}></FloatingMenu>
      {/* add bubble menu for formatting options like bold, italic */}
      <BubbleMenu editor={editor}>This is the bubble menu</BubbleMenu>
      <button onClick={saveNote}>Save Note</button>
      {/* <button onClick={loadNote}> Load Note</button> */}
    </>
  );
};

export default Tiptap;
