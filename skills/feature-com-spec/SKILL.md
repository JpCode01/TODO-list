---
name: feature-com-spec
description: Processo SDD genérico para desenvolvimento orientado por especificação
---

## Objetivo
Guiar o desenvolvimento incremental com especificação aprovada antes de qualquer código.

## Quando utilizar
Quando o usuário solicitar uma funcionalidade que exija análise, documentação e implementação controlada.

## Entradas
- AGENTS.md (contexto)
- docs/spec.md (requisitos)
- docs/plano.md (tarefas)

## Pré-requisitos
- Repositório explorado
- Nenhum arquivo alterado antes da aprovação

## Passo a passo
1. Explorar contexto (AGENTS.md)
2. Confirmar APIs reais (construtores, métodos, dependências) antes de implementar
3. Criar/revisar AGENTS.md
4. Levantar requisitos (spec.md)
5. Aprovar spec
6. Criar plano (plano.md)
7. Implementar uma tarefa por vez
8. Validar com testes reais (não afirmar que passaram sem evidência)
9. Registrar consumo de tokens quando disponível

## Regras
- Confirmar APIs reais antes de implementar; não presumir construtores ou métodos
- Não escrever código antes da aprovação da spec
- Uma tarefa por vez
- Um commit por tarefa
- Não alterar código fora do escopo
- Não inventar estruturas
- Validar com testes; distinguir análise estática, compilação, execução e validação final
- Interromper e consultar o usuário em ambiguidades de negócio ou lacunas na spec
- Preservar arquitetura e padrões existentes
- Registrar consumo de tokens quando disponível

## Rastreabilidade
Relacionar requisitos (spec.md) → tarefas (plano.md) → testes → evidências. Se um requisito não for testável, registrar explicitamente.

## Auditoria de testes
Verificar cobertura, qualidade das asserções, isolamento, APIs reais e divergências entre spec e implementação. Consultar arquivos existentes; não inventar métodos.

## Evidências de validação
Registrar comandos executados, resultados e falhas. Se o usuário executar manualmente, aguardar os resultados; não afirmar aprovação antes de recebê-los.

## Limites de autonomia
Alterações fora do escopo, arquitetura ou commits exigem autorização. Se uma dependência ou mudança adicional for necessária, explicar o motivo e solicitar autorização antes de prosseguir.

## Modelos
- spec.md: contexto, escopo, critérios de aceite, fora do escopo, isolamento
- plano.md: tarefas numeradas, objetivo, arquivos, validação, critério de conclusão

## Checklist
- [ ] AGENTS.md revisado
- [ ] APIs reais confirmadas
- [ ] spec.md aprovada
- [ ] plano.md aprovado
- [ ] Tarefas implementadas uma a uma
- [ ] Testes validados (evidência registrada)
- [ ] Nenhum arquivo alterado fora do escopo
- [ ] Ambiguidades resolvidas com o usuário
- [ ] Consumo de tokens registrado (se disponível)
