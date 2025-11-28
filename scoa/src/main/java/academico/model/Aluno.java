package academico.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "Aluno")
public class Aluno extends Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String statusfinanceiro;
    private String matricula;

    @OneToOne
    @JoinColumn(name = "login", referencedColumnName = "login")
    private Usuario usuario;


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

    @OneToMany(mappedBy = "aluno")
    private List<FrequenciaAluno>  frequencia;

    @OneToMany(mappedBy = "aluno")
    private List<RequisicaoDocumento> requisicoes;

    @OneToMany(mappedBy = "aluno")
    private List<Feedback> feedback;


    // #region Getters e Setters
    


    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    
    public List<FrequenciaAluno> getFrequencia() {
        return frequencia;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public List<RequisicaoDocumento> getRequisicoes() {
        return requisicoes;
    }
    public void setRequisicoes(List<RequisicaoDocumento> requisicoes) {
        this.requisicoes = requisicoes;
    }
    public List<Feedback> getFeedback() {
        return feedback;
    }
    public void setFeedback(List<Feedback> feedback) {
        this.feedback = feedback;
    }
    public void setFrequencia(List<FrequenciaAluno> frequencia) {
        this.frequencia = frequencia;
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
    String cpf, String rg, LocalDate nascimento, String polo, String endereco, LocalDateTime created_at, boolean deleted) {
        setId(id);
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
        setNome(nome);
        setMatricula(matricula);
    }


}
