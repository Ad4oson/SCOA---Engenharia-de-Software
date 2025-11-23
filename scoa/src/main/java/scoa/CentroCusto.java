package main.java.scoa;

import java.time.LocalDateTime;
import java.time.LocalDate;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
@Entity
@Table(name = "CentroCusto")
public class CentroCusto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String descricao;
    private int mes_competencia;
    private float valor_alocado;
    private String status;

    private LocalDateTime created_at;
    private boolean deleted;


    @ManyToMany
    @JoinTable(
        name = "TransacaoCentro",
        joinColumns = @JoinColumn(name = "centro_id"),
        inverseJoinColumns = @JoinColumn(name = "transacao_id")
    )
    private List<TransacaoFinanceira> transacoes;
}
