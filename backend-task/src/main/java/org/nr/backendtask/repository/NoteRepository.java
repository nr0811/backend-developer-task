package org.nr.backendtask.repository;

import org.nr.backendtask.model.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {
}
