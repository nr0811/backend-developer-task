package org.nr.backendtask.repository;

import org.nr.backendtask.model.ListItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListItemRepository extends CrudRepository<ListItem, Long> {
}
