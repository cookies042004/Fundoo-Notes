package com.fundoonotes.service.impl;

import com.fundoonotes.dto.request.NoteDTO;
import com.fundoonotes.entity.Note;
import com.fundoonotes.entity.User;
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

    @Override
    public Note createNote(NoteDTO noteDTO, String tokenEmail) {
        User user = userRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new UserException("User associated with token not found!"));
                
        Note note = new Note();
        note.setTitle(noteDTO.getTitle());
        note.setDescription(noteDTO.getDescription());
        note.setColor(noteDTO.getColor() != null ? noteDTO.getColor() : "#FFFFFF");
        note.setUserId(user.getId());

        return noteRepository.save(note);
    }

    @Override
    public List<Note> getAllNotes(String tokenEmail) {
        // UC9:Security step. Grab user ID from email embedded in JWT context
        User user = userRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new UserException("User not found!"));
                
        // UC9:Fetch EXCLUSIVELY notes that belong to this ID
        return noteRepository.findAllByUserId(user.getId());
    }
}
