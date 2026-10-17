package br.com.todo.exception;

public class AlarmeDesativadoException extends RuntimeException {
    public AlarmeDesativadoException() {
        super("Alarme desativado!");
    }
}
