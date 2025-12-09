package academico.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "Mensalidade")
public class Mensalidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private float valorbase;
    private Integer cargahoraria;
    private float desconto;
    private float valorfinal;

    private LocalDateTime created_at;
    private boolean deleted;

    //Relações, integrar com BD utilizando ORM JPA/Hibernate depois

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "bolsa_id")
    private BolsaFinanciamento bolsa;


    //#region getters e setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getValorbase() {
        return valorbase;
    }

    public void setValorbase(float valorbase) {
        this.valorbase = valorbase;
    }

    public Integer getCargahoraria() {
        return cargahoraria;
    }

    public void setCargahoraria(Integer cargahoraria) {
        this.cargahoraria = cargahoraria;
    }

    public float getDesconto() {
        return desconto;
    }

    public void setDesconto(float desconto) {
        this.desconto = desconto;
    }

    public float getValorfinal() {
        return valorfinal;
    }

    public void setValorfinal(float valorfinal) {
        this.valorfinal = valorfinal;
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

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public BolsaFinanciamento getBolsa() {
        return bolsa;
    }

    public void setBolsa(BolsaFinanciamento bolsa) {
        this.bolsa = bolsa;
    }

    //#endregion
    
}
