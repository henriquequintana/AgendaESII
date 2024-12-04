/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controler;

import java.time.LocalDateTime;
import model.Consulta;
import model.Fisioterapeuta;
import model.Paciente;

/**
 *
 * @author hugol
 */
public class ControladorConsultas {

    public ControladorConsultas() {
    }
    
    public void diagnosticarPaciente(Fisioterapeuta f, Paciente p, String diagnostico, boolean prioritario){
        String diagnosticoFisio = diagnostico + " (Diagnostico feito por: " + f.getNome()+ ").";
        p.setDiagnostico(diagnosticoFisio);
        p.setPrioritario(prioritario);
    }
    
    public Consulta atenderPaciente(Consulta c, String notas){
        c.setNotas(notas);
        return c;
    }
}
