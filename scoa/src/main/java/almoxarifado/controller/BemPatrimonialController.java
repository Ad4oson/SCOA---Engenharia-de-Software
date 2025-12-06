package almoxarifado.controller;

import java.time.LocalDate;
import java.util.List;

import academico.model.JPAUtil;
import almoxarifado.model.BaixaBem;
import almoxarifado.model.BemPatrimonial;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class BemPatrimonialController {

    EntityManager em = JPAUtil.getEntityManager();

    public void cadastrarBem(String nome, LocalDate data_cadastro, String status_bem, String setor_atual, String local) {
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            BemPatrimonial bemNovo = new BemPatrimonial();

            if (nome != null) bemNovo.setNome(nome);
            if (data_cadastro != null) bemNovo.setData_cadastro(data_cadastro);
            if (status_bem != null) bemNovo.setStatus_bem(status_bem);
            if (setor_atual != null) bemNovo.setSetor_atual(setor_atual);
            if (local != null) bemNovo.setLocal(local);

            bemNovo.setCreated_at(LocalDate.now());
            bemNovo.setDeleted(false);

            em.persist(bemNovo);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<BemPatrimonial> consultarBem(String setor) {

        if (setor != null){

            String jpqlBem = """
                    SELECT b
                    FROM BemPatrimonial b
                    WHERE b.setor_atual = :setor AND b.deleted = false
                    """;
            return em.createQuery(jpqlBem, BemPatrimonial.class).setParameter("setor", setor).getResultList();

        }
        else {
            String jpqlBem = """
                    SELECT b
                    FROM BemPatrimonial b
                    WHERE b.deleted = false
                    """;
            return em.createQuery(jpqlBem, BemPatrimonial.class).getResultList();
        }

    }


    public void atualizarBem(Integer bemId, String nome, LocalDate data_cadastro, String status_bem, String setor_atual, 
        String local) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            BemPatrimonial bemNovo = em.find(BemPatrimonial.class, bemId);
            
            
            if (nome != null) bemNovo.setNome(nome);
            if (data_cadastro != null) bemNovo.setData_cadastro(data_cadastro);
            if (status_bem != null) bemNovo.setStatus_bem(status_bem);
            if (setor_atual != null) bemNovo.setSetor_atual(setor_atual);
            if (local != null) bemNovo.setLocal(local);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }



    public void excluirBem (Integer bemId){

        EntityTransaction tx = em.getTransaction();
        try {
            
            tx.begin();
            if (bemId != null){

                BemPatrimonial bem = em.find(BemPatrimonial.class, bemId);
                bem.setDeleted(true);
 
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
