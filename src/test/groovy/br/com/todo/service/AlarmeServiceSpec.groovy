package br.com.todo.service

import br.com.todo.exception.alarme.AlarmeDesativadoException
import br.com.todo.exception.alarme.AlarmeNaoEncontradoException
import br.com.todo.exception.tarefa.TarefaNaoEncontradaException
import br.com.todo.model.Alarme
import br.com.todo.repository.alarme.contrato.AlarmeRepository
import br.com.todo.repository.tarefa.contrato.TarefaRepostiory
import spock.lang.Specification

class AlarmeServiceSpec extends Specification {
    AlarmeRepository alarmeRepo = Mock()
    TarefaRepostiory tarefaRepo = Mock()
    AlarmeService service = new AlarmeService(alarmeRepo, tarefaRepo)

    def "criarAlarme com tarefa existente salva"() {
        given:
        def alarme = new Alarme(5, 1L)
        tarefaRepo.buscarPorId(1L) >> java.util.Optional.of(new br.com.todo.model.Tarefa(1L, "n", "d", java.sql.Date.valueOf("2025-12-31"), 1, "c", 1L))

        when:
        service.criarAlarme(alarme)

        then:
        1 * alarmeRepo.salvar(alarme)
    }

    def "criarAlarme com tarefa inexistente lanca TarefaNaoEncontradaException"() {
        given:
        def alarme = new Alarme(5, 99L)
        tarefaRepo.buscarPorId(99L) >> java.util.Optional.empty()

        when:
        service.criarAlarme(alarme)

        then:
        thrown(TarefaNaoEncontradaException)
    }

    def "atualizarAlarme ativo atualiza"() {
        given:
        def alarme = new Alarme(1L, true, 5, 1L)
        alarmeRepo.buscarPorId(1L) >> java.util.Optional.of(alarme)

        when:
        service.atualizarAlarme(alarme)

        then:
        1 * alarmeRepo.atualizar(alarme)
    }

    def "atualizarAlarme inativo lanca AlarmeDesativadoException"() {
        given:
        def alarme = new Alarme(1L, false, 5, 1L)

        when:
        service.atualizarAlarme(alarme)

        then:
        thrown(AlarmeDesativadoException)
    }

    def "desativarAlarme existente desativa"() {
        given:
        alarmeRepo.buscarPorId(1L) >> java.util.Optional.of(new Alarme(1L, true, 5, 1L))

        when:
        service.desativarAlarme(1L)

        then:
        1 * alarmeRepo.desativar(1L)
    }

    def "ativarAlarme existente ativa"() {
        given:
        alarmeRepo.buscarPorId(1L) >> java.util.Optional.of(new Alarme(1L, false, 5, 1L))

        when:
        service.ativarAlarme(1L)

        then:
        1 * alarmeRepo.ativar(1L)
    }
}
