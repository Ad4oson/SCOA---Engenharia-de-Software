package main.java.scoa;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacaofinanceira")
public class TransacaoFinanceira {

    private int id;
    private float valor;
    private LocalDateTime data_transacao;
  

    private String origem;
    private String destinatario;
    private String descricao;
    private LocalDateTime created_at;
    private boolean deleted;

    @Id
    @OneToOne
    @JoinColumn(name = "pagamento_id")
    private BoletoPagamento pagamento;
    

    @ManyToMany(mappedBy = "transacoes")
    private List<CentroCusto> centros;


}
