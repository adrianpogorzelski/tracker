package Tracker.project;

import java.time.LocalDateTime;
import java.util.List;

import Tracker.person.Person;
import Tracker.task.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq_generator")
    @SequenceGenerator(name = "project_seq_generator", sequenceName = "project_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @Size(min=3)
    private String name;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String description;

    @Column(nullable = false)
    private LocalDateTime dateCreated;

    @Column
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Person manager;
}
