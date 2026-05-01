import { useState } from "react";
type RegisterFormProps = {
  //change this to include email for setting state in app.tsx

  onRegisterSuccess: (id: string, email: string) => void;
};
const RegisterForm = ({ onRegisterSuccess }: RegisterFormProps) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async () => {
    //register endpoint call soon
    console.log("Submit button pressed");
    setError("");

    try {
      const response = await fetch(`http://localhost:8080/api/users`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({
          email: email,
          password: password,
          name: name,
        }),
      });
      if (!response.ok) {
        console.log("in the error block");
        const errorData = await response.json();
        setError(errorData.message || "Failed to create account");
        return;
      }
      const data = await response.json();

      console.log("account created", data);
      onRegisterSuccess(data.id, data.email);
      console.log("User logged in", data.id);
    } catch (err) {
      if (err instanceof Error) {
        setError(err.message);
      }
    }
  };

  return (
    <div>
      <input
        type="text"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        placeholder="Email"
      />
      <input
        type="text"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="Password"
      />
      <input
        type="text"
        value={name}
        onChange={(e) => setName(e.target.value)}
        placeholder="Name"
      />
      {error && <p style={{ color: "red" }}>{error}</p>}
      <button onClick={handleSubmit}>Submit</button>
    </div>
  );
};

export default RegisterForm;
