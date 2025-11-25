package academico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;

public class ProfessorTest {


    private Professor professor;
    private EntityManager em;
    private EntityTransaction tx;

    @BeforeEach
    void setup() {
        professor = new Professor();
        em = mock(EntityManager.class);
        tx = mock(EntityTransaction.class);

        when(em.getTransaction()).thenReturn(tx);

        // mocks para getReference()
        //when(em.getReference(Curso.class, 1)).thenReturn(new Curso());
    }

    @Test
    void lancarPauta_DevePersistirCorretamente() {

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
    void lancarPautaTurmaInexistente() {

      

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
    
}
