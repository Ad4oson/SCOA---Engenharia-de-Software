package scoa;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.time.LocalDate;

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
@Table(name = "NotaAluno")
public class NotaAluno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int valor;
    private float mediaparcial;
    private float mediafinal;
    private String observacao;
    
    private LocalDateTime created_at;
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "avaliacao_id")
    private Avaliacao avaliacao;


}
