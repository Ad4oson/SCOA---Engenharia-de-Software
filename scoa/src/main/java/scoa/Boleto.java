package main.java.scoa;

import java.time.LocalDate;

public class Boleto {

   private int id;
   private float valor;
   private LocalDate data_vencimento;
   private String codigo_barras;
   private boolean pago;
   private LocalDateTime create_at;
   private boolean deleted;

   private StatusBoleto status_id;
   private BoletoPagamento boleto_pagamento_id;
    
}
