package academico.model;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "Pagamento")
public class BoletoPagamento {
    
    @Id //Define como PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Define auto incremento
    private Integer id;

    private LocalDate data_pagamento;
    private float valor_pago;
    private String forma_pagamento;

    @Enumerated(EnumType.STRING)
    private PagamentoStatus status;
    
    private LocalDateTime created_at;
    private boolean deleted;

    
    @ManyToOne
    @JoinColumn(name = "boleto_id")
    private Boleto boleto;

    @OneToOne(mappedBy = "pagamento", cascade = CascadeType.ALL)
    private TransacaoFinanceira transacao;

   
    //#region getters e setters
    
 public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(LocalDate data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public float getValor_pago() {
        return valor_pago;
    }

    public void setValor_pago(float valor_pago) {
        this.valor_pago = valor_pago;
    }

    public String getForma_pagamento() {
        return forma_pagamento;
    }

    public void setForma_pagamento(String forma_pagamento) {
        this.forma_pagamento = forma_pagamento;
    }

    public PagamentoStatus getStatus() {
        return status;
    }

    public void setStatus(PagamentoStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Boleto getBoleto() {
        return boleto;
    }

    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }

    public TransacaoFinanceira getTransacao() {
        return transacao;
    }

    public void setTransacao(TransacaoFinanceira transacao) {
        this.transacao = transacao;
    }



    //#endregion

}


