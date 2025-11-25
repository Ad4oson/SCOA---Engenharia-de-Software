package academico;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.ArrayList;
import academico.Turma;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;

public class Secretario extends Aluno{

    
    //Construtor basico para testes
    public Secretario(){} 

    public Secretario(Aluno secretario){

        super();
        if (secretario == null) return;
        
        setId(secretario.getId());
        setLogin(secretario.getLogin());
        setSenha(secretario.getSenha());
        setTipoUsuario(secretario.getTipoUsuario());
        setNome(secretario.getNome());
        setMatricula(secretario.getMatricula());
        setStatusfinanceiro(secretario.getStatusfinanceiro());
        setCpf(secretario.getCpf());
        setRg(secretario.getRg());
        setNascimento(secretario.getNascimento());
        setPolo(secretario.getPolo());
        setEndereco(secretario.getEndereco());
        setCreated_at(secretario.getCreated_at());
        setDeleted(secretario.isDeleted());
        
    }

  

        //Construtor total
    public Secretario(int id, String login, String senha, TipoUsuario tipoUsuario, String nome, String cpf, String rg,
    LocalDate nascimento, String polo, String endereco, LocalDateTime created_at, boolean deleted) {

        setId(id);
        setLogin(login);
        setSenha(senha);
        setTipoUsuario(tipoUsuario);
        setNome(nome);
        setCpf(cpf);
        setRg(rg);
        setNascimento(nascimento);
        setPolo(polo);
        setEndereco(endereco);
        setCreated_at(created_at);
        setDeleted(deleted);
    }


    public void CadastrarAluno(EntityManager em, String login, String senha, String nome, String cpf,
         String rg, LocalDate nascimento, String endereco, String matricula, int cursoId, int bolsaId){
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Curso curso = em.getReference(Curso.class, cursoId);
            BolsaFinanciamento bolsa = em.getReference(BolsaFinanciamento.class, bolsaId);
            Aluno novoAluno = new Aluno(0, login, senha, TipoUsuario.ALUNO, nome, matricula);
            // preencher campos obrigatórios que vêm do formulário
            novoAluno.setCurso(curso);
            novoAluno.setBolsa(bolsa);
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

            em.getReference(Coordenador.class, coordenador);

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
            cursoNovo.setCreated_at(LocalDate.now());

            em.persist(cursoNovo);
            tx.commit();
            System.out.println("\nCurso cadastrado com sucesso!\n");

        }
        catch(Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }

    }


}