package academico.model;

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


   //#region Getters e Setters
   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public float getValor() {
      return valor;
   }

   public void setValor(float valor) {
      this.valor = valor;
   }

   public LocalDate getData_vencimento() {
      return data_vencimento;
   }

   public void setData_vencimento(LocalDate data_vencimento) {
      this.data_vencimento = data_vencimento;
   }

   public String getCodigo_barras() {
      return codigo_barras;
   }

   public void setCodigo_barras(String codigo_barras) {
      this.codigo_barras = codigo_barras;
   }

   public boolean isPago() {
      return pago;
   }

   public void setPago(boolean pago) {
      this.pago = pago;
   }

   public LocalDateTime getCreate_at() {
      return create_at;
   }

   public void setCreate_at(LocalDateTime create_at) {
      this.create_at = create_at;
   }

   public boolean isDeleted() {
      return deleted;
   }

   public void setDeleted(boolean deleted) {
      this.deleted = deleted;
   }

   public StatusBoleto getStatus() {
      return status;
   }

   public void setStatus(StatusBoleto status) {
      this.status = status;
   }

   public List<BoletoPagamento> getPagamentos() {
      return pagamentos;
   }

   public void setPagamentos(List<BoletoPagamento> pagamentos) {
      this.pagamentos = pagamentos;
   }

   public Aluno getAluno() {
      return aluno;
   }

   public void setAluno(Aluno aluno) {
      this.aluno = aluno;
   }

   //#endregion
   
    
}
