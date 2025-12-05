package biblioteca.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "multa")
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Double valor;
    private String motivo;
    private statusMulta status;
    private LocalDateTime dataGerada;

    private LocalDateTime created_at;
    private boolean deleted;

    // GETTERS & SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public statusMulta getStatus() { return status; }
    public void setStatus(statusMulta status) { this.status = status; }

    public LocalDateTime getDataGerada() { return dataGerada; }
    public void setDataGerada(LocalDateTime dataGerada) { this.dataGerada = dataGerada; }

    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}
