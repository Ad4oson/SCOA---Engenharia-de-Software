package academico.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;

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
