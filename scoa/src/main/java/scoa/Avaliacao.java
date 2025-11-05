package main.java.scoa;

import java.util.List;

@Entity
@Table(name = "Avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private TipoAvaliacao tipo;
    
    private LocalDate data;
    private int peso;
    private String descricao;
    private String conteudo;
    private LocalDateTime created_at;
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;

    @OneToMany(mappedBy = "avaliacao", cascade = CascadeType.ALL)
    private List<NotaAluno> notas;
    
}
