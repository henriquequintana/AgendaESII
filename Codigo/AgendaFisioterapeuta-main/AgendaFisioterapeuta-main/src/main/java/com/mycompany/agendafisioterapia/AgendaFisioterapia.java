/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.agendafisioterapia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import model.Fisioterapeuta;
import model.Paciente;
import model.Sistema;
import model.Usuario;
import view.Login;

/**
 *
 * @author hugol
 */
public class AgendaFisioterapia {

    public static void main(String[] args) {
        
        Sistema s = Sistema.getInstance();
        
        s.carregarDados();
        
        // s.criarContaFisioterapeuta(12345, 100,"Carlos Silva","carlos.silva@exemplo.com","senha123",LocalDate.of(1985, 5, 10));
        // s.criarContaPaciente("Maria Oliveira","maria.oliveira@exemplo.com","senha456", LocalDate.of(1995, 8, 22));
        
        Login l = new Login();
        l.setVisible(true);
        // Usuario u;
        // Fisioterapeuta f = new Fisioterapeuta();
        // Paciente p = new Paciente();

        // u = s.autenticar("carlos.silva@exemplo.com", "senha123");
        // s.adicionarHorariosLivres(LocalDateTime.of(2024, 11, 25, 19, 30));
        
        // u = s.autenticar("maria.oliveira@exemplo.com","senha456");
        // s.marcarConsulta(LocalDateTime.of(2024, 11, 25, 19, 30));
        
        // u = s.autenticar("carlos.silva@exemplo.com", "senha123");
        // s.diagnosticarPaciente("Maria Oliveira", "Dor nos ombros", true);
        // s.atenderPaciente("Maria Oliveira", LocalDateTime.of(2024, 11, 25, 19, 30), "Teve melhora na mobilidade");
    
        // u = s.autenticar("maria.oliveira@exemplo.com","senha456");
        // if (u != null) {
        //     if (u instanceof Fisioterapeuta) {
        //         f = (Fisioterapeuta) u;
        //     } else if (u instanceof Paciente) {
        //         p = (Paciente) u;
        // }
        // } else {
        //     System.out.println("Falha na autenticação: credenciais inválidas.");
        // }
        
        // System.out.println(p.getDiagnostico());
        
        
        Runtime.getRuntime().addShutdownHook(new Thread(s::salvarDados));

    }
}
