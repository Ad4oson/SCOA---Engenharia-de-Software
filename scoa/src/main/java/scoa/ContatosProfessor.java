package main.java.scoa;

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
