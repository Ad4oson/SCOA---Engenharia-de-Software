package main.java.scoa;

import java.time.LocalTime;

@Entity
@Table(name = "Professor")
public class Professor extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String formacao;
    private String registros;
    private LocalDate dataAdmissao;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<ContatosProfessor> contatos;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<EspecialidadesProfessor> especialidades;

    @OneToOne(mappedBy = "coordenador")
    private Curso curso;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<Turma> turmas;

    
}
