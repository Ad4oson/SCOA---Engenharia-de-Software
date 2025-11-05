package main.java.scoa;

import java.time.LocalDateTime;

@Entity
@Table(name = "Usuario")
public class Usuario {

    //Nesta classe terá atributos que todos usuários terão em comum, porém a classe não se espelha em sua respectiva no BD.

    //login / senha / tipo_usuário / created_at / deleted
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Id
    private String login;
    private String senha;
    private TipoUsuario tipoUsuario;

    private String nome;
    private String cpf;
    private String rg;
    private String nascimento;
    private String polo;
    private String endereco;

    private LocalDateTime created_at;
    private boolean deleted;
    
}
