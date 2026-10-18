package br.com.todo.model;

public class Status {
    Long id;
    String nome;

    public Status(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return String.format("""
            ----------------------------------------------------------------

            STATUS:
            
            ID: %d
            NOME: %s
            """,
                id,
                nome
        );
    }
}
