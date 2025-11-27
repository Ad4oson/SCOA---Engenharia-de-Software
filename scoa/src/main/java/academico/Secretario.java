package academico;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

import academico.Turma;

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
import jakarta.persistence.JoinTable;
import jakarta.persistence.CascadeType;

public class Secretario extends Aluno{

    
    //Construtor basico para testes
    public Secretario(){} 

    public Secretario(Aluno secretario){

        super();
        if (secretario == null) return;
        
        setId(secretario.getId());
        setLogin(secretario.getLogin());
        setSenha(secretario.getSenha());
        setTipoUsuario(secretario.getTipoUsuario());
        setNome(secretario.getNome());
        setMatricula(secretario.getMatricula());
        setStatusfinanceiro(secretario.getStatusfinanceiro());
        setCpf(secretario.getCpf());
        setRg(secretario.getRg());
        setNascimento(secretario.getNascimento());
        setPolo(secretario.getPolo());
        setEndereco(secretario.getEndereco());
        setCreated_at(secretario.getCreated_at());
        setDeleted(secretario.isDeleted());
        
    }

    //Construtor total
    public Secretario(int id, String login, String senha, TipoUsuario tipoUsuario, String nome, String cpf, String rg,
    LocalDate nascimento, String polo, String endereco, LocalDateTime created_at, boolean deleted) {

        setId(id);
        setLogin(login);
        setSenha(senha);
        setTipoUsuario(tipoUsuario);
        setNome(nome);
        setCpf(cpf);
        setRg(rg);
        setNascimento(nascimento);
        setPolo(polo);
        setEndereco(endereco);
        setCreated_at(created_at);
        setDeleted(deleted);
    }


 


}