package main.java.scoa;

import javax.annotation.processing.Generated;

@Entity
@Table(name = "NotaAluno")
public class NotaAluno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int valor;
    private float mediaparcial;
    private float mediafinal;
    private String observacao;
    
    private LocalDateTime created_at;
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "avaliacao_id")
    private Avaliacao avaliacao;


}
