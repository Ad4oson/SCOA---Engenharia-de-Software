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
    //Lembrar de adicionar nome no aluno BD
    private String cpf;
    private String rg;
    private String nascimento;
    private String polo;
    private String endereco;
    private String statusfinanceiro;

    private LocalDateTime create_at;
    private boolean deleted;

    //Relações, integrar com BD utilizando ORM JPA/Hibernate depois
    //@JoinColumn(name = "curso_id", referencedColumnName = "id")

    //@ManyToOne
    //@JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToOne
    @JoinColumn(name = "bolsa_id")
    private Bolsa bolsa;

    private Boleto boleto_id;
    private Turma turma_id;
    private ContatosAluno contatos_id;



}
