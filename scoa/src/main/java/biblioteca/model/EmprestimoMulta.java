package biblioteca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "emprestimomulta")
public class EmprestimoMulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "multa_id")
    private Multa multa;

    @ManyToOne
    @JoinColumn(name = "emprestimo_id")
    private Emprestimo emprestimo;

    // GETTERS & SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Multa getMulta() { return multa; }
    public void setMulta(Multa multa) { this.multa = multa; }

    public Emprestimo getEmprestimo() { return emprestimo; }
    public void setEmprestimo(Emprestimo emprestimo) { this.emprestimo = emprestimo; }
}
