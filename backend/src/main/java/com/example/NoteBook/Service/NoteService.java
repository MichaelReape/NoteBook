package com.example.NoteBook.Service;

import org.springframework.stereotype.Service;

import com.example.NoteBook.DTO.CreateNoteDTO;
import com.example.NoteBook.DTO.NoteResponseDTO;
import com.example.NoteBook.Repository.NoteRepository;
import com.example.NoteBook.Entity.Note;

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

  private NoteResponseDTO convertToNoteResponseDTO(Note note) {
    return new NoteResponseDTO(note.getNoteId(), note.getBook(), note.getChapter(), note.getNotes(),
        note.getCreatedAt());
  }
}
