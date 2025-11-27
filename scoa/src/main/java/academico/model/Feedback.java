package academico.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Feedback {

    private int id;
    private String texto;

    private TipoFeedback tipoFeedback;

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

    public TipoFeedback getTipoFeedback() {
        return tipoFeedback;
    }

    public void setTipoFeedback(TipoFeedback tipoFeedback) {
        this.tipoFeedback = tipoFeedback;
    }

    
    //#endregion
    
    
}
