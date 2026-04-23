package com.fundoonotes.service.impl;

import com.fundoonotes.dto.request.NoteExcelDTO;
import com.fundoonotes.entity.Note;
import com.fundoonotes.entity.User;
import com.fundoonotes.repository.NoteRepository;
import com.fundoonotes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public void saveNotes(List<NoteExcelDTO> dtos, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        for (NoteExcelDTO dto : dtos) {

            Note note = new Note();
            note.setTitle(dto.getTitle());
            note.setDescription(dto.getDescription());
            note.setUserId(user.getId());

            noteRepository.save(note);
        }
    }
}