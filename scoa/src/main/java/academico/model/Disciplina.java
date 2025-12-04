package academico.model;

import java.time.LocalDateTime;
import java.time.LocalDate;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "Disciplina")
public class Disciplina {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String ementa;
    private Integer cargaHoraria;
    private Integer creditos;
    private String bibliografia;

    private LocalDateTime created_at;
    private boolean deleted;


    //Relação Cíclica entre disciplina pre requisito
    @ManyToOne
    @JoinColumn(name = "disciplinapre_id")
    private Disciplina disciplinapre;

    @OneToMany(mappedBy = "disciplinapre", cascade = CascadeType.ALL)
    private List<Disciplina> disciplina_dependente;

    @OneToMany(mappedBy = "disciplina")
    private List<Turma> turmas;

    @ManyToMany
    @JoinTable(
        name = "disciplinacurso",
        joinColumns = @JoinColumn(name = "disciplina_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private List<Curso> cursos;

    //#region
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmenta() {
        return ementa;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public String getBibliografia() {
        return bibliografia;
    }

    public void setBibliografia(String bibliografia) {
        this.bibliografia = bibliografia;
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

    public Disciplina getDisciplinapre() {
        return disciplinapre;
    }

    public void setDisciplinapre(Disciplina disciplinapre) {
        this.disciplinapre = disciplinapre;
    }

    public List<Disciplina> getDisciplina_dependente() {
        return disciplina_dependente;
    }

    public void setDisciplina_dependente(List<Disciplina> disciplina_dependente) {
        this.disciplina_dependente = disciplina_dependente;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    
    //#endregion

    

}
