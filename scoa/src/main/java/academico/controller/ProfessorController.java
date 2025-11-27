package academico.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import academico.model.Aluno;
import academico.model.Avaliacao;
import academico.model.FrequenciaAluno;
import academico.model.NotaAluno;
import academico.model.NotaConsultaDTO;
import academico.model.PautaDeAula;
import academico.model.Turma;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


public class ProfessorController {

     public void lancarPauta(
        EntityManager em,
        Integer turmaId,
        LocalDate data,
        String conteudo,
        String atividades,
        String observacoes
    ){
        EntityTransaction tx = em.getTransaction();
        PautaDeAula pauta = new PautaDeAula();
        try {
            tx.begin();

            Turma turma = em.getReference(Turma.class, turmaId);

            pauta.setData(data);
            pauta.setConteudo(conteudo);
            pauta.setAtividades(atividades);
            pauta.setObservacoes(observacoes);
            pauta.setTurma(turma);

            pauta.setCreated_at(LocalDateTime.now());
            pauta.setDeleted(false);
            em.persist(pauta);
            tx.commit();
        }
        catch(Exception e){
            if(tx.isActive()) tx.rollback();
            e.printStackTrace();
            
        }

        
    }

    public List<PautaDeAula> consultarPautas(EntityManager em, Integer turmaId) {

        String jpql = "SELECT p FROM PautaDeAula p WHERE p.turma.id = :turmaId AND p.deleted = false ORDER BY p.data";

    return em.createQuery(jpql, PautaDeAula.class)
             .setParameter("turmaId", turmaId)
             .getResultList();
    }


    public void atualizarPauta(EntityManager em, int pautaId, String novoConteudo, String novasAtividades,
                               String novasObservacoes) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            PautaDeAula pauta = em.find(PautaDeAula.class, pautaId);
            if (pauta == null)
                throw new EntityNotFoundException("Pauta não encontrada");

            if (novoConteudo != null) pauta.setConteudo(novoConteudo);
            if (novasAtividades != null) pauta.setAtividades(novasAtividades);
            if (novasObservacoes != null) pauta.setObservacoes(novasObservacoes);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    //Um soft delete
    public void deletarPauta(EntityManager em, int pautaId) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            PautaDeAula pauta = em.find(PautaDeAula.class, pautaId);
            if (pauta == null) throw new EntityNotFoundException("Pauta não encontrada");

            pauta.setDeleted(true);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }


    
    public void lancarFrequencia(EntityManager em, Integer turmaId, Integer alunoId, 
        LocalDate data, String justificativa, int tempoDeAula, boolean presente){


        EntityTransaction tx = em.getTransaction();
        FrequenciaAluno frequencia = new FrequenciaAluno();
        try {
            tx.begin();

            Turma turma = em.getReference(Turma.class, turmaId);
            Aluno aluno = em.getReference(Aluno.class, alunoId);

            frequencia.setPresente(presente);
            frequencia.setJustificativa(justificativa);
            frequencia.setTempo_de_aula(tempoDeAula);
            frequencia.setData(data);
            frequencia.setAluno(aluno);
            frequencia.setTurma(turma);

            frequencia.setCreated_at(LocalDateTime.now());
            frequencia.setDeleted(false);


            em.persist(frequencia);
            tx.commit();
        }
        catch(Exception e){
            if(tx.isActive()) tx.rollback();
            e.printStackTrace();
            
        }



    }


    public List<FrequenciaAluno> consultarFrequencia(EntityManager em, Integer turmaId){
        
        String jpql = """
            SELECT f.aluno.id,
                   f.aluno.nome,
                   f.presente,
                   f.data 
            FROM FrequenciaAluno f 
            WHERE f.turma.id = :turmaId AND f.deleted = false
            GROUP BY f.aluno.id, f.aluno.nome
            ORDER BY f.aluno.nome
        """;

        return em.createQuery(jpql, FrequenciaAluno.class)
            .setParameter("turmaId", turmaId)
            .getResultList();
    }
    

    public void atualizarFrequencia(EntityManager em, int frequenciaId, String novaJustificativa, boolean presente, LocalDate data) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            FrequenciaAluno frequencia = em.find(FrequenciaAluno.class, frequenciaId);
            if (frequencia == null)
                throw new EntityNotFoundException("Frequência não encontrada");

            if (novaJustificativa != null) frequencia.setJustificativa(novaJustificativa);
            frequencia.setPresente(presente);
            if (data != null) frequencia.setData(data);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }


    //Um soft delete
    public void deletarFrequencia(EntityManager em, int frequenciaId) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            FrequenciaAluno frequencia = em.find(FrequenciaAluno.class, frequenciaId);
            if (frequencia == null) throw new EntityNotFoundException("Frequência não encontrada");

            frequencia.setDeleted(true);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }



    public void lancarNota(EntityManager em,
                            float valor,
                            String observacao,
                            int alunoId,
                            int avalicaoId){


        
        EntityTransaction tx = em.getTransaction();
        NotaAluno nota = new NotaAluno();
        try {
            tx.begin();

            Aluno aluno = em.getReference(Aluno.class, alunoId);
            Avaliacao avaliacao = em.getReference(Avaliacao.class, avalicaoId);

            nota.setValor(valor);
            nota.setObservacao(observacao);
            
            nota.setAluno(aluno);
            nota.setAvaliacao(avaliacao);

            nota.setCreated_at(LocalDateTime.now());
            nota.setDeleted(false);

            em.persist(nota);
            tx.commit();
        }
        catch(Exception e){
            if(tx.isActive()) tx.rollback();
            e.printStackTrace();
            
        }

                        
    


    }


    public List<NotaConsultaDTO> consultarNota(EntityManager em, Integer turmaId){

        String jpql = """
                SELECT n.aluno.nome,
                       n.avaliacao.tipo,
                       n.valor as nota,

                       (SELECT AVG(n2.valor)
                        FROM NotaAluno n2
                        WHERE n0.aluno.id = n2.aluno.id AND n.turma.id = n2.turma.id AND n2.deleted = false) as media
                FROM NotaAluno n
                WHERE n.turma.id = :turmaId AND n.deleted = false
                ORDER BY n.aluno.nome

                """;


        return em.createQuery(jpql, NotaConsultaDTO.class)
            .setParameter("turmaId", turmaId)
            .getResultList();
    }

    
    public void atualizarNota(EntityManager em, Integer valor, String observacao, int notaId) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            NotaAluno nota = em.find(NotaAluno.class, notaId);
            if (nota == null)
                throw new EntityNotFoundException("Nota não encontrada");

            if (valor != null) nota.setValor(valor);
            if (observacao != null) nota.setObservacao(observacao);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }


    public void deletarNota(EntityManager em, int notaId){

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            NotaAluno nota = em.find(NotaAluno.class, notaId );
            if (nota == null) throw new EntityNotFoundException("Nota não encontrada");

            nota.setDeleted(true);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }

    }

}
