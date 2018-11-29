package net.brunomendola.demo.springbootcache.service.cache;

import net.brunomendola.demo.springbootcache.model.Task;
import net.brunomendola.demo.springbootcache.repository.TaskRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@CacheConfig(cacheNames = "tasks")
public class TaskCachingService {
  private final TaskRepository taskRepository;

  public TaskCachingService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Cacheable
  public Collection<Task> getByAssigneeName(String name) {
    return taskRepository.findByAssignee_name(name);
  }
}
