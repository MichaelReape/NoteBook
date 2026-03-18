package com.example.NoteBook.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.NoteBook.Entity.Note;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

  public List<Note> findByUserId(Long userId);

}
