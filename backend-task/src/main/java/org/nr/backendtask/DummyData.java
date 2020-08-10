package org.nr.backendtask;


import org.nr.backendtask.model.ApplicationUser;
import org.nr.backendtask.model.Folder;
import org.nr.backendtask.model.ListItem;
import org.nr.backendtask.model.Note;
import org.nr.backendtask.model.NoteType;
import org.nr.backendtask.repository.ApplicationUserRepository;
import org.nr.backendtask.repository.FolderRepository;
import org.nr.backendtask.repository.ListItemRepository;
import org.nr.backendtask.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DummyData implements CommandLineRunner {


    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    ListItemRepository listItemRepository;


    @Override
    public void run(String... args) throws Exception {

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setFirstName("Janez");
        applicationUser.setLastName("Novak");
        applicationUser.setUsername("janezn");
        applicationUser.setPassword(encoder.encode("password"));
        applicationUser = applicationUserRepository.save(applicationUser);
        createNotes("Privat Janez", "Public Janez", "Folder Janez", applicationUser);


        ApplicationUser applicationUser2 = new ApplicationUser();
        applicationUser2.setFirstName("Elon");
        applicationUser2.setLastName("Musk");
        applicationUser2.setUsername("elonm");
        applicationUser2.setPassword(encoder.encode("tesla"));
        applicationUser2 = applicationUserRepository.save(applicationUser2);


        createNotes("Privat Elin", "Public Elon", "Folder Elon", applicationUser2);


        ApplicationUser applicationUser3 = new ApplicationUser();
        applicationUser3.setFirstName("Jeff");
        applicationUser3.setLastName("Bezos");
        applicationUser3.setUsername("jeffb");
        applicationUser3.setPassword(encoder.encode("amazon"));
        applicationUser3 = applicationUserRepository.save(applicationUser3);


        createNotes("Privat Jeff", "Public Jeff", "Folder Jeff", applicationUser3);


    }

    private void createNotes(String privateName, String publicName, String folderNotesName, ApplicationUser applicationUser) {


        Note listPublicItem0 = new Note();
        listPublicItem0.setAuthor(applicationUser);
        listPublicItem0.setNoteType(NoteType.LIST);
        listPublicItem0.setShared(true);
        listPublicItem0.setName("LP-" + publicName);
        noteRepository.save(listPublicItem0);
        ListItem publicListItem0 = new ListItem();
        publicListItem0.setContent("lp-1_" + applicationUser.getUsername());
        ListItem publicListItem1 = new ListItem();
        publicListItem1.setContent("lp-2_" + applicationUser.getUsername());
        ListItem publicListItem2 = new ListItem();
        publicListItem2.setContent("lp-3_" + applicationUser.getUsername());


        publicListItem0.setNote(listPublicItem0);
        publicListItem1.setNote(listPublicItem0);
        publicListItem2.setNote(listPublicItem0);
        listItemRepository.save(publicListItem0);
        listItemRepository.save(publicListItem1);
        listItemRepository.save(publicListItem2);


        Note publicNote = new Note();
        publicNote.setNoteType(NoteType.TEXT);
        publicNote.setAuthor(applicationUser);
        publicNote.setName(publicName);
        publicNote.setShared(true);

        noteRepository.save(publicNote);
        ListItem publicListItem = new ListItem();
        publicListItem.setContent(publicName + "_note content");
        publicListItem.setNote(publicNote);
        listItemRepository.save(publicListItem);


        Folder folder = new Folder();
        folder.setOwner(applicationUser);
        folderRepository.save(folder);
        Note noteJ1 = new Note();
        noteJ1.setAuthor(applicationUser);
        noteJ1.setNoteType(NoteType.TEXT);
        noteJ1.setShared(false);
        noteJ1.setName(folderNotesName);
        noteJ1.setFolder(folder);
        noteRepository.save(noteJ1);
        ListItem listItem = new ListItem();
        listItem.setContent(folderNotesName + "note" + " to do something");
        listItem.setNote(noteJ1);
        listItemRepository.save(listItem);

        Note noteJ2 = new Note();
        noteJ2.setName(privateName);
        noteJ2.setShared(false);
        noteJ2.setAuthor(applicationUser);
        noteJ2.setNoteType(NoteType.LIST);
        noteRepository.save(noteJ2);

        ListItem listItem21 = new ListItem();
        listItem21.setNote(noteJ2);
        listItem21.setContent(privateName + " private note1" + "not in folder");
        ListItem listItem22 = new ListItem();
        listItem22.setNote(noteJ2);
        listItem22.setContent(privateName + " private note2" + "not in folder");
        ListItem listItem23 = new ListItem();
        listItem23.setNote(noteJ2);
        listItem23.setContent(privateName + " private note3" + "not in folder");

        listItemRepository.save(listItem21);
        listItemRepository.save(listItem22);
        listItemRepository.save(listItem23);


    }


}
