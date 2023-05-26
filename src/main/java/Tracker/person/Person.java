package Tracker.person;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;


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
    @NotNull
    @NotEmpty
    @Size(min=3)
    private String email;

    @Column
    private String role;
}
