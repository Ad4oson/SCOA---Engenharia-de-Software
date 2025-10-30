package main.java.scoa;

import java.time.LocalDateTime;

public class Aluno {

    private int id;

    private String nome;
    //Lembrar de adicionar nome no aluno BD
    private String cpf;
    private String rg;
    private String nascimento;
    private String polo;
    private String endereco;
    private String statusfinanceiro;

    private LocalDateTime create_at;
    private boolean deleted;

    //Relações, integrar com BD utilizando ORM JPA/Hibernate depois
    private Curso curso_id;
    private Bolsa bolsa_id;
    private Boleto boleto_id;




}
