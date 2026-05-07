package com.example.NoteBook.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.NoteBook.DTO.CreateNoteDTO;
import com.example.NoteBook.DTO.NoteResponseDTO;
import com.example.NoteBook.Entity.AppUser;
import com.example.NoteBook.Entity.Note;
import com.example.NoteBook.Repository.AppUserRepository;
import com.example.NoteBook.Repository.NoteRepository;

@Service
public class NoteService {
  private final NoteRepository noteRepository;
  private final AppUserRepository appUserRepository;

  public NoteService(NoteRepository noteRepository, AppUserRepository appUserRepository) {
    this.noteRepository = noteRepository;
    this.appUserRepository = appUserRepository;
  }

  public NoteResponseDTO saveNote(CreateNoteDTO createNoteDTO) {
    // Get the currently authenticated user's email from the security context
    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    // Find the user ID based on the email to save the note to the correct user
    AppUser appUser = appUserRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));
    // Create a new note entity and save it to the database with the userid from the
    // session
    Note note = new Note(appUser.getId(), createNoteDTO.getBook(), createNoteDTO.getChapter(),
        createNoteDTO.getNote());
    return convertToNoteResponseDTO(noteRepository.save(note));
  }

  public List<NoteResponseDTO> findNotes(Long userId) {
    System.out.println("I am in the searchNotes service");
    List<Note> notes = noteRepository.findByUserId(userId);
    List<NoteResponseDTO> notesDTO = new ArrayList<>();
    for (Note n : notes) {
      notesDTO.add(convertToNoteResponseDTO(n));
    }
    return notesDTO;
  }

  public NoteResponseDTO findNote(Long noteId) {
    System.out.println("I am in the findNote service");
    Note note = noteRepository.findById(noteId).orElseThrow(
        () -> new RuntimeException("Note not found"));
    return convertToNoteResponseDTO(note);
  }

  public void deleteNote(Long noteId) {
    System.out.println("I am working in the deleteNote Service");
    noteRepository.deleteById(noteId);
  }

  private NoteResponseDTO convertToNoteResponseDTO(Note note) {
    return new NoteResponseDTO(note.getNoteId(), note.getBook(), note.getChapter(), note.getNotes(),
        note.getCreatedAt());
  }
}
