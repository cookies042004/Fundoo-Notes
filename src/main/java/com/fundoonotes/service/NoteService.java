package com.fundoonotes.service;

import com.fundoonotes.dto.request.NoteDTO;
import com.fundoonotes.entity.Note;

public interface NoteService {
    // [Prajwal]:UC8:Contract for Note creation decoupled logic 
    Note createNote(NoteDTO noteDTO, String tokenEmail);
}
