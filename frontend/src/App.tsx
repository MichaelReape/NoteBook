import { useState } from "react";
import "./App.css";
import type { NoteDTO } from "./types/note";
import Tiptap from "./components/Tiptap.tsx";
import Modal from "./components/Modal.tsx";
import RegisterForm from "./components/RegisterForm.tsx";
import LoginForm from "./components/LoginForm.tsx";
import NotesList from "./components/NotesList.tsx";

function App() {
  const [email, setEmail] = useState("");
  const [userId, setUserId] = useState("");
  const [notes, setNotes] = useState<NoteDTO[]>([]);
  const [isCreateAccountOpen, setIsCreateAccountOpen] = useState(false);
  const [isLoginOpen, setIsLoginOpen] = useState(false);
  const [selectedNote, setSelectedNote] = useState<NoteDTO | null>(null);

  const toggleCreateAccountModal = () => {
    setIsCreateAccountOpen(!isCreateAccountOpen);
    setIsLoginOpen(false);
  };
  const toggleLoginModal = () => {
    setIsLoginOpen(!isLoginOpen);
    setIsCreateAccountOpen(false);
  };
  const logout = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/users/logout`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
      });
      if (!response.ok) {
        throw new Error("Failed to logout user");
      }
      console.log(userId, " logged out");
      setUserId("");
      setNotes([]);
      setEmail("");
    } catch (error) {
      console.log(error);
    }
  };
  const getAllNotes = async (id: string) => {
    try {
      // console.log("Getting all notes for ", email);
      // need to change this in the backend so that the userId is pulled from the session instead of being passed in the request body, for security reasons
      const response = await fetch(
        `http://localhost:8080/api/notes/user/${id}`,
        {
          method: "GET",
          headers: { "Content-Type": "application/json" },
          credentials: "include",
        },
      );
      if (!response.ok) {
        throw new Error("Failed to fetch users notes");
      }
      const data = await response.json();
      setNotes(data);

      // prints the notes to console
      data.forEach((notes: NoteDTO) => {
        console.log(notes.note);
      });
      return;
    } catch (error) {
      console.log(error);
    }
  };
  return (
    <div>
      {/* //login button */}
      {!userId && <button onClick={toggleLoginModal}>Login</button>}
      {!userId && (
        <button onClick={toggleCreateAccountModal}>Create Account</button>
      )}

      {isCreateAccountOpen && (
        <Modal onClose={() => setIsCreateAccountOpen(false)}>
          <RegisterForm
            //need to log user in after registration and display the logout button
            //need to do onLoginsuccess i think
            onRegisterSuccess={(id, email) => {
              setUserId(id);
              setEmail(email);
              setIsCreateAccountOpen(false);
            }}
          />
        </Modal>
      )}
      {isLoginOpen && (
        <Modal onClose={() => setIsLoginOpen(false)}>
          <LoginForm
            onLoginSuccess={(id, email) => {
              setUserId(id);
              getAllNotes(id);
              setEmail(email);
              setIsLoginOpen(false);
            }}
          />
        </Modal>
      )}
      {email && <p>Welcome, {email}!</p>}
      {userId && <button onClick={logout}>Logout</button>}

      <NotesList
        notes={notes}
        onOpen={(note) => {
          setSelectedNote(note);
        }}
      />
      {/* {userId && <button onClick={getAllNotes(userId)}>Notes</button>} */}
      <div className="card">
        {/* here need to add logic that if the note is set to something and load note pressed then set content to it */}
        <Tiptap userId={userId} loadedNote={selectedNote} />
      </div>
    </div>
  );
}

export default App;
