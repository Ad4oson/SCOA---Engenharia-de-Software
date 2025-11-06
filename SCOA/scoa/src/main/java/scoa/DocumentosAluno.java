package main.java.scoa;

@Entity
@Table(name = "DocumentosAluno")
public class DocumentosAluno {
    
    @Id
    private int id;
    private String tipo_documento; 
    private String caminho_arquivo;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno_id;
}
