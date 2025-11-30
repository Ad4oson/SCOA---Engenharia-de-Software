package academico.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import academico.auth.Sessao;
import academico.model.Aluno;
import academico.model.Avaliacao;
import academico.model.FrequenciaAluno;
import academico.model.NotaAluno;
import academico.model.NotaConsultaDTO;
import academico.model.PautaDeAula;
import academico.model.TipoAvaliacao;
import academico.model.Turma;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


public class ProfessorController {

     public void lancarPauta(
        EntityManager em,
        String turma,
        LocalDate data,
        String conteudo,
        String atividades,
        String observacoes
    ){
        EntityTransaction tx = em.getTransaction();
        PautaDeAula pauta = new PautaDeAula();
        try {
            tx.begin();
            System.out.println("\nEntrou na QUERY\n");
            Turma turmaT = em.createQuery(
                    "SELECT DISTINCT t FROM Turma t WHERE t.nome = :turma", Turma.class)
                    .setParameter("turma", turma)
                    .getSingleResult();
            System.out.println("\nPASSOU DA QUERY\n");


            pauta.setData(data);
            pauta.setConteudo(conteudo);
            pauta.setAtividades(atividades);
            pauta.setObservacoes(observacoes);
            pauta.setTurma(turmaT);

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

    public List<PautaDeAula> consultarPautas(EntityManager em, String turma) {

        if (turma != null){
            String jpql = "SELECT p FROM PautaDeAula p WHERE p.turma.nome = :turma AND p.deleted = false ORDER BY p.turma.nome";

            return em.createQuery(jpql, PautaDeAula.class)
                .setParameter("turma", turma)
                .getResultList();
        }
        else {
            String jpql = "SELECT p FROM PautaDeAula p WHERE p.deleted = false ORDER BY p.turma.nome";

            return em.createQuery(jpql, PautaDeAula.class)
             .getResultList();
        }
        
    }


    public void atualizarPauta(EntityManager em, int pautaId, String novoConteudo, String novasAtividades,
                               String novasObservacoes, Boolean deleted) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            PautaDeAula pauta = em.find(PautaDeAula.class, pautaId);
            if (pauta == null)
                throw new EntityNotFoundException("Pauta não encontrada");

            if (novoConteudo != null) pauta.setConteudo(novoConteudo);
            if (novasAtividades != null) pauta.setAtividades(novasAtividades);
            if (novasObservacoes != null) pauta.setObservacoes(novasObservacoes);
            if (deleted != null) pauta.setDeleted(deleted);

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


    public List<FrequenciaAluno> consultarFrequencia(EntityManager em, LocalDate data){

          
        String login = Sessao.getUsuarioLogado().getLogin();
        
        if (data != null ){

            String jpql = """
                SELECT f
                FROM FrequenciaAluno f
                JOIN f.aluno a
                JOIN f.turma t
                JOIN t.disciplina d
                JOIN t.professor p
                WHERE f.data = :data AND p.usuario.login = :login  AND f.deleted = false
                ORDER BY t.nome, a.nome
            """;

            return em.createQuery(jpql, FrequenciaAluno.class)
                .setParameter("data", data)
                .setParameter("login", login)
                .getResultList();

        }
        else {

            String jpql = """
                SELECT f
                FROM FrequenciaAluno f
                JOIN f.aluno a
                JOIN f.turma t
                JOIN t.disciplina d
                JOIN t.professor p 
                WHERE p.usuario.login = :login AND f.deleted = false
                ORDER BY t.nome, a.nome
            """;

            return em.createQuery(jpql, FrequenciaAluno.class)
                .setParameter("login", login)
                .getResultList();


        }

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
                            Double valor,
                            int alunoId,
                            int avalicaoId){


        
        EntityTransaction tx = em.getTransaction();
        NotaAluno nota = new NotaAluno();
        try {
            tx.begin();

            Aluno aluno = em.getReference(Aluno.class, alunoId);
            Avaliacao avaliacao = em.getReference(Avaliacao.class, avalicaoId);

            
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


    public List<NotaConsultaDTO> consultarNota(EntityManager em, String turma){

        if (turma != null){
            String jpql = """
                SELECT DISTINCT new academico.model.NotaConsultaDTO(
                    a.nome,
                    t.nome,
                    d.nome,
                    p1.valor,
                    p2.valor,
                    pf.valor
                )
                FROM Turma t
                JOIN t.disciplina d
                JOIN t.avaliacoes av
                JOIN av.notas n
                JOIN n.aluno a

                LEFT JOIN NotaAluno p1
                    ON p1.aluno = a
                    AND p1.deleted = false
                    AND p1.avaliacao.tipo = 'P1'
                    AND p1.avaliacao.turma = t

                LEFT JOIN NotaAluno p2
                    ON p2.aluno = a
                    AND p2.deleted = false
                    AND p2.avaliacao.tipo = 'P2'
                    AND p2.avaliacao.turma = t

                LEFT JOIN NotaAluno pf
                    ON pf.aluno = a
                    AND pf.deleted = false
                    AND pf.avaliacao.tipo = 'PF'
                    AND pf.avaliacao.turma = t

                WHERE n.deleted = false AND t.nome = :turma
                ORDER BY t.nome, a.nome
            """;

            return em.createQuery(jpql, NotaConsultaDTO.class)
                .setParameter("turma", turma)
                .getResultList();

        }
        else {

            String jpql = """
                SELECT DISTINCT new academico.model.NotaConsultaDTO(
                    a.nome,
                    t.nome,
                    d.nome,
                    p1.valor,
                    p2.valor,
                    pf.valor
                )
                FROM Turma t
                JOIN t.disciplina d
                JOIN t.avaliacoes av
                JOIN av.notas n
                JOIN n.aluno a

                LEFT JOIN NotaAluno p1
                    ON p1.aluno = a
                    AND p1.deleted = false
                    AND p1.avaliacao.tipo = 'P1'
                    AND p1.avaliacao.turma = t

                LEFT JOIN NotaAluno p2
                    ON p2.aluno = a
                    AND p2.deleted = false
                    AND p2.avaliacao.tipo = 'P2'
                    AND p2.avaliacao.turma = t

                LEFT JOIN NotaAluno pf
                    ON pf.aluno = a
                    AND pf.deleted = false
                    AND pf.avaliacao.tipo = 'PF'
                    AND pf.avaliacao.turma = t

                WHERE n.deleted = false
                ORDER BY t.nome, a.nome
            """;


            return em.createQuery(jpql,NotaConsultaDTO.class)
                    .getResultList();


        }
    }

    
    public void atualizarNota(EntityManager em, String aluno, String turma, String tipo, Double novaNota) {

        EntityTransaction tx = em.getTransaction();
        TipoAvaliacao tipoReal = null;
        tx.begin();

        System.out.println("\nNOTA: " + novaNota + " | ALUNO: " + aluno + " | TURma: " + turma + " | TIPO: " + tipo);

        if (tipo == "P1") tipoReal = TipoAvaliacao.P1;
        else if (tipo == "P2") tipoReal = TipoAvaliacao.P2;
        else if (tipo == "PF") tipoReal = TipoAvaliacao.PF;
     
 
        String jpql = """
            SELECT n
            FROM NotaAluno n
            JOIN n.aluno a
            JOIN n.avaliacao av
            JOIN av.turma t
            WHERE a.nome = :aluno
            AND t.nome = :turma
            AND av.tipo = :tipo
            AND n.deleted = false
        """;

        NotaAluno nota = null;

        try {
            nota = em.createQuery(jpql, NotaAluno.class)
                .setParameter("aluno", aluno)
                .setParameter("turma", turma)
                .setParameter("tipo", tipoReal)
                .getSingleResult();
        }
        catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Nota não encontrada para atualização.");
        }

        nota.setValor(novaNota);

        em.merge(nota);
        tx.commit();

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
