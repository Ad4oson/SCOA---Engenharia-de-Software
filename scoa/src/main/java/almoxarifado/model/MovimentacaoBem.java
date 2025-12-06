package almoxarifado.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacaobem")
public class MovimentacaoBem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "bem_id")
    private BemPatrimonial bem;

    private String setor_origem;

    private String setor_destino;

    private LocalDate data_movimentacao;

    private LocalDateTime created_at;

    private boolean deleted = false;

    // GETTERS E SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public BemPatrimonial getBem() { return bem; }
    public void setBem(BemPatrimonial bem) { this.bem = bem; }

    public String getSetor_origem() { return setor_origem; }
    public void setSetor_origem(String setor_origem) { this.setor_origem = setor_origem; }

    public String getSetor_destino() { return setor_destino; }
    public void setSetor_destino(String setor_destino) { this.setor_destino = setor_destino; }

    public LocalDate getData_movimentacao() { return data_movimentacao; }
    public void setData_movimentacao(LocalDate data_movimentacao) { this.data_movimentacao = data_movimentacao; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
    public LocalDateTime getCreated_at() {
        return created_at;
    }
    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }




    
}

