package scoa;

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

@Entity
@Table(name = "ContatosProfessor")
public class ContatosProfessor {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Id
    private String contato; 

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;
    
}
