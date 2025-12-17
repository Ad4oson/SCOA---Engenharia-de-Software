package academico.model;

public class NotaConsultaDTO {

    private String aluno;
    private String turma;
    private String disciplina;
    private Double p1;
    private Double p2;
    private Double pf;

    public NotaConsultaDTO(String aluno, String turma, String disciplina,
                         Double p1, Double p2, Double pf) {
        this.aluno = aluno;
        this.turma = turma;
        this.disciplina = disciplina;
        this.p1 = p1;
        this.p2 = p2;
        this.pf = pf;
    }

   

    //#region getters e setters
    
     public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public Double getP1() {
        return p1;
    }

    public void setP1(Double p1) {
        this.p1 = p1;
    }

    public Double getP2() {
        return p2;
    }

    public void setP2(Double p2) {
        this.p2 = p2;
    }

    public Double getPf() {
        return pf;
    }

    public void setPf(Double pf) {
        this.pf = pf;
    }

    //#endregion-
}
