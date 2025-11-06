package main.java.scoa;

public class Coordenador extends Professor{
    //Não necessária integração com JPA, apenas utilizar construtor com base em Professor



    // Construtor do Coordenador, utilizando dados de um professor existente
    public Coordenador(Professor professor) {
        super(professor.getId(), professor.getNome(), professor.getCpf(), professor.getRg(),
              professor.getDataNascimento(), professor.getEmail(), professor.getTelefone(),
              professor.getEndereco(), professor.getFormacao(), professor.getEspecializacao(),
              professor.getExperiencia(), professor.getDataAdmissao(), professor.getSalario(),
              professor.isDeleted());
        
    }
}
