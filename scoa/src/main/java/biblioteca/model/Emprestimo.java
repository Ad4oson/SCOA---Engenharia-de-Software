package biblioteca.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "obra_id")
    private Obra obra;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioBiblioteca usuario;

    private LocalDateTime data_emprestimo;
    private LocalDateTime previsao_devolucao;
    private LocalDateTime prazo_devolucao;
    private int status;

    private LocalDateTime created_at;
    private boolean deleted;

    // GETTERS & SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Obra getObra() { return obra; }
    public void setObra(Obra obra) { this.obra = obra; }

    public UsuarioBiblioteca getUsuario() { return usuario; }
    public void setUsuario(UsuarioBiblioteca usuario) { this.usuario = usuario; }

    public LocalDateTime getData_emprestimo() { return data_emprestimo; }
    public void setData_emprestimo(LocalDateTime data_emprestimo) { this.data_emprestimo = data_emprestimo; }

    public LocalDateTime getPrevisao_devolucao() { return previsao_devolucao; }
    public void setPrevisao_devolucao(LocalDateTime previsao_devolucao) { this.previsao_devolucao = previsao_devolucao; }

    public LocalDateTime getPrazo_devolucao() { return prazo_devolucao; }
    public void setPrazo_devolucao(LocalDateTime prazo_devolucao) { this.prazo_devolucao = prazo_devolucao; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}
