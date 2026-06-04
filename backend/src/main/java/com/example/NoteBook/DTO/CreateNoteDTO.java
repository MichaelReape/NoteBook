package com.example.NoteBook.DTO;

public class CreateNoteDTO {
  private String book;
  private String chapter;
  private String note;

  public CreateNoteDTO() {

  }

  public CreateNoteDTO(String book, String chapter, String note) {
    this.book = book;
    this.chapter = chapter;
    this.note = note;
  }

  // getters
  public String getBook() {
    return this.book;
  }

  public String getChapter() {
    return this.chapter;
  }

  public String getNote() {
    return this.note;
  }

  // setters
  public void setBook(String book) {
    this.book = book;
  }

  public void setChapter(String chapter) {
    this.chapter = chapter;
  }

  public void setNote(String note) {
    this.note = note;
  }
}
