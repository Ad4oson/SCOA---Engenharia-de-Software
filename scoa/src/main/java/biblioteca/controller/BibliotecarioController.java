package biblioteca.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import academico.model.JPAUtil;
import academico.model.Sala;
import biblioteca.model.Obra;
import biblioteca.model.Bibliotecario;
import biblioteca.model.statusObra;
import jakarta.persistence.EntityManager;

public class BibliotecarioController {

    EntityManager em = JPAUtil.getEntityManager();


    //#region CRUD Obra

    public void criarObra(String titulo, String autor, String tipomaterial, String editora, String anoPublicacao,
         String localizacao, statusObra status) {
        
        try {
            em.getTransaction().begin();
            Obra obra = new Obra();
            
            if (titulo != null) obra.setTitulo(titulo);
            if (autor != null) obra.setAutor(autor);
            if (tipomaterial != null) obra.setTipomateria(tipomaterial);
            if (editora != null) obra.setEditora(editora);
            if (anoPublicacao != null) obra.setAnoPublicacao(anoPublicacao);
            if (localizacao != null) obra.setLocalizacao(localizacao);
            if (status != null) obra.setStatus(status);
            
            obra.setCreated_at(LocalDateTime.now());
            obra.setDeleted(false);

            em.persist(obra);
            em.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        }

    }


    public List<Obra> buscarObra(String titulo) {
        
        
        if (titulo != null) {
            System.out.println("\nPESQUISANDO OBRA\n");
                
            String jpql = """
                SELECT o
                FROM Obra o
                WHERE o.deleted = false AND o.titulo = :obraTitulo
            """;

            return em.createQuery(jpql, Obra.class)
            .setParameter("obraTitulo", titulo)
            .getResultList();

        }
        else {

            String jpql = """
            SELECT o
            FROM Obra o
            WHERE o.deleted = false
            """;

            return em.createQuery(jpql, Obra.class)
            .getResultList();
        }

        
    }


    public void atualizarObra(Integer obraId, String titulo, String autor, String tipomaterial, String editora, String anoPublicacao,
         String localizacao, statusObra status, Boolean deleted) {
        em.getTransaction().begin();

        try {
        Obra obraT = em.find(Obra.class, obraId);

        if (titulo != null) obraT.setTitulo(titulo);
        if (autor != null) obraT.setAutor(autor);
        if (tipomaterial != null) obraT.setTipomateria(tipomaterial);
        if (editora != null) obraT.setEditora(editora);
        if (anoPublicacao != null) obraT.setAnoPublicacao(anoPublicacao);
        if (localizacao != null) obraT.setLocalizacao(localizacao);
        if (status != null) obraT.setStatus(status);

        if (deleted == true) obraT.setDeleted(true);
        
        em.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        }
    }


    //#endregion


    //#region CRUD Bibliotecario


    public void criar(String nome, String cpf, String rg, LocalDate nascimento, String endereco, String email,
         String contato, String login, String senha) {
        
        try {
            em.getTransaction().begin();

            Bibliotecario bibliotecario = new Bibliotecario();

            if (nome != null) bibliotecario.setNome(nome);
            if (cpf != null) bibliotecario.setCpf(cpf);
            if (rg != null) bibliotecario.setRg(rg);
            if (nascimento != null) bibliotecario.setNascimento(nascimento);
            if (endereco != null) bibliotecario.setEndereco(endereco);
            if (email != null) bibliotecario.setEmail(email);
            if (contato != null)bibliotecario.setContato(contato);
            if (login != null) bibliotecario.setLogin(login);
            if (senha != null) bibliotecario.setSenha(senha);

            bibliotecario.setCreated_at(LocalDateTime.now());
            bibliotecario.setDeleted(false);


            em.persist(bibliotecario);
            em.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback();

        }
    }

    public List<Bibliotecario> buscarBibliotecario(String cpf) {
        
        
        if (cpf != null) {
            System.out.println("\nPESQUISANDO BIBLIOTECARIO\n");
                
            String jpql = """
                SELECT b
                FROM Bibliotecario b
                WHERE b.deleted = false AND b.cpf = :cpf
            """;

            return em.createQuery(jpql, Bibliotecario.class)
            .setParameter("cpf", cpf)
            .getResultList();

        }
        else {

            String jpql = """
            SELECT b
            FROM Bibliotecario b
            WHERE b.deleted = false
            """;

            return em.createQuery(jpql, Bibliotecario.class)
            .getResultList();
        }


    }


    public void atualizarBibliotecario(Integer bibliotecarioId, String nome, String cpf, String rg, LocalDate nascimento, 
        String endereco, String email, String contato, String login, String senha, Boolean deleted) {
        
        try {
            em.getTransaction().begin();
            
            Bibliotecario bibliotecario = em.find(Bibliotecario.class, bibliotecarioId);
            
            if (nome != null) bibliotecario.setNome(nome);
            if (cpf != null) bibliotecario.setCpf(cpf);
            if (rg != null) bibliotecario.setRg(rg);
            if (nascimento != null) bibliotecario.setNascimento(nascimento);
            if (endereco != null) bibliotecario.setEndereco(endereco);
            if (email != null) bibliotecario.setEmail(email);
            if (contato != null)bibliotecario.setContato(contato);
            if (login != null) bibliotecario.setLogin(login);
            if (senha != null) bibliotecario.setSenha(senha);

            if(deleted == true) bibliotecario.setDeleted(true);
            
            em.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        }
    }


    //#endregion

    //#region CRUD notificacao

    


    //#endregion

}
