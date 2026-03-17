package com.example.NoteBook.DTO;

import java.time.LocalDateTime;

public class NoteResponseDTO {
  private Long noteId;
  private String book;
  private String chapter;
  private String note;
  private LocalDateTime createdAt;

  public NoteResponseDTO() {

  }

  public NoteResponseDTO(Long noteId, String book, String chapter, String note, LocalDateTime createdAt) {
    this.noteId = noteId;
    this.book = book;
    this.chapter = chapter;
    this.note = note;
    this.createdAt = createdAt;
  }

  public Long getNoteId() {
    return this.noteId;
  }

  public String getBook() {
    return this.book;
  }

  public String getChapter() {
    return this.chapter;
  }

  public String getNote() {
    return this.note;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }
}
