package academico.controller;

import java.time.LocalDateTime;
import java.util.List;

import academico.model.Aluno;
import academico.model.Feedback;
import academico.model.FrequenciaAluno;
import academico.model.NotaAluno;
import academico.model.NotaConsultaDTO;
import academico.model.Requisicao;
import academico.model.TipoFeedback;
import academico.model.TipoRequisicao;
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
