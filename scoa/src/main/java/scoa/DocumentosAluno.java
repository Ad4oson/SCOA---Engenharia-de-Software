package scoa;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "DocumentosAluno")
public class DocumentosAluno {
    
    @Id
    private int id;
    private String tipo_documento; 
    private String caminho_arquivo;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
}
