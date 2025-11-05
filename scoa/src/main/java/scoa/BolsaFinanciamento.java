package main.java.scoa;

import java.time.LocalDateTime;

import main.java.scoa.enums.status_bolsa;

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