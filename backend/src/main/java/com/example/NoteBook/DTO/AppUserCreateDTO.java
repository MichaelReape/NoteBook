package com.example.NoteBook.DTO;

// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Size;

public class AppUserCreateDTO {

    // @NotBlank(message = "Email is mandatory")
    // @Email(message = "Email should be valid")
    // @Size(max = 300, message = "Email is too long")
    private String email;
    // @NotBlank(message = "Password is mandatory")
    // @Size(min = 8, max = 100, message = "Password must be between 8 and 100
    // characters")
    private String password;
    // @NotBlank(message = "Name is mandatory")
    // @Size(max = 100, message = "Name is too long")
    private String name;

    public AppUserCreateDTO() {
    }

    public AppUserCreateDTO(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    // setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
}
