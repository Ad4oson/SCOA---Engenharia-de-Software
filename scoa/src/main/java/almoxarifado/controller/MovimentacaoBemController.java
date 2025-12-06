package almoxarifado.controller;

import almoxarifado.model.BaixaBem;
import almoxarifado.model.BemPatrimonial;
import almoxarifado.model.MovimentacaoBem;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
public class MovimentacaoBemController {

    private EntityManager em;

        private BemPatrimonial bem;

    private String setor_origem;

    private String setor_destino;

    private LocalDate data_movimentacao;

    private LocalDateTime created_at;

    private boolean deleted;


    public void cadastrarMovimentacao(Integer bemId, String setor_origem, String setor_destino, LocalDate data_movimentacao) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            MovimentacaoBem movBem = new MovimentacaoBem();
            BemPatrimonial bemMovido = em.find(BemPatrimonial.class, bemId);

            if (setor_origem != null) movBem.setSetor_origem(setor_origem);
            if (setor_destino != null) movBem.setSetor_destino(setor_destino);
            if (data_movimentacao != null) movBem.setData_movimentacao(data_movimentacao);

            movBem.setCreated_at(LocalDateTime.now());
            movBem.setDeleted(false);

            movBem.setBem(bemMovido);

            em.persist(movBem);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<MovimentacaoBem> consultarMovimentacao(String localBem) {

        if (localBem != null){

             String jpqlMovimentacao = """
                    SELECT mv
                    FROM MovimentacaoBem mv
                    JOIN mv.bem bp
                    WHERE  bp.local = :localBem AND mv.deleted = false
                    """;

            return em.createQuery(jpqlMovimentacao, MovimentacaoBem.class).setParameter("localBem", localBem).getResultList();

        }
        else {
            String jpqlMovimentacao = """
                    SELECT mv
                    FROM MovimentacaoBem mv
                    JOIN mv.bem bp
                    WHERE mv.deleted = false
                    """;

            return em.createQuery(jpqlMovimentacao, MovimentacaoBem.class).getResultList();
        }

    }

    public void excluirMovimentacao (Integer bemId){

            EntityTransaction tx = em.getTransaction();
            try {
                
                tx.begin();
                if (bemId != null){

                    String jpqlMovimentacao = """
                            SELECT mv
                            FROM MovimentacaoBem mv
                            JOIN mv.bem b
                            WHERE b.id = :id AND mv.deleted = false
                            """;

                    MovimentacaoBem movimentacaoBem = em.createQuery(jpqlMovimentacao, MovimentacaoBem.class).setParameter("id", bemId).getSingleResult();
                    movimentacaoBem.setDeleted(true);
    
                }
                else {
                    System.out.println("Id nulo, exclusão não realizada!");
                    throw new RuntimeException("Id nulo, exclusão não realizada!");
                }

                tx.commit();
            }
            catch (Exception e){
                e.printStackTrace();
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Falha na exclusão", e);
        
            }


    }



}
