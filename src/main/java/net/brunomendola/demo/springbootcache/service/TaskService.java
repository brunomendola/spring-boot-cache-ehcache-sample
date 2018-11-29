package net.brunomendola.demo.springbootcache.service;

import net.brunomendola.demo.springbootcache.dto.TaskDto;
import net.brunomendola.demo.springbootcache.model.Employee;
import net.brunomendola.demo.springbootcache.model.Task;
import net.brunomendola.demo.springbootcache.repository.EmployeeRepository;
import net.brunomendola.demo.springbootcache.repository.TaskRepository;
import net.brunomendola.demo.springbootcache.service.cache.TaskCachingService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class TaskService implements InitializingBean {
  private final TaskRepository taskRepository;
  private final EmployeeRepository employeeRepository;
  private final TaskCachingService taskCachingService;

  public TaskService(TaskRepository taskRepository, EmployeeRepository employeeRepository, TaskCachingService taskCachingService) {
    this.taskRepository = taskRepository;
    this.employeeRepository = employeeRepository;
    this.taskCachingService = taskCachingService;
  }

  public Collection<TaskDto> getByAssigneeName(String name) {
    return taskCachingService.getByAssigneeName(name).stream()
        .map(t -> new TaskDto(t.getId(), t.getDescription(), t.getAssignee().getName()))
        .collect(Collectors.toList());
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Employee employee = new Employee();
    employee.setName("Bruno");
    employeeRepository.save(employee);

    IntStream.range(1, 11).forEach(i -> {
      Task task = new Task();
      task.setDescription("do thing " + i);
      task.setAssignee(employee);
      taskRepository.save(task);
    });
  }
}
