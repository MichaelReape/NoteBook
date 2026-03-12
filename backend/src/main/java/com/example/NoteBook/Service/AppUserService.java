package com.example.NoteBook.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.NoteBook.DTO.AppUserCreateDTO;
import com.example.NoteBook.DTO.AppUserViewDTO;
import com.example.NoteBook.DTO.LoginRequestDTO;
import com.example.NoteBook.Entity.AppUser;
import com.example.NoteBook.Repository.AppUserRepository;

@Service
public class AppUserService {
  // inject repository and password encoder
  private final AppUserRepository appUserRepository;
  private final PasswordEncoder passwordEncoder;

  // constructor injection
  public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
    this.appUserRepository = appUserRepository;
    this.passwordEncoder = passwordEncoder;
  }

  // save the user
  public AppUserViewDTO saveUser(AppUserCreateDTO dto) {
    if (appUserRepository.existsByEmail(dto.getEmail())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
    }
    AppUser newUser = new AppUser(dto.getEmail(), dto.getName(),
        passwordEncoder.encode(dto.getPassword()));
    AppUser savedUser = appUserRepository.save(newUser);
    return convertToViewDTO(savedUser);
  }

  // get user by id
  public AppUserViewDTO getUserById(long id) {
    AppUser appUser = appUserRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    return convertToViewDTO(appUser);
  }

  // get user by email
  public AppUserViewDTO getUserByEmail(String email) {
    AppUser appUser = appUserRepository.findByEmail(email)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    return convertToViewDTO(appUser);
  }

  // get all users
  public List<AppUserViewDTO> getAllUsers() {
    return appUserRepository.findAll().stream().map(this::convertToViewDTO).toList();
  }

  // delete user by id
  public void deleteUser(long id) {
    if (!appUserRepository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
    appUserRepository.deleteById(id);
  }

  // Authenticate user
  public AppUserViewDTO authenticateUser(LoginRequestDTO requestDTO) {

    AppUser appUser = appUserRepository.findByEmail(requestDTO.getEmail())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    if (!passwordEncoder.matches(requestDTO.getPassword(),
        appUser.getPasswordHash())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
    return convertToViewDTO(appUser);
  }

  // convert to view DTO
  private AppUserViewDTO convertToViewDTO(AppUser appUser) {
    return new AppUserViewDTO(appUser.getId(), appUser.getEmail(), appUser.getName(), appUser.getCreatedAt());
  }
}
