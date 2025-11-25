package academico;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "Curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String mensalidade;

    @Enumerated(EnumType.STRING)
    private TurnoType turno;

    private int cargahoraria;
    private int periodos;
    private LocalDate prazoconclusao;
    private String descricao;
    private String portaria;

    private LocalDateTime created_at;
    private boolean deleted;

    @Enumerated(EnumType.STRING)
    private StatusCurso status;

    @OneToOne
    @JoinColumn(name = "coordenador_id")
    private Professor coordenador;


    @ManyToMany(mappedBy = "cursos")
    private List<Disciplina> disciplinas;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Aluno> alunos;

    

    //#region Getters e Setters



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

    public String getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(String mensalidade) {
        this.mensalidade = mensalidade;
    }

    public TurnoType getTurno() {
        return turno;
    }

    public void setTurno(TurnoType turno) {
        this.turno = turno;
    }

    public int getCargahoraria() {
        return cargahoraria;
    }

    public void setCargahoraria(int cargahoraria) {
        this.cargahoraria = cargahoraria;
    }

    public int getPeriodos() {
        return periodos;
    }

    public void setPeriodos(int periodos) {
        this.periodos = periodos;
    }

    public LocalDate getPrazoconclusao() {
        return prazoconclusao;
    }

    public void setPrazoconclusao(LocalDate prazoconclusao) {
        this.prazoconclusao = prazoconclusao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPortaria() {
        return portaria;
    }

    public void setPortaria(String portaria) {
        this.portaria = portaria;
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

    public StatusCurso getStatus() {
        return status;
    }

    public void setStatus(StatusCurso status) {
        this.status = status;
    }

    public Professor getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Professor coordenador) {
        this.coordenador = coordenador;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }
    
    //#endregion

    //Construtor padrão p/ JPA
    public Curso() {}

    //Construtor total
    public Curso(int id, String nome, String mensalidade, TurnoType turno, int cargahoraria, int periodos,
            LocalDate prazoconclusao, String descricao, String portaria, LocalDateTime created_at, boolean deleted,
            StatusCurso status, Professor coordenador) {
        this.id = id;
        this.nome = nome;
        this.mensalidade = mensalidade;
        this.turno = turno;
        this.cargahoraria = cargahoraria;
        this.periodos = periodos;
        this.prazoconclusao = prazoconclusao;
        this.descricao = descricao;
        this.portaria = portaria;
        this.created_at = created_at;
        this.deleted = deleted;
        this.status = status;
        this.coordenador = coordenador;
    }
}
