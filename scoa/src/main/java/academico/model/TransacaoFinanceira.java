package academico.model;


import java.time.LocalDateTime;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "transacaofinanceira")
public class TransacaoFinanceira {

    private int id;
    private float valor;
    private LocalDateTime data_transacao;
  

    private String origem;
    private String destinatario;
    private String descricao;
    private LocalDateTime created_at;
    private boolean deleted;

    @Id
    @OneToOne
    @JoinColumn(name = "pagamento_id")
    private BoletoPagamento pagamento;
    

    @ManyToMany(mappedBy = "transacoes")
    private List<CentroCusto> centros;


 
    //#region getters e setters
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


    public LocalDateTime getData_transacao() {
        return data_transacao;
    }


    public void setData_transacao(LocalDateTime data_transacao) {
        this.data_transacao = data_transacao;
    }


    public String getOrigem() {
        return origem;
    }


    public void setOrigem(String origem) {
        this.origem = origem;
    }


    public String getDestinatario() {
        return destinatario;
    }


    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }


    public String getDescricao() {
        return descricao;
    }


    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public LocalDateTime getCreated_at() {
        return created_at;
    }


    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }


    public boolean isDeleted() {
        return deleted;
    }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    public BoletoPagamento getPagamento() {
        return pagamento;
    }


    public void setPagamento(BoletoPagamento pagamento) {
        this.pagamento = pagamento;
    }


    public List<CentroCusto> getCentros() {
        return centros;
    }


    public void setCentros(List<CentroCusto> centros) {
        this.centros = centros;
    }




    //#endregion


}
