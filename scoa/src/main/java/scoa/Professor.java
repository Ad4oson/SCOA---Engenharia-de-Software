package main.java.scoa;

import java.time.LocalTime;

@Entity
@Table(name = "Professor")
public class Professor extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
    

    //#endregion

  
    // Construtor padrão para JPA
    public Professor() { }
    // Construtor total
    public Professor(int id, String login, String senha, TipoUsuario tipoUsuario, String nome, String cpf, String rg,
            String nascimento, String polo, String endereco, String formacao, String registros, LocalDate dataAdmissao,
            LocalDateTime created_at, boolean deleted) {
        setId(id);
        setLogin(login);    
        setSenha(senha);
        setTipoUsuario(tipoUsuario);
        setNome(nome);
        setCpf(cpf);
        setRg(rg);
        setNascimento(nascimento);
        setPolo(polo);
        setEndereco(endereco);
        setFormacao(formacao);
        setRegistros(registros);
        setDataAdmissao(dataAdmissao);
        setCreated_at(created_at);
        setDeleted(deleted);
            }
    
            
    }
