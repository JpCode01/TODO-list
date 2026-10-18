package br.com.todo.exception.prioridade;

public class PrioridadeIncorretaException extends RuntimeException {
    public PrioridadeIncorretaException(int prioridade) {
        super("Prioridade de '" + prioridade + "' incorreta (apenas 1 a 5) tente novamente: ");
    }
}
