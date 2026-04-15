// src/Tiptap.tsx
import { useEditor, EditorContent } from '@tiptap/react'
import { FloatingMenu, BubbleMenu } from '@tiptap/react/menus'
import StarterKit from '@tiptap/starter-kit'
import type { NoteDTO } from '../types/note.ts'
import { useState } from 'react';

const Tiptap = ({ userId }: { userId: String }) => {

  const editor = useEditor({
    extensions: [StarterKit], // define your extension array
    content: '<p>Enter your note here</p>', // initial content
  })

  const [note, setNote] = useState<NoteDTO | null>(null);
  const saveNote = async () => {
    try {
      console.log("Saving notes");
      const content = editor.getHTML();
      const response = await fetch(
        `http://localhost:8080/api/notes`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          credentials: "include",
          body: JSON.stringify({
            userId: userId,
            note: content
          }),
        }
      );
      const data = await response.json();
      setNote(data);
      console.log("Saved ", data.noteId, " at ", data.createdAt);
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <>
      <EditorContent editor={editor} />
      <FloatingMenu editor={editor}></FloatingMenu>
      <BubbleMenu editor={editor}>This is the bubble menu</BubbleMenu>
      <button onClick={saveNote}>Save Note</button>
    </>
  )
}

export default Tiptap
