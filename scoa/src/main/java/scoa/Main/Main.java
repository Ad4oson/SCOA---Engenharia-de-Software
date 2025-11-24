package scoa;

import java.io.IOException;
import java.util.List;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import jakarta.persistence.Entity;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import scoa.JPAUtil;
import scoa.Secretario;
import scoa.TipoUsuario;


public class Main { //Ler 

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean rodando = true;
        int choice = 0;

        while (rodando) {

            System.out.println("Abreo LoL!");

            System.out.println("\n---TELA DE LOGIN---\n");


            System.out.println("\nO que deseja fazer?\n1- Cadastrar Aluno | 2- Cadastrar Professor \n3- Consultar Aluno | 4- Consultar Professor \n0- Encerrar");
            String choiceNew = sc.nextLine();
            
            Secretario secretario = new Secretario();
            boolean cadastro = true;

            switch (choiceNew) {
                
                case "1":
                    System.out.println("Cadastrando aluno...\n");
                    //#region

                    System.out.println("Insira dados do aluno:");
                    while (cadastro) {

                        System.out.println("\nLogin: ");
                        String login = sc.nextLine(); 

                        System.out.println("\nSenha");
                        String senha = sc.nextLine(); 

                        System.out.println("\nNome: ");
                        String nome = sc.nextLine(); 

                        System.out.println("\nMatricula (nulo caso secretario): ");
                        String matricula = sc.nextLine();

                        System.out.println("\nCPF: ");
                        String cpf = sc.nextLine();

                        System.out.println("\nRG: ");
                        String rg = sc.nextLine();

                        System.out.println("\nData de Nascimento: ");
                        String nascimento = sc.nextLine();

                        System.out.println("\nEndereço: ");
                        String endereco = sc.nextLine();

                        secretario.CadastrarAluno(JPAUtil.getEntityManager(),login,senha, nome, cpf, rg, 
                        nascimento, endereco, matricula, 1, 1);

                        cadastro = false;
                    }

                    break;
                    //#endregion

                case "2":
                    System.out.println("Cadastrando professor...\n");
                    //#region

                    System.out.println("Insira dados do professor:");

                    while (cadastro) {

                        System.out.println("\nLogin: ");
                        String login = sc.nextLine(); 

                        System.out.println("\nSenha:");
                        String senha = sc.nextLine(); 

                        System.out.println("\nNome: ");
                        String nome = sc.nextLine(); 

                        System.out.println("\nCPF: ");
                        String cpf = sc.nextLine();

                        System.out.println("\nRG: ");
                        String rg = sc.nextLine();

                        System.out.println("\nData de Nascimento: ");
                        String nascimento = sc.nextLine();

                        System.out.println("\nEndereço: ");
                        String endereco = sc.nextLine();

                        System.out.println("\nFormação: ");
                        String formacao = sc.nextLine();

                        System.out.println("\nRegistros: ");
                        String registros = sc.nextLine();
                        
                        System.out.println("\nData de Admissão: ");
                        LocalDate dataAdmissaoReal = null;
                        while(dataAdmissaoReal == null){
                            String dataAdmissao = sc.nextLine();
                            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            DateTimeFormatter fmt2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    

                                try {dataAdmissaoReal = LocalDate.parse(dataAdmissao, fmt);} catch (DateTimeParseException e1){}
                                try {dataAdmissaoReal = LocalDate.parse(dataAdmissao);} catch (DateTimeParseException e2) {}
                                try {dataAdmissaoReal = LocalDate.parse(dataAdmissao, fmt2);}
                                catch (DateTimeParseException e3) {
                                    System.out.println("\nFormado inválido, tente o formato dd/MM/yyyy:\n");
                                }

                        }
                        

                        secretario.CadastrarProfessor(JPAUtil.getEntityManager(), login, senha, nome, cpf, rg, 
                        nascimento, endereco, formacao, registros, dataAdmissaoReal);

                        cadastro = false;
                    }
                    
                case "3":
                    System.out.println("\nConsultar Aluno...");
                    System.out.println("\nFiltros: 1- Nome | 2- Curso | 0- Nenhum\n");
                    String filtro = sc.nextLine();
                    String filtroValor = "";
                    if (!"0".equals(filtro)) {
                        System.out.print("\nDigite o valor para o filtro: ");
                        filtroValor = sc.nextLine();
                    }
                    System.out.println("\nOrdenação: 1- Alfabética | 2- Curso | 0- Nenhum\n");
                    String order = sc.nextLine();

                    if (filtroValor.isEmpty()) {

                        if ("0".equals(order)) {
                            try {
                                TypedQuery<Aluno> q = JPAUtil.getEntityManager().createQuery(
                                    "SELECT a FROM Aluno a", Aluno.class);
                                List<Aluno> resultadoAluno = q.getResultList();
                                resultadoAluno.forEach(a -> System.out.println("\n" + a.getNome() + " | " + a.getCpf() + " | " + a.getCurso().getNome()) ); 
                                //Faz trabalho de um FOR
                                
                            }
                            catch (NoResultException e1) {
                                System.out.println("\nNenhum resultado encontrado!\n");
                            }
                            
                        }

                    }
                    break;

                
                }



            //#region
            try {
                choice = System.in.read();
            }
            catch (IOException e) {
                System.out.println("Ocorreu um erro!");
            }
          

            if (choice == '0') rodando = false;

            try {
                 System.in.read();
            }
            catch (IOException e) { 
                System.out.println("Ocorreu um erro 2!");
            }
           //#endregion
        }

        sc.close();
    }
}