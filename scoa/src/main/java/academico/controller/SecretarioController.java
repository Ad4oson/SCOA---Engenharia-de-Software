package academico.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import academico.model.Aluno;
import academico.model.BolsaFinanciamento;
import academico.model.ContatosAluno;
import academico.model.Coordenador;
import academico.model.Curso;
import academico.model.Disciplina;
import academico.model.DocumentosAluno;
import academico.model.Professor;
import academico.model.Sala;
import academico.model.StatusCurso;
import academico.model.TipoUsuario;
import academico.model.Turma;
import academico.model.TurnoType;
import academico.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;

public class SecretarioController {

    //#region CRUD ALUNO
    public void cadastrarAluno(EntityManager em, String login, String senha, String nome, String cpf, String rg,
         String polo, LocalDate nascimento, String endereco, String matricula, String curso, String bolsa, String statusFinanceiro,
        List<DocumentosAluno> documentos, List<ContatosAluno> contatos){
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Aluno novoAluno = new Aluno();

            // preencher campos obrigatórios que vêm do formulário

            String jpqlCurso = """
                SELECT c
                FROM Curso c
                WHERE c.nome = :cursoNome AND c.deleted = false
                    """;
            
            Curso cursoT = em.createQuery(jpqlCurso, Curso.class).setParameter("cursoNome",curso).getSingleResult();
            if (curso != null) novoAluno.setCurso(cursoT);
            
            String jpqlBolsa = """
                SELECT b
                FROM BolsaFinanciamento b
                WHERE b.nome = :bolsaNome AND b.deleted = false
                    """;
            

            BolsaFinanciamento bolsaT = em.createQuery(jpqlBolsa, BolsaFinanciamento.class).setParameter("bolsaNome", bolsa).getSingleResult();
            if (bolsa != null) novoAluno.setBolsa(bolsaT);
            
            Usuario usuario = new Usuario();
            usuario.setLogin(login);
            usuario.setSenha(senha);
            usuario.setTipoUsuario(TipoUsuario.ALUNO);  

            novoAluno.setUsuario(usuario);
            novoAluno.setNome(nome);
            novoAluno.setMatricula(matricula);
            novoAluno.setCpf(cpf);
            novoAluno.setRg(rg);
            novoAluno.setEndereco(endereco);
            novoAluno.setNascimento(nascimento);
            novoAluno.setPolo(polo);
            novoAluno.setStatusfinanceiro(statusFinanceiro);
            novoAluno.setDocumentos(documentos);
            novoAluno.setContatos(contatos);

            novoAluno.setCreated_at(LocalDateTime.now());
            novoAluno.setDeleted(false);
            em.persist(novoAluno);
            em.persist(usuario);
            
            tx.commit();
            System.out.println("\n\nAluno cadastrado com sucesso!\n\n");

        } catch (Exception e) { 
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }

    }

    
    public List<Aluno> consultarAlunos(EntityManager em, Integer alunoId){
        
        if (alunoId != null){

            String jpql = """
                SELECT a
                FROM Aluno a
                JOIN a.usuario u
                WHERE a.deleted = false AND a.id = :alunoId
                ORDER BY a.nome
            """;
            return em.createQuery(jpql, Aluno.class)
            .setParameter("alunoId", alunoId)
            .getResultList();
        }
        else {

            String jpql = """
                SELECT a
                FROM Aluno a 
                JOIN usuario u
                WHERE a.deleted = false
                ORDER BY a.nome
            """;
            return em.createQuery(jpql, Aluno.class)
            .getResultList();

        }

    }


    public void atualizarAluno(EntityManager em, String login, String senha, String cpf, String rg, LocalDate nascimento,
         String endereco, String matricula, Integer cursoId, Integer bolsaId, int alunoId) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Aluno aluno = em.find(Aluno.class, alunoId);
            if (aluno == null)
                throw new EntityNotFoundException("Aluno não encontrada");

            Usuario usuario = em.find(Usuario.class, aluno.getUsuario().getLogin());
            if (usuario == null)
                throw new EntityNotFoundException("Usuario não encontrado");

            if(login != null) usuario.setLogin(login);
            if(senha != null) usuario.setSenha(senha);
            if(cpf != null) aluno.setCpf(cpf);
            if (rg != null) aluno.setRg(rg);
            if (nascimento != null) aluno.setNascimento(nascimento);
            if (endereco != null) aluno.setEndereco(endereco);
            if (matricula != null) aluno.setMatricula(matricula);

            Curso curso = em.find(Curso.class, cursoId);
            if (curso != null) aluno.setCurso(curso);

            BolsaFinanciamento bolsa = em.find(BolsaFinanciamento.class, bolsaId);
            if (bolsa != null) aluno.setBolsa(bolsa);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    

    //Um soft delete
    public void deletarAluno(EntityManager em, int alunoId) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Aluno aluno = em.find(Aluno.class, alunoId);
            if (aluno == null) throw new EntityNotFoundException("Aluno não encontrado");

            Usuario usuario = em.find(Usuario.class, aluno.getUsuario().getLogin());
            if (usuario == null) throw new EntityNotFoundException("Usuário não encontrado");

            usuario.setDeleted(true);
            aluno.setDeleted(true);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

