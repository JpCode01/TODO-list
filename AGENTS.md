# AGENTS.md — Análise Verificada

## Visão Geral
Aplicação Java TODO (console) com frontend JS independente (localStorage, sem integração ao backend).

## Stack
- Java, Gradle 9.3.0, PostgreSQL 42.7.14, Lombok 1.18.42, JUnit 6 (BOM 6.0.0).
- Frontend: HTML/JS/CSS (localStorage).

## Arquitetura
Camadas: controller → service → repository (DAO) → model. Config via classes `*Config`.

## Execução
`gradlew run` (ou `java -cp` após build). Frontend: abrir `Frontend/index.html` diretamente.

## Testes
Gradle configurado (`useJUnitPlatform()`), mas nenhum arquivo de teste existe no projeto.

## Convenções
Pacote `br.com.todo`, nomes em português, DTOs para entrada/visualização.

## Regras de Negócio
Tarefas com status, prioridade, alarme; validações de dias negativos e prioridade incorreta.

## Restrições
- Não modificar arquivos existentes.
- Não integrar frontend ao backend sem instrução.
- Preservar arquitetura existente.
- Não inventar informações; testes não implementados confirmados.
