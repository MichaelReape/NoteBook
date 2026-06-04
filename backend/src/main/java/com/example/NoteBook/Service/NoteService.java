package com.example.NoteBook.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    // we use the email when establishing the sessiion in the auth service
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

  /**
   * Update a current note
   * 
   * @param noteDTO DTO containing the updated note information, including the
   *                noteId to identify which note to update
   * @return noteDTO
   * @throws RuntimeException if the note does not belong to the current context
   *                          holder or if the note is not found
   */
  public NoteResponseDTO updateNote(NoteResponseDTO noteDTO) {
    System.out.println("updating note in Service");
    // find the note by the noteId
    Note note = findNoteByNoteId(noteDTO.getNoteId());
    // confirm the note belongs to the user
    if (checkOwnership(note) && note != null) {
      note.setBook(noteDTO.getBook());
      note.setChapter(noteDTO.getChapter());
      note.setNotes(noteDTO.getNote());
      System.out.println("Updated note in Service");
      return convertToNoteResponseDTO(noteRepository.save(note));
    } else {
      throw new RuntimeException("Note not updated");
    }
  }

  /**
   * Finds all notes user has
   * 
   * @return List of notes the user has, each note in a DTO
   */
  public List<NoteResponseDTO> findNotes() {
    Long userId = getUserIdFromSession();
    List<Note> notes = noteRepository.findByUserId(userId);
    List<NoteResponseDTO> notesDTO = new ArrayList<>();
    for (Note n : notes) {
      notesDTO.add(convertToNoteResponseDTO(n));
    }
    return notesDTO;
  }

  /**
   * Finds a note by its unique ID
   * 
   * @param noteId ID of the note to find
   * @return note as a DTO
   * @throws RuntimeException if the note does not belong to the current context
   *                          holder
   */
  public NoteResponseDTO findNote(Long noteId) {
    Note note = findNoteByNoteId(noteId);
    if (checkOwnership(note)) {
      return convertToNoteResponseDTO(note);
    } else {
      throw new RuntimeException("Note does not belong to user");
    }
  }

  /**
   * Deletes a note specified by its unique ID
   * 
   * @param noteId ID of the note to delete
   * @throws RuntimeException if the note does not belong to the current context
   *                          holder
   */
  public void deleteNote(Long noteId) {
    if (checkOwnership(findNoteByNoteId(noteId))) {
      noteRepository.deleteById(noteId);
    } else {
      throw new RuntimeException("Note does not belong to user");
    }
  }

  // helper method to convert note to a DTO
  private NoteResponseDTO convertToNoteResponseDTO(Note note) {
    return new NoteResponseDTO(note.getNoteId(), note.getBook(), note.getChapter(), note.getNotes(),
        note.getCreatedAt());
  }

  // helper method to get the userId from the session
  private Long getUserIdFromSession() {
    // Get the currently authenticated user's email from the security context
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    // Find the user ID based on the email
    AppUser appUser = appUserRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found in database"));
    return appUser.getId();
  }

  // helper method to confirm the note belongs to the authenticated user
  private boolean checkOwnership(Note note) {
    // Get the userId associated with the note
    Long userId = note.getUserId();
    // Grabs the current authenticated user's email from the context
    return Objects.equals(userId, getUserIdFromSession());
  }

  // helper method to find a note by the noteId key
  private Note findNoteByNoteId(Long noteId) {
    return noteRepository.findById(noteId).orElseThrow(
        () -> new RuntimeException("Note not found"));
  }
}// End of NoteService
