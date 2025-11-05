package main.java.scoa;

@Entity
@Table(name = "CentroCusto")
public class CentroCusto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String descricao;
    private int mes_competencia;
    private float valor_alocado;
    private String status;

    private LocalDateTime created_at;
    private boolean deleted;


    @ManyToMany
    @JoinTable(
        name = "TransacaoCentro",
        joinColumns = @JoinColumn(name = "centro_id"),
        inverseJoinColumns = @JoinColumn(name = "transacao_id")
    )
    private List<TransacaoFinanceira> transacoes;
}
