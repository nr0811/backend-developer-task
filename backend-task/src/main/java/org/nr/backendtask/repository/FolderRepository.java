package org.nr.backendtask.repository;

import org.nr.backendtask.model.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends CrudRepository<Folder, Long> {

    Page<Folder> findAll(Pageable pageable);
}
