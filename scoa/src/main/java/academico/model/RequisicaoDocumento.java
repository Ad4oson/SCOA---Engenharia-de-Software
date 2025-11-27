package academico.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "RequisicaoDocumento")
public class RequisicaoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String texto;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;


    //#region getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
    //#endregion

    
    
}
