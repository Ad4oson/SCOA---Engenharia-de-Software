package main.java.scoa;

import java.time.LocalDateTime;

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
