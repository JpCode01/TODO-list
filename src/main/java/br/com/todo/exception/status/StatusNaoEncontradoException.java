package br.com.todo.exception.status;

public class StatusNaoEncontradoException extends RuntimeException {
    public StatusNaoEncontradoException(String nomeStatus) {
        super("Status '" + nomeStatus + "' nao encontrado!");
    }

    public StatusNaoEncontradoException(Long idStatus) {
        super("Status  de ID '" + idStatus + "' nao encontrado!");
    }
}
