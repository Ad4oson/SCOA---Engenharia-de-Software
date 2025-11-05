package main.java.scoa;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String mensalidade;

    @Enumerated(EnumType.STRING)
    private TurnoType turno;

    private int cargahoraria;
    private int periodos;
    private LocalDate prazoconclusao;
    private String descricao;
    private String portaria;

    private LocalDate created_at;
    private boolean deleted;

    @Enumerated(EnumType.STRING)
    private StatusCurso status;

    @OneToOne
    @JoinColumn(name = "coordenador_id")
    private Professor coordenador;


    @ManyToMany(mappedBy = "cursos")
    private List<Disciplina> disciplinas;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Aluno> alunos;

}
