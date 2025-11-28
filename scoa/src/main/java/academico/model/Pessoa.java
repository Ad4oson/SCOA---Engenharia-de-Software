package academico.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Pessoa{

    private String nome;
    private String cpf;
    private String rg;
    private LocalDate nascimento;
    private String polo;
    private String endereco;

    
    private LocalDateTime created_at;
    private boolean deleted;



    //#region Getters e Setters


    
    public String getNome() {
        return nome;
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
    public LocalDate getNascimento() {
        return nascimento;
    }
    public void setNascimento(LocalDate nascimento) {
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

    //#endregion

    

    
}
