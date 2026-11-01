package br.com.todo.service

import br.com.todo.dto.CadastrarTarefaDTO
import br.com.todo.dto.AtualizarTarefaDTO
import br.com.todo.exception.status.StatusNaoEncontradoException
import br.com.todo.exception.tarefa.TarefaNaoEncontradaException
import br.com.todo.model.Tarefa
import br.com.todo.repository.status.contrato.StatusRepository
import br.com.todo.repository.tarefa.contrato.TarefaRepostiory
import spock.lang.Specification

class TarefaServiceSpec extends Specification {
    TarefaRepostiory tarefaRepo = Mock()
    StatusRepository statusRepo = Mock()
    TarefaService service = new TarefaService(tarefaRepo, statusRepo)

    def "criarTarefa com status valido salva tarefa"() {
        given:
        def dto = new CadastrarTarefaDTO("nome", "desc", java.sql.Date.valueOf("2025-12-31"), 1, "cat", "Pendente")
        statusRepo.buscarIdPorNome("Pendente") >> java.util.Optional.of(1L)

        when:
        service.criarTarefa(dto)

        then:
        1 * tarefaRepo.salvar(_)
    }

    def "criarTarefa com status inexistente lanca StatusNaoEncontradoException"() {
        given:
        def dto = new CadastrarTarefaDTO("nome", "desc", java.sql.Date.valueOf("2025-12-31"), 1, "cat", "Inexistente")
        statusRepo.buscarIdPorNome("Inexistente") >> java.util.Optional.empty()

        when:
        service.criarTarefa(dto)

        then:
        thrown(StatusNaoEncontradoException)
    }

    def "buscarTarefaPorId existente retorna tarefa"() {
        given:
        tarefaRepo.buscarPorId(1L) >> java.util.Optional.of(new Tarefa(1L, "n", "d", java.sql.Date.valueOf("2025-12-31"), 1, "c", 1L))

        expect:
        service.buscarTarefaPorId(1L) != null
    }

    def "buscarTarefaPorId inexistente lanca TarefaNaoEncontradaException"() {
        given:
        tarefaRepo.buscarPorId(99L) >> java.util.Optional.empty()

        when:
        service.buscarTarefaPorId(99L)

        then:
        thrown(TarefaNaoEncontradaException)
    }

    def "removerTarefa existente remove"() {
        given:
        tarefaRepo.buscarPorId(1L) >> java.util.Optional.of(new Tarefa(1L, "n", "d", java.sql.Date.valueOf("2025-12-31"), 1, "c", 1L))

        when:
        service.removerTarefa(1L)

        then:
        1 * tarefaRepo.remover(1L)
    }

    def "listarTarefas retorna lista"() {
        given:
        tarefaRepo.listarTarefas() >> []

        expect:
        service.listarTarefas() != null
    }

    def "listarTarefasAFazer retorna lista"() {
        given:
        tarefaRepo.listarTarefasAFazer() >> []

        expect:
        service.listarTarefasAFazer() != null
    }

    def "listarTarefasFazendo retorna lista"() {
        given:
        tarefaRepo.listarTarefasFazendo() >> []

        expect:
        service.listarTarefasFazendo() != null
    }

    def "listarTarefasFeitas retorna lista"() {
        given:
        tarefaRepo.listarTarefasFeitas() >> []

        expect:
        service.listarTarefasFeitas() != null
    }

    def "atualizarTarefa com status valido atualiza"() {
        given:
        def dto = new AtualizarTarefaDTO(1L, "n", "d", java.sql.Date.valueOf("2025-12-31"), 1, "c", "Pendente")
        tarefaRepo.buscarPorId(1L) >> java.util.Optional.of(new Tarefa(1L, "n", "d", java.sql.Date.valueOf("2025-12-31"), 1, "c", 1L))
        statusRepo.buscarIdPorNome("Pendente") >> java.util.Optional.of(1L)

        when:
        service.atualizarTarefa(dto)

        then:
        1 * tarefaRepo.atualizar(_)
    }
}
