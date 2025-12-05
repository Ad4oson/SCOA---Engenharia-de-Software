package biblioteca.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "notificacao")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private UsuarioBiblioteca destinatario;

    private String mensagem;
    private LocalDateTime data_envio;
    private int tipo;

    // GETTERS & SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public UsuarioBiblioteca getDestinatario() { return destinatario; }
    public void setDestinatario(UsuarioBiblioteca destinatario) { this.destinatario = destinatario; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public LocalDateTime getData_envio() { return data_envio; }
    public void setData_envio(LocalDateTime data_envio) { this.data_envio = data_envio; }

    public int getTipo() { return tipo; }
    public void setTipo(int tipo) { this.tipo = tipo; }
}
