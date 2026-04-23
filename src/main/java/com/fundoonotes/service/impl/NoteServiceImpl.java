package com.fundoonotes.service.impl;

import com.fundoonotes.dto.request.NoteDTO;
import com.fundoonotes.entity.Note;
import com.fundoonotes.entity.User;
import com.fundoonotes.exception.NoteException;
import com.fundoonotes.exception.UserException;
import com.fundoonotes.repository.NoteRepository;
import com.fundoonotes.repository.UserRepository;
import com.fundoonotes.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    private final MessageProducer producer;

    // Helper method to DRY up user extraction
    private User getAuthenticatedUser(String tokenEmail) {
        return userRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new UserException("User not found!"));
    }

    @CacheEvict(value = "notes", key = "#tokenEmail")
    @Override
    public Note createNote(NoteDTO noteDTO, String tokenEmail) {

        User user = getAuthenticatedUser(tokenEmail);

        Note note = new Note();
        note.setTitle(noteDTO.getTitle());
        note.setDescription(noteDTO.getDescription());
        note.setUserId(user.getId());

        Note savedNote = noteRepository.save(note);

        producer.sendMessage("Note Created: " + savedNote.getTitle());

        return savedNote;
    }

    @Override
    @Cacheable(value = "notes", key = "#tokenEmail")
    public List<Note> getAllNotes(String tokenEmail) {

        User user = getAuthenticatedUser(tokenEmail);

        System.out.println("Fetching from DB...");

        return noteRepository.findByUserId(user.getId());
    }

    // UC10:Crucial security helper checking Note ID AND User ID ownership
    private Note getVerifiedNote(Long noteId, Long userId) {
        return noteRepository.findByIdAndUserId(noteId, userId)
                .orElseThrow(() -> new NoteException("Note not found or Unauthorized access!"));
    }

    @CacheEvict(value = "notes", key = "#user.id")
    @Override
    public Note togglePin(Long noteId, String tokenEmail) {

        User user = getAuthenticatedUser(tokenEmail);
        Note note = getVerifiedNote(noteId, user.getId());

        note.setPinned(!note.isPinned());
        return noteRepository.save(note);
    }

    @CacheEvict(value = "notes", key = "#user.id")
    @Override
    public Note toggleArchive(Long noteId, String tokenEmail) {

        User user = getAuthenticatedUser(tokenEmail);
        Note note = getVerifiedNote(noteId, user.getId());

        boolean newState = !note.isArchived();
        note.setArchived(newState);

        if (newState) note.setPinned(false);

        return noteRepository.save(note);
    }

    @CacheEvict(value = "notes", key = "#user.id")
    @Override
    public Note toggleTrash(Long noteId, String tokenEmail) {

        User user = getAuthenticatedUser(tokenEmail);
        Note note = getVerifiedNote(noteId, user.getId());

        boolean newState = !note.isTrashed();
        note.setTrashed(newState);

        if (newState) note.setPinned(false);

        return noteRepository.save(note);
    }
}
