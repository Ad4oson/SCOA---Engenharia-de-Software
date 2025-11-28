package academico.view;

import academico.auth.Autenticacao;
import academico.auth.Sessao;
import academico.model.Usuario;
import academico.model.TipoUsuario;

import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField campoLogin;
    private JPasswordField campoSenha;
    private JPanel painelLogin;
    private JButton botaoLogin;

    // Caminho da imagem de fundo (altere para o seu arquivo)
    private Image imagemFundo = new ImageIcon("imgs/background.png").getImage();

    public LoginView(EntityManager em) {

        setTitle("Login do Sistema Acadêmico");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel de fundo com imagem
        JPanel painelFundo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        painelFundo.setLayout(new GridBagLayout());
        add(painelFundo);

        // Painel interno (com cor alterável)
        painelLogin = new JPanel();
        painelLogin.setBackground(new Color(255, 255, 255, 180)); // branco semi-transparente
        painelLogin.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelLogin.setLayout(new GridLayout(3, 1, 10, 10));
        painelLogin.setPreferredSize(new Dimension(250, 150));

        campoLogin = new JTextField();
        campoSenha = new JPasswordField();

        botaoLogin = new JButton("Entrar");
        botaoLogin.setBackground(new Color(0, 120, 215)); // cor alterável
        botaoLogin.setForeground(Color.WHITE);

        painelLogin.add(campoLogin);
        painelLogin.add(campoSenha);
        painelLogin.add(botaoLogin);

        painelFundo.add(painelLogin);

        // Ação do botão
        botaoLogin.addActionListener(e -> {
            String login = campoLogin.getText();
            String senha = new String(campoSenha.getPassword());

            System.out.println("\nLOGIN: " + login + " | SENHA: " + senha + "\n");

            Autenticacao auth = new Autenticacao();
            Usuario usuario = auth.login(em, login, senha);

            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "Login inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                abrirTelaUsuario();
            }
        });

        setVisible(true);
    }


    // Abre nova tela com fundo cinza e tipo do usuário ao centro
    private void abrirTelaUsuario() {

        TipoUsuario tipo = Sessao.getUsuarioLogado().getTipoUsuario();
        String texto = "Usuário logado: " + tipo.name();

        JFrame tela = new JFrame("Bem-vindo");
        tela.setSize(400, 300);
        tela.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel();
        painel.setBackground(Color.GRAY);

        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        painel.add(label);
        tela.add(painel);

        tela.setVisible(true);
        dispose(); // fecha tela de login
    }

}
