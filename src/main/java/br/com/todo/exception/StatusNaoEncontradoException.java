package br.com.todo.exception;

public class StatusNaoEncontradoException extends RuntimeException {
    public StatusNaoEncontradoException(String nomeStatus) {
        super("Status '" + nomeStatus + "' nao encontrado!");
    }
}
