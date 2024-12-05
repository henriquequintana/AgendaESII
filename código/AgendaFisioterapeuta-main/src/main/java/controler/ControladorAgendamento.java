/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controler;

import java.time.LocalDateTime;
import java.util.List;
import model.Consulta;
import model.Fisioterapeuta;
import model.Paciente;

/**
 *
 * @author hugol
 */
public class ControladorAgendamento {

    public ControladorAgendamento() {
    }
    
    public void adicionarHorariosLivres(Fisioterapeuta f, LocalDateTime dataHora){
        List<LocalDateTime> horarios_livres = f.getHorarios_livres();
        horarios_livres.add(dataHora);
        f.setHorarios_livres(horarios_livres);
    }
    
    public List<Consulta> marcarConsulta(List<Consulta> consultas_marcadas, LocalDateTime data, Paciente paciente, Fisioterapeuta fisioterapeuta) {
        Consulta c = new Consulta(data, paciente, fisioterapeuta);
        fisioterapeuta.getHorarios_livres();
        consultas_marcadas.add(c);       
        fisioterapeuta.remover_horario_livre(data);
        System.out.println(c.toString());
        return consultas_marcadas;
    }
    
    public List<Consulta> cancelarConsulta(List<Consulta> consultas_marcadas, LocalDateTime data, Paciente paciente, Fisioterapeuta fisioterapeuta){
        return consultas_marcadas;
    }
}
