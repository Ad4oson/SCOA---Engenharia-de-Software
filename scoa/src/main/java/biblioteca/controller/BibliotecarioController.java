package biblioteca.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import academico.model.Aluno;
import academico.model.JPAUtil;
import academico.model.Professor;
import academico.model.Sala;
import academico.model.TipoUsuario;
import academico.model.Usuario;
import almoxarifado.model.BemPatrimonial;
import biblioteca.model.Obra;
import biblioteca.model.statusEmprestimo;
import biblioteca.model.Bibliotecario;
import biblioteca.model.Emprestimo;
import biblioteca.model.statusObra;
import biblioteca.model.tipoUsuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class BibliotecarioController {

    EntityManager em = JPAUtil.getEntityManager();


    //#region CRUD Obra

    public void cadastrarObra(String titulo, String autor, String tipomaterial, String editora, String anoPublicacao,
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


    public void cadastrarBibliotecario(String nome, String cpf, String rg, LocalDate nascimento, String endereco, String email,
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

            Usuario usuario = new Usuario();
            usuario.setTipoUsuario(TipoUsuario.BIBLIOTECA);
            if (login != null) usuario.setLogin(login);
            if (senha != null) usuario.setSenha(senha);
            bibliotecario.setUsuario(usuario);

            bibliotecario.setCreated_at(LocalDateTime.now());
            bibliotecario.setDeleted(false);


            em.persist(bibliotecario);
            em.persist(usuario);
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
            if (login != null) bibliotecario.getUsuario().setLogin(login);
            if (senha != null) bibliotecario.getUsuario().setSenha(senha);

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

    public void enviarNotificacao(){


    }
    //#endregion


    //#region CRUD EMPRESTIMO



    public void cadastrarEmprestimo(LocalDateTime data_emprestimo, LocalDateTime previsao_devolucao, LocalDateTime prazo_devolucao,
        statusEmprestimo status, Integer obra_id, Integer usuarioId){

        
        try {
            em.getTransaction().begin();
            Emprestimo emprestimo = new Emprestimo();
            
            if (data_emprestimo != null) emprestimo.setData_emprestimo(data_emprestimo);
            if (previsao_devolucao != null) emprestimo.setPrevisao_devolucao(previsao_devolucao);
            if (prazo_devolucao != null) emprestimo.setPrazo_devolucao(prazo_devolucao);
            if (status != null) emprestimo.setStatus(status);
            

            Obra obra = em.find(Obra.class, obra_id);
            Usuario usuario = em.find(Usuario.class, usuarioId);

            emprestimo.setObra(obra);
            emprestimo.setUsuario(usuario);

            emprestimo.setCreated_at(LocalDateTime.now());
            emprestimo.setDeleted(false);

            em.persist(emprestimo);
            em.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        }

    }

    public void atualizarEmprestimo(Integer emprestimoId, statusEmprestimo status, Boolean deleted){

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Emprestimo emprestimo = em.find(Emprestimo.class, emprestimoId);
            
            
            if ( status != null) emprestimo.setStatus(status);
            if (deleted == true) emprestimo.setDeleted(true);


            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }



    }

    public List<Emprestimo> consultarEmprestimo(LocalDateTime data){


         
        if (data != null) {
            System.out.println("\nPESQUISANDO EMPRESTIMO\n");
                
            String jpql = """
                    SELECT e
                    FROM Emprestimo e
                    WHERE e.data_emprestimo > :data AND e.deleted = false
                    ORDER BY e.data_emprestimo ASC
            """;

            return em.createQuery(jpql, Emprestimo.class)
            .setParameter("data", data)
            .getResultList();

        }
        else {

            String jpql = """
                    SELECT e
                    FROM Emprestimo e
                    WHERE e.deleted = false
                    ORDER BY e.data_emprestimo ASC
            """;

            return em.createQuery(jpql, Emprestimo.class)
            .getResultList();
        }


    }
    //#endregion



    //#region Consultar usuario (professor / aluno)

    //consulta lista ou por ID
    public List<Professor> consultarProfessores(String professorId){
        
        if (professorId != null){

            String jpql = """
                SELECT p
                FROM Professor p
                JOIN usuario u
                WHERE p.deleted = false AND p.id = :id
                ORDER BY p.nome
            """;

            return em.createQuery(jpql, Professor.class)
                .setParameter("id", professorId)
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



    //consulta lista ou por ID
    public List<Aluno> consultarAlunos(String alunoId){
        
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
    


    //#endregion

}
