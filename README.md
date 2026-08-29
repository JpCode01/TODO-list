# TODO List - ZG-Hero Project (K1-T3 Java, K1-T6 JavaScript)

Aplicação backend em Java, desenvolvida como ZG-Hero Project da trilha K1-T3 do **Acelera ZG**, programa de treinamento técnico. O desafio consistia em implementar, sem uso de frameworks, o backend de uma aplicação de TODO List que futuramente se comunicará com um frontend (a ser desenvolvido na trilha de JavaScript).

## Sobre o projeto

Cada tarefa possui: nome, descrição, data de término, nível de prioridade (1 a 5), categoria e status (TODO, DOING, DONE).

A aplicação roda via terminal e permite criar, listar, atualizar e remover tarefas, além de listá-las por categoria, prioridade ou status. Sempre que uma nova tarefa é adicionada, a lista é reordenada com base na prioridade, mantendo a tarefa na posição ideal.

Também é possível definir um alarme para a tarefa, com quantos dias de antecedência ele deve ser acionado. A aplicação verifica automaticamente a data atual em relação ao período definido e exibe um aviso quando a tarefa estiver próxima do vencimento.

## Tecnologias utilizadas

* **Java**
* **Gradle** como build tool
* **Jackson** (`tools.jackson.core:jackson-databind`) para converter as tarefas em JSON e vice-versa
* **Streams** para auxiliar nas listagens e filtros

## Organização do projeto

O projeto não usa nenhum framework, mas foi organizado em pacotes para separar as responsabilidades, buscando simular algo próximo de uma API service:

* `model` - contém a entidade `Tarefa` e o enum `Status`
* `service` - `TarefaService`, com as regras de negócio (inserir, listar, atualizar, remover, reordenar por prioridade, verificar alarmes)
* `validation` - `ValidationTarefa`, responsável por validar os dados informados pelo usuário
* `view` - `Menu`, responsável por exibir as opções no terminal e tratar a interação com o usuário
* `Main` - ponto de entrada da aplicação

## Persistência

Os dados são persistidos em um arquivo `tarefas.json`, usando o Jackson para ler e escrever as tarefas. Assim, a lista de tarefas não se perde ao fechar o programa. O arquivo `tarefas.json` incluído no repositório é apenas um exemplo, usado durante os testes.

## Menu

Ao rodar a aplicação, o usuário tem as seguintes opções:

```text
1 - Adicionar tarefa
2 - Listar todas as tarefas
3 - Listar por prioridade
4 - Listar por categoria
5 - Listar por status
6 - Atualizar por nome
7 - Remover por nome
0 - Sair
```

## Frontend

Como continuação do projeto, foi desenvolvido um frontend utilizando **HTML, CSS e JavaScript**, conforme a proposta da trilha de JavaScript do Acelera ZG.

O frontend foi desenvolvido separadamente na pasta `Frontend/` e, neste momento, funciona de forma independente do backend, utilizando `localStorage` para simular a persistência dos dados enquanto a comunicação entre frontend e backend ainda não foi abordada na trilha.

### Funcionalidades

O frontend implementa as operações básicas de um CRUD:

* **Criar tarefas** - permite informar título, descrição e status da tarefa.
* **Listar tarefas** - exibe todas as tarefas cadastradas na página.
* **Editar tarefas** - permite selecionar uma tarefa existente, carregar seus dados no mesmo formulário de criação e salvar as alterações.
* **Remover tarefas** - permite excluir uma tarefa tanto da lista exibida quanto do `localStorage`.

Cada tarefa recebe um identificador próprio para permitir sua identificação durante as operações de edição e remoção.

### Tecnologias utilizadas no frontend

* **HTML** - estrutura da página e formulário de tarefas
* **CSS** - estilização e layout da aplicação
* **JavaScript** - lógica de criação, listagem, edição e remoção das tarefas
* **LocalStorage** - armazenamento temporário das tarefas no navegador

A comunicação com o backend será implementada posteriormente, conforme os conteúdos da trilha avançarem.

## Como executar

Basta executar a classe `Main` (`src/main/java/br/com/todo/Main.java`) para utilizar o backend.

Para utilizar o frontend, abra o arquivo `Frontend/index.html` em um navegador.

## Autor

Desenvolvido por João Pedro Vaz durante o Acelera ZG.
