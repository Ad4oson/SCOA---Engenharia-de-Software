package scoa;

import java.lang.annotation.Inherited;
import java.time.LocalDateTime;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "Aluno")
public class Aluno extends Usuario {
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
    private BolsaFinanciamento bolsa;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<NotaAluno> notas;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<DocumentosAluno> documentos;

    // #region Getters e Setters
    

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

    public BolsaFinanciamento getBolsa() {
        return bolsa;
    }

    public void setBolsa(BolsaFinanciamento bolsa) {
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

    //Construtor padrão p/ JPA
    public Aluno() { }

    //Construtor total
    public Aluno(int id, String login, String senha, TipoUsuario tipoUsuario, String nome, String matricula, String statusfinanceiro,
    String cpf, String rg, String nascimento, String polo, String endereco, LocalDateTime created_at, boolean deleted) {
        setId(id);
        super.setLogin(login);
        super.setSenha(senha);
        super.setTipoUsuario(tipoUsuario);
        setNome(nome);
        setMatricula(matricula);
        setStatusfinanceiro(statusfinanceiro);
        setCpf(cpf);
        setRg(rg);
        setNascimento(nascimento);
        setPolo(polo);
        setEndereco(endereco);
        super.setCreated_at(created_at);
        super.setDeleted(deleted);
    }


    //Construtor parcial
    public Aluno(int id, String login, String senha, TipoUsuario tipoUsuario, String nome, String matricula) {
        setId(id);
        setLogin(login);
        setSenha(senha);
        setTipoUsuario(tipoUsuario);
        setNome(nome);
        setMatricula(matricula);
    }
}
