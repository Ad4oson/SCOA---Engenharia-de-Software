package academico;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import academico.controller.SecretarioController;
import academico.model.Aluno;
import academico.model.BolsaFinanciamento;
import academico.model.ContatosAluno;
import academico.model.Curso;
import academico.model.Disciplina;
import academico.model.DocumentosAluno;
import academico.model.Professor;
import academico.model.StatusCurso;
import academico.model.Turma;
import academico.model.TurnoType;
import academico.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;




public class SecretarioTest {

    private SecretarioController secretario;
    private EntityManager em;
    private EntityTransaction tx;

    @BeforeEach
    void setup() {
        secretario = new SecretarioController();
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

    // prepara valores
    String cursoNome = "1";
    LocalDate nascimento = LocalDate.parse("2008-12-01");

    // mock da query de curso
    TypedQuery<Curso> queryCurso = mock(TypedQuery.class);
    when(em.createQuery(anyString(), eq(Curso.class))).thenReturn(queryCurso);
    when(queryCurso.setParameter(eq("cursoNome"), eq(cursoNome))).thenReturn(queryCurso);
    Curso cursoFake = new Curso();
    when(queryCurso.getSingleResult()).thenReturn(cursoFake);

    // mock da query de bolsa
    TypedQuery<BolsaFinanciamento> queryBolsa = mock(TypedQuery.class);
    when(em.createQuery(contains("BolsaFinanciamento"), eq(BolsaFinanciamento.class))).thenReturn(queryBolsa);
    when(queryBolsa.setParameter(eq("bolsaNome"), eq(1))).thenReturn(queryBolsa);
    BolsaFinanciamento bolsaFake = new BolsaFinanciamento();
    when(queryBolsa.getSingleResult()).thenReturn(bolsaFake);

    // execução
    secretario.cadastrarAluno(
            em,
            "loginUser",
            "123",
            "NomeTeste",
            "11111111111",
            "2222222",
            "POLO",
            nascimento,
            "Rua X",
            "202501",
            cursoNome,
            1,
            "statusFinanceiroBom",
            new ArrayList<>(),
            new ArrayList<>());

    verify(tx).begin();

    // verifica se query de curso foi chamada
    verify(em).createQuery(anyString(), eq(Curso.class));
    verify(queryCurso).setParameter("cursoNome", cursoNome);
    verify(queryCurso).getSingleResult();

    // verifica persistência do aluno
    ArgumentCaptor<Aluno> cap = ArgumentCaptor.forClass(Aluno.class);
    verify(em).persist(cap.capture());
    Aluno aluno = cap.getValue();

    assertEquals("NomeTeste", aluno.getNome());
    assertEquals("11111111111", aluno.getCpf());
    assertEquals(nascimento, aluno.getNascimento());
    assertFalse(aluno.isDeleted());
    assertNotNull(aluno.getCreated_at());

    verify(tx).commit();
}



 @Test
void testCadastrarAlunoCursoInexistente() {

    // simula query de curso
    TypedQuery<Curso> queryCurso = mock(TypedQuery.class);
    when(em.createQuery(anyString(), eq(Curso.class))).thenReturn(queryCurso);
    when(queryCurso.setParameter(eq("cursoNome"), eq("999"))).thenReturn(queryCurso);
    when(queryCurso.getSingleResult()).thenThrow(new EntityNotFoundException("Curso não encontrado"));

    List<DocumentosAluno> documentos = new ArrayList<>();
    List<ContatosAluno> contatos= new ArrayList<>();
    try {
        secretario.cadastrarAluno(
                em,
                "loginUser",
                "123",
                "Nome",
                "Fulano",
                "11111111111",
                "2222222",
                LocalDate.parse("2001-02-10"),
                "Rua X",
                "2025001",
                "999",
                1,
                "statusOk",
                documentos,
                contatos
        );

        fail("Era esperado lançar EntityNotFoundException");

    } catch (Exception e) {

        verify(tx).begin();

        verify(queryCurso).getSingleResult();

        verify(em, never()).persist(any(Aluno.class));

        verify(tx).rollback();
    }
}


