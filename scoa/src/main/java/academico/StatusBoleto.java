package academico;

import java.lang.annotation.Inherited;
import java.util.List;

import javax.annotation.processing.Generated;

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
@Table(name = "StatusBoleto")
public class StatusBoleto {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    
    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private List<Boleto> boletos;
}
