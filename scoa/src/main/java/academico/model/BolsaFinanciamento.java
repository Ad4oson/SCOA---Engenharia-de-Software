package academico.model;

import java.time.LocalDateTime;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "BolsaFinanciamento")
public class BolsaFinanciamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private StatusBolsa status;

    private LocalDateTime created_at;
    private boolean deleted;

    @OneToMany(mappedBy = "bolsa", cascade = CascadeType.ALL)
    private List<Mensalidade> mensalidades;


    @OneToOne(mappedBy = "bolsa")
    private Aluno aluno;

    //#region getters e setters
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public StatusBolsa getStatus() {
        return status;
    }


    public void setStatus(StatusBolsa status) {
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


    public List<Mensalidade> getMensalidades() {
        return mensalidades;
    }


    public void setMensalidades(List<Mensalidade> mensalidades) {
        this.mensalidades = mensalidades;
    }


    public Aluno getAluno() {
        return aluno;
    }


    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
    //#endregion


    

}