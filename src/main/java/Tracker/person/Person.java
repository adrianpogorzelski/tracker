package Tracker.person;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import Tracker.authority.Authority;
import Tracker.project.Project;
import Tracker.task.Task;


@Entity
@Data
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq_generator")
    @SequenceGenerator(name = "person_seq_generator", sequenceName = "person_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateCreated;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @Size(min=3)
    private String firstName;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @Size(min=3)
    private String lastName;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @Size(min=8)
    private String password;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @Size(min=3)
    private String email;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "person_authorities",
                joinColumns = @JoinColumn(name = "person_id"),
                inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;

    @OneToMany(mappedBy = "manager")
    private List<Project> managedProjects;

    @OneToMany(mappedBy = "assignee")
    private List<Task> tasks;

    @Column
    private Boolean enabled;
    
}
