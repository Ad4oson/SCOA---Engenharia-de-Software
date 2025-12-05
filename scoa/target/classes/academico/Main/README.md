# Anotações

- Lembrar de definir colunas como not null, com: @Column(nullable = false)

- Definir tamanho do varchar com @Column(length = X), pode adicionar ao nullable também

- Lembrar de criar classes para login e senha futuramente

- Criar tabela "feedback" com sugestões/reclamações dos alunos. Campos: aluno_id, text

- Lembrar de adicionar nome no aluno BD

- Adicionar matrícula para aluno no BD

- Criado classe genérica Usuário e extensões dela como: aluno -> secretário, professor -> coordenador, ALTERAR NO DC. 

- Criar "entidade" >>usuário<< no BD, com login / senha / tipo_usuário / created_at / deleted, alterar no Diagrama BD. FAZER LOGIN SER MATRICULA.

- Melhorar a tabela "funcionários" e adicionar sempre secretários dentro de funcionários

- Alterar no DIAGRAMA UC Secretário acadêmico cria cursos, coordenador não

- Revisar todos os tipos dos dados do BD e das docummentações e do código, garantindo que estejam congruentes

- Alterar nos diagramas de BD e no BD em si: enums = varchar(20)

- Tornar "enums" do diagrama de classes em enums e não varchar(20)

- Revisar todos os "not null" do BD, alterar na documentação do BD

- TURMA: numero de vagas = nullable

- PADRONIZAR LocalDateTime PARA TODAS TABELAS EM CREATED_AT

-não precisa separar em pacotes muito se estiver muito ruim

-Generalizar funcoes de cadastro com CRUD

-apenas 1 teste para funções principais, apenas do academico

- criar sugestoes/reclamações no BD e JAVA

- criar requisições no BD e JAVA

- criar função de criação de requisições para Aluno

- criar função de manipulação de requisições para Secretário

- ^alterar no diagrama de classes, alterar no diagrama de BD, alterar no diagrama de UC

-criar tabela testes 

- adicionar classes de autenticação no Diagrama de Classes

- CRUD SALA

- LOGIN NÃO VAI SER REPETÍVEL, SENHA VAI

- COLOCAR LOGIN NAS COLUNAS DE ENTIDADES

- LOGIN NÃO PODERÁ SER EDITÁVEL, APENAS CRIADO

----------- GRAVE GRAVE GRAVE: alterar sql em SALVAR BUTTON, na GUI colocar campo ID não-editavel para referencia permanente

-CREATE TABLE Curso (
id serial,
nome varchar(50) not null,
modalidade varchar(50),
turno turnoType not null,
cargaHoraria smallint not null,
periodos smallint not null,
prazoConclusao date --not null--,
coordenador_id int,
descricao text,
portaria varchar(30),
status statusCurso not null


----------ESTRUTURA MVC------------
✔ MODEL
Contém as entidades JPA (Professor, Turma, PautaDeAula etc.).
É "onde os dados moram".

✔ CONTROLLER
Contém as regras de negócio.
É quem recebe as ações da View e usa o EntityManager para alterar o banco.

Aqui ficam métodos como:

cadastrarTurma

atualizarDisciplina

lançarPauta

excluirProfessor

etc.

✔ VIEW
É a interface com o usuário final — pode ser:

terminal

Swing

HTML/JSF

ou seus testes (JUnit atuando como View)

A View apenas chama os métodos do Controller.


### Dúvidas perguntar professor

- Necessário criar caso de uso consultar turmas? (criado o método)

- Necessário criar caso de uso consultar professores? (criado o método)

- Necessário criar caso de uso filtrar alunos/professor, ordenar aluno/professor? (criado o método)

- 



### Notas importantes do log de execução com função cadastrar aluno

Implementar equals()/hashCode() nas classes de chave composta: adicione equals() e hashCode() nas classes usadas como composite-id (por exemplo ContatosAluno, EspecialidadesProfessor, TransacaoFinanceira).

Desabilitar hbm2ddl.auto=update em produção: use migrações controladas (Flyway, Liquibase) para evitar alterações automáticas perigosas.

Substituir pool embutido por um pool de produção: configure HikariCP (ou similar) em persistence.xml/application.properties para maior robustez.


### Alterar diagrama de UserCase conforme comentado pelo professor na entrega do trabalho:

- Modelo de caso de uso ainda apr4senta problemas com as extensões e relacionamentos. Eliminar as dependências entre os módulos. Optar por informar isso textualmente na descrição dos UCs.

### Aluno:

- Alterar coluna nascimento de varchar() para DATE

### Avaliacao:

- Remover peso


### Frequencia Aluno:

- Lembrar de alterar nomes no BD

### Boleto Pagamento:

- CRIADO ENUM PARA STATUS PAGAMENTO, ALTERAR NO BD

- Lembrar de retirar tabela pagamentotransacao, adicionar pagamento_id em transacao

- Lembrar de retirar tabela boletopagamento, adicionar boleto_id em pagamento

- id como PK em pagamento,, pagamento_id como PK em transacao

### Bolsa financiamento:

- Remover tabela bolsamensalidade, adicionar bolsa_id em mensalidade.

- bolsa_id nullable

### Contatos Professor / Contatos Aluno:

- Adicionar coluna tipo (email/telefone). Alterar Diagramas. Alterar views / controller (secretario)

- Adicionar unique em ambos contatos


### Professor:

- Adicionar coluna int salario



### Curso:

-  EDITAR NO BD medalidade -> mensalidade 

### Nota ALUNO:

- Remover media final/parcial, deixar apenas media

- Remoção total de "observacao"

- Tornar aluno_id e avaliacao_id em unique juntos


### Documentos Aluno:

- Criar no BD tipo_documento VARCHAR, alterar no diagrama de classes, not null

### Professor:

- ALTERAR NO BD, transformar CPF e RG em VARCHAR

- Adicionar Polo no BD e DC

- Transformar login e senha em PK compartilhada OU fazer o controle de login e senha através da tabela usuário

### Pauta de Aula

- turma_id -> nullable

### Transação Financeira:

-   //data_transacao como  LocalDateTIME

- //pagamento_id como PK, já que um mesmo pagamento não pode ter mais de uma transação e vice versa

- //Acrescentado pagamento_id como PK

### Turma

- Adicionar nome para turma

### Requisicaodocumento

- Alterado para Requisicao

- Colocar tipo requisicao: BD =  varchar(20) , JAVA = enum



### MÓDULO BIBLIOTECA

- USUARIO -> adicionar login/senha, remover tipo de usuario. Único usuário será o bibliotecário.

- Alterar RF cadastrar usuários, remover tipos de usuário.

- REMOVER TODAS ALUSÕES A TIPO DE USUARIO. HAVERÁ APENAS 1 TIPO: BIBLIOTECARIO. O resto dos usuários serão entidades do módulo academico(prof, aluno)

- TIPO USUARIO ADICIONADO EM >>NOTIFICACAO<<, destinatario_id -> usuario_login FK de usuario login

### MÓDULO ALMOXARIFADO

- Linkar produto à estoque através de estoque_id na tabela produto. Alterar documentação

- Adicionar coluna local ao Bem Patrimonial. Alterar documentação