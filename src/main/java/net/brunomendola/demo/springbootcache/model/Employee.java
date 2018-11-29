package net.brunomendola.demo.springbootcache.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends AbstractPersistable<Long> {
  private String name;
  @OneToMany(mappedBy = "assignee")
  private Collection<Task> tasks;
}
