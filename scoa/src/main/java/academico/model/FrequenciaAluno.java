package academico.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "FrequenciaAluno")
public class FrequenciaAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //LEMBRAR DE ALTERAR NOMES NO BD
    private LocalDate data;
    private boolean presente;
    private Integer tempo_de_aula;

    private LocalDateTime created_at;
    private boolean deleted;


    @ManyToOne
    @JoinColumn(name= "turma_id")
    private Turma turma;


    @ManyToOne
    @JoinColumn(name= "aluno_id")
    private Aluno aluno;


    //#region getters e setters
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }



    public LocalDateTime getCreated_at() {
        return created_at;
    }


    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }


    public boolean isDeleted() {
        return deleted;
    }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }



    public LocalDate getData() {
        return data;
    }


    public void setData(LocalDate data) {
        this.data = data;
    }


    public boolean isPresente() {
        return presente;
    }


    public void setPresente(boolean presente) {
        this.presente = presente;
    }


    public Integer getTempo_de_aula() {
        return tempo_de_aula;
    }


    public void setTempo_de_aula(Integer tempo_de_aula) {
        this.tempo_de_aula = tempo_de_aula;
    }


    public Turma getTurma() {
        return turma;
    }


    public void setTurma(Turma turma) {
        this.turma = turma;
    }


    public Aluno getAluno() {
        return aluno;
    }


    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
//#endregion


    

    
}
