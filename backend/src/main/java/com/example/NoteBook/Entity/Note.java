package com.example.NoteBook.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "notes")
public class Note {

  public Note() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long noteId;
  private Long userId;
  private String book;
  private String chapter;
  private String notes;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  public Note(Long userId, String book, String chapter, String notes) {
    this.userId = userId;
    this.book = book;
    this.chapter = chapter;
    this.notes = notes;
  }

  // getter and setter

  public Long getNoteId() {
    return noteId;
  }

  public Long getUserId() {
    return userId;
  }

  public String getBook() {
    return book;
  }

  public String getChapter() {
    return chapter;
  }

  public String getNotes() {
    return notes;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setBook(String book) {
    this.book = book;
  }

  public void setChapter(String chapter) {
    this.chapter = chapter;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
