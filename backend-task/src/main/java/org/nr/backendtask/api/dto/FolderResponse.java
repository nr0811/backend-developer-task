package org.nr.backendtask.api.dto;

import java.util.ArrayList;
import java.util.List;

public class FolderResponse {
    private Long id;
    private String name;
    private String owner;
    private List<NoteResponse> notes = new ArrayList<>();

    public FolderResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FolderResponse(Long id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public FolderResponse(Long id, String name, String owner, List<NoteResponse> notes) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<NoteResponse> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteResponse> notes) {
        this.notes = notes;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
