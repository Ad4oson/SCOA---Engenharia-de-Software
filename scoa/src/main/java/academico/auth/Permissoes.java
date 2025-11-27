package academico.auth;

public class Permissoes {

    public static void exigirAluno() {
        if (!Sessao.isAluno())
            throw new SecurityException("\nApenas alunos podem acessar esta funcionalidade.\n");
    }

    public static void exigirProfessor() {
        if (!Sessao.isProfessor())
            throw new SecurityException("\nApenas professores podem acessar esta funcionalidade.\n");
    }

    public static void exigirCoordenador() {
        if (!Sessao.isCoordenador())
            throw new SecurityException("\nApenas coordenadores podem acessar esta funcionalidade.\n");
    }

    public static void exigirLogado() {
        if (!Sessao.isLogado())
            throw new SecurityException("\nÉ necessário estar logado.\n");
    }
}
