package com.example.NoteBook.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.NoteBook.DTO.AppUserCreateDTO;
import com.example.NoteBook.DTO.AppUserViewDTO;
import com.example.NoteBook.DTO.LoginRequestDTO;
import com.example.NoteBook.Service.AppUserService;
import com.example.NoteBook.Service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class AppUserController {
  // api access
  private final AppUserService appUserService;
  private final AuthService authService;

  // constructor injection
  public AppUserController(AppUserService appUserService, AuthService authService) {
    this.appUserService = appUserService;
    this.authService = authService;
  }

  // // save user
  @PostMapping
  public AppUserViewDTO createUser(@Valid @RequestBody AppUserCreateDTO dto,
      HttpServletResponse response,
      HttpServletRequest request) {
    AppUserViewDTO newUser = appUserService.saveUser(dto);
    // Automatically log in the user after registration
    authService.establishSession(newUser.getEmail(), request, response);
    return newUser;
  }

  // login user
  @PostMapping("/login")
  public ResponseEntity<AppUserViewDTO> loginUser(@Valid @RequestBody LoginRequestDTO loginRequest,
      HttpServletResponse response, HttpServletRequest request) {
    // Authenticate the user
    AppUserViewDTO dto = appUserService.authenticateUser(loginRequest);
    // Create an authentication token and establish session
    authService.establishSession(dto.getEmail(), request, response);
    System.out.println("good job Michael, I am logged in ");
    return ResponseEntity.ok(dto);
  }

  // get user by id
  // i want this to check if there is a valid session first
  @GetMapping("/{id}")
  public ResponseEntity<AppUserViewDTO> getUserById(@PathVariable long id,
      Authentication auth) {
    if (auth == null) {
      System.out.print("getting user in controller, auth is null");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    return ResponseEntity.ok(appUserService.getUserById(id));
  }

  // get all users
  @GetMapping
  public List<AppUserViewDTO> getUsers() {
    return appUserService.getAllUsers();
  }

  // delete user by id
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT) // 204 if successful
  public void deleteById(@PathVariable long id) {
    appUserService.deleteUser(id);
  }

  // // delete later just for testing
  // @GetMapping("/me")
  // public ResponseEntity<UserViewDTO> currentUser(Authentication auth) {
  // // this should return the user based on the session
  // if (auth == null) {
  // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  // }
  // return ResponseEntity.ok(userService.getUserByEmail(auth.getName()));
  // }

}
