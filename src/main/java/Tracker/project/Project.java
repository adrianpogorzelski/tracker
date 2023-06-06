package Tracker.project;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @OneToOne
    @JoinColumn(name = "creator_id")
    private Person creator;

    @Column(nullable = false)
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;
    
    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = true)
    private Person manager;

    @Column
    private Boolean enabled;
}
