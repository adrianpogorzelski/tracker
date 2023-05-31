package Tracker.task;

import Tracker.person.Person;
import Tracker.project.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskFilter {

    private Project project;
    private TaskStatus taskStatus;
    private Person assignee;
    
    public Specification<Task> buildSpecification() {
        return Specification.allOf(
            equalTo("project", project),
            equalTo("taskStatus", taskStatus),
            equalTo("assignee", assignee)
        );
    }

    private Specification<Task> equalTo(String property, Object value) {
        if (value == null) {
            return Specification.where(null);
        }

        return (root, query, builder) -> builder.equal(root.get(property), value);
    }

    private Specification<Task> ilike(String property, String value) {
        if (value == null) {
            return Specification.where(null);
        }
        return (root, query, builder) -> builder.like(builder.lower(root.get(property)), "%" + value.toLowerCase() + "%");
    }
}
