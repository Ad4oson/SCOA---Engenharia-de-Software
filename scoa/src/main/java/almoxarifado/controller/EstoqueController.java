package almoxarifado.controller;

import java.time.LocalDateTime;
import java.util.List;

import almoxarifado.model.Estoque;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class EstoqueController {

    private EntityManager em;



    // CREATE
    public void criarEstoque(Integer estoque_min, Integer estoque_max, String local, String codigo_barras) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Estoque estoque = new Estoque();

        if (estoque_min != null)estoque.setEstoque_min(estoque_min);
        if (estoque_max != null) estoque.setEstoque_max(estoque_max);
        if (local != null) estoque.setLocalFisico(local);
        if (codigo_barras != null) estoque.setCodigo_barras(codigo_barras);

        estoque.setCreated_at(LocalDateTime.now());
        estoque.setDeleted(false);

        em.persist(estoque);
        tx.commit();
    }

    // READ
    public List<Estoque> consultarEstoque(String local_fisico) {

        if (local_fisico != null){

            String jpqlEstoque = """
                    SELECT e
                    FROM Estoque e
                    WHERE e.local_fisico = :local AND e.deleted = false
                    """;
            return em.createQuery(jpqlEstoque, Estoque.class).setParameter("local", local_fisico).getResultList();

        }
        else {

            String jpqlEstoque = """
                    SELECT e
                    FROM Estoque e
                    WHERE e.deleted = false
                    """;

            return em.createQuery(jpqlEstoque, Estoque.class).getResultList();

        }
        
    }




    // UPDATE
    public void atualizarEstoque(Integer estoqueId, Integer estoque_min, Integer estoque_max, String local, String codigo_barras) {
        if (estoqueId == null)
            throw new RuntimeException("Id nulo! Não é possível atualizar.");
        EntityTransaction tx = em.getTransaction();
        try {

        
            tx.begin();

            Estoque estoque = em.find(Estoque.class, estoqueId);

            if (estoque_min != null)estoque.setEstoque_min(estoque_min);
            if (estoque_max != null) estoque.setEstoque_max(estoque_max);
            if (local != null) estoque.setLocalFisico(local);
            if (codigo_barras != null) estoque.setCodigo_barras(codigo_barras);

            tx.commit();
        }
        catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    // DELETE (soft delete)
    public void deletarEstoque(Integer estoqueId) {
        Estoque estoque = em.find(Estoque.class, estoqueId);

        if (estoque == null)
            throw new RuntimeException("Estoque não encontrado!");

        em.getTransaction().begin();
        estoque.setDeleted(true);
        
        
        em.getTransaction().commit();
    }


    
}
