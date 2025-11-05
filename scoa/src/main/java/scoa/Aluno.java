package main.java.scoa;

import java.lang.annotation.Inherited;
import java.time.LocalDateTime;

@Entity
@Table(name = "Aluno")
public class Aluno extends Usuario {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String statusfinanceiro;
    private String matricula;


    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<Boleto> boletos;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<Mensalidade> mensalidades;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<ContatosAluno> contatos;

    @ManyToMany(mappedBy = "alunos")
    private List<Turma> turmas;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToOne
    @JoinColumn(name = "bolsa_id")
    private Bolsa bolsa;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<NotaAluno> notas;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<DocumentosAluno> documentos;

    // #region Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public String getMatricula() {
        return matricula;
    }

    public String getStatusfinanceiro() {
        return statusfinanceiro;
    }

    public void setStatusfinanceiro(String statusfinanceiro) {
        this.statusfinanceiro = statusfinanceiro;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public List<Boleto> getBoletos() {
        return boletos;
    }

    public void setBoletos(List<Boleto> boletos) {
        this.boletos = boletos;
    }

    public List<Mensalidade> getMensalidades() {
        return mensalidades;
    }

    public void setMensalidades(List<Mensalidade> mensalidades) {
        this.mensalidades = mensalidades;
    }

    public List<ContatosAluno> getContatos() {
        return contatos;
    }

    public void setContatos(List<ContatosAluno> contatos) {
        this.contatos = contatos;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Bolsa getBolsa() {
        return bolsa;
    }

    public void setBolsa(Bolsa bolsa) {
        this.bolsa = bolsa;
    }

    public List<NotaAluno> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaAluno> notas) {
        this.notas = notas;
    }

    public List<DocumentosAluno> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentosAluno> documentos) {
        this.documentos = documentos;
    }
//#endregion

    


}
