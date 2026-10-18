package br.com.todo.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
public class Tarefa {
    private Long id;
    private String nome;
    private String descricao;
    private Date dataTermino;
    private int prioridade;
    private String categoria;
    private Long idStatus;

    public Tarefa(String nome, String descricao, Date dataTermino, int prioridade, String categoria, Long idStatus) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataTermino = dataTermino;
        this.prioridade = prioridade;
        this.categoria = categoria;
        this.idStatus = idStatus;
    }

    public Tarefa(Long id, String nome, String descricao, Date dataTermino, int prioridade, String categoria, Long idStatus) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataTermino = dataTermino;
        this.prioridade = prioridade;
        this.categoria = categoria;
        this.idStatus = idStatus;
    }

}
