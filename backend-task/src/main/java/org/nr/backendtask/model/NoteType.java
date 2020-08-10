package org.nr.backendtask.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NoteType {
    TEXT("text"), LIST("list");


    private final String key;


    NoteType(String key) {
        this.key = key;
    }

    @JsonValue
    public String getKey() {
        return key;
    }

    @JsonCreator
    public static NoteType fromKey(String key) {
        if (key == null) {
            return null;
        }
        for (NoteType noteType : values()) {
            if (noteType.getKey().toLowerCase().equals(key.toLowerCase())) {
                return noteType;
            }
        }
        return null;
    }
}
