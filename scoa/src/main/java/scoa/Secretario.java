package main.java.scoa;

import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class Secretario extends Aluno{



    //Construtor total
    public Secretario(int id, String login, String senha, TipoUsuario tipoUsuario, String nome, String cpf, String rg,
    String nascimento, String polo, String endereco, LocalDateTime created_at, boolean deleted) {

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


    public void CadastrarAluno(EntityManager em, int id, String login, String senha, TipoUsuario tipoUsuario, String nome, String matricula){
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Aluno novoAluno = new Aluno(id, login, senha, tipoUsuario, nome, matricula, getCreated_at(), isDeleted());
            em.persist(novoAluno);
            
            tx.commit();
            System.out.println("\n\nAluno cadastrado com sucesso!\n\n");

        } catch (Exception e) {
            if (tx.isactive()) tx.rollback();
            e.printStackTrace();
        }

    }

}