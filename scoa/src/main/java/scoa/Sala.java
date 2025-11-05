package main.java.scoa;

import java.time.LocalDateTime;

@Entity
@Table(name = "Sala")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String local;
    private int capacidade;
    private LocalDateTime created_at;
    private boolean deleted;
    
    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
    private List<Turma> turmas;
    
}
