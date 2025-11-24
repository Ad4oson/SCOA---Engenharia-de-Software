package scoa;

import java.time.LocalDateTime;

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
@Table(name = "Mensalidade")
public class Mensalidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int valorbase;
    private int cargahoraria;
    private int desconto;
    private int valorfinal;

    private LocalDateTime created_at;
    private boolean deleted;

    //Relações, integrar com BD utilizando ORM JPA/Hibernate depois

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "bolsa_id")
    private BolsaFinanciamento bolsa;
}
