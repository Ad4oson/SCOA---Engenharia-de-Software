package main.java.scoa;
import java.time.LocalDate;

public class BoletoPagamento {
    
    private int id;
    private LocalDate data_pagamento;
    private float valor_pago;
    private String forma_pagamento;
    private PagamentoStatus status;
    private LocalDate created_at;
    private boolean deleted;

    private TransacaoFinanceira transacao_id;
    private Boleto boleto_id;
}

//CRIADO ENUM PARA STATUS PAGAMENTO, ALTERAR NO BD
