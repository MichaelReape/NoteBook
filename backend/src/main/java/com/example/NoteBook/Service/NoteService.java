package com.example.NoteBook.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.NoteBook.DTO.CreateNoteDTO;
import com.example.NoteBook.DTO.NoteResponseDTO;
import com.example.NoteBook.Entity.Note;
import com.example.NoteBook.Repository.NoteRepository;

@Service
public class NoteService {
  private final NoteRepository noteRepository;

  public NoteService(NoteRepository noteRepository) {
    this.noteRepository = noteRepository;
  }

  public NoteResponseDTO saveNote(CreateNoteDTO createNoteDTO) {
    Note note = new Note(createNoteDTO.getUserId(), createNoteDTO.getBook(), createNoteDTO.getChapter(),
        createNoteDTO.getNote());
    System.out.println("i am working in the service");
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
    Note note = noteRepository.findById(noteId).orElseThrow();
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
