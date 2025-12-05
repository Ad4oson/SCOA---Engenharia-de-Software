package almoxarifado.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bempatimonial")
public class BemPatrimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;

    private LocalDate data_cadastro;

    private String status_bem;

    private String local;

    private String setor_atual;

    private LocalDate created_at;

    private boolean deleted;

    // GETTERS E SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getData_cadastro() { return data_cadastro; }
    public void setData_cadastro(LocalDate data_cadastro) { this.data_cadastro = data_cadastro; }

    public String getStatus_bem() { return status_bem; }
    public void setStatus_bem(String status_bem) { this.status_bem = status_bem; }

    public String getSetor_atual() { return setor_atual; }
    public void setSetor_atual(String setor_atual) { this.setor_atual = setor_atual; }

    public LocalDate getCreated_at() { return created_at; }
    public void setCreated_at(LocalDate created_at) { this.created_at = created_at; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }


    
}

