package net.brunomendola.demo.springbootcache.controller;

import net.brunomendola.demo.springbootcache.dto.TaskDto;
import net.brunomendola.demo.springbootcache.service.TaskService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @RequestMapping("/assignee/{employeeName}")
  public Collection<TaskDto> getByAssignee(@PathVariable String employeeName) {
    return taskService.getByAssigneeName(employeeName);
  }
}
