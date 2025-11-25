package academico;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;




public class SecretarioTest {

    private Secretario secretario;
    private EntityManager em;
    private EntityTransaction tx;

    @BeforeEach
    void setup() {
        secretario = new Secretario();
        em = mock(EntityManager.class);
        tx = mock(EntityTransaction.class);

        when(em.getTransaction()).thenReturn(tx);

        // mocks para getReference()
        when(em.getReference(Curso.class, 1)).thenReturn(new Curso());
        when(em.getReference(BolsaFinanciamento.class, 1)).thenReturn(new BolsaFinanciamento());
    }

    //Cadastro Aluno com dados corretos e data congruente
    @Test
    void testCadastrarAluno() {

        //Transforma String em LocalDate
        String dataNascimento = "2008-12-01";
        LocalDate dataNascimentoReal = LocalDate.parse(dataNascimento);

        secretario.CadastrarAluno(
            em,
            "loginUser",
            "123",
            "NomeTeste",
            "11111111111",
            "2222222",
            dataNascimentoReal,
            "Rua X",
            "202501",
            1,  
            1   
        );

        // Verifica se iniciou transação
        verify(tx).begin();

        // Verifica se o curso foi buscado
        verify(em).getReference(Curso.class, 1);

        // Verifica se a bolsa foi buscada
        verify(em).getReference(BolsaFinanciamento.class, 1);

        // Captura o aluno persistido
        ArgumentCaptor<Aluno> alunoCaptor = ArgumentCaptor.forClass(Aluno.class);
        verify(em).persist(alunoCaptor.capture());

        Aluno aluno = alunoCaptor.getValue();

        // Verifica se informações principais foram atribuídas corretamente
        assertEquals("loginUser", aluno.getLogin());
        assertEquals("123", aluno.getSenha());
        assertEquals("NomeTeste", aluno.getNome());
        assertEquals("11111111111", aluno.getCpf());
        assertEquals("Rua X", aluno.getEndereco());
        assertEquals(aluno.getNascimento(), dataNascimentoReal);
        assertNotNull(aluno.getCreated_at());
        assertEquals(aluno.isDeleted(), false);


        // Verifica commit
        verify(tx).commit();
    }

    @Test
    void testCadastrarAlunoCursoInexistente() {

    // Simula que o curso não existe → lança EntityNotFoundException
    when(em.getReference(Curso.class, 999))
        .thenThrow(new jakarta.persistence.EntityNotFoundException("Curso não encontrado"));

    try {
        LocalDate dataNascimentoReal = LocalDate.parse("2001-02-10");
        secretario.CadastrarAluno(
            em,
            "loginUser",
            "123",
            "Fulano",
            "11111111111",
            "2222222",
            dataNascimentoReal,
            "Rua X",
            "2025001",
            999,   // curso inexistente
            1
        );

        // transação deve iniciar
        verify(tx).begin();

        // deve ter tentado buscar o curso
        verify(em).getReference(Curso.class, 999);

        // aluno NÃO deve ser persistido
        verify(em, never()).persist(any(Aluno.class));

        // como houve exceção, commit NÃO deve ocorrer
        verify(tx, never()).commit();
        }
    catch(EntityNotFoundException e) {
        // rollback deve ocorrer
        verify(tx).rollback();
    }


    }


    @Test
    void testCadastrarAlunoBolsaInexistente() {

        when(em.getReference(Curso.class, 1)).thenReturn(new Curso());

        // bolsa inexistente → gera erro
        when(em.getReference(BolsaFinanciamento.class, 999))
            .thenThrow(new jakarta.persistence.EntityNotFoundException("Bolsa não encontrada"));

        try {
            LocalDate dataNascimentoReal = LocalDate.parse("2002-10-10");
            secretario.CadastrarAluno(
                em,
                "loginUser",
                "123",
                "Fulano",
                "11111111111",
                "2222222",
                dataNascimentoReal,
                "Rua X",
                "2025001",
                1,     // curso ok
                999    // bolsa inexistente
            );

            verify(tx).begin();

            // curso foi buscado normalmente
            verify(em).getReference(Curso.class, 1);

            // busca da bolsa falhou
            verify(em).getReference(BolsaFinanciamento.class, 999);

            
            // aluno não deve ser persistido
            verify(em, never()).persist(any(Aluno.class));
            // commit não deve acontecer
            verify(tx, never()).commit();

        }
        catch (EntityNotFoundException e1) {

            // rollback deve acontecer
            verify(tx).rollback();

        }


    }


