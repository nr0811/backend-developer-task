package org.nr.backendtask.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Note extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(optional = false)
    private ApplicationUser author;

    @Column(nullable = false)
    private NoteType noteType;
    @Column(nullable = false)
    private String heading;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String name) {
        this.heading = name;
    }

    private boolean shared;


    @OneToMany(mappedBy = "note", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ListItem> listItems = new ArrayList<>();

    @ManyToOne
    private Folder folder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public ApplicationUser getAuthor() {
        return author;
    }

    public void setAuthor(ApplicationUser author) {
        this.author = author;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    public List<ListItem> getListItems() {
        return listItems;
    }

    public void addToItems(ListItem listItem) {
        this.listItems.add(listItem);
    }

    public void setListItems(List<ListItem> listItems) {
        this.listItems = listItems;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", author=" + author +
                ", noteType=" + noteType +
                ", name='" + heading + '\'' +
                ", shared=" + shared +
                ", listItems=" + listItems +
                ", folder=" + folder +
                '}';
    }
}
