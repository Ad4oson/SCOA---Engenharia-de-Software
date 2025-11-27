package academico.controller;

import academico.model.Aluno;
import jakarta.persistence.EntityManager;

public class AlunoController {

    public Aluno consultarAluno(EntityManager em, Integer alunoId){
        
        String jpql = """
            SELECT a
            FROM Aluno a 
            WHERE a.deleted = false AND a.id = :alunoId
            ORDER BY a.nome
        """;

        return em.createQuery(jpql, Aluno.class).setParameter("alunoId", alunoId)
            .getSingleResult();
    }

    
}
