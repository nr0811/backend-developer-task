package org.nr.backendtask.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.nr.backendtask.model.ListItem;
import org.nr.backendtask.model.Note;
import org.nr.backendtask.model.NoteType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoteResponse {

    private Long id;
    private String name;
    private String author;
    private NoteType noteType;
    private boolean shared;
    private String content;
    private Long folder;
    private List<String> items;
    private Instant dateCreated;
    private Instant dateUpdated;

    public NoteResponse() {
    }

    public NoteResponse(Note note) {
        this.id = note.getId();
        this.author = note.getAuthor().getUsername();
        this.noteType = note.getNoteType();
        this.shared = note.isShared();
        this.name = note.getName();
        this.dateCreated = note.getDateCreated();
        this.dateUpdated = note.getDateUpdated();
        if (note.getFolder() != null) {
            this.folder = note.getFolder().getId();
        }
        if (note.getNoteType() == NoteType.TEXT) {
            ListItem firstItem = note.getListItems().get(0);
            if (firstItem != null) {
                content = firstItem.getContent();
            }
        } else {
            items = note.getListItems().stream().map(ListItem::getContent).collect(Collectors.toList());
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getFolder() {
        return folder;
    }

    public void setFolder(Long folder) {
        this.folder = folder;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
