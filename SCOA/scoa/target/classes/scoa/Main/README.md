# Anotações

- Lembrar de definir colunas como not null, com: @Column(nullable = false)

- Definir tamanho do varchar com @Column(length = X), pode adicionar ao nullable também

- Lembrar de criar classes para login e senha futuramente

- Lembrar de adicionar nome no aluno BD

- Adicionar matrícula para aluno no BD

- Criado classe genérica Usuário e extensões dela como: aluno -> secretário, professor -> coordenador, ALTERAR NO DC. 

- Criar "entidade" usuário no BD, com login / senha / tipo_usuário / created_at / deleted, alterar no Diagrama BD.


### Alterar diagrama de UserCase conforme comentado pelo professor na entrega do trabalho:

- Modelo de caso de uso ainda apr4senta problemas com as extensões e relacionamentos. Eliminar as dependências entre os módulos. Optar por informar isso textualmente na descrição dos UCs.


### Boleto Pagamento:

- CRIADO ENUM PARA STATUS PAGAMENTO, ALTERAR NO BD

- Lembrar de retirar tabela pagamentotransacao, adicionar pagamento_id em transacao

- Lembrar de retirar tabela boletopagamento, adicionar boleto_id em pagamento

- id como PK em pagamento,, pagamento_id como PK em transacao

### Bolsa financiamento:

- Remover tabela bolsamensalidade, adicionar bolsa_id em mensalidade.

### Contatos Professor:

- Alterar contato para PK ao invés de Id

### Curso:

-  EDITAR NO BD medalidade -> mensalidade 


### Documentos Aluno:

- Criar no BD tipo_documento VARCHAR, alterar no diagrama de classes, not null

### Professor:

- ALTERAR NO BD, transformar CPF e RG em VARCHAR

- Adicionar Polo no BD e DC

### Transação Financeira:

-   //data_transacao como  LocalDateTIME

- //pagamento_id como PK, já que um mesmo pagamento não pode ter mais de uma transação e vice versa

- //Acrescentado pagamento_id como PK
