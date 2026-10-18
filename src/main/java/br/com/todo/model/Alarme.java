package br.com.todo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Alarme {
    Long id;
    boolean ativo;
    int antecendenciaDiasParaDisparo;
    Long idTarefa;

    public Alarme(int antecendenciaDiasParaDisparo, Long idTarefa) {
        this.ativo = true;
        this.antecendenciaDiasParaDisparo = antecendenciaDiasParaDisparo;
        this.idTarefa = idTarefa;
    }

    public Alarme(Long id, boolean ativo, int antecendenciaDiasParaDisparo, Long idTarefa) {
        this.id = id;
        this.ativo = ativo;
        this.antecendenciaDiasParaDisparo = antecendenciaDiasParaDisparo;
        this.idTarefa = idTarefa;
    }
}
