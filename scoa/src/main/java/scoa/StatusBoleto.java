package main.java.scoa;

@Entity
@Table(name = "StatusBoleto")
public class StatusBoleto {

    private int id;
    private String nome;
    
    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private List<Boleto> boletos;
}
