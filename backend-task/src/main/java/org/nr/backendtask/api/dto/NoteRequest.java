package org.nr.backendtask.api.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.nr.backendtask.model.NoteType;

@CreateNoteValidation
public class NoteRequest {
    @NotNull(message = "noteType must be of type list of text")
    private NoteType noteType;
    @NotNull(message = "shared field must not be null")
    private Boolean shared;
    @NotNull(message = "name must not be empty")
    private String name;
    private String content;
    private Long folder;
    private List<String> items;

    public NoteRequest() {
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
