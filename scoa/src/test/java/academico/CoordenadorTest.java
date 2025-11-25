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
import jakarta.persistence.EntityTransaction;


public class CoordenadorTest {

    private Coordenador coordenador;
    private EntityManager em;
    private EntityTransaction tx;

    @BeforeEach
    void setup() {
        coordenador = new Coordenador();
        em = mock(EntityManager.class);
        tx = mock(EntityTransaction.class);

        when(em.getTransaction()).thenReturn(tx);

        // mocks para getReference()
        //when(em.getReference(Curso.class, 1)).thenReturn(new Curso());
    }



    
}
