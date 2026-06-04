package com.example.NoteBook.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.NoteBook.DTO.CreateNoteDTO;
import com.example.NoteBook.DTO.NoteResponseDTO;
import com.example.NoteBook.Service.NoteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/notes")
public class NoteController {
  private final NoteService noteService;

  // constructor injection
  public NoteController(NoteService noteService) {
    this.noteService = noteService;
  }

  /**
   * Saves a note to the database for the currently authenticated user. The userId
   * is pulled from the session and associated with the note when it is saved.
   * 
   * @param createNoteDTO DTO containing the book, chapter, and note content to be
   *                      saved
   * @return The saved note in a safe DTO format without sensitive information
   *         like userId
   */
  @PostMapping
  public NoteResponseDTO createNote(@Valid @RequestBody CreateNoteDTO createNoteDTO) {
    return noteService.saveNote(createNoteDTO);
  }

  // update note
  @PutMapping
  /**
   * Update a current note
   * 
   * @param noteDTO DTO containing the updated note information, including the
   *                noteId to identify which note to update
   * @return noteDTO
   * @throws RuntimeException if the note does not belong to the current context
   *                          holder or if the note is not found
   */
  public NoteResponseDTO updateNote(@RequestBody NoteResponseDTO noteDTO) {
    System.out.println("updating note in Controller");
    return noteService.updateNote(noteDTO);
  }

  /**
   * Finds all notes user has
   *
   * @return List of notes the user has, each note in a DTO
   */
  @GetMapping("/allNotes")
  public ResponseEntity<List<NoteResponseDTO>> findNotes() {
    System.out.println("getting all notes for user in Controller");
    return ResponseEntity.ok(noteService.findNotes());
  }

  // gets a note by the noteId key, primary key in note database table
  // returns noteResponseDTO => book, chapter, note, noteId, createdAt
  @GetMapping("/{noteId}")
  public ResponseEntity<NoteResponseDTO> findNote(@PathVariable Long noteId, Authentication auth) {
    System.out.println("getting note by noteId in Controller");
    if (auth == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    return ResponseEntity.ok(noteService.findNote(noteId));
  }

  // deletes a note by the noteId key
  @DeleteMapping("/{noteId}")
  public ResponseEntity<Void> deleteNote(@PathVariable Long noteId) {
    noteService.deleteNote(noteId);
    return ResponseEntity.noContent().build();
  }
}
