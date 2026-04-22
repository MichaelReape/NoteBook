import { useState } from 'react'
import './App.css'
import type { NoteDTO } from './types/note'
import Tiptap from './components/Tiptap.tsx'
import Modal from './components/Modal.tsx'
import RegisterForm from './components/RegisterForm.tsx'

function App() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [userId, setUserId] = useState("");
  const [notes, setNotes] = useState<NoteDTO[]>([]);
  const [isCreateAccountOpen, setIsCreateAccountOpen] = useState(false);

  const handleLogin = async () => {
    //send the username and password to the backend
    try {
      console.log("Login clicked, " + email + " " + password);
      const response = await fetch(
        `http://localhost:8080/api/users/login`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          credentials: "include",
          body: JSON.stringify({
            email: email,
            password: password,
          }),
        }
      );
      if (!response.ok) {
        throw new Error("Login failed");
      }
      const data = await response.json();
      setUserId(data.id);
      console.log("User logged in", data);
      //why do we return?
      return;
    }
    catch (error) {
      console.log(error)
    }
  };

  const toggleCreateAccountModal = () => {
    setIsCreateAccountOpen(!isCreateAccountOpen);
  };
  const getAllNotes = async () => {
    try {
      console.log("Getting all notes");
      const response = await fetch(
        `http://localhost:8080/api/notes/user/${userId}`,
        {
          method: "GET",
          headers: { "Content-Type": "application/json" },
          credentials: "include",
        }
      )
      if (!response.ok) {
        throw new Error("Failed to fetch users notes");
      }
      const data = await response.json();
      setNotes(data);
      // console.log(data);
      // for(NotesDTO : notes){
      //
      // };

      // prints the notes to console
      data.forEach((notes: NoteDTO) => {
        console.log(notes.note);
      });
      return;
    } catch (error) { console.log(error); }
  }
  return (
    <div>
      {/* //input box for email address */}
      <input type="text" value={email} onChange={(e) => setEmail(e.target.value)} placeholder='Email' />
      {/* //input box for password */}
      <input type="text" value={password} onChange={(e) => setPassword(e.target.value)} placeholder='password' />
      {/* //login button */}
      <button onClick={handleLogin}>Login</button>

      <button onClick={toggleCreateAccountModal}>Create Account</button>
      <button onClick={getAllNotes}>Notes</button>

      {isCreateAccountOpen && <Modal onClose={() => setIsCreateAccountOpen(false)} >
        <RegisterForm />
      </Modal>
      }
      <div className='card'>
        <Tiptap userId={userId} />
      </div>


    </div>
  );
}

export default App
