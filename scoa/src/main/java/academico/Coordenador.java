package academico;

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

public class Coordenador extends Professor{
    //Não necessária integração com JPA, apenas utilizar construtor com base em Professor


    //Construtor base para JPA
    public Coordenador () {}


    // Construtor do Coordenador, utilizando dados de um professor existente
    public Coordenador(Professor professor) {
        // Use default superclass constructor then copy properties via setters
        super();
        if (professor == null) return;

        // Usuario fields
        setId(professor.getId());
        setLogin(professor.getLogin());
        setSenha(professor.getSenha());
        setTipoUsuario(professor.getTipoUsuario());
        setNome(professor.getNome());
        setCpf(professor.getCpf());
        setRg(professor.getRg());
        setNascimento(professor.getNascimento());
        setPolo(professor.getPolo());
        setEndereco(professor.getEndereco());
        setCreated_at(professor.getCreated_at());
        setDeleted(professor.isDeleted());

        // Professor fields
        setFormacao(professor.getFormacao());
        setRegistros(professor.getRegistros());
        setDataAdmissao(professor.getDataAdmissao());
        setSalario(professor.getSalario());

        // Collections / relations
        setContatos(professor.getContatos());
        setEspecialidades(professor.getEspecialidades());
        setCurso(professor.getCurso());
        setTurmas(professor.getTurmas());

    }
}
