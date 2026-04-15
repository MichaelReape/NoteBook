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
import org.springframework.web.bind.annotation.RestController;

import com.example.NoteBook.DTO.CreateNoteDTO;
import com.example.NoteBook.DTO.NoteResponseDTO;
import com.example.NoteBook.Service.NoteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/notes")
public class NoteController {
  private final NoteService noteService;

  public NoteController(NoteService noteService) {
    this.noteService = noteService;
  }

  // save the note
  @PostMapping
  public NoteResponseDTO createNote(@Valid @RequestBody CreateNoteDTO createNoteDTO) {
    System.out.println("I am working in the post endpoint");
    return noteService.saveNote(createNoteDTO);
    // maybe we should have a response entity here?
    // or just something to confirm it?
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<NoteResponseDTO>> findNotes(@PathVariable Long userId, Authentication auth) {
    System.out.print("getting notes by the userId in Controller");
    if (auth == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    return ResponseEntity.ok(noteService.findNotes(userId));
  }

  @GetMapping("/{noteId}")
  public ResponseEntity<NoteResponseDTO> findNote(@PathVariable Long noteId, Authentication auth) {
    System.out.println("getting note by noteId in Controller");
    if (auth == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    return ResponseEntity.ok(noteService.findNote(noteId));
  }

  @DeleteMapping("/{noteId}")
  public ResponseEntity<Void> deleteNote(@PathVariable Long noteId) {
    System.out.println("I am working in the delete endpoint");
    noteService.deleteNote(noteId);
    return ResponseEntity.noContent().build();
  }
}
