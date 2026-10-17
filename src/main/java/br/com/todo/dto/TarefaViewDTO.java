package br.com.todo.dto;

import java.sql.Date;

public record TarefaViewDTO(
        Long id,
        String nome,
        String descricao,
        Date dataTermino,
        int prioridade,
        String categoria,
        String status
        ) {
    
        public TarefaViewDTO(Long id, String nome, String descricao, Date dataTermino, int prioridade, String categoria, String status) {
            this.id = id;
            this.nome = nome;
            this.descricao = descricao;
            this.dataTermino = dataTermino;
            this.prioridade = prioridade;
            this.categoria = categoria;
            this.status = status;
        }
}
