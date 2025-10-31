package main.java.scoa;

public class Disciplina {
    
    private int id;
    private String nome;
    private String ementa;
    private int carga_horaria;
    private int creditos;
    private String bibliografia;

    private LocalDateTime created_at;
    private boolean deleted;

    private Disciplina disciplinapre_id;
    private Curso curso_id;

}
