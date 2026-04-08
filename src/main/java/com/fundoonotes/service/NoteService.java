package com.fundoonotes.service;

import com.fundoonotes.dto.request.NoteDTO;
import com.fundoonotes.entity.Note;

import java.util.List;

public interface NoteService {
    Note createNote(NoteDTO noteDTO, String tokenEmail);
    List<Note> getAllNotes(String tokenEmail);

    // [Prajwal]:UC10:Contracts for toggling note states
    Note togglePin(Long noteId, String tokenEmail);
    Note toggleArchive(Long noteId, String tokenEmail);
    Note toggleTrash(Long noteId, String tokenEmail);
}
