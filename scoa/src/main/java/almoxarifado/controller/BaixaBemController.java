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

    public List<BaixaBem> listarBaixaBem(String bemLocal) {
        
        if (bemLocal != null){

            String jpqlBaixa = """
                    SELECT bb
                    FROM BaixaBem bb
                    JOIN bb.BemPatrimonial bp
                    """;

            return em.createQuery(jpqlBaixa, BaixaBem.class).setParameter().getResultList();

        }
        else {

            String jpqlBaixa = """
                    SELECT bb
                    FROM BaixaBem bb
                    JOIN bb.BemPatrimonial bp
                    """;

            return em.createQuery(jpqlBaixa, BaixaBem.class).getResultList();
        }


    }
}