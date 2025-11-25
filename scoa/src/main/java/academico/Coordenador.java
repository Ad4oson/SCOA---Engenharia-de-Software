package academico;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


public class Coordenador extends Professor{
    //Não necessária integração com JPA, apenas utilizar construtor com base em Professor


    //Construtor base para JPA
    public Coordenador () {}


    // Construtor do Coordenador, utilizando dados de um professor existente
    public Coordenador(Professor professor) {
        // Usar construtor padrao
        super();
        if (professor == null) return;

        // campos do usuario
        setId(professor.getId());
        setLogin(professor.getLogin());
        setSenha(professor.getSenha());
        setTipoUsuario(professor.getTipoUsuario());
        setNome(professor.getNome());
        setCpf(professor.getCpf());
        setRg(professor.getRg());
        setNascimento(professor.getNascimento());
        setPolo(professor.getPolo());
        setEndereco(professor.getEndereco());
        setCreated_at(professor.getCreated_at());
        setDeleted(professor.isDeleted());

        // campos do professor
        setFormacao(professor.getFormacao());
        setRegistros(professor.getRegistros());
        setDataAdmissao(professor.getDataAdmissao());
        setSalario(professor.getSalario());

        // relações
        setContatos(professor.getContatos());
        setEspecialidades(professor.getEspecialidades());
        setCurso(professor.getCurso());
        setTurmas(professor.getTurmas());

    }


    public void cadastrarDisciplina(EntityManager em,
        String nome,
        String ementa,
        Integer carga_horaria,
        Integer creditos,
        String bibliografia,
        Integer disciplina_pre,
        List<Integer> cursos,
        List<Integer> turmas)
    {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Disciplina disciplinaNovo = new Disciplina();
            

            if (disciplina_pre != null) {
                Disciplina disciplinaP = em.getReference(Disciplina.class, disciplina_pre);
                disciplinaNovo.setDisciplinapre(disciplinaP);
            }

            ArrayList<Curso> cursosL = new ArrayList<>();
            if (cursos !=null){
                try {
                    for(Integer id : cursos){
                        Curso curso = em.getReference(Curso.class, id);
                        cursosL.add(curso);
                    }

                    for (Curso c: cursosL) {
                        c.getDisciplinas().add(disciplinaNovo);
                        em.merge(c);
                    }

                }
                catch (EntityNotFoundException e1) {
                    System.out.println("\nCurso não encontrado!\n");
                    tx.rollback();
                    return;
                }
            }

            ArrayList<Turma> turmasL = new ArrayList<>();
            if (turmas !=null){
                try {
                    for(Integer id : turmas){
                        Turma turma = em.getReference(Turma.class, id);
                        turmasL.add(turma);
                    }

                    for (Turma t : turmasL) {
                        t.setDisciplina(disciplinaNovo);
                        em.merge(t);
                    }

                }
                catch (EntityNotFoundException e1) {
                    System.out.println("\nTurma não encontrada!\n");
                    tx.rollback();
                    return;
                }
            }


            disciplinaNovo.setNome(nome);
            disciplinaNovo.setEmenta(ementa);
            disciplinaNovo.setCarga_horaria(carga_horaria);
            disciplinaNovo.setCreditos(creditos);
            disciplinaNovo.setBibliografia(bibliografia);
            disciplinaNovo.setCursos(cursosL);

            disciplinaNovo.setCreated_at(LocalDateTime.now());
            disciplinaNovo.setDeleted(false);

            em.persist(disciplinaNovo);

            tx.commit();
        }
        catch(Exception e){
            if(tx.isActive()) tx.rollback();
            System.out.println("ERRO DENTRO DA CLASSE!!!!");
            
            e.printStackTrace();
        }


    }



}
