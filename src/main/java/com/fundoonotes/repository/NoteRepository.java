package com.fundoonotes.repository;

import com.fundoonotes.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    // UC7:Find every note owned by a generic User ID
    List<Note> findAllByUserId(Long userId);

    // UC7:Critical Query -> Prevents "User A" from modifying "User B's" Note via URL tampering
    Optional<Note> findByIdAndUserId(Long id, Long userId);
}
