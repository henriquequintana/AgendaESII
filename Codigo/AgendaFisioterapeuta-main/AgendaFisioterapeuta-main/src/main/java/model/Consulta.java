/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author hugol
 */
public class Consulta implements Serializable{
    private LocalDateTime data;
    private Paciente paciente;
    private Fisioterapeuta fisioterapeuta;
    private String notas;

    public Consulta(LocalDateTime data, Paciente paciente, Fisioterapeuta fisioterapeuta) {
        this.data = data;
        this.paciente = paciente;
        this.fisioterapeuta = fisioterapeuta;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Fisioterapeuta getFisioterapeuta() {
        return fisioterapeuta;
    }

    public void setFisioterapeuta(Fisioterapeuta fisioterapeuta) {
        this.fisioterapeuta = fisioterapeuta;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    @Override
    public String toString() {
        return data.getMonthValue() + "/" + data.getDayOfMonth() + " - " + data.toLocalTime().toString() + " - " + this.fisioterapeuta.nome;
    }
}