package academico;

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

@Entity
@Table(name = "EspecialidadesProfessor")
public class EspecialidadesProfessor {

    @Id
    private String especialidade;

    @Id
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;
    
}
