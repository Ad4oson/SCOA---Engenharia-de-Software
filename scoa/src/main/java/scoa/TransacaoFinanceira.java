package main.java.scoa;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransacaoFinanceira {

    private int id;
    private float valor;
    private LocalDateTime data_transacao;
    //data_transacao como  LocalDateTIME

    private String origem;
    private String destinatario;
    private String descricao;
    private LocalDateTime created_at;
    private boolean deleted;

    private BoletoPagamento boleto_pagamento_id;
    private CentroCusto centro_custo_id;


    
}