    @Test
    void testCadastrarAlunoBolsaInexistente() {

        when(em.getReference(Curso.class, 1)).thenReturn(new Curso());

        // bolsa inexistente → gera erro
        when(em.getReference(BolsaFinanciamento.class, 999))
            .thenThrow(new jakarta.persistence.EntityNotFoundException("Bolsa não encontrada"));

            List<DocumentosAluno> documentos = new ArrayList<>();
            List<ContatosAluno> contatos= new ArrayList<>();
        try {
            LocalDate dataNascimentoReal = LocalDate.parse("2002-10-10");


            secretario.cadastrarAluno(
                em,
                "loginUser",
                "Nome",
                "123",
                "Fulano",
                "11111111111",
                "2222222",
                dataNascimentoReal,
                "Rua X",
                "2025001",
                "1",     // curso ok
                999,    // bolsa inexistente
                "statusFinanceiro",
                documentos,
                contatos
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
    void testProfessor() {

        // Mock de turmas reais
        Turma t1 = new Turma();
        Turma t2 = new Turma();
        Turma t3 = new Turma();

        List<Turma> turmas = Arrays.asList(t1, t2, t3);

        LocalDate dataAdmissaoReal = LocalDate.parse("2025-10-20");
        LocalDate dataNascimentoReal = LocalDate.parse("2003-06-10");

        secretario.cadastrarProfessor(
            em,
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
            5000,      // salario – obrigatório no método atual
            turmas
        );

        verify(tx).begin();

        ArgumentCaptor<Professor> profCaptor = ArgumentCaptor.forClass(Professor.class);
        verify(em).persist(profCaptor.capture());

        Professor prof = profCaptor.getValue();

        assertEquals("nome01", prof.getNome());
        assertEquals("cpf01", prof.getCpf());
        assertEquals(dataAdmissaoReal, prof.getDataAdmissao());
        assertEquals(dataNascimentoReal, prof.getNascimento());
        assertNotNull(prof.getCreated_at());
        assertFalse(prof.isDeleted());
        assertEquals(5000, prof.getSalario());
        assertEquals(turmas, prof.getTurmas());

        verify(tx).commit();
    }

    //Cadastro de Professor junto de Turma inexistente
   @Test
void testCadastrarProfessorTurmaInexistente() {
    // given
    final Turma TURMA_INEXISTENTE = new Turma();
    when(em.getReference(Turma.class, TURMA_INEXISTENTE))
        .thenThrow(new EntityNotFoundException("Turma não encontrada"));

    List<Turma> turmas = List.of(TURMA_INEXISTENTE);

    LocalDate dataAdmissao = LocalDate.parse("2022-02-10");
    LocalDate dataNascimento = LocalDate.parse("2001-02-10");

    // when + then
    assertThrows(EntityNotFoundException.class, () -> 
        secretario.cadastrarProfessor(
            em,
            "login01",
            "senha01",
            "nome01",
            "cpf01",
            "rg01",
            dataNascimento,
            "end01",
            "formacao01",
            "registro01",
            dataAdmissao,
            100,
            turmas
        )
    );

    // then (verificações)
    verify(tx).begin();
    verify(em, never()).persist(any(Professor.class));
    verify(tx, never()).commit();
    verify(tx).rollback();

    // opcional: verificar ordem begin -> rollback
    InOrder inOrder = inOrder(tx);
    inOrder.verify(tx).begin();
    inOrder.verify(tx).rollback();
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
  
        Curso c = cursoCaptor.getValue();
        assertEquals(c.getNome(), "cursonome1");
        assertEquals(c.getPrazoconclusao(), null);
        assertEquals(c.getDescricao(), null);
        assertEquals(c.getPortaria(), null);

        verify(tx).commit();

    }

    //Cadastro de Curso Coordenador Inexistente
   @Test
void testCadastrarCursoCoordenadorInexistente() {

    TypedQuery<Professor> queryMock = mock(TypedQuery.class);

    when(em.createQuery(anyString(), eq(Professor.class))).thenReturn(queryMock);
    when(queryMock.setParameter(eq("cpf"), eq("999"))).thenReturn(queryMock);
    when(queryMock.getSingleResult()).thenThrow(new EntityNotFoundException("Coordenador Inexistente"));

    assertThrows(EntityNotFoundException.class, () -> {

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
            "999"     // <----- importante: coordenadorCpf é STRING no método
        );

    });

    verify(tx).begin();

    // Confirmamos que NADA foi persistido
    verify(em, never()).persist(any(Curso.class));

    // Commit não deve ser chamado
    verify(tx, never()).commit();

    // Rollback deve ser chamado
    verify(tx).rollback();
}
    /* 
    //Cadastro turma (SALA null | ALUNOS null)
    @Test
    void testCadastrarTurma(){

        when(em.getReference(Disciplina.class, 1)).thenReturn(new Disciplina());
        when(em.getReference(Professor.class, 1)).thenReturn(new Professor());

        LocalTime horario = LocalTime.parse("22:45");

        secretario.cadastrarTurma(
            em,
            "TURMA01",
            horario,
            10,
            TurnoType.VESPERTINO,
            null,
            1,
            1
        );

        verify(tx).begin();

        verify(em).getReference(Disciplina.class, 1);
        verify(em).getReference(Professor.class, 1);

        ArgumentCaptor<Turma> turmaCaptor = ArgumentCaptor.forClass(Turma.class);
        verify(em).persist(turmaCaptor.capture());

        Turma turma = turmaCaptor.getValue();

        assertEquals(10, turma.getNumerovagas());
        assertNull(turma.getSala());
        assertNull(turma.getAlunos());

        verify(tx).commit();
    }
*/




}

