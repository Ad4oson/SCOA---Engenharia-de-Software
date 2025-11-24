package scoa;

import java.time.LocalDateTime;
import java.time.LocalDate;

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
