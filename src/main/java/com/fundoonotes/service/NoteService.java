package com.fundoonotes.service;

import com.fundoonotes.dto.request.NoteDTO;
import com.fundoonotes.entity.Note;

import java.util.List;

public interface NoteService {
    // [Prajwal]:UC8:Contract for Note creation decoupled logic 
    Note createNote(NoteDTO noteDTO, String tokenEmail);

    // [Prajwal]:UC9:Contract for fetching all notes relative to user email
    List<Note> getAllNotes(String tokenEmail);
}
