package org.nr.backendtask.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.nr.backendtask.api.dto.FolderRequest;
import org.nr.backendtask.api.dto.FolderResponse;
import org.nr.backendtask.api.dto.NoteResponse;
import org.nr.backendtask.api.dto.PaginatedResponse;
import org.nr.backendtask.api.exceptions.ApiNotFoundException;
import org.nr.backendtask.api.exceptions.ApiUnhandledException;
import org.nr.backendtask.api.exceptions.ApiValidationException;
import org.nr.backendtask.model.ApplicationUser;
import org.nr.backendtask.model.Folder;
import org.nr.backendtask.model.Note;
import org.nr.backendtask.repository.ApplicationUserRepository;
import org.nr.backendtask.repository.FolderRepository;
import org.nr.backendtask.repository.NoteRepository;
import org.nr.backendtask.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;
    private final NoteRepository noteRepository;
    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public FolderServiceImpl(FolderRepository folderRepository, NoteRepository noteRepository, ApplicationUserRepository applicationUserRepository) {
        this.folderRepository = folderRepository;
        this.noteRepository = noteRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public FolderResponse getOne(Long id, ApplicationUser applicationUser) throws ApiNotFoundException {
        Optional<Folder> optionalFolder = folderRepository.findById(id);

        if (optionalFolder.isPresent()) {
            Folder folder = optionalFolder.get();
            if (applicationUser == null || !folder.getOwner().getId().equals(applicationUser.getId())) {
                List<Note> sharedNotes = noteRepository.findAllByFolderAndSharedTrue(folder);
                List<NoteResponse> noteResponses = sharedNotes.stream().map(NoteResponse::new).collect(Collectors.toList());
                return new FolderResponse(folder.getId(), folder.getName(), folder.getOwner().getUsername(), noteResponses);
            } else {

                List<Note> sharedNotes = noteRepository.findAllByFolder(folder);
                List<NoteResponse> noteResponses = sharedNotes.stream().map(NoteResponse::new).collect(Collectors.toList());
                return new FolderResponse(folder.getId(), folder.getName(), folder.getOwner().getUsername(), noteResponses);
            }
        }

        throw new ApiNotFoundException("Folder not present");


    }

    @Override
    public boolean deleteOne(Long id, ApplicationUser applicationUser) throws ApiNotFoundException, ApiUnhandledException {
        Optional<Folder> optionalFolder = folderRepository.findById(id);
        if (optionalFolder.isPresent()) {
            Folder folder = optionalFolder.get();
            if (folder.getOwner().getId().equals(applicationUser.getId())) {
                try {
                    folderRepository.deleteById(id);
                    return true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new ApiUnhandledException();
                }
            }
            throw new ApiNotFoundException("Cannot delete folders from others");


        }
        throw new ApiNotFoundException("Folder not present");

    }

    @Override
    public PaginatedResponse<FolderResponse> findAllFolders(ApplicationUser applicationUser, Pageable pageable) {
        Page<Folder> folders = folderRepository.findAll(pageable);
        List<FolderResponse> content = folders.getContent().stream().map(folder -> {
            if (applicationUser == null || !folder.getOwner().getId().equals(applicationUser.getId())) {
                List<Note> sharedNotes = noteRepository.findAllByFolderAndSharedTrue(folder);
                List<NoteResponse> noteResponses = sharedNotes.stream().map(NoteResponse::new).collect(Collectors.toList());
                return new FolderResponse(folder.getId(), folder.getName(), folder.getOwner().getUsername(), noteResponses);
            } else {

                List<Note> sharedNotes = noteRepository.findAllByFolder(folder);
                List<NoteResponse> noteResponses = sharedNotes.stream().map(NoteResponse::new).collect(Collectors.toList());
                return new FolderResponse(folder.getId(), folder.getName(), folder.getOwner().getUsername(), noteResponses);
            }
        }).collect(Collectors.toList());


        PaginatedResponse<FolderResponse> response = new PaginatedResponse<>();
        response.setCount(folders.getTotalElements());
        response.setPage(folders.getNumber());
        response.setPerPage(folders.getSize());
        response.setResults(content);
        return response;

    }

    @Override
    public FolderResponse createFolder(FolderRequest folderRequest, ApplicationUser applicationUser) throws ApiNotFoundException {
        Folder folder = new Folder();
        folder.setOwner(applicationUser);
        folder.setName(folderRequest.getName());
        folder = folderRepository.save(folder);
        return new FolderResponse(folder.getId(), folder.getName(), folder.getOwner().getUsername());
    }

    @Override
    public FolderResponse updateFolder(Long id, FolderRequest updateFolderRequest, ApplicationUser applicationUser) throws ApiNotFoundException, ApiValidationException {
        Optional<Folder> optionalFolder = folderRepository.findById(id);
        if (optionalFolder.isPresent()) {
            Folder folder = optionalFolder.get();
            if (folder.getOwner().getId().equals(applicationUser.getId())) {
                folder.setName(updateFolderRequest.getName());
                folder = folderRepository.save(folder);
                return new FolderResponse(folder.getId(), folder.getName(), folder.getOwner().getUsername(), folder.getNotes().stream().map(NoteResponse::new).collect(Collectors.toList()));
            }
            throw new ApiNotFoundException("Folder not present");

        }
        throw new ApiNotFoundException("Folder not present");
    }
}
