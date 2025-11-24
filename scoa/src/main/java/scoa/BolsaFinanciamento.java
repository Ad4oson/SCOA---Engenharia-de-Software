package scoa;

import java.time.LocalDateTime;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;

import scoa.enums.status_bolsa;

@Entity
@Table(name = "BolsaFinanciamento")
public class BolsaFinanciamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private StatusBolsa status;

    private LocalDateTime created_at;
    private boolean deleted;

    @OneToMany(mappedBy = "bolsa", cascade = CascadeType.ALL)
    private List<Mensalidade> mensalidades;


    @OneToOne(mappedBy = "bolsa")
    private Aluno aluno;

}