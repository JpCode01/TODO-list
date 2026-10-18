package br.com.todo.exception.tarefa;

public class SemTarefasDisponiveisException extends RuntimeException {
    public SemTarefasDisponiveisException() {
        super("Sem tarefas disponiveis!");
    }
}
