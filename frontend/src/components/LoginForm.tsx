import { useState } from "react";

type LoginFormProps = {
  onLoginSuccess: (id: string, email: string) => void;
};

const LoginForm = ({ onLoginSuccess }: LoginFormProps) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleLogin = async () => {
    //send the username and password to the backend
    console.log("login button pressed");
    setError("");
    try {
      console.log("Login clicked, " + email + " " + password);
      const response = await fetch(`http://localhost:8080/api/users/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({
          email: email,
          password: password,
        }),
      });
      if (!response.ok) {
        throw new Error("Login failed");
      }
      const data = await response.json();
      onLoginSuccess(data.id, data.email);
      console.log("User logged in", data);
      return;
    } catch (err) {
      if (err instanceof Error) {
        setError(err.message);
      }
    }
  };
  return (
    <div>
      {/* //input box for email address */}
      <input
        type="text"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        placeholder="Email"
      />
      {/* //input box for password */}
      <input
        type="text"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="password"
      />
      {error && <p style={{ color: "red" }}>{error}</p>}
      <button onClick={handleLogin}>Submit</button>
    </div>
  );
};
export default LoginForm;
