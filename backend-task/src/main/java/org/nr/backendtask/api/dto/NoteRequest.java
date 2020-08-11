package org.nr.backendtask.api.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.nr.backendtask.model.NoteType;

@CreateNoteValidation
public class NoteRequest {
    @NotNull(message = "noteType must be of type list of text")
    private NoteType noteType;
    @NotNull(message = "shared field must not be null")
    private Boolean shared;
    @NotNull(message = "heading must not be empty")
    @Size(min = 1,message = "heading must be at least one character long")
    private String heading;
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

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
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
