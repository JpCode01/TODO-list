package br.com.todo.exception.alarme;

public class AlarmeDesativadoException extends RuntimeException {
    public AlarmeDesativadoException() {
        super("Alarme desativado!");
    }
}
