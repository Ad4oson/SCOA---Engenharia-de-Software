package main.java.scoa;

import java.lang.annotation.Inherited;
import java.time.LocalDateTime;

@Entity
@Table(name = "Aluno")
public class Aluno {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String cpf;
    private String rg;
    private String nascimento;
    private String polo;
    private String endereco;
    private String statusfinanceiro;

    private LocalDateTime create_at;
    private boolean deleted;


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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getPolo() {
        return polo;
    }

    public void setPolo(String polo) {
        this.polo = polo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
