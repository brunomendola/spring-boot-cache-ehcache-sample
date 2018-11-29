package net.brunomendola.demo.springbootcache.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
  private Long id;
  private String description;
  private String assigneeName;
}
