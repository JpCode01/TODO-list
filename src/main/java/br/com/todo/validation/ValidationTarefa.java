package br.com.todo.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationTarefa {
    public boolean prioridadeValida(int prioridade) {
        return prioridade >= 1 && prioridade <= 5;
    }

    public boolean statusValido(String status) {
        return status.equalsIgnoreCase("TODO") 
                || status.equalsIgnoreCase("DOING") 
                || status.equalsIgnoreCase("DONE");
    }

    public boolean dataValida(String textoData) {
        try {
            LocalDate.parse(textoData);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
