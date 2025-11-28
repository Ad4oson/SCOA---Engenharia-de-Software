package academico.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


public class Coordenador extends Professor{
    //Não necessária integração com JPA, apenas utilizar construtor com base em Professor


    //Construtor base para JPA
    public Coordenador () {}


    // Construtor do Coordenador, utilizando dados de um professor existente
    public Coordenador(Professor professor) {
        // Usar construtor padrao
        super();
        if (professor == null) return;

        // campos do usuario
        setId(professor.getId());
        setNome(professor.getNome());
        setCpf(professor.getCpf());
        setRg(professor.getRg());
        setNascimento(professor.getNascimento());
        setPolo(professor.getPolo());
        setEndereco(professor.getEndereco());
        setCreated_at(professor.getCreated_at());
        setDeleted(professor.isDeleted());

        // campos do professor
        setFormacao(professor.getFormacao());
        setRegistros(professor.getRegistros());
        setDataAdmissao(professor.getDataAdmissao());
        setSalario(professor.getSalario());

        // relações
        setContatos(professor.getContatos());
        setEspecialidades(professor.getEspecialidades());
        setCurso(professor.getCurso());
        setTurmas(professor.getTurmas());

    }


}
