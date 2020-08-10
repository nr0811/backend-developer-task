package org.nr.backendtask.service;

import org.nr.backendtask.api.dto.NoteRequest;
import org.nr.backendtask.api.exceptions.ApiNotFoundException;
import org.nr.backendtask.api.exceptions.ApiValidationException;
import org.nr.backendtask.model.ApplicationUser;
import org.nr.backendtask.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface NoteService {
    Note getOne(Long id, ApplicationUser applicationUser) throws ApiNotFoundException;

    boolean deleteOne(Long id, ApplicationUser applicationUser) throws ApiNotFoundException;

    Page<Note> findAllNotes(ApplicationUser applicationUser, Pageable pageable);

    Note createNote(NoteRequest noteRequest, ApplicationUser applicationUser) throws ApiNotFoundException;

    Note updateNote(Long id, NoteRequest updateNoteRequest, ApplicationUser applicationUser) throws ApiNotFoundException, ApiValidationException;
}
