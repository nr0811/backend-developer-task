package org.nr.backendtask.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.nr.backendtask.api.dto.NoteRequest;
import org.nr.backendtask.api.exceptions.ApiNotFoundException;
import org.nr.backendtask.api.exceptions.ApiValidationException;
import org.nr.backendtask.model.ApplicationUser;
import org.nr.backendtask.model.Folder;
import org.nr.backendtask.model.ListItem;
import org.nr.backendtask.model.Note;
import org.nr.backendtask.model.NoteType;
import org.nr.backendtask.repository.FolderRepository;
import org.nr.backendtask.repository.ListItemRepository;
import org.nr.backendtask.repository.NoteRepository;
import org.nr.backendtask.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final FolderRepository folderRepository;
    private final ListItemRepository listItemRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, FolderRepository folderRepository, ListItemRepository listItemRepository) {
        this.noteRepository = noteRepository;
        this.folderRepository = folderRepository;
        this.listItemRepository = listItemRepository;
    }

    @Override
    public Note getOne(Long id, ApplicationUser applicationUser) throws ApiNotFoundException {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            if (note.isShared()) {
                return note;
            } else {
                if (applicationUser != null && note.getAuthor().getId().equals(applicationUser.getId())) {
                    return note;
                } else {
                    throw new ApiNotFoundException("Note not found");
                }
            }
        } else {
            throw new ApiNotFoundException("Note not found");
        }
    }

    @Override
    public boolean deleteOne(Long id, ApplicationUser applicationUser) throws ApiNotFoundException {
        Optional<Note> optionalNote = noteRepository.findById(id);

        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            if (!note.getAuthor().getId().equals(applicationUser.getId())) {
                throw new ApiNotFoundException("Note not found");
            }
            try {
                noteRepository.delete(note);
                return true;
            } catch (Exception exception) {
                exception.printStackTrace();
                return false;
            }

        } else {
            throw new ApiNotFoundException("Note not found");
        }
    }

    @Override
    public Page<Note> findAllNotes(ApplicationUser applicationUser, Pageable pageable) {
        if (applicationUser == null) {
            return noteRepository.findAllBySharedTrue(pageable);
        } else {
            return noteRepository.findAllByAuthorOrSharedTrue(applicationUser, pageable);
        }
    }

    @Override
    public Note createNote(NoteRequest noteRequest, ApplicationUser applicationUser) throws ApiNotFoundException {
        Note note = new Note();
        note.setAuthor(applicationUser);
        note.setShared(noteRequest.getShared());
        note.setName(noteRequest.getName());
        note.setNoteType(noteRequest.getNoteType());
        Folder folder = null;

        if (noteRequest.getFolder() != null) {
            System.out.println(noteRequest.getFolder());
            folder = getFolderForUser(noteRequest.getFolder(), applicationUser);
        }

        note.setFolder(folder);
        note = noteRepository.save(note);
        if (noteRequest.getNoteType() == NoteType.LIST) {
            List<ListItem> listItemList = new ArrayList<>();
            for (String item : noteRequest.getItems()) {
                ListItem listItem = new ListItem();
                listItem.setNote(note);
                listItem.setContent(item);
                listItemList.add(listItem);
                note.addToItems(listItem);
            }
            listItemRepository.saveAll(listItemList);

        } else {
            ListItem listItem = new ListItem();
            listItem.setContent(noteRequest.getContent());
            listItem.setNote(note);
            listItem = listItemRepository.save(listItem);
            note.addToItems(listItem);

        }

        note = noteRepository.save(note);
        System.out.println(note);
        return note;
    }

    @Override
    public Note updateNote(Long id, NoteRequest updateNoteRequest, ApplicationUser applicationUser) throws ApiNotFoundException, ApiValidationException {
        Optional<Note> optionalNote = noteRepository.findById(id);

        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            if (!note.getAuthor().getId().equals(applicationUser.getId())) {
                throw new ApiNotFoundException("Note not found");
            }
            if (updateNoteRequest.getName() != null) {
                note.setName(updateNoteRequest.getName());
            }
            if (updateNoteRequest.getShared() != null) {
                note.setShared(updateNoteRequest.getShared());
            }

            if (updateNoteRequest.getFolder() != null) {
                Folder folder = getFolderForUser(updateNoteRequest.getFolder(), applicationUser);
                note.setFolder(folder);
            }

            if (updateNoteRequest.getNoteType() != null) {
                if (note.getNoteType() != updateNoteRequest.getNoteType()) {
                    throw new ApiValidationException("Type migration currently not supported");
                }

            }

            if (updateNoteRequest.getContent() != null) {
                if (note.getNoteType() == NoteType.LIST) {
                    throw new ApiValidationException("Cannot update content on list type");
                } else {
                    ListItem listItem = note.getListItems().get(0);
                    listItem.setContent(updateNoteRequest.getContent());
                    note.getListItems().clear();
                    note.addToItems(listItem);

                }
            }
            if (updateNoteRequest.getItems() != null) {
                if (note.getNoteType() == NoteType.TEXT) {
                    throw new ApiValidationException("Cannot update list items on text type");
                } else {
                    List<ListItem> currentItems = note.getListItems();

                    for (int i = 0; i < updateNoteRequest.getItems().size(); i++) {
                        if (i < currentItems.size() - 1) {
                            ListItem listItem = currentItems.get(i);
                            listItem.setContent(updateNoteRequest.getItems().get(i));
                            listItemRepository.save(listItem);
                        } else {
                            ListItem listItem = new ListItem(updateNoteRequest.getItems().get(i), note);
                            note.addToItems(listItem);
                            listItemRepository.save(listItem);
                        }
                    }
                }
            }
            return noteRepository.save(note);


        } else {
            throw new ApiNotFoundException("Note not found");
        }
    }


    private Folder getFolderForUser(Long folderId, ApplicationUser applicationUser) throws ApiNotFoundException {
        Folder folder;
        Optional<Folder> optionalFolder = folderRepository.findById(folderId);
        if (optionalFolder.isPresent()) {
            folder = optionalFolder.get();
            if (!folder.getOwner().getId().equals(applicationUser.getId())) {
                throw new ApiNotFoundException("Folder not found");
            }
            return folder;
        }
        throw new ApiNotFoundException("Folder not found");
    }
}
