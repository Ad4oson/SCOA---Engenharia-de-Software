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

    private int verificarAluno(EntityManager em, String login, String matricula){
        //Verifica se já existe login ou matricula do aluno no BD


        String jpqlLogin = "SELECT COUNT(a) FROM Aluno a WHERE a.login = :login";
        String jpqlMatricula =  "SELECT COUNT(a) FROM Aluno a WHERE a.matricula = :matricula";

        Long countLogin = em.createQuery(jpqlLogin, Long.class)
                        .setParameter("login", login)
                        .getSingleResult();

        if (countLogin > 0) return 1; // Já existe Login

        Long countMatricula = em.createQuery(jpqlMatricula, Long.class)
                        .setParameter("matricula", matricula)
                        .getSingleResult();
        if (countMatricula > 0 ) return 2; // Já existe matrícula

        return 0; // Nenhum dado duplicado no BD
    }

    public void cadastrarAluno(EntityManager em, int id, String login, String senha, TipoUsuario tipoUsuario, String nome, String matricula){
        EntityTransaction tx = em.getTransaction();

        try {

            int existeAluno = verificarAluno(em, login, matricula);

            if (existeAluno == 1) {
                System.out.println("\n\nJá existe um aluno com mesmo login!\n\n");
                return;
            }
            else if (existeAluno == 2) {
                System.out.println("\n\nJá existe um aluno com a mesma matrícula\n\n");
                return;
            }


            tx.begin();

            Aluno novoAluno = new Aluno(id, login, senha, tipoUsuario, nome, matricula);
            novoAluno.setCreated_at(LocalDateTime.now());
            novoAluno.setDeleted(false);

            em.persist(novoAluno);
            
            tx.commit();
            System.out.println("\n\nAluno cadastrado com sucesso!\n\n");

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.out.println("\nHouve um erro durante o cadastro, tente novamente!\n");
            e.printStackTrace();
        }

    }


    public void cadastrarProfessor(EntityManager em, int id, String login, String senha, TipoUsuario tipoUsuario, 
    String nome) {


    }


}