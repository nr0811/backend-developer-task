package org.nr.backendtask.api.controllers;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.nr.backendtask.Constants;
import org.nr.backendtask.api.dto.NoteRequest;
import org.nr.backendtask.api.dto.NoteResponse;
import org.nr.backendtask.api.dto.PaginatedResponse;
import org.nr.backendtask.api.exceptions.ApiNotFoundException;
import org.nr.backendtask.api.exceptions.ApiValidationException;
import org.nr.backendtask.model.ApplicationUser;
import org.nr.backendtask.model.Note;
import org.nr.backendtask.security.IgnoreAuth;
import org.nr.backendtask.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/note", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "Authorization")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public NoteResponse updateNote(@PathVariable Long id, @RequestBody NoteRequest noteRequest, @RequestAttribute(value = Constants.APPLICATION_USER_ATTRIBUTE) ApplicationUser applicationUser) throws ApiNotFoundException, ApiValidationException {
        Note note = noteService.updateNote(id, noteRequest, applicationUser);
        return new NoteResponse(note);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteOne(@PathVariable Long id, @RequestAttribute(value = Constants.APPLICATION_USER_ATTRIBUTE) ApplicationUser applicationUser) throws ApiNotFoundException {
        boolean success = noteService.deleteOne(id, applicationUser);
        return ResponseEntity.ok(Map.of("deleteSuccess", success));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public NoteResponse createNote(@Valid @RequestBody @NotNull NoteRequest noteRequest, @RequestAttribute(value = Constants.APPLICATION_USER_ATTRIBUTE) ApplicationUser applicationUser) throws ApiNotFoundException {
        Note note = noteService.createNote(noteRequest, applicationUser);
        return new NoteResponse(note);
    }

    @GetMapping(path = "/{id}")
    @IgnoreAuth
    public NoteResponse findOne(@PathVariable Long id, @RequestAttribute(value = Constants.APPLICATION_USER_ATTRIBUTE, required = false) ApplicationUser applicationUser) throws ApiNotFoundException {
        Note note = noteService.getOne(id, applicationUser);
        return new NoteResponse(note);
    }


    @GetMapping
    @IgnoreAuth
    public PaginatedResponse<NoteResponse> getAll(@RequestAttribute(value = Constants.APPLICATION_USER_ATTRIBUTE, required = false) ApplicationUser applicationUser, @PageableDefault(page = 0, size = 20) @SortDefault.SortDefaults({
            @SortDefault(sort = "heading", direction = Sort.Direction.ASC, caseSensitive = false),
            @SortDefault(sort = "shared", direction = Sort.Direction.ASC, caseSensitive = false)
    }) Pageable pageable) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Sort passedSort = pageable.getSort();

        Sort.Order orderHeading = passedSort.getOrderFor("heading");
        Sort.Order orderShared = passedSort.getOrderFor("shared");

        if (orderShared != null) {
            sort = passedSort;
        }
        if (orderHeading != null) {
            sort = passedSort;
        }


        Pageable formattedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Note> notes = noteService.findAllNotes(applicationUser, formattedPageable, null);
        return new PaginatedResponse<>(notes, NoteResponse::new);
    }

}
