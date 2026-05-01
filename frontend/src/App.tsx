import { useState } from "react";
import "./App.css";
import type { NoteDTO } from "./types/note";
import Tiptap from "./components/Tiptap.tsx";
import Modal from "./components/Modal.tsx";
import RegisterForm from "./components/RegisterForm.tsx";
import LoginForm from "./components/LoginForm.tsx";

function App() {
  const [email, setEmail] = useState("");
  const [userId, setUserId] = useState("");
  const [notes, setNotes] = useState<NoteDTO[]>([]);
  const [isCreateAccountOpen, setIsCreateAccountOpen] = useState(false);
  const [isLoginOpen, setIsLoginOpen] = useState(false);

  const toggleCreateAccountModal = () => {
    setIsCreateAccountOpen(!isCreateAccountOpen);
    setIsLoginOpen(false);
  };
  const toggleLoginModal = () => {
    setIsLoginOpen(!isLoginOpen);
    setIsCreateAccountOpen(false);
  };
  const logout = () => {
    setUserId("");
    setNotes([]);
    setEmail("");
    //need to put logout logic here or maybe in a component? not sure
  };
  const getAllNotes = async () => {
    try {
      console.log("Getting all notes for ", email);
      const response = await fetch(
        `http://localhost:8080/api/notes/user/${userId}`,
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
              setEmail(email);
              setIsLoginOpen(false);
            }}
          />
        </Modal>
      )}
      {email && <p>Welcome, {email}!</p>}
      {userId && <button onClick={logout}>Logout</button>}

      {userId && <button onClick={getAllNotes}>Notes</button>}
      <div className="card">
        <Tiptap userId={userId} />
      </div>
    </div>
  );
}

export default App;
