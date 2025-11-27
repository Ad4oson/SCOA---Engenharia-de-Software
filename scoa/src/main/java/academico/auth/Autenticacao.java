package academico.auth;


import academico.model.Usuario;
import jakarta.persistence.EntityManager;

public class Autenticacao {

    public Usuario login(EntityManager em, String login, String senha) {

        String jpql = """
            SELECT u FROM Usuario u
            WHERE u.login = :login AND u.senha = :senha AND u.deleted = false
        """;

        try {
            Usuario usuario = em.createQuery(jpql, Usuario.class)
                                .setParameter("login", login)
                                .setParameter("senha", senha)
                                .getSingleResult();
            if (usuario != null) Sessao.iniciarSessao(usuario);
            return usuario;
        }
        catch (Exception e){
            System.out.println("\nUsuário não encontrado!\n");
        }

        return null;
    }

    public void logout() {
        Sessao.encerrarSessao();
    }

    
}
