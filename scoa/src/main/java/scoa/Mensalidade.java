package main.java.scoa;

import java.time.LocalDateTime;

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
