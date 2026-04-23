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

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    // Injecting both Repositories! 
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Override
    public Note createNote(NoteDTO noteDTO, String tokenEmail) {
        
        // UC8:Cross-check: Ensure the email embedded in the JWT actually still exists in DB
        User user = userRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new UserException("User associated with token not found!"));
                
        // UC8:Hydrate Entity from DTO
        Note note = new Note();
        note.setTitle(noteDTO.getTitle());
        note.setDescription(noteDTO.getDescription());
        
        // Defaults to White if null is sent
        note.setColor(noteDTO.getColor() != null ? noteDTO.getColor() : "#FFFFFF");
        
        // UC8:Link operation. Tie this note to the user ID!
        note.setUserId(user.getId());

        // UC8:Persist the note
        return noteRepository.save(note);
    }
}
