package org.nr.backendtask.api.controllers;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.nr.backendtask.Constants;
import org.nr.backendtask.api.dto.FolderRequest;
import org.nr.backendtask.api.dto.FolderResponse;
import org.nr.backendtask.api.dto.PaginatedResponse;
import org.nr.backendtask.api.exceptions.ApiNotFoundException;
import org.nr.backendtask.api.exceptions.ApiUnhandledException;
import org.nr.backendtask.api.exceptions.ApiValidationException;
import org.nr.backendtask.model.ApplicationUser;
import org.nr.backendtask.security.IgnoreAuth;
import org.nr.backendtask.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/folder", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "Authorization")
public class FolderController {

    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }


    @GetMapping(path = "/{id}")
    @IgnoreAuth
    public FolderResponse findOne(@PathVariable Long id, @RequestAttribute(value = Constants.APPLICATION_USER_ATTRIBUTE, required = false) ApplicationUser applicationUser) throws ApiNotFoundException {
        return folderService.getOne(id, applicationUser);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public FolderResponse createNote(@Valid @RequestBody @NotNull FolderRequest folderRequest, @RequestAttribute(value = Constants.APPLICATION_USER_ATTRIBUTE) ApplicationUser applicationUser) throws ApiNotFoundException {
        return folderService.createFolder(folderRequest, applicationUser);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public FolderResponse updateNote(@PathVariable Long id, @Valid @RequestBody FolderRequest folderRequest, @RequestAttribute(value = Constants.APPLICATION_USER_ATTRIBUTE) ApplicationUser applicationUser) throws ApiNotFoundException, ApiValidationException {
        return folderService.updateFolder(id, folderRequest, applicationUser);

    }

    @GetMapping
    @IgnoreAuth
    public PaginatedResponse<FolderResponse> getAll(@RequestAttribute(value = Constants.APPLICATION_USER_ATTRIBUTE, required = false) ApplicationUser applicationUser, Pageable pageable) {
        return folderService.findAllFolders(applicationUser, pageable);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteOne(@PathVariable Long id, @RequestAttribute(value = Constants.APPLICATION_USER_ATTRIBUTE) ApplicationUser applicationUser) throws ApiNotFoundException, ApiUnhandledException {
        boolean success = folderService.deleteOne(id, applicationUser);
        return ResponseEntity.ok(Map.of("deleteSuccess", success));
    }

}
