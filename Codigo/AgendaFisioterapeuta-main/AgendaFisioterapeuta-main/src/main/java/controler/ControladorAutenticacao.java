/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controler;


import java.util.List;
import model.Fisioterapeuta;
import model.Paciente;
import model.Usuario;

/**
 *
 * @author hugol
 */
public class ControladorAutenticacao {

    private List<Fisioterapeuta> lista_fisioterapeutas;
    private List<Paciente> lista_pacientes;

    public ControladorAutenticacao(List<Fisioterapeuta> listaFisioterapeutas, List<Paciente> listaPacientes) {
        this.lista_fisioterapeutas = listaFisioterapeutas;
        this.lista_pacientes = listaPacientes;
    }

    public ControladorAutenticacao() {
    }
    
    public Usuario autenticar(String email, String senha) {
        for (Fisioterapeuta fisioterapeuta : lista_fisioterapeutas) {
            if (fisioterapeuta.getEmail().equals(email) && fisioterapeuta.getSenha().equals(senha)) {
                return fisioterapeuta;
            }
        }
        for (Paciente paciente : lista_pacientes) {
            if (paciente.getEmail().equals(email) && paciente.getSenha().equals(senha)) {
                return paciente;
            }
        }
        return null;
    }
}