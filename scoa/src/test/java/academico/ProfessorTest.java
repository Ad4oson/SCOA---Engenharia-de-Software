package academico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import academico.controller.ProfessorController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class ProfessorTest {


    private ProfessorController professor;
    private EntityManager em;
    private EntityTransaction tx;

    @BeforeEach
    void setup() {
        professor = new ProfessorController();
        em = mock(EntityManager.class);
        tx = mock(EntityTransaction.class);

        when(em.getTransaction()).thenReturn(tx);

        // mocks para getReference()
        //when(em.getReference(Curso.class, 1)).thenReturn(new Curso());
    }

    @Test
    void testLancarPauta_DevePersistirCorretamente() {

        Turma turmaMock = new Turma();
        turmaMock.setId(1);

        when(em.getReference(Turma.class, 1)).thenReturn(turmaMock);

        LocalDate data = LocalDate.of(2024,1,10);

        professor.lancarPauta(
            em,
            1,
            data,
            "Conteúdo da aula",
            "Atividades",
            "Observações gerais"
        );

        verify(tx).begin();

        ArgumentCaptor<PautaDeAula> pautaCaptor = ArgumentCaptor.forClass(PautaDeAula.class);
        verify(em).persist(pautaCaptor.capture());

        PautaDeAula pauta = pautaCaptor.getValue();

        assertEquals(data, pauta.getData());
        assertEquals("Conteúdo da aula", pauta.getConteudo());
        assertEquals("Atividades", pauta.getAtividades());
        assertEquals("Observações gerais", pauta.getObservacoes());
        assertEquals(turmaMock, pauta.getTurma());
        assertNotNull(pauta.getCreated_at());
        assertEquals(false, pauta.isDeleted());

        verify(tx).commit();
    }

    //Lançar Pauta com Turma inexistente
    @Test
    void testLancarPautaTurmaInexistente() {

      

        when(em.getReference(Turma.class, 999))
            .thenThrow(new jakarta.persistence.EntityNotFoundException("Turma não encontrada"));

        try {

            professor.lancarPauta(
            em,
            999,                 
            LocalDate.now(),
            "Conteúdo",
            "Atividades",
            "Observações"
        );

        verify(tx).begin();

        verify(em).getReference(Turma.class, 999);

        verify(tx, never()).commit();
        verify(em, never()).persist(any(PautaDeAula.class));  // nada deve ser persistido

        }
        catch(EntityNotFoundException e){
            verify(tx).rollback();      // rollback deve acontecer
        }
        
        
    }


    //Consultar Pauta com sucesso
    @Test
    void testConsultarPautasSucesso() {

        TypedQuery<PautaDeAula> query = mock(TypedQuery.class);

        when(em.createQuery(anyString(), eq(PautaDeAula.class))).thenReturn(query);
        when(query.setParameter(eq("turmaId"), eq(3))).thenReturn(query);

        List<PautaDeAula> lista = Arrays.asList(new PautaDeAula(), new PautaDeAula());
        when(query.getResultList()).thenReturn(lista);

        List<PautaDeAula> resultado = professor.consultarPautas(em, 3);

        assertEquals(2, resultado.size());
    }


    //Lançar frequencia com sucesso
    @Test
    void lancarFrequenciaSucesso() {

        // mock da referência
        when(em.getReference(Turma.class, 1)).thenReturn(new Turma());
        when(em.getReference(Aluno.class, 1)).thenReturn(new Aluno());

        professor.lancarFrequencia(
                em,
                1,  // turma
                1,  // aluno
                LocalDate.now(),
                "Justificativa",
                45,
                true);

        verify(tx).begin();
        verify(em).getReference(Turma.class, 1);
        verify(em).getReference(Aluno.class, 1);
        verify(em).persist(any(FrequenciaAluno.class));
        verify(tx).commit();
    }

    // Consultar frequência com sucesso
    @Test
    void consultarFrequenciaSucesso() {

        TypedQuery<FrequenciaAluno> query = mock(TypedQuery.class);
        List<FrequenciaAluno> listaEsperada = List.of(new FrequenciaAluno());

        when(em.createQuery(anyString(), eq(FrequenciaAluno.class)))
                .thenReturn(query);

        when(query.setParameter("turmaId", 1)).thenReturn(query);
        when(query.getResultList()).thenReturn(listaEsperada);

        List<FrequenciaAluno> resultado = professor.consultarFrequencia(em, 1);

        assertEquals(1, resultado.size());
        verify(em).createQuery(anyString(), eq(FrequenciaAluno.class));
    }


    // Atualizar frequência com sucesso
    @Test
    void atualizarFrequenciaSucesso() {

        FrequenciaAluno freq = new FrequenciaAluno();
        when(em.find(FrequenciaAluno.class, 10)).thenReturn(freq);

        professor.atualizarFrequencia(em, 10, "Nova", true, LocalDate.now());

        verify(tx).begin();
        verify(tx).commit();
        assertTrue(freq.isPresente());
        assertEquals("Nova", freq.getJustificativa());
    }


    // Soft delete com sucesso
    @Test
    void deletarFrequenciaSucesso() {

        FrequenciaAluno freq = new FrequenciaAluno();
        when(em.find(FrequenciaAluno.class, 1)).thenReturn(freq);

        professor.deletarFrequencia(em, 1);

        verify(tx).begin();
        verify(tx).commit();
        assertTrue(freq.isDeleted());
    }

    @Test
    void lancarNotaSucesso() {

        // mock da referência
        when(em.getReference(Aluno.class, 1)).thenReturn(new Aluno());
        when(em.getReference(Avaliacao.class, 1)).thenReturn(new Avaliacao());

        professor.lancarNota(
            em,
            8,
            "observacao",
            1,
            1
        );

        verify(tx).begin();
        verify(em).getReference(Avaliacao.class, 1);
        verify(em).getReference(Aluno.class, 1);

        verify(em).persist(any(NotaAluno.class));
        verify(tx).commit();
    }


    @Test
    void atualizarNotaSucesso(){

        NotaAluno nota = new NotaAluno();
        when(em.find(NotaAluno.class, 1)).thenReturn(nota);

        professor.atualizarNota(em, 10, "observacaonova",1);

        verify(tx).begin();
        verify(tx).commit();
        assertEquals(nota.getValor(), 10);
        assertEquals("observacaonova", nota.getObservacao());
    }

    //Consultar nota com sucesso
    @Test
    void consultarNotaSucesso() {

        TypedQuery<NotaConsultaDTO> query = mock(TypedQuery.class);

        List<NotaConsultaDTO> listaEsperada = List.of(
            new NotaConsultaDTO("João", "PROVA", 8.0, 7.5),
            new NotaConsultaDTO("Maria", "TRABALHO", 9.0, 8.2)
        );

        when(em.createQuery(anyString(), eq(NotaConsultaDTO.class)))
                .thenReturn(query);
        when(query.setParameter("turmaId", 10))
                .thenReturn(query);
        when(query.getResultList())
                .thenReturn(listaEsperada);

        // 4 — Chamar o método real
        List<NotaConsultaDTO> resultado = professor.consultarNota(em, 10);

        // 5 — Validações
        assertEquals(2, resultado.size());
        assertEquals("João", resultado.get(0).getAluno());


        verify(em).createQuery(anyString(), eq(NotaConsultaDTO.class));

        verify(query).setParameter("turmaId", 10);

        verify(query).getResultList();
    }


    @Test
    void deletarNotaSucesso(){

        NotaAluno nota = new NotaAluno();
        when(em.find(NotaAluno.class, 1)).thenReturn(nota);

        professor.deletarNota(em, 1);

        verify(tx).begin();
        verify(tx).commit();
        assertTrue(nota.isDeleted());


    }



 
}
    

