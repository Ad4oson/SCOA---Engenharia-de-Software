package biblioteca.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "Obra")
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titulo;
    private String autor;
    private String tipomaterial;
    private String editora;
    private String anoPublicacao;
    private String localizacao;

    @Enumerated(EnumType.STRING)
    private statusObra status;
    private LocalDateTime created_at;
    private boolean deleted;

    // GETTERS & SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getTipomateria() { return tipomaterial; }
    public void setTipomateria(String tipomateria) { this.tipomaterial = tipomateria; }

    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }

    public String getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(String anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public statusObra getStatus() { return status; }
    public void setStatus(statusObra status) { this.status = status; }

    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}
