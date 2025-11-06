package main.java.scoa;

import java.time.LocalDateTime;

@Entity
@Table(name = "Usuario")
public class Usuario {

    //Nesta classe terá atributos que todos usuários terão em comum, porém a classe não se espelha em sua respectiva no BD.

    //login / senha / tipo_usuário / created_at / deleted
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Id
    private String login;
    private String senha;
    private TipoUsuario tipoUsuario;

    private String nome;
    private String cpf;
    private String rg;
    private String nascimento;
    private String polo;
    private String endereco;

    private LocalDateTime created_at;
    private boolean deleted;
    
    //#region Getters e Setters


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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


    //#endregion
    
    
}
