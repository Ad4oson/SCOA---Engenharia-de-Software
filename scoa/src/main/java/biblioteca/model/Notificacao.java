package biblioteca.model;

import java.time.LocalDateTime;

import academico.model.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "Notificacao")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "destinatario_login")
    private Usuario destinatario_login;

    private String mensagem;

    @Enumerated(EnumType.STRING)
    private tipoNotificacao tipo;

    @Enumerated(EnumType.STRING)
    private tipoUsuario tipoUsuario;

    private LocalDateTime created_at;
    private Boolean deleted;

    // GETTERS & SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }



    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }


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
    public Usuario getUsuario() {
        return destinatario_login;
    }
    public void setUsuario(Usuario login) {
        this.destinatario_login = login;
    }


    

}
