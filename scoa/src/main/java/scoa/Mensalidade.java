package main.java.scoa;

import java.time.LocalDateTime;

public class Mensalidade {

    private int id;

    private int valorbase;
    private int cargahoraria;
    private int desconto;
    private int valorfinal;

    private LocalDateTime created_at;
    private boolean deleted;

    //Relações, integrar com BD utilizando ORM JPA/Hibernate depois
    private Aluno aluno_id;
    private BolsaFinanciamento bolsa_id;
}
