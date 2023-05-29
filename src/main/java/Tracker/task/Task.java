package Tracker.task;

import java.time.LocalDateTime;

import Tracker.person.Person;
import Tracker.project.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq_generator")
    @SequenceGenerator(name = "task_seq_generator", sequenceName = "task_SEQ", allocationSize = 1)
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

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus = TaskStatus.BACKLOG;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskType taskType = TaskType.REQUEST;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private Person assignee;
}
