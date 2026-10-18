package br.com.todo.exception.alarme;

public class AlarmeNaoEncontradoException extends RuntimeException {
    public AlarmeNaoEncontradoException(Long idAlarme) {
        super("Alarme nao encontrado de ID " + idAlarme);
    }
}
