package scoa;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

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

    @Test
    void testCadastrarAluno() {
        secretario.CadastrarAluno(
            em,
            "loginUser",
            "123",
            "NomeTeste",
            "11111111111",
            "2222222",
            "2001-01-01",
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
        assertNotNull(aluno.getCreated_at());
        assertEquals(aluno.isDeleted(), false);


        // Verifica commit
        verify(tx).commit();
    }
}
