package academico.model;

import java.time.LocalDateTime;


import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "CentroCusto")
public class CentroCusto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    private Integer mes_competencia;
    private float valor_alocado;
    private String status;

    private LocalDateTime created_at;
    private boolean deleted;


    @ManyToMany
    @JoinTable(
        name = "TransacaoCentro",
        joinColumns = @JoinColumn(name = "centro_id"),
        inverseJoinColumns = @JoinColumn(name = "transacao_id")
    )
    private List<TransacaoFinanceira> transacoes;



    //#region getters e setters

    
    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getDescricao() {
        return descricao;
    }


    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public Integer getMes_competencia() {
        return mes_competencia;
    }


    public void setMes_competencia(Integer mes_competencia) {
        this.mes_competencia = mes_competencia;
    }


    public float getValor_alocado() {
        return valor_alocado;
    }


    public void setValor_alocado(float valor_alocado) {
        this.valor_alocado = valor_alocado;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
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


    public List<TransacaoFinanceira> getTransacoes() {
        return transacoes;
    }


    public void setTransacoes(List<TransacaoFinanceira> transacoes) {
        this.transacoes = transacoes;
    }

    //#endregion
}
