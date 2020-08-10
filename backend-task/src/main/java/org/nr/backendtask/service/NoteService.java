package org.nr.backendtask.service;

import org.nr.backendtask.api.dto.CreateNoteRequest;
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

    Note createNote(CreateNoteRequest createNoteRequest, ApplicationUser applicationUser) throws ApiNotFoundException;

    Note updateNote(Long id, CreateNoteRequest updateNoteRequest, ApplicationUser applicationUser) throws ApiNotFoundException, ApiValidationException;
}
