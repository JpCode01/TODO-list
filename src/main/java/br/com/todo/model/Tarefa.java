package br.com.todo.model;

import br.com.todo.enums.Status;

import java.time.LocalDate;

public class Tarefa {
    private String nome;
    private String descricao;
    private LocalDate dataTermino;
    private int prioridade;
    private String categoria;
    private Status status;
    private boolean alarmeAtivo;
    private int antecedenciaDias;

    public Tarefa() {
    }

    public Tarefa(String nome, String descricao, LocalDate dataTermino, int prioridade, String categoria, Status status) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataTermino = dataTermino;
        this.prioridade = prioridade;
        this.categoria = categoria;
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public String getCategoria() {
        return categoria;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isAlarmeAtivo() {
        return alarmeAtivo;
    }

    public int getAntecedenciaDias() {
        return antecedenciaDias;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setAlarmeAtivo(boolean alarmeAtivo) {
        this.alarmeAtivo = alarmeAtivo;
    }

    public void setAntecedenciaDias(int antecedenciaDias) {
        this.antecedenciaDias = antecedenciaDias;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", prioridade=" + prioridade +
                ", categoria='" + categoria + '\'' +
                ", dataTermino=" + dataTermino +
                ", status=" + status +
                ", alarmeAtivo=" + alarmeAtivo +
                ", antecedenciaDias=" + antecedenciaDias +
                '}';

    }

}
