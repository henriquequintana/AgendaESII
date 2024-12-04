/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controler;

import java.time.LocalDate;
import model.Fisioterapeuta;
import model.Paciente;

/**
 *
 * @author hugol
 */
public class ControladorConta {
    
    public Fisioterapeuta criarContaFisioterapeuta(int nro_registro, int valorConsujlta, String nome, String email, String senha, LocalDate dataNascimento) {
        Fisioterapeuta fisioterapeuta = new Fisioterapeuta(nro_registro, valorConsujlta, nome, email, senha, dataNascimento);
        return fisioterapeuta;
    }

    public Paciente criarContaPaciente(String nome, String email, String senha, LocalDate dataNascimento) {
        Paciente paciente = new Paciente(nome, email, senha, dataNascimento);
        return paciente;
    }

}
