# TODO List - ZG-Hero Project (K1-T3 Java, K1-T6 JavaScript)

Aplicação backend em Java, desenvolvida como ZG-Hero Project da trilha K1-T3 do **Acelera ZG**, programa de treinamento técnico. O desafio consistia em implementar, sem uso de frameworks, o backend de uma aplicação de TODO List que futuramente se comunicará com um frontend (desenvolvido na trilha de JavaScript).

O backend passou por duas grandes evoluções em relação à primeira versão:

1. **Persistência em banco de dados PostgreSQL**, substituindo o armazenamento em arquivo JSON;
2. **Refatoração com Clean Code e princípios SOLID**, reorganizando o projeto em camadas com responsabilidades bem definidas.

Ambas estão explicadas na seção [Evolução do backend](#evolução-do-backend).

## Sobre o projeto

Cada tarefa possui: nome, descrição, data de término, nível de prioridade (1 a 5), categoria e status (`TODO`, `DOING`, `DONE`).

A aplicação roda via terminal e permite cadastrar, listar, atualizar e remover tarefas, listá-las por status e alterar o status de uma tarefa.

Também é possível criar um **alarme** para a tarefa, definindo com quantos dias de antecedência do prazo ele deve disparar. A cada ciclo do menu, a aplicação consulta o banco e exibe os alarmes ativos cujas tarefas estão dentro do período de antecedência e ainda não foram concluídas.

## Tecnologias utilizadas

* **Java** (usa `records` e *text blocks*, portanto Java 16 ou superior)
* **Gradle** como build tool
* **PostgreSQL** como banco de dados
* **JDBC** puro, com o driver `org.postgresql:postgresql`, sem ORM
* **Lombok** para reduzir código repetitivo nos modelos
* **JUnit Platform** configurada no `build.gradle` (ainda sem testes implementados)

---

# Evolução do backend

## 1. Do arquivo JSON para o banco de dados

**Antes:** as tarefas eram gravadas em um arquivo `tarefas.json` com o Jackson. Os dados sobreviviam ao fechamento do programa, mas a leitura e a gravação envolviam o arquivo inteiro a cada operação, e filtros, buscas e a verificação de alarmes eram feitos em memória.

**Agora:** os dados ficam em um banco **PostgreSQL**, acessado via **JDBC** puro:

* **Três tabelas relacionadas:** `tarefa`, `status_tarefa` e `alarme`. O status deixou de ser um simples texto da tarefa e passou a ser uma tabela própria, referenciada por chave estrangeira. O alarme, que antes eram campos soltos dentro da tarefa (`alarmeAtivo` e `antecedenciaDias`), virou uma entidade separada ligada à tarefa.
* **IDs gerados pelo banco:** o DAO usa `RETURN_GENERATED_KEYS` e atribui o ID gerado ao objeto salvo.
* **Consultas feitas pelo banco:** filtros por status são `WHERE` e `JOIN` em SQL, e a verificação de alarmes também: o banco compara `CURRENT_DATE` com o intervalo entre `data_termino - antecedência` e `data_termino`, ignorando tarefas já `DONE` e alarmes desativados.
* **Segurança nas consultas:** todas as queries usam `PreparedStatement`, evitando SQL injection.
* **Gerenciamento de recursos:** conexões, statements e result sets são abertos com `try-with-resources`.
* **Desativação de alarme sem apagar o registro:** o campo `ativo` do alarme permite ativar e desativar sem perder o histórico.
* **Dependência removida:** o Jackson deixou de ser necessário.

## 2. Clean Code

* **Nomes que revelam a intenção:** métodos como `buscarIdPorNome`, `listarTarefasAFazer` e `escolherTarefaParaAtualizar` descrevem o que fazem, e as classes seguem o vocabulário do domínio (`Tarefa`, `Alarme`, `Status`).
* **Classes pequenas e coesas:** cada classe tem um papel claro. A validação de entrada saiu da regra de negócio e foi para `validation`; a interação com o terminal ficou em `view`, dividida em `Menu`, `MenuTarefa`, `MenuStatus` e `MenuAlarme`, em vez de uma única classe gigante.
* **Exceções específicas no lugar de códigos de erro ou `null`:** `TarefaNaoEncontradaException`, `StatusNaoEncontradoException`, `AlarmeNaoEncontradoException`, `AlarmeDesativadoException`, `PrioridadeIncorretaException`, `DiasNegativosException` e `SemTarefasDisponiveisException` carregam mensagens claras. As buscas retornam `Optional` e o service decide o que fazer quando o valor está ausente.
* **DTOs imutáveis (`record`):** `CadastrarTarefaDTO`, `AtualizarTarefaDTO` e `TarefaViewDTO` definem exatamente os dados que entram e saem de cada operação, sem expor o modelo diretamente à camada de apresentação.
* **SQL legível:** consultas escritas em *text blocks*, com colunas alinhadas e uma por linha.
* **Sem dependências escondidas:** nenhuma classe cria suas próprias dependências. Tudo chega pelo construtor.

## 3. Princípios SOLID

| Princípio | Como aparece no projeto |
|---|---|
| **S** — Responsabilidade única | Cada camada tem um único motivo para mudar: `model` (entidades), `dto` (transporte de dados), `repository` (acesso ao banco), `service` (regras de negócio), `validation` (validação de entrada), `controller` (ponto de entrada da view para os services) e `view` (terminal). Também há um service, um controller e um repositório por conceito (`Tarefa`, `Alarme`, `Status`). |
| **O** — Aberto/fechado | É possível adicionar outra forma de persistência ou outro banco criando uma nova implementação das interfaces de repositório e de `ConnectionFactory`, sem alterar services nem controllers. |
| **L** — Substituição de Liskov | `TarefaDAO`, `AlarmeDAO` e `StatusDAO` podem ser trocados por qualquer outra implementação das respectivas interfaces sem que os services percebam. |
| **I** — Segregação de interfaces | Em vez de uma interface genérica e grande, existe um contrato enxuto por entidade (`TarefaRepostiory`, `AlarmeRepository`, `StatusRepository`), expondo só as operações que cada uma precisa. |
| **D** — Inversão de dependência | Os services dependem das **interfaces** de repositório (pasta `contrato`), e os DAOs dependem da interface `ConnectionFactory`, nunca de classes concretas. A criação e a ligação dos objetos acontece em um único lugar, a raiz de composição (`DaoConfig`, `ServiceConfig`, `ControllerConfig`, `MenuConfig` e `MenuFactory`), com injeção manual por construtor e sem framework. |

---

# Organização do projeto

O projeto não usa nenhum framework, mas está organizado em pacotes que separam as responsabilidades e simulam algo próximo de uma API em camadas:

```text
br.com.todo
├── model          Tarefa, Alarme, Status
├── dto            CadastrarTarefaDTO, AtualizarTarefaDTO, TarefaViewDTO
├── repository
│   ├── tarefa     TarefaDAO
│   │   └── contrato   TarefaRepostiory
│   ├── alarme     AlarmeDAO
│   │   └── contrato   AlarmeRepository
│   └── status     StatusDAO
│       └── contrato   StatusRepository
├── service        TarefaService, AlarmeService, StatusService
├── controller     TarefaController, AlarmeController, StatusController
├── validation     ValidationTarefa, ValidationAlarme
├── exception      exceções de negócio, agrupadas por assunto
├── factory
│   ├── database   ConnectionFactory, PostgresConnectionFactory
│   └── menu       MenuFactory
├── config         DaoConfig, ServiceConfig, ControllerConfig, MenuConfig
├── view           Menu, MenuTarefa, MenuStatus, MenuAlarme
└── Main           ponto de entrada da aplicação
```

Fluxo de uma operação:

```text
view  →  controller  →  service  →  repository (interface)  →  DAO  →  ConnectionFactory  →  PostgreSQL
```

* `model` — entidades `Tarefa`, `Alarme` e `Status`;
* `dto` — dados de entrada (`CadastrarTarefaDTO`, `AtualizarTarefaDTO`) e de saída (`TarefaViewDTO`, já com o nome do status);
* `repository` — contratos (interfaces) e DAOs que executam o SQL;
* `service` — regras de negócio: por exemplo, o `TarefaService` converte o nome do status em ID, verifica se a tarefa existe antes de atualizar ou remover, e o `AlarmeService` impede criar alarme para tarefa inexistente ou atualizar alarme desativado;
* `controller` — camada intermediária entre a `view` e os services;
* `validation` — validação de data (`dd/MM/yyyy`), prioridade (1 a 5) e dias de antecedência, repetindo a pergunta até receber um valor válido;
* `factory` e `config` — criação das conexões e montagem das dependências;
* `view` — interação com o usuário pelo terminal.

---

# Banco de dados

O backend usa **PostgreSQL** com três tabelas:

* **`status_tarefa`** — status disponíveis (`TODO`, `DOING`, `DONE`);
* **`tarefa`** — nome, descrição, data de término, prioridade, categoria e referência ao status;
* **`alarme`** — referência à tarefa, indicador de ativo e antecedência, em dias, para o disparo.

Estrutura esperada pelas consultas dos DAOs (os tipos abaixo são uma sugestão compatível com elas):

```sql
CREATE TABLE status_tarefa (
    id_status_tarefa SERIAL PRIMARY KEY,
    nome             VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO status_tarefa (nome) VALUES ('TODO'), ('DOING'), ('DONE');

CREATE TABLE tarefa (
    id_tarefa        SERIAL PRIMARY KEY,
    nome             VARCHAR(100) NOT NULL,
    descricao        TEXT,
    data_termino     DATE NOT NULL,
    prioridade       INT NOT NULL CHECK (prioridade BETWEEN 1 AND 5),
    categoria        VARCHAR(50),
    id_status_tarefa INT NOT NULL REFERENCES status_tarefa (id_status_tarefa)
);

CREATE TABLE alarme (
    id_alarme                       SERIAL PRIMARY KEY,
    id_tarefa                       INT NOT NULL REFERENCES tarefa (id_tarefa) ON DELETE CASCADE,
    ativo                           BOOLEAN NOT NULL DEFAULT TRUE,
    antecedencias_dias_para_disparo INT NOT NULL CHECK (antecedencias_dias_para_disparo >= 0)
);
```

**Alarmes:** um alarme é exibido quando está ativo, a tarefa não está `DONE` e a data atual está entre `data_termino - antecedência` e `data_termino`.

---

# Menu

Opções que aparecem ao rodar a aplicação:

```text
1 - Adicionar tarefa
2 - Listar todas as tarefas
3 - Listar tarefas a fazer
4 - Listar tarefas sendo feitas
5 - Listar tarefas já feitas
6 - Atualizar tarefa
7 - Remover tarefa
8 - Atualizar status da tarefa
9 - Criar alarme
10 - Ativar alarme
11 - Desativar alarme

0 - Sair
```

Tarefas são escolhidas pelo **ID**, exibido na listagem. Na atualização, campos deixados em branco mantêm o valor atual.

---

# Frontend

Como continuação do projeto, foi desenvolvido um frontend utilizando **HTML, CSS e JavaScript**, conforme a proposta da trilha de JavaScript do Acelera ZG.

O frontend está na pasta `Frontend/` e, neste momento, funciona de forma independente do backend, utilizando `localStorage` para armazenar as tarefas, pois a comunicação entre frontend e backend ainda não foi abordada na trilha.

### Funcionalidades

* **Criar tarefas** — nome, descrição, data de término, prioridade de 1 a 5, categoria e status;
* **Listar tarefas** — exibe todas as tarefas cadastradas com suas informações;
* **Editar tarefas** — carrega os dados da tarefa no formulário para salvar as alterações;
* **Remover tarefas** — exclui a tarefa da lista e do `localStorage`;
* **Alterar status** — seleciona uma ou mais tarefas por checkboxes e altera o status delas simultaneamente;
* **Bloqueio durante edição** — enquanto uma tarefa é editada, a remoção fica indisponível, evitando conflitos.

Cada tarefa recebe um identificador próprio, usado nas operações de edição, remoção e alteração de status.

### Tecnologias do frontend

* **HTML** — estrutura da página e formulário;
* **CSS** — estilização e layout;
* **JavaScript** — lógica das operações;
* **LocalStorage** — armazenamento das tarefas no navegador.

A comunicação com o backend será implementada posteriormente, conforme os conteúdos da trilha avançarem.

---

# Como executar

## Backend

1. Tenha o **PostgreSQL** em execução e crie o banco `todo`, com as tabelas descritas na seção [Banco de dados](#banco-de-dados);
2. Ajuste os dados de conexão (URL, usuário e senha) na classe `PostgresConnectionFactory`;
3. Execute a classe `Main` (`src/main/java/br/com/todo/Main.java`).

## Frontend

Abra o arquivo `Frontend/index.html` em um navegador.

---

# Autor

Desenvolvido por João Pedro Vaz durante o Acelera ZG.
