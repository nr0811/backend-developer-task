package org.nr.backendtask.repository;

import java.util.Optional;
import org.nr.backendtask.model.ApplicationUser;
import org.nr.backendtask.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

    Optional<Note> findByAuthor(ApplicationUser applicationUser);

    Page<Note> findAllBySharedTrue(Pageable pageable);

    Page<Note> findAllByAuthorOrSharedTrue(ApplicationUser applicationUser, Pageable pageable);

}
