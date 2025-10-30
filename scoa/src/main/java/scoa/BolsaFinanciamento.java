package main.java.scoa;

import java.time.LocalDateTime;

import main.java.scoa.enums.status_bolsa;

public class BolsaFinanciamento {
    
    private int id;
    private status_bolsa status;
    private LocalDateTime created_at;
    private boolean deleted;

    private Mensalidade mensalidade_id;

}