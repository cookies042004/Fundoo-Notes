package com.fundoonotes.controller;

import com.fundoonotes.dto.request.NoteDTO;
import com.fundoonotes.dto.response.ResponseDTO;
import com.fundoonotes.entity.Note;
import com.fundoonotes.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Slf4j
public class NoteController {

    private final NoteService noteService;

    // [Prajwal]:UC8:Endpoint to create a new Note. This route is locked via SecurityConfig!
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createNote(@Valid @RequestBody NoteDTO noteDTO, Principal principal) {
        
        // Extract dynamically injected Email from context
        String userEmail = principal.getName();
        log.info("Request to create note received by: {}", userEmail);

        Note savedNote = noteService.createNote(noteDTO, userEmail);
        
        // [Prajwal]:UC8:Return ID of generated Note
        ResponseDTO responseDTO = new ResponseDTO("Note Created Successfully", savedNote.getId());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
