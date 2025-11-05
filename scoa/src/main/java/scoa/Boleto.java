package main.java.scoa;

import java.time.LocalDate;

@Entity
@Table(name = "Boleto")
public class Boleto {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   private float valor;
   private LocalDate data_vencimento;
   private String codigo_barras;
   private boolean pago;

   private LocalDateTime create_at;
   private boolean deleted;

   @ManyToOne
   @JoinColumn(name = "status_id")
   private StatusBoleto status;

   @OneToMany(mappedBy = "boleto", cascade = CascadeType.ALL)
   private List<BoletoPagamento> pagamentos;

   @ManyToOne
   @JoinColumn(name = "aluno_id")
   private Aluno aluno;
    
}
