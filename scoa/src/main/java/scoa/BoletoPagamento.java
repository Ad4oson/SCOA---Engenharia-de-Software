package scoa;
import java.lang.annotation.Inherited;
import java.time.LocalDate;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "Pagamento")
public class BoletoPagamento {
    
    @Id //Define como PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Define auto incremento
    private int id;

    private LocalDate data_pagamento;
    private float valor_pago;
    private String forma_pagamento;

    @Enumerated(EnumType.STRING)
    private PagamentoStatus status;
    
    private LocalDate created_at;
    private boolean deleted;

    
    @ManyToOne
    @JoinColumn(name = "boleto_id")
    private Boleto boleto;

    @OneToOne(mappedBy = "pagamento", cascade = CascadeType.ALL)
    private TransacaoFinanceira transacao;


}


