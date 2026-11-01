package br.com.todo.service

import br.com.todo.exception.status.StatusNaoEncontradoException
import br.com.todo.model.Status
import br.com.todo.repository.status.contrato.StatusRepository
import spock.lang.Specification

class StatusServiceSpec extends Specification {
    StatusRepository repo = Mock()
    StatusService service = new StatusService(repo)

    def "buscarPorId existente retorna nome"() {
        given:
        repo.buscarNomePorId(1L) >> java.util.Optional.of("Pendente")

        expect:
        service.buscarPorId(1L) == "Pendente"
    }

    def "buscarPorId inexistente lanca StatusNaoEncontradoException"() {
        given:
        repo.buscarNomePorId(99L) >> java.util.Optional.empty()

        when:
        service.buscarPorId(99L)

        then:
        thrown(StatusNaoEncontradoException)
    }

    def "buscarIdPorNome existente retorna id"() {
        given:
        repo.buscarIdPorNome("Pendente") >> java.util.Optional.of(1L)

        expect:
        service.buscarIdPorNome("Pendente") == 1L
    }

    def "listarStatus retorna lista"() {
        given:
        repo.listarStatus() >> [new Status(1L, "Pendente")]

        expect:
        service.listarStatus().size() == 1
        service.listarStatus()[0].nome == "Pendente"
    }
}
