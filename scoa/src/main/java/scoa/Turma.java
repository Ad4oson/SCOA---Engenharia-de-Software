package main.java.scoa;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "turma")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime horario;
    private int numerovagas;

    @Enumerated(EnumType.STRING)
    private TurnoType turno;

    private LocalDateTime created_at;
    private boolean deleted;
    
    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @OneToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToMany
    @JoinTable(
        name = "alunoturma",
        joinColumns = @JoinColumn(name = "turma_id"),
        inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> alunos;


    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
    private List<PautaDeAula> pautas;

}
