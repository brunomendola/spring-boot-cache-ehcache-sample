package net.brunomendola.demo.springbootcache.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Task extends AbstractPersistable<Long> {
  private String description;
  @ManyToOne
  private Employee assignee;
}
