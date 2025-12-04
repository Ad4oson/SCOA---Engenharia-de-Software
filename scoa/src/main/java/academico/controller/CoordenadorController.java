package academico.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import academico.model.Curso;
import academico.model.Disciplina;
import academico.model.Turma;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;

public class CoordenadorController {

    //#region CRUD Disciplina
    public void cadastrarDisciplina(EntityManager em,
        String nome,
        String ementa,
        Integer carga_horaria,
        Integer creditos,
        String bibliografia,
        Integer disciplina_pre,
        List<Integer> cursos,
        List<Integer> turmas)
    {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Disciplina disciplinaNovo = new Disciplina();
            

            if (disciplina_pre != null) {
                Disciplina disciplinaP = em.getReference(Disciplina.class, disciplina_pre);
                disciplinaNovo.setDisciplinapre(disciplinaP);
            }

            ArrayList<Curso> cursosL = new ArrayList<>();
            if (cursos !=null){
                try {
                    for(Integer id : cursos){
                        Curso curso = em.getReference(Curso.class, id);
                        cursosL.add(curso);
                    }

                    for (Curso c: cursosL) {
                        c.getDisciplinas().add(disciplinaNovo);
                        em.merge(c);
                    }

                }
                catch (EntityNotFoundException e1) {
                    System.out.println("\nCurso não encontrado!\n");
                    tx.rollback();
                    return;
                }
            }

            ArrayList<Turma> turmasL = new ArrayList<>();
            if (turmas !=null){
                try {
                    for(Integer id : turmas){
                        Turma turma = em.getReference(Turma.class, id);
                        turmasL.add(turma);
                    }

                    for (Turma t : turmasL) {
                        t.setDisciplina(disciplinaNovo);
                        em.merge(t);
                    }

                }
                catch (EntityNotFoundException e1) {
                    System.out.println("\nTurma não encontrada!\n");
                    tx.rollback();
                    return;
                }
            }


            disciplinaNovo.setNome(nome);
            disciplinaNovo.setEmenta(ementa);
            disciplinaNovo.setCargaHoraria(carga_horaria);
            disciplinaNovo.setCreditos(creditos);
            disciplinaNovo.setBibliografia(bibliografia);
            disciplinaNovo.setCursos(cursosL);

            disciplinaNovo.setCreated_at(LocalDateTime.now());
            disciplinaNovo.setDeleted(false);

            em.persist(disciplinaNovo);

            tx.commit();
        }
        catch(Exception e){
            if(tx.isActive()) tx.rollback();
            System.out.println("ERRO DENTRO DA CLASSE!!!!");
            
            e.printStackTrace();
        }


    }
    

    public List<Disciplina> consultarDisciplinas(EntityManager em, Integer disciplinaId) {

        if (disciplinaId != null){

            String jpql = """
                SELECT d
                FROM Disciplina d
                WHERE d.deleted = false AND d.id = :disciplinaId
            """;

            return em.createQuery(jpql, Disciplina.class).setParameter("disciplinaId", disciplinaId).getResultList();

        }
        else {

            String jpql = """
                SELECT d
                FROM Disciplina d
                WHERE d.deleted = false
                ORDER BY d.nome
            """;

            return em.createQuery(jpql, Disciplina.class).getResultList();


        }
 
    }



    public void atualizarDisciplina(EntityManager em, int disciplinaId, String nome, String ementa, Integer carga_horaria, Integer creditos,
            String bibliografia, String disciplinaPreNome, List<Integer> cursosId, List<Integer> turmasId) {
        
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Disciplina disciplina = em.find(Disciplina.class, disciplinaId);

            if (nome != null) disciplina.setNome(nome);
            if (ementa != null) disciplina.setEmenta(ementa);
            if (carga_horaria != null) disciplina.setCargaHoraria(carga_horaria);
            if (creditos != null) disciplina.setCreditos(creditos);
            if (bibliografia != null) disciplina.setBibliografia(bibliografia);

            if (disciplinaPreNome != null){

                String jpql = "SELECT d FROM Disciplina d WHERE d.nome = :nome AND d.deleted = false";
                
                Disciplina disciplina_pre = em.createQuery(jpql, Disciplina.class)
                    .setParameter("nome", disciplinaPreNome)
                    .getSingleResult();

                disciplina.setDisciplinapre(disciplina_pre);
            }

            if (cursosId != null){
                ArrayList<Curso> cursos = new ArrayList<>();
                for (Integer id : cursosId){
                    Curso curso = em.find(Curso.class, id);
                    cursos.add(curso);
                }
                disciplina.setCursos(cursos);
            }

            if (turmasId != null){
                ArrayList<Turma> turmas = new ArrayList<>();
                for (Integer id : turmasId){
                    Turma turma = em.find(Turma.class, id);
                    turma.setDisciplina(disciplina);
                    turmas.add(turma);
                }
                disciplina.setTurmas(turmas);
            }


            tx.commit();
        }
        catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }


    public boolean deletarDisciplina(EntityManager em, int id) {
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Disciplina d = em.find(Disciplina.class, id);

            if (d == null || d.isDeleted()) {
                tx.rollback();
                return false;
            }

            d.setDeleted(true);
            em.merge(d);

            tx.commit();
            return true;
        }
        catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }


    //#endregion

}
