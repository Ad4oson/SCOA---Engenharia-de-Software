package main.java.scoa;

import java.lang.annotation.Inherited;

@Entity
@Table(name = "EspecialidadesProfessor")
public class EspecialidadesProfessor {

    @Id
    private String especialidade;

    @Id
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;
    
}
