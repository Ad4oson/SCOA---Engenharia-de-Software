package auth;

import academico.model.TipoUsuario;
import academico.model.Usuario;

public class Sessao {
    
    private static Usuario usuarioLogado;


    
    public static void iniciarSessao(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void encerrarSessao() {
        usuarioLogado = null;
    }

    public static boolean isLogado() {
        return usuarioLogado != null;
    }

    public static boolean isProfessor() {
        return isLogado() && usuarioLogado.getTipoUsuario() == TipoUsuario.PROFESSOR;
    }

    public static boolean isAluno() {
        return isLogado() && usuarioLogado.getTipoUsuario() == TipoUsuario.ALUNO;
    }

    public static boolean isCoordenador() {
        return isLogado() && usuarioLogado.getTipoUsuario() == TipoUsuario.COORDENADOR;
    }


    
}


