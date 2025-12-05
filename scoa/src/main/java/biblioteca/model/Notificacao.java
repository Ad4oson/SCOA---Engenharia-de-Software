package biblioteca.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "Notificacao")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Bibliotecario destinatario;

    private String mensagem;
    private LocalDateTime data_envio;

    @Enumerated(EnumType.STRING)
    private tipoNotificacao tipo;

    @Enumerated(EnumType.STRING)
    private tipoUsuario tipoUsuario;

    private LocalDateTime created_at;
    private Boolean deleted;

    // GETTERS & SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Bibliotecario getDestinatario() { return destinatario; }
    public void setDestinatario(Bibliotecario destinatario) { this.destinatario = destinatario; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public LocalDateTime getData_envio() { return data_envio; }
    public void setData_envio(LocalDateTime data_envio) { this.data_envio = data_envio; }

    public tipoNotificacao getTipo() { return tipo; }
    public void setTipo(tipoNotificacao tipo) { this.tipo = tipo; }


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
    public tipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
    public void setTipoUsuario(tipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }


    

}
