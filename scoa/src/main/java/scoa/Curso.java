package main.java.scoa;

import java.time.LocalDate;

public class Curso {

    private int id;
    private String nome;
    private String mensalidade;
    //EDITAR NO BD medalidade -> mensalidade 

    private TurnoType turno;
    private int cargahoraria;
    private int periodos;
    private LocalDate prazoconclusao;
    private String descricao;
    private String portaria;

    private LocalDate created_at;
    private boolean deleted;

    private StatusCurso status;
    private Coordenador coordenador_id;
    private Disciplina disciplina_id;
    
}
