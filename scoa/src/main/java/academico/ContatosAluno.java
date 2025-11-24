package academico;

import java.util.List;
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

import java.lang.annotation.Inherited;

import javax.annotation.processing.Generated;

@Entity
@Table(name = "ContatosAluno")
public class ContatosAluno {

    @Id
    private String contato;

    @Id
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

}
