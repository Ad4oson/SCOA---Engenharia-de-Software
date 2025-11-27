package academico.controller;

import java.util.List;

import academico.model.Aluno;
import academico.model.FrequenciaAluno;
import academico.model.TipoFeedback;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AlunoController {

    public Aluno consultarAluno(EntityManager em){

        String jpql = """
            SELECT a
            FROM Aluno a 
            WHERE a.deleted = false
        """;

        return em.createQuery(jpql, Aluno.class)
            .getSingleResult();
    }

    public List<FrequenciaAluno> consultarFrequencia(EntityManager em, int alunoCpf){

        String jpql = """

            SELECT f
            FROM FrequenciaAluno f 
            WHERE f.aluno.cpf = :alunoCpf AND f.deleted = false
            ORDER BY data
        
                """;

        return em.createQuery(jpql, FrequenciaAluno.class)
        .setParameter("alunoCpf", alunoCpf)
        .getResultList();
    }

    public void registrarFeedback(EntityManager em, String texto, TipoFeedback tipoFeedback){

        EntityTransaction tx = em.getTransaction();

        


    }

    
}
