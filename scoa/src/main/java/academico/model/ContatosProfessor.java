package academico.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "ContatosProfessor")
public class ContatosProfessor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String contato; 

    @Enumerated(EnumType.STRING)
    private tipoContato tipo; 

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public tipoContato getTipo() {
        return tipo;
    }

    public void setTipo(tipoContato tipo) {
        this.tipo = tipo;
    }


    
    
}
