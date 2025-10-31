package main.java.scoa;

import java.time.LocalTime;

public class Professor {

    private int id;
    private String cpf;
    private String rg;
    //ALTERAR NO BD, transformar CPF e RG em VARCHAR
    private String nome;
    private LocalDate nascimento;
    private String endereco;
    private String formacao;
    private String registros;
    private LocalDate dataadmissao;

    private LocalDateTime created_at;
    private boolean deleted;

    private ContatosProfessor contatos_id;
    private EspecialidadesProfessor especialidades_id;

    
}
