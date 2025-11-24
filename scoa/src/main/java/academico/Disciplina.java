package academico;

import java.time.LocalDateTime;
import java.time.LocalDate;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "Disciplina")
public class Disciplina {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String ementa;
    private int carga_horaria;
    private int creditos;
    private String bibliografia;

    private LocalDateTime created_at;
    private boolean deleted;


    //Relação Cíclica entre disciplina pre requisito
    @ManyToOne
    @JoinColumn(name = "disciplinapre_id")
    private Disciplina disciplinapre;

    @OneToMany(mappedBy = "disciplinapre", cascade = CascadeType.ALL)
    private List<Disciplina> disciplina_dependente;

    @OneToOne(mappedBy = "disciplina")
    private Turma turma;

    @ManyToMany
    @JoinTable(
        name = "disciplinacurso",
        joinColumns = @JoinColumn(name = "disciplina_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private List<Curso> cursos;

}
