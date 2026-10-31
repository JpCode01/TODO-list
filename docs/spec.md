# docs/spec.md — Especificação de Testes Spock

## 1. Contexto e Objetivo
Validar comportamento existente dos services (Tarefa, Status, Alarme) com Spock, sem alterar código de produção.

## 2. Escopo
- TarefaService: criar, buscar, listar, atualizar, remover, atualizarStatus.
- StatusService: buscar por id/nome, listar.
- AlarmeService: comportamentos confirmados no código.
- Regras de validação e exceções existentes.

## 3. Regras de Negócio (existentes)
- Status deve existir para criar/atualizar tarefa.
- Tarefa deve existir para atualizar/remover.
- Exceções: StatusNaoEncontradoException, TarefaNaoEncontradaException, etc.

## 4. Critérios de Aceite (Dado / Quando / Então)
- Dado mock de repository, Quando criar tarefa com status válido, Então salvar é chamado.
- Dado status inexistente, Quando criar tarefa, Então StatusNaoEncontradoException.

## 5. Impacto
Nenhum impacto em funcionalidades existentes; testes isolados com mocks.

## 6. Fora do Escopo
- Integração com PostgreSQL real.
- Testes do frontend (localStorage).
- Refatoração de services.
- Novas regras de negócio.
- Validações (`ValidationTarefa`, `ValidationAlarme`) — apenas services.

## 7. Isolamento
Mocks para TarefaRepostiory, StatusRepository, AlarmeRepository. Sem conexão real ao banco.

## Comportamentos confirmados
- Validação de dias negativos: `ValidationAlarme` (não service).
- Validação de prioridade (1-5): `ValidationTarefa` (não service).
- `AlarmeService`: `atualizarAlarme` rejeita inativo (`AlarmeDesativadoException`); `desativarAlarme`/`ativarAlarme` operam via repository.

## Comportamentos não claros (para decisão)
- Nenhum — todos confirmados para escopo de services.
