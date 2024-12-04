/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import controler.ControladorAgendamento;
import controler.ControladorAutenticacao;
import controler.ControladorConsultas;
import controler.ControladorConta;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hugol
 */
public class Sistema {
    private List<Fisioterapeuta> lista_fisioterapeutas = new ArrayList<>();
    private List<Paciente> lista_pacientes = new ArrayList<>();
    
    private List<Consulta> consultas_marcadas = new ArrayList<>();
    private List<Consulta> consultas_realizadas = new ArrayList<>();
    
    private ControladorConta c_conta = new ControladorConta();
    private ControladorAutenticacao c_autenticacao = new ControladorAutenticacao();
    private ControladorConsultas c_consultas = new ControladorConsultas();
    private ControladorAgendamento c_agendamento = new ControladorAgendamento();
    
    private Usuario usuarioLogado;
    
    public Sistema() {
    }
    
    Fisioterapeuta buscarFisioterapeutaPorNome(String nome) {
        for (Fisioterapeuta fisioterapeuta : lista_fisioterapeutas) {
            if (fisioterapeuta.getNome().equalsIgnoreCase(nome)) {
                return fisioterapeuta; // Retorna o paciente encontrado
            }
        }
        return null; // Retorna null se o paciente não for encontrado
    }
    
    Paciente buscarPacientePorNome(String nome) {
        for (Paciente paciente : lista_pacientes) {
            if (paciente.getNome().equalsIgnoreCase(nome)) {
                return paciente; // Retorna o paciente encontrado
            }
        }
        return null; // Retorna null se o paciente não for encontrado
    }
    
    Consulta buscarConsulta(Paciente paciente, Fisioterapeuta fisioterapeuta, LocalDateTime data) {
        for (Consulta consulta : consultas_marcadas) {
            if (consulta.getPaciente().equals(paciente) && 
                consulta.getFisioterapeuta().equals(fisioterapeuta) && 
                consulta.getData().equals(data)) {
                return consulta; 
            }
        }
        return null;
    }
    
    public void criarContaFisioterapeuta(int nro_registro, int valorConsujlta, String nome, String email, String senha, LocalDate dataNascimento) {
        Fisioterapeuta f = c_conta.criarContaFisioterapeuta(nro_registro, valorConsujlta, nome, email, senha, dataNascimento);
        lista_fisioterapeutas.add(f);
    }
    
    public void criarContaPaciente(String nome, String email, String senha, LocalDate dataNascimento) {
        Paciente p = c_conta.criarContaPaciente(nome, email, senha, dataNascimento);
        lista_pacientes.add(p);
    }
    
    public Usuario autenticar(String email, String senha){
        c_autenticacao = new ControladorAutenticacao(lista_fisioterapeutas,  lista_pacientes);
        usuarioLogado = c_autenticacao.autenticar(email, senha);
        return usuarioLogado;
    }
    
    public void adicionarHorariosLivres(LocalDateTime dataHora){
        Fisioterapeuta f = (Fisioterapeuta) usuarioLogado;
        c_agendamento.adicionarHorariosLivres(f, dataHora);
    }
    
    public void marcarConsulta(LocalDateTime data, String nomeFisioterapeuta){
        Paciente p = (Paciente) usuarioLogado;
        Fisioterapeuta f = buscarFisioterapeutaPorNome(nomeFisioterapeuta);
        
        consultas_marcadas = c_agendamento.marcarConsulta(consultas_marcadas, data, p, f);
    }
    
    public void diagnosticarPaciente(String nomePaciente, String diagnostico, boolean prioritario){
        Fisioterapeuta f = (Fisioterapeuta) usuarioLogado;
        Paciente p = buscarPacientePorNome(nomePaciente);
        
        c_consultas.diagnosticarPaciente(f, p, diagnostico, prioritario);    
    }
    
    public void atenderPaciente(String nomePaciente, LocalDateTime dataHora, String notas){
        Fisioterapeuta f = (Fisioterapeuta) usuarioLogado;
        Paciente p = buscarPacientePorNome(nomePaciente);
        Consulta c = buscarConsulta(p, f, dataHora);
        
        c_consultas.atenderPaciente(c, notas);
        consultas_marcadas.remove(c);
        consultas_realizadas.add(c);
    }
    
    
}
