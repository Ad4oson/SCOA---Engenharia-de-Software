package academico;

public class NotaConsultaDTO {


    private String aluno;
    private String avaliacao;
    private Double nota;
    private Double media;

    public NotaConsultaDTO(String aluno, String avaliacao, Double nota, Double media) {
        this.aluno = aluno;
        this.avaliacao = avaliacao;
        this.nota = nota;
        this.media = media;
    }


    //#region
    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Double getMedia() {
        return media;
    }

    public void setMedia(Double media) {
        this.media = media;
    }
//#endregion

    

    
}
