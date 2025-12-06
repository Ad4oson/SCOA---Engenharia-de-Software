package almoxarifado.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "baixabem")
public class BaixaBem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "bem_id")
    private BemPatrimonial bem;

    private String motivo;

    private LocalDate data_baixa;

    private String responsavel;

    private LocalDateTime created_at;

    private boolean deleted = false;

    // GETTERS E SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public BemPatrimonial getBem() { return bem; }
    public void setBem(BemPatrimonial bem) { this.bem = bem; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public LocalDate getData_baixa() { return data_baixa; }
    public void setData_baixa(LocalDate data_baixa) { this.data_baixa = data_baixa; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
    
    public LocalDateTime getCreated_at() {
        return created_at;
    }
    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }


    
}
