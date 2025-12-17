package almoxarifado.model;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Estoque")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer estoque_min;
    private Integer estoque_max;
    private String local_fisico;
    private String codigo_barras;

    private LocalDateTime created_at;
    private Boolean deleted = false;



    //#region Getters e Setters

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getEstoque_min() {
        return estoque_min;
    }
    public void setEstoque_min(Integer estoque_min) {
        this.estoque_min = estoque_min;
    }
    public Integer getEstoque_max() {
        return estoque_max;
    }
    public void setEstoque_max(Integer estoque_max) {
        this.estoque_max = estoque_max;
    }
    public String getLocalFisico() {
        return local_fisico;
    }
    public void setLocalFisico(String local_fisico) {
        this.local_fisico = local_fisico;
    }
    public String getCodigo_barras() {
        return codigo_barras;
    }
    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
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
