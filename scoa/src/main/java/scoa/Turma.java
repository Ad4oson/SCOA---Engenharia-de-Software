package main.java.scoa;

import java.time.LocalDate;

public class Turma {

    private int id;
    private LocalDateTime horario;
    private int numerovagas;
    private TurnoType turno;

    private LocalDateTime created_at;
    private boolean deleted;
    
    private Sala sala_id;
    private Disciplina disciplina_id;
    private Professor professor_id;
}
