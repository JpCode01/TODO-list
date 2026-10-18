package br.com.todo.exception;

public class DiasNegativosException extends RuntimeException {
    public DiasNegativosException() {
        super("Dias nao podem ser negativos!");
    }
}
