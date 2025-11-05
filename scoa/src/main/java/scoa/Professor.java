package main.java.scoa;

import java.time.LocalTime;

@Entity
@Table(name = "Professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cpf;
    private String rg;

    private String nome;
    private LocalDate nascimento;
    private String endereco;
    private String formacao;
    private String registros;
    private LocalDate dataAdmissao;

    private LocalDateTime created_at;
    private boolean deleted;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<ContatosProfessor> contatos;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<EspecialidadesProfessor> especialidades;

    @OneToOne(mappedBy = "coordenador")
    private Curso curso;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<Turma> turmas;

    
}
