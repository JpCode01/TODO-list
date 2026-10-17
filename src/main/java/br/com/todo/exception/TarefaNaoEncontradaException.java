package br.com.todo.exception;

public class TarefaNaoEncontradaException extends RuntimeException {
    public TarefaNaoEncontradaException(Long idTarefa) {
        super("Tarefa não encontrada de ID " + idTarefa);
    }
}
