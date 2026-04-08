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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    // Helper method to DRY up user extraction
    private User getAuthenticatedUser(String tokenEmail) {
        return userRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new UserException("User not found!"));
    }

    @Override
    public Note createNote(NoteDTO noteDTO, String tokenEmail) {
        User user = getAuthenticatedUser(tokenEmail);
                
        Note note = new Note();
        note.setTitle(noteDTO.getTitle());
        note.setDescription(noteDTO.getDescription());
        note.setColor(noteDTO.getColor() != null ? noteDTO.getColor() : "#FFFFFF");
        note.setUserId(user.getId());

        return noteRepository.save(note);
    }

    @Override
    public List<Note> getAllNotes(String tokenEmail) {
        User user = getAuthenticatedUser(tokenEmail);
        return noteRepository.findAllByUserId(user.getId());
    }

    // [Prajwal]:UC10:Crucial security helper checking Note ID AND User ID ownership
    private Note getVerifiedNote(Long noteId, Long userId) {
        return noteRepository.findByIdAndUserId(noteId, userId)
                .orElseThrow(() -> new NoteException("Note not found or Unauthorized access!"));
    }

    @Override
    public Note togglePin(Long noteId, String tokenEmail) {
        User user = getAuthenticatedUser(tokenEmail);
        Note note = getVerifiedNote(noteId, user.getId());
        
        // [Prajwal]:UC10:Toggle logic
        note.setPinned(!note.isPinned());
        return noteRepository.save(note);
    }

    @Override
    public Note toggleArchive(Long noteId, String tokenEmail) {
        User user = getAuthenticatedUser(tokenEmail);
        Note note = getVerifiedNote(noteId, user.getId());
        
        boolean newState = !note.isArchived();
        note.setArchived(newState);
        if (newState) note.setPinned(false); // Unpin if archiving
        
        return noteRepository.save(note);
    }

    @Override
    public Note toggleTrash(Long noteId, String tokenEmail) {
        User user = getAuthenticatedUser(tokenEmail);
        Note note = getVerifiedNote(noteId, user.getId());
        
        boolean newState = !note.isTrashed();
        note.setTrashed(newState);
        if (newState) note.setPinned(false); // Unpin if trashing
        
        return noteRepository.save(note);
    }
}
