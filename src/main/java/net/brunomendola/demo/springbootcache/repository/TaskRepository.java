package net.brunomendola.demo.springbootcache.repository;

import net.brunomendola.demo.springbootcache.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
  Collection<Task> findByAssignee_name(String assigneeName);
}
