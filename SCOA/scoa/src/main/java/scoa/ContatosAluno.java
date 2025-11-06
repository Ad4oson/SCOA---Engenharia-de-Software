package main.java.scoa;

import java.lang.annotation.Inherited;

import javax.annotation.processing.Generated;

@Entity
@Table(name = "ContatosAluno")
public class ContatosAluno {

    @Id
    private String contato;

    @Id
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

}
