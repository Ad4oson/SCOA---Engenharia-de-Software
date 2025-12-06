package almoxarifado.controller;

import almoxarifado.model.BaixaBem;
import almoxarifado.model.BemPatrimonial;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BaixaBemController {

    private EntityManager em;

    public void registrarBaixa(Integer bemId, String motivo, LocalDate data_baixa, String responsavel) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            BaixaBem baixa = new BaixaBem();
            BemPatrimonial bem = em.find(BemPatrimonial.class, bemId);

            baixa.setBem(bem);
            baixa.setMotivo(motivo);
            baixa.setData_baixa(data_baixa);
            baixa.setResponsavel(responsavel);
            
            baixa.setCreated_at(LocalDateTime.now());
            baixa.setDeleted(false);
            
            em.persist(baixa);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<BaixaBem> consultarBaixaBem(String bemLocal) {
        
        if (bemLocal != null){

            String jpqlBaixa = """
                    SELECT bb
                    FROM BaixaBem bb
                    JOIN bb.bem bp 
                    WHERE bp.local = :bemLocal AND bb.deleted = false
                    """;

            return em.createQuery(jpqlBaixa, BaixaBem.class).setParameter("bemLocal", bemLocal).getResultList();

        }
        else {

            String jpqlBaixa = """
                    SELECT bb
                    FROM BaixaBem bb
                    JOIN bb.bem bp
                    WHERE bb.deleted = false
                    """;

            return em.createQuery(jpqlBaixa, BaixaBem.class).getResultList();
        }
    }

    public void excluirBaixa (Integer bemId){

        EntityTransaction tx = em.getTransaction();
        try {
            
            tx.begin();
            if (bemId != null){

                BaixaBem baixaBem = em.find(BaixaBem.class, bemId);
                baixaBem.setDeleted(true);
 
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