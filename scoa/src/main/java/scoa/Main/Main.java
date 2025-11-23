package scoa;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import jakarta.persistence.Entity;
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
import main.java.scoa.JPAUtil;
import main.java.scoa.Secretario;
import main.java.scoa.TipoUsuario;


public class Main { //Ler 

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean rodando = true;
        int choice = 0;

        while (rodando) {

            System.out.println("Abreo LoL!");
            System.out.println("\nO que deseja fazer?\n1- Cadastrar Aluno | 2- Cadastrar Professor | 3- Consultar Aluno | 4- Consultar Professor");
            String choiceNew = sc.nextLine();
            
            switch (choiceNew) {
                case "1":
                    System.out.println("Criando aluno...\n");
                    Secretario secretario = new Secretario();

                    System.out.println("Insira dados do aluno:");
                    boolean cadastro = true;
                    while (cadastro) {

                        System.out.println("\nLogin: ");
                        String login = sc.nextLine(); 

                        System.out.println("\nSenha");
                        String senha = sc.nextLine(); 

                        System.out.println("\nNome: ");
                        String nome = sc.nextLine(); 

                        System.out.println("\nTipoUsuario: ");
                        String entradaTipo = sc.nextLine().toUpperCase();
                        TipoUsuario tipo = TipoUsuario.valueOf(entradaTipo);

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