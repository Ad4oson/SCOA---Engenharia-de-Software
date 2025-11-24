package academico;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

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


    public void CadastrarAluno(EntityManager em, String login, String senha, String nome, String cpf,
         String rg, LocalDate nascimento, String endereco, String matricula, int cursoId, int bolsaId){
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Curso curso = em.getReference(Curso.class, cursoId);
            BolsaFinanciamento bolsa = em.getReference(BolsaFinanciamento.class, bolsaId);
            Aluno novoAluno = new Aluno(0, login, senha, TipoUsuario.ALUNO, nome, matricula);
            // preencher campos obrigatórios que vêm do formulário
            novoAluno.setCurso(curso);
            novoAluno.setBolsa(bolsa);
            novoAluno.setCpf(cpf);
            novoAluno.setRg(rg);
            novoAluno.setEndereco(endereco);
            novoAluno.setNascimento(nascimento);
            
            novoAluno.setCreated_at(LocalDateTime.now());
            novoAluno.setDeleted(false);
            em.persist(novoAluno);
            
            tx.commit();
            System.out.println("\n\nAluno cadastrado com sucesso!\n\n");

        } catch (Exception e) { 
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }

    }



    public void CadastrarProfessor(EntityManager em, String login, String senha, String nome, String cpf,
         String rg, LocalDate nascimento, String endereco, String formacao, String registros, LocalDate dataAdmissao){
        
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Professor novoProfessor = new Professor();
            // preencher campos obrigatórios que vêm do formulário
        
            novoProfessor.setLogin(login);
            novoProfessor.setSenha(senha);
            novoProfessor.setNome(nome);
            novoProfessor.setCpf(cpf);
            novoProfessor.setRg(rg);
            novoProfessor.setEndereco(endereco);
            novoProfessor.setNascimento(nascimento);
            novoProfessor.setFormacao(formacao);
            novoProfessor.setRegistros(registros);
            novoProfessor.setDataAdmissao(dataAdmissao);
            novoProfessor.setTipoUsuario(TipoUsuario.PROFESSOR);
            
            novoProfessor.setCreated_at(LocalDateTime.now());
            novoProfessor.setDeleted(false);
            em.persist(novoProfessor);
            
            tx.commit();
            System.out.println("\n\nProfessor cadastrado com sucesso!\n\n");

        } catch (Exception e) { 
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }

    }

}