package academico.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


import academico.Aluno;
import academico.Curso;
import academico.JPAUtil;
import academico.Professor;
import academico.Secretario;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;



public class Main { //Ler 

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in, "UTF-8");
        boolean rodando = true;

        while (rodando) {

            System.out.println("Abreo LoL!");

            System.out.println("\n---TELA DE LOGIN---\n");


            System.out.println("\nO que deseja fazer?\n1- Cadastrar Aluno | 2- Cadastrar Professor \n3- Consultar Aluno | 4- Consultar Professor \n0- Encerrar");
            String choiceNew = sc.nextLine();
            
            Secretario secretario = new Secretario();
            boolean cadastro = true;
            int choice = 0;

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

                        System.out.println("\nMatricula: ");
                        String matricula = sc.nextLine();

                        System.out.println("\nCPF: ");
                        String cpf = sc.nextLine();

                        System.out.println("\nRG: ");
                        String rg = sc.nextLine();

                        System.out.println("\nData de Nascimento: ");
                        LocalDate nascimento = LocalDate.parse(sc.nextLine());

                        System.out.println("\nEndereço: ");
                        String endereco = sc.nextLine();

                        System.out.println("\nCurso: ");
                        String cursoT = sc.nextLine();
                        int curso = 0;
                        try {
                            TypedQuery<Curso> q = JPAUtil.getEntityManager().createQuery(
                                "SELECT c FROM Curso c WHERE c.nome = :nome", Curso.class
                            );
                            q.setParameter("nome", cursoT);
                            Curso resultado = q.getSingleResult();
                            curso = resultado.getId();
                        }
                        catch(NoResultException e1) {
                            System.out.println("\nNenhum resultado encontrado!\n");
                            break;
                        }


                        secretario.CadastrarAluno(JPAUtil.getEntityManager(),login,senha, nome, cpf, rg, 
                        nascimento, endereco, matricula, curso, 1);

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
                        LocalDate nascimentoReal = null;
                        while(nascimentoReal == null){
                            String nascimento = sc.nextLine();
                            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            DateTimeFormatter fmt2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    

                            try {nascimentoReal = LocalDate.parse(nascimento, fmt);} catch (DateTimeParseException e1){}
                            try {nascimentoReal = LocalDate.parse(nascimento);} catch (DateTimeParseException e2) {}
                            try {nascimentoReal = LocalDate.parse(nascimento, fmt2);}
                            catch (DateTimeParseException e3) {
                                    System.out.println("\nFormado inválido, tente o formato dd/MM/yyyy:\n");
                                }

                        }

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
                        
                        ArrayList<Integer> turmas = new ArrayList<>();
                        turmas.add(1);

                        secretario.CadastrarProfessor(JPAUtil.getEntityManager(), login, senha, nome, cpf, rg, 
                        nascimentoReal, endereco, formacao, registros, dataAdmissaoReal, turmas);

                        cadastro = false;
                    }
                    break;
                    
                case "3":
                    System.out.println("\nConsultar Aluno...");
                    System.out.println("\nFiltros: 1- Nome | 0- Nenhum\n");
                    String filtro = sc.nextLine();
                    String filtroValor = "";
                    String order = "";

                    if (!"0".equals(filtro)) {
                        System.out.print("\nDigite o valor para o filtro: ");
                        filtroValor = sc.nextLine();
                    }
                    else {
                        System.out.println("\nOrdenação: 1- Alfabética | 2- Curso | 0- Nenhum\n");
                        order = sc.nextLine();
                    }

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
                        else if ("1".equals(order)) {
                            try {
                                TypedQuery<Aluno> q = JPAUtil.getEntityManager().createQuery(
                                    "SELECT a FROM Aluno a ORDER BY a.nome", Aluno.class);
                                List <Aluno> resultadoAluno = q.getResultList();
                                resultadoAluno.forEach(a -> System.out.println("\nNome: " + a.getNome() + " | Matrícula: " + a.getMatricula() + " | Curso: " + a.getCurso().getNome()));

                            }
                            catch (NoResultException e2) {
                                System.out.println("\nNenhum resultado encontrado!\n");
                            }
                        }
                        else if ("2".equals(order)) {
                            try {
                                TypedQuery<Aluno> q = JPAUtil.getEntityManager().createQuery(
                                    "SELECT a FROM Aluno a INNER JOIN a.curso c ORDER BY c.nome", Aluno.class
                                );
                                List<Aluno> resultadoAluno = q.getResultList();
                                resultadoAluno.forEach(a -> System.out.println("\nNome: " + a.getNome() + " | Matrícula: " + a.getMatricula() + " | Curso: " + a.getCurso().getNome()));

                            }
                            catch (NoResultException e1) {
                                System.out.println("\nNenhum resultado encontrado!\n");
                            }
                        }

                    }
                    else {
                        
        
                        try {
                            TypedQuery<Aluno> q = JPAUtil.getEntityManager().createQuery(
                                "SELECT a FROM Aluno a INNER JOIN a.curso c WHERE a.nome = :nome"
                                ,Aluno.class);
                            q.setParameter("nome", filtroValor);
                            List<Aluno> resultado = q.getResultList();
                            resultado.forEach(a-> System.out.println("\nNome: " + a.getNome() + " | Matrícula: " + a.getMatricula() + " | Curso: " + a.getCurso().getNome()));

                        }
                        catch(NoResultException e1) {}
                        
                    }
                    break;
                
                case "4":
                    while(true){
                        System.out.println("\nConsultar Professor...\n");
                        System.out.println("1- Lista de Professores | 2- Busca por nome\n");
                        String choice1 = sc.nextLine();
                        if ("1".equals(choice1)){

                            try {
                                TypedQuery<Professor> q = JPAUtil.getEntityManager().createQuery(
                                    "SELECT p FROM Professor p", Professor.class);
                                List<Professor> resultado = q.getResultList();

                                resultado.forEach(a-> System.out.println("\nNome: " + a.getNome() + " | CPF: " + a.getCpf()));


                            }
                            catch (Exception e1) {
                                System.out.println("\nErro encontrado!\n");
                            }
        
                            break;
                        }
                        else if ("2".equals(choice1)) {

                            try {

                                System.out.print("\nDigite o nome desejado: ");
                                String nomeP = sc.nextLine();
                                TypedQuery<Professor> q = JPAUtil.getEntityManager().createQuery(
                                    "SELECT p FROM Professor p WHERE p.nome = :nomeP", Professor.class);
                                q.setParameter("nomeP", nomeP);
                                List<Professor> resultado = q.getResultList();

                                if (!resultado.isEmpty()) {
                                    resultado.forEach(a-> System.out.println("\nNome: " + a.getNome() + " | CPF: " + a.getCpf()));
                                    System.out.println("\nNenhum resultado encontrado!");
                                }
                                else System.out.println("\nNenhum resultado encontrado!");
                            }
                            catch (Exception e2){
                                System.out.println("\nErro encontrado!\n");
                            }
                            break;
                        }
                        else {
                            System.out.println("\nDígito incorreto, por favor tente novamente.\n");
                        }
                    }
                    break;
                case "0" :
                    System.out.println("\nEncerrando programa...");
                    rodando = false;
                    break;
                
                }



            //#region

            if (rodando) {
                try {
                    choice = System.in.read();
                }
                catch (IOException e) {
                    System.out.println("Ocorreu um erro!");
                }

                try {
                    System.in.read();
                }
                catch (IOException e) { 
                    System.out.println("Ocorreu um erro 2!");
                }
            //#endregion
           }
        }

        sc.close();
    }
}