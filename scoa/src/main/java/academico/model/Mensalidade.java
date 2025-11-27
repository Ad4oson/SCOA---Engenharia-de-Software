package academico.model;

import java.time.LocalDateTime;

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
@Table(name = "Mensalidade")
public class Mensalidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int valorbase;
    private int cargahoraria;
    private int desconto;
    private int valorfinal;

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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValorbase() {
        return valorbase;
    }

    public void setValorbase(int valorbase) {
        this.valorbase = valorbase;
    }

    public int getCargahoraria() {
        return cargahoraria;
    }

    public void setCargahoraria(int cargahoraria) {
        this.cargahoraria = cargahoraria;
    }

    public int getDesconto() {
        return desconto;
    }

    public void setDesconto(int desconto) {
        this.desconto = desconto;
    }

    public int getValorfinal() {
        return valorfinal;
    }

    public void setValorfinal(int valorfinal) {
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