    //Cadastro de Professor com dados corretos e data congruente
    @Test
    void cadastrarProfessorTeste() {
        
        when(em.getReference(Turma.class, 1)).thenReturn(new Turma());
        //Transforma String em LocalDate
        String dataAdmissao = "2025-10-20";
        LocalDate dataAdmissaoReal = LocalDate.parse(dataAdmissao);
        String dataNascimento = "2003-06-10";
        LocalDate dataNascimentoReal = LocalDate.parse(dataNascimento);


        ArrayList<Integer> turmas = new ArrayList<>();
        turmas.add(1);

        secretario.CadastrarProfessor(em,
            "login01", 
            "senha01",
            "nome01",
            "cpf01",
            "rg01",
            dataNascimentoReal, 
            "end01", 
            "formacao01", 
            "registro01", 
            dataAdmissaoReal,
            turmas);

        verify(tx).begin();

        ArgumentCaptor<Professor> profCaptor = ArgumentCaptor.forClass(Professor.class);

        verify(em).persist(profCaptor.capture());
        Professor prof = profCaptor.getValue();

        assertEquals(prof.getLogin(), "login01");
        assertEquals(prof.getSenha(), "senha01");
        assertEquals(prof.getNome(), "nome01");
        assertEquals(prof.getCpf(), "cpf01");
        assertEquals(prof.getDataAdmissao(), dataAdmissaoReal);
        assertEquals(prof.getNascimento(), dataNascimentoReal);
        assertNotNull(prof.getCreated_at());
        assertFalse(prof.isDeleted());

        verify(tx).commit();
        
    }

    //Cadastro de Professor junto de Turma inexistente
    @Test
    void cadastrarProfessorTurmaInexistente(){
        
        when(em.getReference(Turma.class, 999)).thenThrow(new jakarta.persistence.EntityNotFoundException("\nTurma não encontrada"));
        ArrayList<Integer> turmas = new ArrayList<>();
        turmas.add(999);

        try {

            LocalDate dataAdmissaoReal = LocalDate.parse("2022-02-10");
            LocalDate dataNascimentoReal = LocalDate.parse("2001-02-10");
            secretario.CadastrarProfessor(em,
            "login01", 
            "senha01",
            "nome01",
            "cpf01",
            "rg01",
            dataNascimentoReal, 
            "end01", 
            "formacao01", 
            "registro01", 
            dataAdmissaoReal,
            turmas);
            
            verify(tx).begin();
            

            ArgumentCaptor<Professor> profCaptor = ArgumentCaptor.forClass(Professor.class);
            verify(em, never()).persist(profCaptor.capture());


            verify(tx, never()).commit();
        }
        catch(EntityNotFoundException e1){
            verify(tx).rollback();
        }
    }


    //Cadastro de Curso (ALUNOS null | DISCIPLINAS null | COORDENADOR null), sem referências
    @Test
    void testCadastrarCurso() {

    
        secretario.cadastrarCurso(
        em,
        "cursonome1", 
        "mensalidade1", 
        TurnoType.MATUTINO, 
        3600, 
        8, 
        null, 
        null, 
        null, 
        StatusCurso.AGUARDO, 
        null, 
        null, 
        null);

        verify(tx).begin();
        ArgumentCaptor<Curso> cursoCaptor = ArgumentCaptor.forClass(Curso.class);
        verify(em).persist(cursoCaptor.capture());
        verify(tx).commit();

    }

    @Test
    void testCadastrarCursoCoordenadorInexistente(){

        when(em.getReference(Coordenador.class, 999))
        .thenThrow(new jakarta.persistence.EntityNotFoundException("\nCoordenador Inexistente"));
  
        try {
                
            secretario.cadastrarCurso(
            em,
            "cursonome1", 
            "mensalidade1", 
            TurnoType.MATUTINO, 
            3600, 
            8, 
            null, 
            null, 
            null, 
            StatusCurso.AGUARDO, 
            null, 
            null, 
            999);

            verify(tx).begin();
            
            //tenta buscar o coordenador
            verify(em).getReference(Coordenador.class, 999);

            verify(em, never()).persist(any());
            verify(tx, never()).commit();
        }
        catch(EntityNotFoundException e1){
            verify(tx).rollback();
        }



    }



}
