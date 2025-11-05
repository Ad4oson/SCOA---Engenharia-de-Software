package main.java.scoa;

@Entity
@Table(name = "PautaDeAula")
public class PautaDeAula {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate data;
    private String conteudo;
    private String atividades;
    private String observacoes;


    private LocalDateTime created_at;
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;


}
