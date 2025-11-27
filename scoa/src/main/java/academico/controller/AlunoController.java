package academico.controller;

import java.util.List;

import academico.auth.Permissoes;
import academico.auth.Sessao;
import academico.model.Aluno;
import academico.model.Feedback;
import academico.model.FrequenciaAluno;
import academico.model.NotaAluno;
import academico.model.RequisicaoDocumento;
import academico.model.TipoFeedback;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AlunoController {

    public Aluno consultarAluno(EntityManager em){

        try {
            Permissoes.exigirAluno();
            Integer alunoId = Sessao.getUsuarioLogado().getId();

            String jpql = """
                SELECT a
                FROM Aluno a 
                WHERE a.deleted = false AND a.id = :alunoId
            """;

            return em.createQuery(jpql, Aluno.class)
                .setParameter("alunoId", alunoId)
                .getSingleResult();
        }
        catch (Exception e){
            return null;
        }
    }

    public List<FrequenciaAluno> consultarFrequencia(EntityManager em){

        try {
            Permissoes.exigirAluno();
            Integer alunoId = Sessao.getUsuarioLogado().getId();

            String jpql = """

                SELECT f, d
                FROM FrequenciaAluno f 
                INNER JOIN f.turma t
                INNER JOIN t.disciplina
                WHERE f.aluno.id = :alunoId AND f.deleted = false
                ORDER BY f.data
            
                    """;

            return em.createQuery(jpql, FrequenciaAluno.class)
            .setParameter("alunoId", alunoId)
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

            Integer alunoId = Sessao.getUsuarioLogado().getId();

            String jpql = """
                SELECT n
                FROM NotaAluno n
                INNER JOIN n.avaliacao a
                INNER JOIN a.disciplina d
                WHERE n.deleted = false
                AND n.aluno.id = :alunoId
                ORDER BY d.nome, a.data
            """;

            return em.createQuery(jpql, NotaAluno.class)
                .setParameter("alunoId", alunoId)
                .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


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
            
            Integer alunoId = Sessao.getUsuarioLogado().getId();
            Aluno aluno = em.find(Aluno.class, alunoId);

            Feedback feedback = new Feedback();

            feedback.setTexto(texto);
            feedback.setTipoFeedback(tipoFeedback);
            feedback.setAluno(aluno);

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
        Integer alunoId = Sessao.getUsuarioLogado().getId();

        if (feedbackId != null) {
            String jpql = """
                SELECT f 
                FROM Feedback f
                WHERE f.id = :id AND f.aluno.id = :alunoId
            """;

            return em.createQuery(jpql, Feedback.class)
                .setParameter("id", feedbackId)
                .setParameter("alunoId", alunoId)
                .getResultList();
        }
        else {

            String jpql = """
                SELECT f 
                FROM Feedback f
                WHERE f.aluno.id = :alunoId
            """;

            return em.createQuery(jpql, Feedback.class)
                .setParameter("alunoId", alunoId)
                .getResultList();
        }

    }

    public void atualizarFeedback(EntityManager em, int feedbackId, String novoTexto, TipoFeedback novoTipo) {

        Permissoes.exigirAluno();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Integer alunoId = Sessao.getUsuarioLogado().getId();

            Feedback feedback = em.find(Feedback.class, feedbackId);

            if (feedback == null)
                throw new IllegalArgumentException("Feedback não encontrado.");

            if (feedback.getAluno().getId() != alunoId)
                throw new SecurityException("Você não pode alterar um feedback que não é seu.");

            feedback.setTexto(novoTexto);
            feedback.setTipoFeedback(novoTipo);

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

            Integer alunoId = Sessao.getUsuarioLogado().getId();
            Feedback feedback = em.find(Feedback.class, feedbackId);

            if (feedback == null)
                throw new IllegalArgumentException("Feedback não encontrado.");

            if (feedback.getAluno().getId() != alunoId)
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

    public void registrarRequisicao(EntityManager em, String texto) {

        Permissoes.exigirAluno();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Integer alunoId = Sessao.getUsuarioLogado().getId();
            Aluno aluno = em.find(Aluno.class, alunoId);

            RequisicaoDocumento req = new RequisicaoDocumento();
            req.setTexto(texto);
            req.setAluno(aluno);

            em.persist(req);

            tx.commit();
        }
        catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }


    public List<RequisicaoDocumento> consultarRequisicoes(EntityManager em, Integer id) {

        Permissoes.exigirAluno();
        Integer alunoId = Sessao.getUsuarioLogado().getId();

        if (id != null){

            String jpql = """
            SELECT r
            FROM RequisicaoDocumento r
            WHERE r.aluno.id = :alunoId AND r.id = :id
            """;

        return em.createQuery(jpql, RequisicaoDocumento.class)
                .setParameter("alunoId", alunoId)
                .setParameter("id", id)
                .getResultList();

        }
        else {
            String jpql = """
                SELECT r
                FROM RequisicaoDocumento r
                WHERE r.aluno.id = :alunoId
                ORDER BY r.id DESC
            """;

            return em.createQuery(jpql, RequisicaoDocumento.class)
                    .setParameter("alunoId", alunoId)
                    .getResultList();

        }
    }


    public void atualizarRequisicao(EntityManager em, int id, String novoTexto) {

        Permissoes.exigirAluno();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Integer alunoId = Sessao.getUsuarioLogado().getId();

            // Verifica se a requisição é do aluno
            RequisicaoDocumento req = em.find(RequisicaoDocumento.class, id);

            if (req == null || req.getAluno().getId() != alunoId) {
                throw new RuntimeException("Requisição não encontrada ou não pertence ao aluno logado.");
            }

            req.setTexto(novoTexto);

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

            Integer alunoId = Sessao.getUsuarioLogado().getId();
            RequisicaoDocumento req = em.find(RequisicaoDocumento.class, id);

            if (req == null || req.getAluno().getId() != alunoId) {
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
