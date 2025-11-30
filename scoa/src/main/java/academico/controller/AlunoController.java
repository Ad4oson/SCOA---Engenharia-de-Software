package academico.controller;

import java.util.List;

import academico.auth.Permissoes;
import academico.auth.Sessao;
import academico.model.Aluno;
import academico.model.Feedback;
import academico.model.FrequenciaAluno;
import academico.model.NotaAluno;
import academico.model.Requisicao;
import academico.model.TipoFeedback;
import academico.model.TipoRequisicao;
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
            String alunoLogin = Sessao.getUsuarioLogado().getLogin();

            String jpql = """

                SELECT f, d
                FROM FrequenciaAluno f 
                INNER JOIN f.turma t
                INNER JOIN t.disciplina
                WHERE f.aluno.login = :alunoLogin AND f.deleted = false
                ORDER BY f.data
            
                    """;

            return em.createQuery(jpql, FrequenciaAluno.class)
            .setParameter("alunoLogin", alunoLogin)
            .getResultList();
        }
        catch (Exception e){
            return null;
        }
    }


    public List<NotaAluno> consultarNotas(EntityManager em) {
        try {
            // Garante que apenas alunos acessem
            Permissoes.exigirAluno();

            String alunoLogin = Sessao.getUsuarioLogado().getLogin();

            String jpql = """
                SELECT n
                FROM NotaAluno n
                INNER JOIN n.avaliacao a
                INNER JOIN a.disciplina d
                WHERE n.deleted = false
                AND n.aluno.login = :alunoLogin
                ORDER BY d.nome, a.data
            """;

            return em.createQuery(jpql, NotaAluno.class)
                .setParameter("alunoLogin", alunoLogin)
                .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //NÃO FEITO
    public void inscreverEmTurma (EntityManager em, String disciplinaNome){

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            




            tx.commit();
        }
        catch (Exception e){
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
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
                WHERE f.id = :id AND f.aluno.usuario.login = :alunoLogin
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
                WHERE f.aluno.usuario.login = :alunoLogin
                ORDER BY f.id DESC
            """;

            return em.createQuery(jpql, Feedback.class)
                .setParameter("alunoLogin", alunoLogin)
                .getResultList();
        }

    }

    public void atualizarFeedback(EntityManager em, int feedbackId, String novoTexto, TipoFeedback novoTipo, Boolean deleted) {

        Permissoes.exigirAluno();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            String alunoLogin = Sessao.getUsuarioLogado().getLogin();

            Feedback feedback = em.find(Feedback.class, feedbackId);

            if (feedback == null)
                throw new IllegalArgumentException("Feedback não encontrado.");

            if (feedback.getAluno().getUsuario().getLogin() != alunoLogin)
                throw new SecurityException("Você não pode alterar um feedback que não é seu.");

            feedback.setTexto(novoTexto);
            feedback.setTipoFeedback(novoTipo);

            if (deleted != null) feedback.setDeleted(deleted);

            em.merge(feedback);

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


    public void atualizarRequisicao(EntityManager em, int id, String novoTexto, TipoRequisicao tipo, Boolean deleted) {

        Permissoes.exigirAluno();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            String alunoLogin = Sessao.getUsuarioLogado().getLogin();

            // Verifica se a requisição é do aluno
            Requisicao req = em.find(Requisicao.class, id);

            if (req == null || req.getAluno().getUsuario().getLogin() != alunoLogin) {
                throw new RuntimeException("Requisição não encontrada ou não pertence ao aluno logado.");
            }

            req.setTexto(novoTexto);
            req.setTipo(tipo);
            req.setDeleted(deleted);

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
