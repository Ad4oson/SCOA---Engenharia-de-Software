package biblioteca.controller;

import java.time.LocalDateTime;

import academico.model.JPAUtil;
import biblioteca.model.Obra;
import jakarta.persistence.EntityManager;

public class BibliotecarioController {

    EntityManager em = JPAUtil.getEntityManager();

/* 
    //#region CRUD Obra

    public void criarObra(String titulo, String autor, String tipomaterial, String editora, String anoPublicacao,
         String localizacao, String status) {
        em.getTransaction().begin();
        Obra obra = new Obra();
        
        if (titulo != null)
        

        obra.setCreated_at(LocalDateTime.now());
        obra.setDeleted(false);

        em.persist(obra);
        em.getTransaction().commit();
    }

    public Obra buscarObra(int id) {
        return em.find(Obra.class, id);
    }

    public List<Obra> listarObras() {
        return em.createQuery("SELECT o FROM Obra o", Obra.class).getResultList();
    }

    public void atualizarObra(Obra o) {
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
    }

    public void deletarObra(int id) {
        Obra o = buscar(id);
        if (o != null) {
            em.getTransaction().begin();
            em.remove(o);
            em.getTransaction().commit();
        }
    }

    //#endregion

    */
}
