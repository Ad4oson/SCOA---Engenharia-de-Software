package almoxarifado.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codigo;
    private String nome;

    @Enumerated(EnumType.STRING)
    private tipoProduto tipo;

    private String descricao;
    private String unidade_medida;
    private Integer qt_atual;
    
    @ManyToOne
    @JoinColumn(name = "estoque_id")
    private Estoque estoque;

    private LocalDateTime created_at;
    private Boolean deleted = false;



    //#region getters e setters

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public tipoProduto getTipo() {
        return tipo;
    }
    public void setTipo(tipoProduto tipo) {
        this.tipo = tipo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getUnidade_medida() {
        return unidade_medida;
    }
    public void setUnidade_medida(String unidade_medida) {
        this.unidade_medida = unidade_medida;
    }
    public Integer getQt_atual() {
        return qt_atual;
    }
    public void setQt_atual(Integer qt_atual) {
        this.qt_atual = qt_atual;
    }
    public Estoque getEstoque() {
        return estoque;
    }
    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }
    public LocalDateTime getCreated_at() {
        return created_at;
    }
    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
    public Boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }


    //#endregion

    


}