//#endregion


    //#region CRUD PROFESSOR
    public void cadastrarProfessor(EntityManager em, String login, String senha, String nome, String cpf,
         String rg, LocalDate nascimento, String endereco, String formacao, String registros, LocalDate dataAdmissao, List<Turma> turmas){
        
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Professor novoProfessor = new Professor();

            if (turmas != null) {
                try {

                    for (Turma t : turmas) {
                        t.setProfessor(novoProfessor);
                        em.merge(t);
                    }
                }
                catch (EntityNotFoundException e1) {
                    System.out.println("\nTurma não encontrada!\n");
                    tx.rollback();
                    return;
                }
            }
   
            // preencher campos obrigatórios que vêm do formulário
            

            Usuario usuario = new Usuario();
            usuario.setLogin(login);
            usuario.setSenha(senha);
            usuario.setTipoUsuario(TipoUsuario.PROFESSOR);

            novoProfessor.setUsuario(usuario);
            novoProfessor.setNome(nome);
            novoProfessor.setCpf(cpf);
            novoProfessor.setRg(rg);
            novoProfessor.setEndereco(endereco);
            novoProfessor.setNascimento(nascimento);
            novoProfessor.setFormacao(formacao);
            novoProfessor.setRegistros(registros);
            novoProfessor.setDataAdmissao(dataAdmissao);
            novoProfessor.setTurmas(turmas);

            
            novoProfessor.setCreated_at(LocalDateTime.now());
            novoProfessor.setDeleted(false);
            em.persist(usuario);
            em.persist(novoProfessor);
            
            tx.commit();
            System.out.println("\n\nProfessor cadastrado com sucesso!\n\n");

        } catch (Exception e) { 
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }

    }


    //consulta lista ou por CPF
    public List<Professor> consultarProfessores(EntityManager em, String professorCpf){
        
        if (professorCpf != null){

            String jpql = """
                SELECT p
                FROM Professor p
                JOIN usuario u
                WHERE p.deleted = false AND p.cpf = :cpf
                ORDER BY p.nome
            """;

            return em.createQuery(jpql, Professor.class)
                .setParameter("cpf", professorCpf)
                .getResultList();

        }
        else {

            String jpql = """
                SELECT p
                FROM Professor p
                JOIN usuario u
                WHERE p.deleted = false
                ORDER BY p.nome
            """;

            return em.createQuery(jpql, Professor.class)
                .getResultList();

        }

    }



    public void atualizarProfessor(EntityManager em, String login, String senha, String cpf, String rg, LocalDate nascimento,
         String endereco, String formacao, String registros, LocalDate dataAdmissao, ArrayList<Integer> turmasId, int professorId) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Professor professor = em.find(Professor.class, professorId);
            if (professor == null)
                throw new EntityNotFoundException("Professor não encontrado");

            Usuario usuario = em.find(Usuario.class, professor.getUsuario().getLogin());
            if (usuario == null)
                throw new EntityNotFoundException("Usuario não encontrado");


            if (login != null) usuario.setLogin(login);
            if (senha != null) usuario.setSenha(senha);
            if (cpf != null) professor.setCpf(cpf);
            if (rg != null) professor.setRg(rg);
            if (nascimento != null) professor.setNascimento(nascimento);
            if (endereco != null) professor.setEndereco(endereco);
            if (formacao != null) professor.setFormacao(formacao);
            if (registros != null) professor.setRegistros(registros);
            if (dataAdmissao != null) professor.setDataAdmissao(dataAdmissao);
            
            if (turmasId != null) {
                ArrayList<Turma> turmas = new ArrayList<>();
                for (Integer id : turmasId) turmas.add(em.find(Turma.class, id));
                
                professor.setTurmas(turmas);
            }


            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }


        //Um soft delete
    public void deletarProfessor(EntityManager em, int professorId) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Professor professor = em.find(Professor.class, professorId);
            if (professor == null) throw new EntityNotFoundException("Professor não encontrado");

            Usuario usuario = em.find(Usuario.class, professor.getUsuario().getLogin());
            if (usuario == null) throw new EntityNotFoundException("Usuario não encontrado");

            usuario.setDeleted(true);
            professor.setDeleted(true);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }


    //#endregion


    //#region CRUD CURSO

    public void cadastrarCurso(EntityManager em, String nome, String mensalidade, TurnoType turno, Integer cargahoraria, Integer periodos,
        LocalDate prazoconclusao, String descricao, String portaria, StatusCurso status, 
        List<Disciplina> disciplinas, List<Aluno> alunos, String coordenadorCpf) {

        Curso cursoNovo = new Curso();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            if (coordenadorCpf != null){
                
                String jpqlCoordenador = """
                        SELECT p
                        FROM Professor p
                        WHERE p.cpf = :coordenadorCpf
                        """;
                Coordenador coordenadorNovo = em.createQuery(jpqlCoordenador, Coordenador.class).setParameter("cpf", coordenadorCpf).getSingleResult();

                cursoNovo.setCoordenador(coordenadorNovo);
            }

            if (disciplinas != null) {
                try {
                        cursoNovo.setDisciplinas(disciplinas);
                }
                catch (EntityNotFoundException e1) {
                    System.out.println("\nDisciplina não encontrada!\n");
                    tx.rollback();
                    return;
                }
            }

            if (alunos != null) {
                try {
                        cursoNovo.setAlunos(alunos);
                }
                catch (EntityNotFoundException e1) {
                    System.out.println("\nAluno não encontrada!\n");
                    tx.rollback();
                    return;
                }
            }

            cursoNovo.setNome(nome);
            cursoNovo.setMensalidade(mensalidade);
            cursoNovo.setTurno(turno);

            cursoNovo.setCargahoraria(cargahoraria);
            cursoNovo.setPeriodos(periodos);
            cursoNovo.setPrazoconclusao(prazoconclusao);

            cursoNovo.setDescricao(descricao);
            cursoNovo.setPortaria(portaria);
            cursoNovo.setStatus(status);

            cursoNovo.setDeleted(false);
            cursoNovo.setCreated_at(LocalDateTime.now());

            em.persist(cursoNovo);
            tx.commit();
            System.out.println("\nCurso cadastrado com sucesso!\n");

        }
        catch(Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }

    }

            
    public List<Curso> consultarCursos(EntityManager em, Integer cursoId){
        
        if (cursoId != null){

            String jpql = """
                SELECT c
                FROM Curso c
                WHERE c.deleted = false AND c.id = :cursoId
                ORDER BY c.nome
            """;

            return em.createQuery(jpql, Curso.class)
                .setParameter("cursoId", cursoId)
                .getResultList();

        }
        else {

            String jpql = """
                SELECT c
                FROM Curso c
                WHERE c.deleted = false
                ORDER BY c.nome
            """;

            return em.createQuery(jpql, Curso.class)
                .getResultList();
        }

    }



    public void atualizarCurso (EntityManager em, int cursoId, String nome, String mensalidade, TurnoType turno, Integer cargahoraria,
    Integer periodos, LocalDate prazoconclusao, String descricao, String portaria, StatusCurso status, ArrayList<Integer> disciplinasId,
    ArrayList<Integer> alunosId, Integer coordenadorId){

        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();

            Curso curso = em.find(Curso.class, cursoId);
            if (curso == null) throw new EntityNotFoundException("\nCurso não encontrado!\n");

            if (nome != null) curso.setNome(nome);
            if (mensalidade != null) curso.setMensalidade(mensalidade);
            if (turno != null) curso.setTurno(turno);
            if (cargahoraria != null) curso.setCargahoraria(cargahoraria);
            if (periodos != null) curso.setPeriodos(periodos);
            if (prazoconclusao != null) curso.setPrazoconclusao(prazoconclusao);
            if (descricao != null) curso.setDescricao(descricao);
            if (portaria != null) curso.setPortaria(portaria);
            if (status != null) curso.setStatus(status);
            
            if (disciplinasId != null) {
                ArrayList<Disciplina> disciplinas = new ArrayList<>();
                for (Integer id : disciplinasId){

                    Disciplina disciplina = em.find(Disciplina.class, id);
                    if (disciplina != null){
                        disciplina.getCursos().add(curso);
                    }
                    
                }
                curso.setDisciplinas(disciplinas);
            }

            if (alunosId != null){
            
                ArrayList<Aluno> alunos = new ArrayList<>();
                for (Integer id : alunosId){

                    Aluno aluno = em.find(Aluno.class, id);
                    if (aluno != null){
                        alunos.add(aluno);
                        aluno.setCurso(curso);
                    }
                    
                }
                curso.setAlunos(alunos);
            }

            if (coordenadorId != null){
                Coordenador coordenador = em.find(Coordenador.class, coordenadorId);
                if (coordenador != null) curso.setCoordenador(coordenador);
            }


            tx.commit();
        }
        catch (Exception e){
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }

    }

        //Um soft delete
    public void deletarCurso(EntityManager em, int cursoId) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Curso curso = em.find(Curso.class, cursoId);
            if (curso == null) throw new EntityNotFoundException("Curso não encontrado");

            curso.setDeleted(true);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    //#endregion


    //#region CRUD TURMA


    public void cadastrarTurma(
        EntityManager em,
        String nome, 
        LocalTime horario, 
        Integer numerovagas, 
        TurnoType turno,
        Sala sala,
        Disciplina disciplina,
        Professor professor
    ){

        Turma turmaNova = new Turma();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            if (sala !=null){
                Sala salaNova = em.getReference(Sala.class, sala);
                turmaNova.setSala(salaNova);
            }
            if (disciplina !=null) {
                Disciplina disciplinaNova = em.getReference(Disciplina.class, disciplina);
                turmaNova.setDisciplina(disciplinaNova);
            }
            if(professor !=null){
                Professor professorNovo = em.getReference(Professor.class, professor);
                turmaNova.setProfessor(professorNovo);
            }
            
            
            turmaNova.setHorario(horario);
            turmaNova.setNumerovagas(numerovagas);
            turmaNova.setTurno(turno);
            turmaNova.setCreated_at(LocalDateTime.now());
            turmaNova.setDeleted(false);

            em.persist(turmaNova);
            tx.commit();
            System.out.println("\nTurma cadastrada com sucesso!!\n");
        }
        catch (Exception e){

            if(tx.isActive()) tx.rollback();
            e.printStackTrace();
        }


    }


    public List<Turma> consultarTurmas(EntityManager em, Integer turmaId){
        
        if (turmaId != null) {

            String jpql = """
                SELECT t
                FROM Turma t
                WHERE t.deleted = false AND t.id = :turmaId
                ORDER BY t.nome
            """;

            return  em.createQuery(jpql, Turma.class)
            .setParameter("turmaId", turmaId)
            .getResultList();

        }
        else {
   


            String jpql = """
                SELECT t
                FROM Turma t
                WHERE t.deleted = false
                ORDER BY t.nome
            """;
            return em.createQuery(jpql, Turma.class)
            .getResultList();
        }


    }

    public void atualizarTurma(EntityManager em, LocalTime horario, Integer numerovagas, TurnoType turno, Integer salaId,
        Integer disciplinaId, Integer professorId, List<Integer> alunosId, Turma turmaId) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Turma turma = em.find(Turma.class, turmaId);
            if (turma == null)
                throw new EntityNotFoundException("Turma não encontrada");

            if (horario != null) turma.setHorario(horario);
            if (numerovagas != null) turma.setNumerovagas(numerovagas);
            if (turno !=null) turma.setTurno(turno);
        
            if (salaId != null) turma.setSala(em.find(Sala.class,salaId));
            if (disciplinaId != null) turma.setDisciplina(em.find(Disciplina.class, disciplinaId));
            if (professorId != null) turma.setProfessor(em.find(Professor.class, professorId));


            if (alunosId != null) {
                ArrayList<Aluno> aluno = new ArrayList<>();
                for (Integer id : alunosId) aluno.add(em.find(Aluno.class, id));
                
                turma.setAlunos(aluno);
            }

    
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }


    //Um soft delete
    public void deletarTurma(EntityManager em, int turmaId) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Turma turma = em.find(Turma.class, turmaId);
            if (turma == null) throw new EntityNotFoundException("Turma não encontrada");

            turma.setDeleted(true);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    //#endregion
    
    //#region CRUD SALA

    
    public void cadastrarSala(EntityManager em, String local, Integer capacidade, List<Turma> turmas){

        EntityTransaction tx = em.getTransaction();

        try {
             tx.begin();

            Sala sala = new Sala();
            sala.setLocal(local);
            sala.setCapacidade(capacidade);
            
            if (turmas !=null){
              
                for (Turma turma : turmas){

                    if (turma == null) throw new EntityNotFoundException("\nTurma não encontrada!\n");

                    //relacao bidirecional
                    turma.setSala(sala);
                }
                sala.setTurmas(turmas);
            }

            em.persist(sala);
            tx.commit();
            System.out.println("\nSala cadastrada com sucesso!\n");
        }
        catch (Exception e) {

            if(tx.isActive()) tx.rollback();
            e.printStackTrace();

        }

    }

    public List<Sala> consultarSalas (EntityManager em, Integer salaId){
        //Lembrar de atualizar as outras consultas duplas para serem uma só, utilizando um Id nullable

        if (salaId != null) {
                
            String jpql = """
                SELECT s
                FROM Sala s
                WHERE s.deleted = false AND s.id = :salaId
            """;

            return em.createQuery(jpql, Sala.class)
            .setParameter("salaId", salaId)
            .getResultList();

        }
        else {

            String jpql = """
            SELECT s
            FROM Sala s
            WHERE s.deleted = false
            """;

            return em.createQuery(jpql, Sala.class)
            .getResultList();
        }
    }



    public void atualizarSala(EntityManager em, int salaId, String local, Integer capacidade, List<Integer> turmasId) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Sala sala = em.find(Sala.class, salaId);
            if (sala == null)
                throw new EntityNotFoundException("Sala não encontrada");

            if (local !=null) sala.setLocal(local);
            if (capacidade != null) sala.setCapacidade(capacidade);

            
            if (turmasId != null) { 
                ArrayList<Turma> turmas = new ArrayList<>();
                for (Integer id : turmasId){

                    if (id == null) continue;

                    Turma turma = em.find(Turma.class, id);
                    if (turma != null) {
                        turma.setSala(sala);
                        turmas.add(turma);
                    }

                }

                sala.setTurmas(turmas);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }


    //Um soft delete
    public void deletarSala(EntityManager em, int salaId) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Sala sala = em.find(Sala.class, salaId);
            if (sala == null) throw new EntityNotFoundException("Sala não encontrada");

            sala.setDeleted(true);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }


    //#endregion

}
