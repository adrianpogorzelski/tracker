package Tracker.task;

import Tracker.filters.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.domain.Specification;

@Data
@AllArgsConstructor
@Configurable
public class TaskFilter {
    private ProjectFilter project;    
    private TaskStatus taskStatus;    
    private PersonFilter assignee;    
    
    public Specification<Task> buildSpecification() {        
        Long projectId = project != null ? project.getId() : null;
        Long assigneeId = assignee != null ? assignee.getId() : null;         
        
        return Specification.allOf(                
            equalTo("project", "id", projectId),                
            equalTo("taskStatus", taskStatus),                
            equalTo("assignee", "id", assigneeId)        
        );    
    }    
    
/*     private Specification<Task> equalTo(String property, Object value) {        
        if (value == null) {            
            return Specification.where(null);        
        }        
        
        return (root, query, builder) -> builder.equal(root.get(property), value);   
    }
 */    
     private Specification<Task> equalTo(String property, TaskStatus value) {
        if (value == null) {
            return Specification.where(null);
        }

        return (root, query, builder) -> builder.equal(root.get(property), value);
    }
    
    private Specification<Task> equalTo(String property, String relProperty, Object value) {        
        if (value == null) {            
            return Specification.where(null);        
        }        
        
        return (root, query, builder) -> builder.equal(root.get(property).get(relProperty), value);    
    }    
    
    /* private Specification<Task> ilike(String property, String value) {        
        if (value == null) {            
            return Specification.where(null);        
        }       
         
        return (root, query, builder) -> builder.like(builder.lower(root.get(property)), "%" + value.toLowerCase() + "%");    
    } */
}