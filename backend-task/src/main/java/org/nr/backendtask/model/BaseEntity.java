package org.nr.backendtask.model;

import java.time.Instant;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class BaseEntity {

    private Instant dateCreated;

    private Instant dateUpdated;


    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        this.dateCreated = now;
        this.dateUpdated = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.dateUpdated = Instant.now();
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
