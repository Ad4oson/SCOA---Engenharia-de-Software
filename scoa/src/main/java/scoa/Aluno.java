package main.java.scoa;

import java.lang.annotation.Inherited;
import java.time.LocalDateTime;

@Entity
@Table(name = "Aluno")
public class Aluno {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String cpf;
    private String rg;
    private String nascimento;
    private String polo;
    private String endereco;
    private String statusfinanceiro;

    private LocalDateTime create_at;
    private boolean deleted;


    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<Boleto> boletos;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<Mensalidade> mensalidades;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<ContatosAluno> contatos;

    @ManyToMany(mappedBy = "alunos")
    private List<Turma> turmas;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToOne
    @JoinColumn(name = "bolsa_id")
    private Bolsa bolsa;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<NotaAluno> notas;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<DocumentosAluno> documentos;


}
