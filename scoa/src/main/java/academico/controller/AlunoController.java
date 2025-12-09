package academico.controller;

import java.time.LocalDateTime;
import java.util.List;

import academico.model.Aluno;
import academico.model.Feedback;
import academico.model.FrequenciaAluno;
import academico.model.NotaConsultaDTO;
import academico.model.Requisicao;
import academico.model.Sala;
import academico.model.TipoFeedback;
import academico.model.TipoRequisicao;
import academico.model.Turma;
import auth.Permissoes;
import auth.Sessao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AlunoController {

    public Aluno consultarAluno(EntityManager em){

        try {
            Permissoes.exigirAluno();
            String alunoLogin = Sessao.getUsuarioLogado().getLogin();

            String jpql = """
                SELECT a
                FROM Aluno a
                WHERE a.deleted = false AND a.login = :alunoLogin
            """;

            return em.createQuery(jpql, Aluno.class)
                .setParameter("alunoLogin", alunoLogin)
                .getSingleResult();
        }
        catch (Exception e){
            return null;
        }
    }

    public List<FrequenciaAluno> consultarFrequencia(EntityManager em){

        try {
            Permissoes.exigirAluno();
            System.out.println("ALUNO LOGIN:" + Sessao.getUsuarioLogado().getLogin());
            String alunoLogin = Sessao.getUsuarioLogado().getLogin();

            String jpql = """

                SELECT f
                FROM FrequenciaAluno f 
                INNER JOIN f.turma t
                WHERE f.aluno.usuario.login = :alunoLogin AND f.deleted = false
                ORDER BY t.nome ASC, f.created_at DESC
            
                    """;

            return em.createQuery(jpql, FrequenciaAluno.class)
            .setParameter("alunoLogin", alunoLogin)
            .getResultList();
        }
        catch (Exception e){
            e.printStackTrace();
            return List.of();
        }
    }


    public List<Sala> consultarSalasAluno (EntityManager em, String salaLocal){
        //Lembrar de atualizar as outras consultas duplas para serem uma só, utilizando um Id nullable

        if (salaLocal != null) {
            System.out.println("\nPESQUISANDO SALA LOCAL\n");
                
            String jpql = """
                SELECT s
                FROM Sala s
                WHERE s.deleted = false AND s.local = :salaLocal
            """;

            return em.createQuery(jpql, Sala.class)
            .setParameter("salaLocal", salaLocal)
            .getResultList();

        }
        else {

            String jpql = """
            SELECT s
            FROM Sala s
            WHERE s.deleted = false
            """;

            return em.createQuery(jpql, Sala.class)
            .getResultList();
        }
    }


    public List<NotaConsultaDTO> consultarNota(EntityManager em){

        Permissoes.exigirAluno();
        System.out.println("ALUNO LOGIN:" + Sessao.getUsuarioLogado().getLogin());
        String alunoLogin = Sessao.getUsuarioLogado().getLogin();


            String jpql = """
                SELECT DISTINCT new academico.model.NotaConsultaDTO(
                    a.nome,
                    t.nome,
                    d.nome,
                    p1.valor,
                    p2.valor,
                    pf.valor
                )
                FROM Turma t
                JOIN t.disciplina d
                JOIN t.avaliacoes av
                JOIN av.notas n
                JOIN n.aluno a

                LEFT JOIN NotaAluno p1
                    ON p1.aluno = a
                    AND p1.deleted = false
                    AND p1.avaliacao.tipo = 'P1'
                    AND p1.avaliacao.turma = t

                LEFT JOIN NotaAluno p2
                    ON p2.aluno = a
                    AND p2.deleted = false
                    AND p2.avaliacao.tipo = 'P2'
                    AND p2.avaliacao.turma = t

                LEFT JOIN NotaAluno pf
                    ON pf.aluno = a
                    AND pf.deleted = false
                    AND pf.avaliacao.tipo = 'PF'
                    AND pf.avaliacao.turma = t

                WHERE n.deleted = false AND a.usuario.login = :alunoLogin
                ORDER BY t.nome
            """;

            return em.createQuery(jpql, NotaConsultaDTO.class)
                .setParameter("alunoLogin", alunoLogin)
                .getResultList();

        
    }




    //NÃO FEITO
    public String inscreverEmTurma (EntityManager em, String turmaNome){

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            
            String jpqlTurma = """
                    SELECT t
                    FROM Turma t
                    WHERE t.nome = :turmaNome AND t.deleted = false
                    """;
            Turma turmaT = em.createQuery(jpqlTurma, Turma.class).setParameter("turmaNome", turmaNome).getSingleResult();

            System.out.println("TURMA ATUAL: " + turmaT.getNome());
            String loginAtual = Sessao.getUsuarioLogado().getLogin();
            System.out.println("LOGIN ATUAL: " + loginAtual);

            
            String jpqlAluno = """
                    SELECT a
                    FROM Aluno a
                    JOIN a.usuario u
                    WHERE u.login = :loginAtual AND a.deleted = false
                    """;

            Aluno alunoT = em.createQuery(jpqlAluno, Aluno.class).setParameter("loginAtual", loginAtual).getSingleResult();


            for (Aluno a : turmaT.getAlunos()){

                if (a.getCpf().equals(alunoT.getCpf())){
                    System.out.println("Aluno já cadastrado nesta turma!");
                    return "Aluno já cadastrado nesta turma! Cancelando...";
                }
            }


            turmaT.getAlunos().add(alunoT);
            alunoT.getTurmas().add(turmaT);


            tx.commit();

            return "Aluno matriculado na turma com sucesso!";
        }
        catch (Exception e){
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }

        return "Aluno matriculado na turma com sucesso!";

    }


    public List<Turma> consultarTurmas(EntityManager em, String turmaNome){
        
        LocalDateTime dias35 = LocalDateTime.now().minusDays(35);
        if (turmaNome != null) {

            //variavel com data de 35 dias anteriores
            
            

            String jpql = """
                SELECT t
                FROM Turma t
                WHERE t.deleted = false AND t.nome = :turmaNome AND t.created_at > :diasAtras35
                ORDER BY t.nome
            """;


            return  em.createQuery(jpql, Turma.class)
            .setParameter("turmaNome", turmaNome)
            .setParameter("diasAtras35", dias35)
            .getResultList(); 

        }
        else {

            String jpql = """
                SELECT t
                FROM Turma t
                WHERE t.deleted = false AND t.created_at > :diasAtras35
                ORDER BY t.nome
            """;
            return em.createQuery(jpql, Turma.class)
            .setParameter("diasAtras35", dias35)
            .getResultList();
        }


    }


    //#region CRUD Feedback
    public void registrarFeedback(EntityManager em, String texto, TipoFeedback tipoFeedback){

        EntityTransaction tx = em.getTransaction();

        try {
            Permissoes.exigirAluno();
            tx.begin();
            
            String alunoLogin = Sessao.getUsuarioLogado().getLogin();
            System.out.println("\nPEGOU LOGIN: " + alunoLogin);
            String jpql = """
                SELECT a 
                FROM Aluno a
                WHERE a.usuario.login = :login        
            
                    """;
            Aluno alunoT = em.createQuery(jpql, Aluno.class).setParameter("login", alunoLogin).getSingleResult();

            Feedback feedback = new Feedback();
            System.out.println("\nPEGOU ALUNO\n");
            feedback.setTexto(texto);
            feedback.setTipoFeedback(tipoFeedback);
            feedback.setAluno(alunoT);

            feedback.setDeleted(false);
            feedback.setCreated_at(LocalDateTime.now());

            em.persist(feedback);
            tx.commit();
        }
        catch (Exception e){
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }

    }

    public List<Feedback> consultarFeedbacks(EntityManager em, Integer feedbackId) {

       
        Permissoes.exigirAluno(); // deve estar logado como aluno
        String alunoLogin = Sessao.getUsuarioLogado().getLogin();

        
        if (feedbackId != null) {
    
            String jpql = """
                SELECT f 
                FROM Feedback f
                WHERE f.id = :id AND f.aluno.usuario.login = :alunoLogin AND f.deleted = false
                ORDER BY f.id DESC
            """;

            return em.createQuery(jpql, Feedback.class)
                .setParameter("id", feedbackId)
                .setParameter("alunoLogin", alunoLogin)
                .getResultList();
        }
        else {
     
            String jpql = """
                SELECT f 
                FROM Feedback f
                WHERE f.aluno.usuario.login = :alunoLogin AND f.deleted = false
                ORDER BY f.id DESC
            """;

            return em.createQuery(jpql, Feedback.class)
                .setParameter("alunoLogin", alunoLogin)
                .getResultList();
        }

    }

    public void atualizarFeedback(EntityManager em, int feedbackId, String novoTexto, TipoFeedback novoTipo) {

        Permissoes.exigirAluno();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            String alunoLogin = Sessao.getUsuarioLogado().getLogin();

            Feedback feedback = em.find(Feedback.class, feedbackId);

            if (feedback == null)
                throw new IllegalArgumentException("Feedback não encontrado.");

            System.out.println("LoginSessao: " + alunoLogin + " | LoginFeedback: " + feedback.getAluno().getUsuario().getLogin() + " | feedbackId: " + feedback.getId());

            if (!feedback.getAluno().getUsuario().getLogin().equals(alunoLogin)){
                throw new SecurityException("Você não pode alterar um feedback que não é seu.");
            }

            feedback.setTexto(novoTexto);
            feedback.setTipoFeedback(novoTipo);

            tx.commit();
        }
        catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }

    }

    public void excluirFeedback(EntityManager em, int feedbackId) {

        Permissoes.exigirAluno();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            String alunoLogin = Sessao.getUsuarioLogado().getLogin();
            Feedback feedback = em.find(Feedback.class, feedbackId);

            if (feedback == null)
                throw new IllegalArgumentException("Feedback não encontrado.");

            if (feedback.getAluno().getUsuario().getLogin() != alunoLogin)
                throw new SecurityException("Você não pode excluir este feedback.");

            em.remove(feedback);

            tx.commit();
        }
        catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    //#endregion



    //#region CRUD requisicao

    public void registrarRequisicao(EntityManager em, String texto, TipoRequisicao tipo) {

        Permissoes.exigirAluno();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            String alunoLogin = Sessao.getUsuarioLogado().getLogin();

            System.out.println("\nPEGOU LOGIN: " + alunoLogin);
            String jpql = """
                SELECT a 
                FROM Aluno a
                WHERE a.usuario.login = :login        
            
                    """;
            Aluno alunoT = em.createQuery(jpql, Aluno.class).setParameter("login", alunoLogin).getSingleResult();

            Requisicao req = new Requisicao();
            req.setTexto(texto);
            req.setAluno(alunoT);
            req.setTipo(tipo);

            req.setDeleted(false);
            req.setCreated_at(LocalDateTime.now());

            em.persist(req);

            tx.commit();
        }
        catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }


    public List<Requisicao> consultarRequisicoes(EntityManager em, Integer id) {

        Permissoes.exigirAluno();
        String alunoLogin = Sessao.getUsuarioLogado().getLogin();

        if (id != null){

            String jpql = """
            SELECT r
            FROM Requisicao r
            WHERE r.aluno.usuario.login = :alunoLogin AND r.id = :id
            """;

        return em.createQuery(jpql, Requisicao.class)
                .setParameter("alunoLogin", alunoLogin)
                .setParameter("id", id)
                .getResultList();

        }
        else {
            String jpql = """
                SELECT r
                FROM Requisicao r
                WHERE r.aluno.usuario.login = :alunoLogin
                ORDER BY r.id DESC
            """;

            return em.createQuery(jpql, Requisicao.class)
                    .setParameter("alunoLogin", alunoLogin)
                    .getResultList();

        }
    }


    public void atualizarRequisicao(EntityManager em, int id, String novoTexto, TipoRequisicao tipo) {

        Permissoes.exigirAluno();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            String alunoLogin = Sessao.getUsuarioLogado().getLogin();

            // Verifica se a requisição é do aluno
            Requisicao req = em.find(Requisicao.class, id);

            if (req == null || !req.getAluno().getUsuario().getLogin().equals( alunoLogin)) {
                throw new RuntimeException("Requisição não encontrada ou não pertence ao aluno logado.");
            }

            req.setTexto(novoTexto);
            req.setTipo(tipo);

            tx.commit();
        }
        catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }



    public void deletarRequisicao(EntityManager em, int id) {

        Permissoes.exigirAluno();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            String alunoLogin = Sessao.getUsuarioLogado().getLogin();
            Requisicao req = em.find(Requisicao.class, id);

            if (req == null || req.getAluno().getUsuario().getLogin() != alunoLogin) {
                throw new RuntimeException("Requisição não encontrada ou não pertence ao aluno logado.");
            }

            em.remove(req);

            tx.commit();
        }
        catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    //#endregion
    




}
