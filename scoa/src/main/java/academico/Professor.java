package academico;

import java.time.LocalTime;

import java.time.LocalDateTime;
import java.time.LocalDate;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
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
@Table(name = "Professor")
public class Professor extends Usuario {

    private int salario;
    private String formacao;
    private String registros;
    private LocalDate dataAdmissao;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<ContatosProfessor> contatos;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<EspecialidadesProfessor> especialidades;

    @OneToOne(mappedBy = "coordenador")
    private Curso curso;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<Turma> turmas;

 
    //#region Getters e Setters


    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public String getRegistros() {
        return registros;
    }

    public void setRegistros(String registros) {
        this.registros = registros;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public List<ContatosProfessor> getContatos() {
        return contatos;
    }

    public void setContatos(List<ContatosProfessor> contatos) {
        this.contatos = contatos;
    }

    public List<EspecialidadesProfessor> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<EspecialidadesProfessor> especialidades) {
        this.especialidades = especialidades;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }
    
    

    //#endregion

  
    // Construtor padrão para JPA
    public Professor() { }

    // Construtor total
    public Professor(int id, String login, String senha, TipoUsuario tipoUsuario, String nome, String cpf, String rg,
            LocalDate nascimento, String polo, String endereco, String formacao, String registros, LocalDate dataAdmissao,
            LocalDateTime created_at, boolean deleted) {
        setId(id);
        setLogin(login);    
        setSenha(senha);
        setTipoUsuario(TipoUsuario.PROFESSOR);
        setNome(nome);
        setCpf(cpf);
        setRg(rg);
        super.setNascimento(nascimento);
        setPolo(polo);
        setEndereco(endereco);
        setFormacao(formacao);
        setRegistros(registros);
        setDataAdmissao(dataAdmissao);
        setCreated_at(created_at);
        setDeleted(deleted);
            }


    public void lancarPauta(
        EntityManager em,
        Integer turmaId,
        LocalDate data,
        String conteudo,
        String atividades,
        String observacoes
    ){
        EntityTransaction tx = em.getTransaction();
        PautaDeAula pauta = new PautaDeAula();
        try {
            tx.begin();

            Turma turma = em.getReference(Turma.class, turmaId);

            pauta.setData(data);
            pauta.setConteudo(conteudo);
            pauta.setAtividades(atividades);
            pauta.setObservacoes(observacoes);
            pauta.setTurma(turma);

            pauta.setCreated_at(LocalDateTime.now());
            pauta.setDeleted(false);
            em.persist(pauta);
            tx.commit();
        }
        catch(Exception e){
            if(tx.isActive()) tx.rollback();
            e.printStackTrace();
            
        }

    }

    
/* 

        // 2. Atualizar os atributos desejados
        if (novoNome != null) disciplina.setNome(novoNome);
        if (novaCargaHoraria != null) disciplina.setCarga_horaria(novaCargaHoraria);
        if (novosCreditos != null) disciplina.setCreditos(novosCreditos);

        // 3. O JPA detecta automaticamente as mudanças (dirty checking)
        tx.commit();
    }
    catch (Exception e) {
        if (tx.isActive()) tx.rollback();
        throw e;
    }
}


*/

            
    }
