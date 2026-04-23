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
import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Slf4j
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createNote(@Valid @RequestBody NoteDTO noteDTO, Principal principal) {
        Note savedNote = noteService.createNote(noteDTO, principal.getName());
        return new ResponseEntity<>(new ResponseDTO("Note Created Successfully", savedNote.getId()), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getAllNotes(Principal principal) {
        List<Note> notesList = noteService.getAllNotes(principal.getName());
        return new ResponseEntity<>(new ResponseDTO("Notes Fetched Successfully", notesList), HttpStatus.OK);
    }

    // UC10:Toggle Endpoints mapping to PUT requests
    @PutMapping("/{noteId}/pin")
    public ResponseEntity<ResponseDTO> togglePin(@PathVariable Long noteId, Principal principal) {
        Note note = noteService.togglePin(noteId, principal.getName());
        String msg = note.isPinned() ? "Note Pinned" : "Note Unpinned";
        return new ResponseEntity<>(new ResponseDTO(msg, note.getId()), HttpStatus.OK);
    }

    @PutMapping("/{noteId}/archive")
    public ResponseEntity<ResponseDTO> toggleArchive(@PathVariable Long noteId, Principal principal) {
        Note note = noteService.toggleArchive(noteId, principal.getName());
        String msg = note.isArchived() ? "Note Archived" : "Note Unarchived";
        return new ResponseEntity<>(new ResponseDTO(msg, note.getId()), HttpStatus.OK);
    }

    @PutMapping("/{noteId}/trash")
    public ResponseEntity<ResponseDTO> toggleTrash(@PathVariable Long noteId, Principal principal) {
        Note note = noteService.toggleTrash(noteId, principal.getName());
        String msg = note.isTrashed() ? "Note moved to Trash" : "Note recovered from Trash";
        return new ResponseEntity<>(new ResponseDTO(msg, note.getId()), HttpStatus.OK);
    }
}
