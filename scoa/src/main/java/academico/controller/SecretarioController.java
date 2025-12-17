package academico.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import academico.model.Aluno;
import academico.model.BolsaFinanciamento;
import academico.model.ContatosAluno;
import academico.model.ContatosProfessor;
import academico.model.Curso;
import academico.model.Disciplina;
import academico.model.DocumentosAluno;
import academico.model.EspecialidadesProfessor;
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
         String polo, LocalDate nascimento, String endereco, String matricula, String curso, Integer bolsaId, String statusFinanceiro,
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
                WHERE b.id = :bolsaId AND b.deleted = false
                    """;
            

            BolsaFinanciamento bolsaT = em.createQuery(jpqlBolsa, BolsaFinanciamento.class).setParameter("bolsaId", bolsaId).getSingleResult();
            if (bolsaId != null) novoAluno.setBolsa(bolsaT);
            
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


    public void atualizarAluno(EntityManager em, String login, String senha, String nome, String cpf, String rg, LocalDate nascimento, String polo,
         String endereco, String matricula, Curso curso, BolsaFinanciamento bolsa, Aluno aluno, 
         List<DocumentosAluno> documentos, List<ContatosAluno> contatos) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            aluno = em.merge(aluno);


            if (aluno == null)
                throw new EntityNotFoundException("Aluno não encontrada");

            String jpqlUsuario = """
                        SELECT u FROM Usuario u WHERE u.login = :login AND u.deleted = false
                                """; 
            Usuario usuario = em.createQuery(jpqlUsuario, Usuario.class).setParameter("login", login).getSingleResult();
            if (usuario == null)
                throw new EntityNotFoundException("Usuario não encontrado");


            if(login != null) usuario.setLogin(login);
            if(senha != null) usuario.setSenha(senha);


            if (nome != null) aluno.setNome(nome);
            if(cpf != null) aluno.setCpf(cpf);
            if (rg != null) aluno.setRg(rg);
            if (nascimento != null) aluno.setNascimento(nascimento);
            if (polo != null) aluno.setPolo(polo);
            if (endereco != null) aluno.setEndereco(endereco);
            if (matricula != null) aluno.setMatricula(matricula);
            
            //DOCUMENTOS
            
            List<DocumentosAluno> documentosT = aluno.getDocumentos();
            if (documentos != null) {


                 //Garante que o for percorra todo vetor
                        int tamanhoDocumento = documentosT.size();
                        if (tamanhoDocumento < documentos.size()) tamanhoDocumento = documentos.size();
                        System.out.println(" DocumentosTSize: " + documentosT.size() + " DocumentosSize: " + documentos.size());

                        for (int count=0; count<=tamanhoDocumento; count++){

                                DocumentosAluno bd = (count < documentosT.size()) ? documentosT.get(count) : null;
                                DocumentosAluno view = (count < documentos.size()) ? documentos.get(count) : null;   
                                System.out.println("ITERACAO: " + count); 


                            if ((bd!= null) && (view!= null)){

                                System.out.println( "Ambos possuem registro: " + documentos.get(count).getCaminho_arquivo());
                                DocumentosAluno documentoBD = documentosT.get(count);
                                DocumentosAluno documentoView = documentos.get(count);
                                

                                documentos.get(count).setId(documentoBD.getId());
                                if(Objects.equals(documentoBD.getCaminho_arquivo(), documentoView.getCaminho_arquivo()) && 
                                Objects.equals(documentoBD.getTipo_documento(), documentoView.getTipo_documento())){
                                    System.out.println( "Documentos iguais, sem alteração ");

                                }
                                else {
                                    System.out.println("Documentos diferentes, BD alterado");
                                    documentoBD.setCaminho_arquivo(documentoView.getCaminho_arquivo());
                                    documentoBD.setTipo_documento(documentoView.getTipo_documento());
                                }

                            }
                            else if ((bd == null) && (view!= null)){

                                System.out.println( "Registro Novo adicionado: " + documentos.get(count).getCaminho_arquivo());
                                documentosT.add(documentos.get(count));

                            }
                            else if ((bd!= null) && (view == null)){
                                System.out.println( "Registro Removido: " + documentosT.get(count).getCaminho_arquivo());
                                documentosT.remove(count);

                            }

                        }

                aluno.setDocumentos(documentosT);

            } 


            //CONTATOS
            List<ContatosAluno> contatosT = aluno.getContatos();
            if (contatos != null){

                        //Garante que o for percorra todo vetor
                        int tamanhoContato = contatosT.size();
                        if (tamanhoContato < contatos.size()) tamanhoContato = contatos.size();
                        System.out.println(" CONTATOSTSize: " + contatosT.size() + " ContatosSize: " + contatos.size());

                        for (int count=0; count<=tamanhoContato; count++){

                                ContatosAluno bd = (count < contatosT.size()) ? contatosT.get(count) : null;
                                ContatosAluno view = (count < contatos.size()) ? contatos.get(count) : null;   
                                System.out.println("ITERACAO: " + count); 


                            if ((bd!= null) && (view!= null)){

                                System.out.println( "Ambos possuem registro: " + contatos.get(count).getContato());
                                ContatosAluno contatoBD = contatosT.get(count);
                                ContatosAluno contatoView = contatos.get(count);
                                

                                contatos.get(count).setId(contatoBD.getId());
                                if(Objects.equals(contatoBD.getContato(), contatoView.getContato())){
                                    System.out.println( "Contatos iguais, sem alteração ");

                                }
                                else {
                                    System.out.println("Contatos diferentes, BD alterado");
                                    contatoBD.setContato(contatoView.getContato());
                                }

                            }
                            else if ((bd == null) && (view!= null)){

                                System.out.println( "Registro Novo adicionado: " + contatos.get(count).getContato());
                                contatosT.add(contatos.get(count));

                            }
                            else if ((bd!= null) && (view == null)){
                                System.out.println( "Registro Removido: " + contatosT.get(count).getContato());
                                contatosT.remove(count);

                            }

                        }
                    
                        aluno.setContatos(contatosT);
            }
                

            

            if (curso != null) aluno.setCurso(curso);
            if (bolsa != null) aluno.setBolsa(bolsa);


            for (ContatosAluno c : contatosT) c.setAluno(aluno); 
            for (DocumentosAluno d : documentosT) d.setAluno(aluno); 

      
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
             e.printStackTrace(); 
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
    public void cadastrarProfessor(EntityManager em, String login, String senha, String nome, String cpf, String rg, 
        LocalDate nascimento, String endereco, String formacao, String registros, LocalDate dataAdmissao, Integer salario, List<Turma> turmas){
        
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
            novoProfessor.setSalario(salario);
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



    public void atualizarProfessor(EntityManager em, String login, String senha, String nome, String cpf, String rg, LocalDate nascimento,
         String endereco, String formacao, String registros, LocalDate dataAdmissao, String polo, Integer salario,
         List<Turma> turmas, List<EspecialidadesProfessor> especialidades, List<ContatosProfessor> contatos) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            String jpqlProfessor = """
                    SELECT p
                    FROM Professor p
                    WHERE p.usuario.login = :loginProf AND p.deleted = false        

                    """;

            Professor professor = em.createQuery(jpqlProfessor, Professor.class).setParameter("loginProf", login).getSingleResult();

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
            
            if (turmas != null) {
       
                for (Turma t : turmas) t.setProfessor(professor);
                
                professor.setTurmas(turmas);
            }




            //ESPECIALIDADES
            List<EspecialidadesProfessor> especialidadesT = professor.getEspecialidades();
            if (especialidades != null){

                        //Garante que o for percorra todo vetor
                        int tamanhoEsp = especialidadesT.size();
                        if (tamanhoEsp < especialidades.size()) tamanhoEsp = especialidades.size();
                        System.out.println(" especialidadeTSize: " + especialidadesT.size() + " especialidadeSize: " + especialidades.size());

                        for (int count=0; count<=tamanhoEsp; count++){

                                EspecialidadesProfessor bd = (count < especialidadesT.size()) ? especialidadesT.get(count) : null;
                                EspecialidadesProfessor view = (count < especialidades.size()) ? especialidades.get(count) : null;   
                                System.out.println("ITERACAO: " + count); 


                            if ((bd!= null) && (view!= null)){

                                System.out.println( "Ambos possuem registro: " + especialidades.get(count).getEspecialidade());
                                EspecialidadesProfessor especialidadeBD = especialidadesT.get(count);
                                EspecialidadesProfessor especialidadeView = especialidades.get(count);
                                

                                especialidades.get(count).setId(especialidadeBD.getId());
                                if(Objects.equals(especialidadeBD.getEspecialidade(), especialidadeView.getEspecialidade())){
                                    System.out.println( "Especialidades iguais, sem alteração ");

                                }
                                else {
                                    System.out.println("Especialidades diferentes, BD alterado");
                                    especialidadeBD.setEspecialidade(especialidadeView.getEspecialidade());
                                }

                            }
                            else if ((bd == null) && (view!= null)){

                                System.out.println( "Registro Novo adicionado: " + especialidades.get(count).getEspecialidade());
                                especialidadesT.add(especialidades.get(count));

                            }
                            else if ((bd!= null) && (view == null)){
                                System.out.println( "Registro Removido: " + especialidadesT.get(count).getEspecialidade());
                                especialidadesT.remove(count);

                            }

                        }
                    
                        professor.setEspecialidades(especialidadesT);
            }
                

            //CONTATOS
            List<ContatosProfessor> contatosT = professor.getContatos();
            if (contatos != null){

                        //Garante que o for percorra todo vetor
                        int tamanhoContato = contatosT.size();
                        if (tamanhoContato < contatos.size()) tamanhoContato = contatos.size();
                        System.out.println(" CONTATOSTSize: " + contatosT.size() + " ContatosSize: " + contatos.size());

                        for (int count=0; count<=tamanhoContato; count++){

                                ContatosProfessor bd = (count < contatosT.size()) ? contatosT.get(count) : null;
                                ContatosProfessor view = (count < contatos.size()) ? contatos.get(count) : null;   
                                System.out.println("ITERACAO: " + count); 


                            if ((bd!= null) && (view!= null)){

                                System.out.println( "Ambos possuem registro: " + contatos.get(count).getContato());
                                ContatosProfessor contatoBD = contatosT.get(count);
                                ContatosProfessor contatoView = contatos.get(count);
                                

                                contatos.get(count).setId(contatoBD.getId());
                                if(Objects.equals(contatoBD.getContato(), contatoView.getContato())){
                                    System.out.println( "Contatos iguais, sem alteração ");

                                }
                                else {
                                    System.out.println("Contatos diferentes, BD alterado");
                                    contatoBD.setContato(contatoView.getContato());
                                }

                            }
                            else if ((bd == null) && (view!= null)){

                                System.out.println( "Registro Novo adicionado: " + contatos.get(count).getContato());
                                contatosT.add(contatos.get(count));

                            }
                            else if ((bd!= null) && (view == null)){
                                System.out.println( "Registro Removido: " + contatosT.get(count).getContato());
                                contatosT.remove(count);

                            }

                        }
                    
                        professor.setContatos(contatosT);
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
                        WHERE p.cpf = :cpf
                        """;
                Professor coordenadorNovo = em.createQuery(jpqlCoordenador, Professor.class).setParameter("cpf", coordenadorCpf).getSingleResult();

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


    public void atualizarCurso (EntityManager em, String cursoNome, String mensalidade, TurnoType turno, Integer cargahoraria,
    Integer periodos, LocalDate prazoconclusao, String descricao, String portaria, StatusCurso status, List<String> disciplinas,
    List<String> alunos, String coordenadorCpf){

        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            String jpqlCurso = """
                    SELECT c
                    FROM Curso c
                    WHERE c.nome = :cursoNome AND c.deleted = false    
                    """;
            Curso curso = em.createQuery(jpqlCurso, Curso.class).setParameter("cursoNome", cursoNome).getSingleResult();
            if (curso == null) throw new EntityNotFoundException("\nCurso não encontrado!\n");


            if (cursoNome != null) curso.setNome(cursoNome);
            if (mensalidade != null) curso.setMensalidade(mensalidade);
            if (turno != null) curso.setTurno(turno);
            if (cargahoraria != null) curso.setCargahoraria(cargahoraria);
            if (periodos != null) curso.setPeriodos(periodos);
            if (prazoconclusao != null) curso.setPrazoconclusao(prazoconclusao);
            if (descricao != null) curso.setDescricao(descricao);
            if (portaria != null) curso.setPortaria(portaria);
            if (status != null) curso.setStatus(status);
            
            if (disciplinas != null) {
                ArrayList<Disciplina> disciplinasT = new ArrayList<>();
                for (String dNovo : disciplinas){

                    String jpqlDisciplina = """
                            SELECT d
                            FROM Disciplina d
                            WHERE d.nome = :dNome AND d.deleted = false
                            """;
                    Disciplina disciplina = new Disciplina();
                    try {
                        disciplina = em.createQuery(jpqlDisciplina, Disciplina.class).setParameter("dNome", dNovo).getSingleResult();
                    }
                    catch (Exception e){
                        System.out.println("Disciplina não encontrada!");
                        e.printStackTrace();
                    }
                    
                    if (disciplina != null){
                        disciplina.getCursos().add(curso);
                        disciplinasT.add(disciplina);
                    }
 
                }
                curso.setDisciplinas(disciplinasT);
            }

            //LISTA ALUNOS
            if (alunos != null){
                ArrayList<Aluno> alunosT = new ArrayList<>();
            
                for (String aNovo : alunos){

                    String jpqlAluno = """
                            SELECT a
                            FROM Aluno a
                            WHERE a.matricula = :aMatricula AND a.deleted = false
                            """;
                    Aluno aluno = new Aluno();
                    try {
                        aluno = em.createQuery(jpqlAluno, Aluno.class).setParameter("aMatricula", aNovo).getSingleResult();
                    }
                    catch (Exception e){
                        System.out.println("Aluno não encontrado!");
                        e.printStackTrace();
                    }
                    
                    if (aluno != null){
                        aluno.setCurso(curso);
                        alunosT.add(aluno);
                    }
 
                }

                curso.setAlunos(alunosT);
            }



            if (coordenadorCpf != null){

                String jpqlCoordenador = """
                        SELECT p
                        FROM Professor p
                        WHERE p.cpf = :coordenadorCpf AND p.deleted = false
                        """;
                Professor coordenador = em.createQuery(jpqlCoordenador, Professor.class).setParameter("coordenadorCpf", coordenadorCpf).getSingleResult();
                
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


    public List<Turma> consultarTurmas(EntityManager em, String turmaNome){
        
        if (turmaNome != null) {

            String jpql = """
                SELECT t
                FROM Turma t
                WHERE t.deleted = false AND t.nome = :turmaNome
                ORDER BY t.nome
            """;

            return  em.createQuery(jpql, Turma.class)
            .setParameter("turmaNome", turmaNome)
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

    public void atualizarTurma(EntityManager em, String nome, LocalTime horario, Integer numerovagas, TurnoType turno, Sala sala,
        Disciplina disciplina, Professor professor, List<String> alunos, Integer turmaId) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            System.out.println("ATUALIZAR TURMA");

            Turma turma = em.find(Turma.class, turmaId);
            if (turma == null)
                throw new EntityNotFoundException("Turma não encontrada");

            if (nome != null) turma.setNome(nome);
            if (horario != null) turma.setHorario(horario);
            if (numerovagas != null) turma.setNumerovagas(numerovagas);
            if (turno !=null) turma.setTurno(turno);
        
            if (sala != null) turma.setSala(sala);
            if (disciplina != null) turma.setDisciplina(disciplina);
            if (professor != null) turma.setProfessor(professor);

            if (alunos != null){
                List<Aluno> alunosT = new ArrayList<>();
                System.out.println("ALUNO SIZE: " + alunos.size());

                for (int r=0; r<alunos.size(); r++){
                    System.out.println("matricula : " + alunos.get(r));
                    String jpqlAluno = """
                            SELECT a
                            FROM Aluno a
                            WHERE a.matricula = :matricula AND a.deleted = false
                            """;
                    Aluno alunoTemp = em.createQuery(jpqlAluno, Aluno.class).setParameter("matricula", alunos.get(r)).getSingleResult();
                    
                    alunosT.add(alunoTemp);

                }
                System.out.println("PASSOU ALUNO \n");
                turma.setAlunos(alunosT);
            }
    
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
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

    
    public void cadastrarSala(EntityManager em, String local, Integer capacidade, List<String> turmas){

        EntityTransaction tx = em.getTransaction();

        try {
             tx.begin();

            Sala sala = new Sala();
            sala.setLocal(local);
            sala.setCapacidade(capacidade);

            sala.setCreated_at(LocalDateTime.now());
            sala.setDeleted(false);
            
            if (turmas !=null){

                List <Turma> turmasT = new ArrayList<>();
                for (String turma : turmas){

                    Turma turmaT = new Turma();

                    String jpqlTurma = """
                    SELECT t
                    FROM Turma t
                    WHERE t.nome = :turmaNome AND t.deleted = false
                    """;
                    try {
                        turmaT = em.createQuery(jpqlTurma, Turma.class).setParameter("turmaNome", turma).getSingleResult();
                    }
                    catch (EntityNotFoundException e){

                        e.printStackTrace();
                        throw new RuntimeException("Turma não encontrada!", e);
             
                    }


                    //relacao bidirecional
                    turmaT.setSala(sala);
                    turmasT.add(turmaT);
                }
                sala.setTurmas(turmasT);
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

    public List<Sala> consultarSalas (EntityManager em, String salaLocal){
        //Lembrar de atualizar as outras consultas duplas para serem uma só, utilizando um Id nullable

        if (salaLocal != null) {
            System.out.println("\nPESQUISANDO SALA LOCAL\n");
                
            String jpql = """
                SELECT s
                FROM Sala s
                WHERE s.deleted = false AND s.local = :salaLocal
            """;

            return em.createQuery(jpql, Sala.class)
            .setParameter("salaLocal", salaLocal)
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



    public void atualizarSala(EntityManager em, Integer salaId, String local, Integer capacidade, List<Turma> turmas) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            System.out.println("\nSALAID: " + salaId);

            Sala sala = em.find(Sala.class, salaId);

           
            if (sala == null)
                throw new EntityNotFoundException("Sala não encontrada");
            System.out.println("\nDEPOIS SALA\n");
            if (local !=null) sala.setLocal(local);
            if (capacidade != null) sala.setCapacidade(capacidade);

            
            if (turmas != null) { 
                System.out.println("\nENTRANDO TURMAS\n");
                ArrayList<Turma> turmasT = new ArrayList<>();
                for (Turma t : turmas){

                    if (t == null) continue;
                    else {
                        System.out.println("\nTURMA: " + t.getNome());

                        t.setSala(sala);
                        turmasT.add(t);
                    }

                }
                 System.out.println("\nSAIU FOR\n");
                sala.setTurmas(turmasT);
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


    //#region CR DISCIPLINA


     public void cadastrarDisciplina(EntityManager em,
        String nome,
        String ementa,
        Integer carga_horaria,
        Integer creditos,
        String bibliografia,
        Disciplina disciplinaPre,
        List<Curso> cursos,
        List<Turma> turmas)
    {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Disciplina disciplinaNovo = new Disciplina();
            System.out.println("entrou cadastro");

            //seta lista curso
            List<Curso> cursosT = new ArrayList<>();
            if (cursos !=null){
                try {
                    for (Curso c: cursosT) {
                        c.getDisciplinas().add(disciplinaNovo);
                        em.merge(c);
                    }

                }
                catch (EntityNotFoundException e1) {
                    System.out.println("\nCurso não encontrado!\n");
                    e1.printStackTrace();
                    tx.rollback();
                    return;
                }
            }

            //seta lista turma
            List<Turma> turmasT = new ArrayList<>();
            if (turmas !=null){
                try {
                    for (Turma t : turmasT) {
                        t.setDisciplina(disciplinaNovo);
                        em.merge(t);
                    }

                }
                catch (EntityNotFoundException e1) {
                    System.out.println("\nTurma não encontrada!\n");
                    e1.printStackTrace();
                    tx.rollback();
                    return;
                }
            }

                        
            //seta disciplina pre requisito
            if (disciplinaPre != null) {
                System.out.println("Entrou != null");
                disciplinaNovo.setDisciplinapre(disciplinaPre);
            }
            else disciplinaNovo.setDisciplinapre(null);

            disciplinaNovo.setNome(nome);
            disciplinaNovo.setEmenta(ementa);
            disciplinaNovo.setCargaHoraria(carga_horaria);
            disciplinaNovo.setCreditos(creditos);
            disciplinaNovo.setBibliografia(bibliografia);
            disciplinaNovo.setCursos(cursosT);
            disciplinaNovo.setTurmas(turmasT);

            disciplinaNovo.setCreated_at(LocalDateTime.now());
            disciplinaNovo.setDeleted(false);

            em.persist(disciplinaNovo);

            tx.commit();
        }
        catch(Exception e){
            if(tx.isActive()) tx.rollback();
            System.out.println("ERRO DENTRO DA CLASSE!");
            
            e.printStackTrace();
        }


    }
    

    public List<Disciplina> consultarDisciplinas(EntityManager em, String disciplina) {

        if (disciplina != null){

            String jpql = """
                SELECT d
                FROM Disciplina d
                WHERE d.deleted = false AND d.nome = :disciplinaNome
            """;

            return em.createQuery(jpql, Disciplina.class).setParameter("disciplinaNome", disciplina).getResultList();

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


    //#endregion

}
