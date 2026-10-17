package br.com.todo.dto;

import java.time.LocalDate;

public record CadastrarTarefaDTO(
        String nome,
        String descricao,
        LocalDate dataTermino,
        int prioridade,
        String categoria,
        String status
) {
    public CadastrarTarefaDTO(String nome, String descricao, LocalDate dataTermino, int prioridade, String categoria, String status) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataTermino = dataTermino;
        this.prioridade = prioridade;
        this.categoria = categoria;
        this.status = status;
    }
}