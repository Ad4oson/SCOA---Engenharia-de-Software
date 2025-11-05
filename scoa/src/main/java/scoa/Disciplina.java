package main.java.scoa;

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
