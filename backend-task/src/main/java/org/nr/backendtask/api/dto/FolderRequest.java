package org.nr.backendtask.api.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FolderRequest {

    @NotNull
    @Size(min = 1, message = "name must be at least 1 char long")
    private String name;

    public FolderRequest() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


