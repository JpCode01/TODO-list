# Relatório — Desenvolvimento Orientado por Especificação (SDD)

## 1. Consumo de tokens

Os valores abaixo representam o consumo informado para cada etapa do processo.

| Etapa                   | Tokens de entrada | Tokens de saída |
| AGENTS.md               | 127.344           | 613             |
| Especificação (spec.md) | 584.897           | 3.858           |
| Planejamento (plano.md) | 421.464           | 1.520           |
| Implementação 1         | 487.763           | 814             |
| Implementação 2         | 1.796.883         | 5.288           |
| Implementação 3         | 1.389.219         | 3.977           |
| Implementação 4         | 347.820           | 1.078           |
| Revisão e validação     | 361.580           | 625             |
| Aprimoramento da skill  | 419.433           | 436             |
| **Total**               | **5.936.403**     | **18.209**      |

*Observação: os valores devem ser conferidos com os registros originais de consumo. O total corresponde à soma das etapas apresentadas e não necessariamente ao consumo integral da ferramenta ou do projeto.*

## 2. Etapas do processo

### 2.1. Contextualização do projeto

Foi preparado o arquivo `AGENTS.md` para fornecer ao agente as informações e orientações necessárias para trabalhar no repositório, estabelecendo o contexto e as restrições do desenvolvimento.

### 2.2. Especificação e planejamento

A especificação foi utilizada para definir os requisitos e o comportamento esperado. Em seguida, o plano organizou o trabalho em tarefas menores, com objetivos e critérios de validação.

A separação dessas etapas buscou reduzir decisões improvisadas durante a implementação e permitir que o desenvolvimento ocorresse de maneira incremental.

### 2.3. Implementação incremental

O desenvolvimento foi dividido em quatro etapas de implementação. Durante esse processo, foram elaborados testes para os serviços do projeto utilizando Spock, com testes escritos em Groovy e código de produção em Java.

A abordagem incremental permitiu revisar os resultados por partes e identificar problemas específicos sem precisar avaliar toda a implementação de uma única vez.

### 2.4. Revisão e validação

Foi realizada uma revisão dos testes para avaliar sua aderência à especificação e às implementações existentes.

A revisão identificou lacunas de cobertura, incluindo cenários de listagem de tarefas, verificações insuficientes dos status retornados e casos de exceção relacionados às operações de alarme.

Também foi identificada uma inconsistência no relatório de auditoria sobre a existência de `buscarIdPorNome`, exigindo a conferência da implementação real antes de qualquer alteração.

## 3. Problemas identificados e aprendizados

Um dos principais problemas ocorreu durante a criação dos testes: o agente utilizou construtores que não correspondiam às assinaturas reais das classes do projeto. Isso provocou falhas de compilação ou execução dos testes.

Esse caso demonstrou que uma especificação funcional, embora necessária, não substitui a inspeção do código existente. Antes de escrever testes, o agente precisa verificar os construtores, métodos, dependências e contratos efetivamente disponíveis.

Também foram encontrados problemas na configuração das dependências do Spock e do Groovy. Isso evidenciou a importância de distinguir falhas de configuração do ambiente, problemas de compilação, falhas de execução e defeitos de comportamento.

Por fim, a auditoria mostrou que a existência de testes não garante, por si só, uma cobertura adequada. É necessário conferir se os cenários testados correspondem aos requisitos, se as asserções verificam resultados relevantes e se os testes estão isolados de dependências externas.

## 5. Aprimoramento da skill

Com base nos problemas observados, a skill `skills/feature-com-spec/SKILL.md` foi aprimorada para reforçar as seguintes orientações:

- Confirmar as APIs reais antes de implementar.
- Respeitar a aprovação da especificação antes de escrever código.
- Executar uma tarefa por vez.
- Diferenciar análise estática, compilação, execução de testes e validação final.
- Não declarar testes aprovados sem evidências.
- Consultar o usuário quando houver ambiguidades de negócio ou lacunas relevantes na especificação.
- Preservar a arquitetura e evitar alterações fora do escopo.
- Registrar o consumo de tokens quando os dados estiverem disponíveis.

O objetivo dessas mudanças foi tornar o processo mais verificável e reduzir erros evitáveis, mantendo a abordagem incremental do SDD.

## 6. Conclusão

A experiência demonstrou que o SDD oferece uma estrutura útil para organizar o desenvolvimento por meio de especificações, planejamento e implementação incremental. Entretanto, a qualidade do resultado depende também da capacidade do agente de inspecionar o projeto, respeitar os contratos existentes, escrever testes significativos e apresentar evidências reais de validação.

Os problemas encontrados não invalidam a abordagem, mas evidenciam a necessidade de instruções operacionais mais precisas. O aprimoramento da skill representa uma tentativa de incorporar esses aprendizados às próximas execuções do processo.

A avaliação final deve considerar não apenas a quantidade de etapas concluídas, mas também a rastreabilidade dos requisitos, a qualidade dos testes, a preservação do escopo e a comprovação dos resultados obtidos.