package academico.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import academico.Aluno;
import academico.BolsaFinanciamento;
import academico.Coordenador;
import academico.Curso;
import academico.Disciplina;
import academico.Professor;
import academico.Sala;
import academico.StatusCurso;
import academico.TipoUsuario;
import academico.Turma;
import academico.TurnoType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;

public class SecretarioController {

    
    public void CadastrarAluno(EntityManager em, String login, String senha, String nome, String cpf,
         String rg, LocalDate nascimento, String endereco, String matricula, Integer cursoId, Integer bolsaId){
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Aluno novoAluno = new Aluno(0, login, senha, TipoUsuario.ALUNO, nome, matricula);

            // preencher campos obrigatórios que vêm do formulário
            
            if (cursoId != null){
                 Curso curso = em.getReference(Curso.class, cursoId);
                 novoAluno.setCurso(curso);
            }
           
            if (bolsaId != null) {
                BolsaFinanciamento bolsa = em.getReference(BolsaFinanciamento.class, bolsaId);
                novoAluno.setBolsa(bolsa);
            }
            
            novoAluno.setCpf(cpf);
            novoAluno.setRg(rg);
            novoAluno.setEndereco(endereco);
            novoAluno.setNascimento(nascimento);
            
            novoAluno.setCreated_at(LocalDateTime.now());
            novoAluno.setDeleted(false);
            em.persist(novoAluno);
            
            tx.commit();
            System.out.println("\n\nAluno cadastrado com sucesso!\n\n");

        } catch (Exception e) { 
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }

    }



    public void CadastrarProfessor(EntityManager em, String login, String senha, String nome, String cpf,
         String rg, LocalDate nascimento, String endereco, String formacao, String registros, LocalDate dataAdmissao, ArrayList<Integer> turmas){
        
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Professor novoProfessor = new Professor();

            if (turmas != null) {
                try {

                    ArrayList<Turma> turmasL = new ArrayList<>();
                    for (Integer id : turmas){
                        Turma turma = em.getReference(Turma.class, id);
                        turmasL.add(turma);
                    }
                    for (Turma t : turmasL) {
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
        
            novoProfessor.setLogin(login);
            novoProfessor.setSenha(senha);
            novoProfessor.setNome(nome);
            novoProfessor.setCpf(cpf);
            novoProfessor.setRg(rg);
            novoProfessor.setEndereco(endereco);
            novoProfessor.setNascimento(nascimento);
            novoProfessor.setFormacao(formacao);
            novoProfessor.setRegistros(registros);
            novoProfessor.setDataAdmissao(dataAdmissao);
            novoProfessor.setTipoUsuario(TipoUsuario.PROFESSOR);
            
            novoProfessor.setCreated_at(LocalDateTime.now());
            novoProfessor.setDeleted(false);
            em.persist(novoProfessor);
            
            tx.commit();
            System.out.println("\n\nProfessor cadastrado com sucesso!\n\n");

        } catch (Exception e) { 
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }

    }


    public void cadastrarCurso(EntityManager em, String nome, String mensalidade, TurnoType turno, Integer cargahoraria, Integer periodos,
        LocalDate prazoconclusao, String descricao, String portaria, StatusCurso status, 
        ArrayList<Integer> disciplinas, ArrayList<Integer> alunos, Integer coordenador) {

        Curso cursoNovo = new Curso();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            if (coordenador != null){
                Coordenador coordenadorNovo = em.getReference(Coordenador.class, coordenador);
                cursoNovo.setCoordenador(coordenadorNovo);
            }

            if (disciplinas != null) {
                try {

                    ArrayList<Disciplina> disciplinasL = new ArrayList<>();
                    for (Integer id : disciplinas){
                        Disciplina disciplina = em.getReference(Disciplina.class, id);
                        disciplinasL.add(disciplina);
                    }
                    for (Disciplina d : disciplinasL) {
                        cursoNovo.setDisciplinas(disciplinasL);;
                        em.merge(d);
                    }
                }
                catch (EntityNotFoundException e1) {
                    System.out.println("\nDisciplina não encontrada!\n");
                    tx.rollback();
                    return;
                }
            }

            if (alunos != null) {
                try {

                    ArrayList<Aluno> alunosL = new ArrayList<>();
                    for (Integer id : alunos){
                        Aluno aluno = em.getReference(Aluno.class, id);
                        alunosL.add(aluno);
                    }
                    for (Aluno a : alunosL) {
                        cursoNovo.setAlunos(alunosL);
                        em.merge(a);
                    }
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


    public void cadastrarTurma(
        EntityManager em, 
        LocalTime horario, 
        Integer numerovagas, 
        TurnoType turno,
        Integer sala,
        Integer disciplina,
        Integer professor,
        List<Integer> alunos
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
            
            if(alunos!= null){

                ArrayList<Aluno> alunosL = new ArrayList<>();
                for (Integer id : alunos){

                    Aluno aluno = em.getReference(Aluno.class, id);
                    alunosL.add(aluno);
                }

                for (Aluno a : alunosL){

                    a.getTurmas().add(turmaNova);
                    em.merge(a);
                }
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

    
}
