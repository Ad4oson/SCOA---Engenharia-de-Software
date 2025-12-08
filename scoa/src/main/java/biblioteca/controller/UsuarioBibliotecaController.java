package biblioteca.controller;

import java.util.List;

import academico.model.JPAUtil;
import biblioteca.model.Obra;
import jakarta.persistence.EntityManager;

public class UsuarioBibliotecaController {

    EntityManager em = JPAUtil.getEntityManager();

    public List<Obra> consultarObra(String titulo) {
        
        
        if (titulo != null) {
            System.out.println("\nPESQUISANDO OBRA\n");
                
            String jpql = """
                SELECT o
                FROM Obra o
                WHERE o.deleted = false AND o.titulo = :obraTitulo
            """;

            return em.createQuery(jpql, Obra.class)
            .setParameter("obraTitulo", titulo)
            .getResultList();

        }
        else {

            String jpql = """
            SELECT o
            FROM Obra o
            WHERE o.deleted = false
            """;

            return em.createQuery(jpql, Obra.class)
            .getResultList();
        }

    }
    
        
    public Obra consultarObraId(Integer id) {
        
    
            System.out.println("\nPESQUISANDO OBRA\n");
                
            String jpql = """
                SELECT o
                FROM Obra o
                WHERE o.deleted = false AND o.id = :obraId
            """;

            return em.createQuery(jpql, Obra.class)
            .setParameter("obraId", id)
            .getSingleResult();


        
    }
    
}
