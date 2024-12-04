/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hugol
 */
public class Paciente extends Usuario implements Serializable{
    private List<Consulta> historico_consulta = new ArrayList<>();;
    private String diagnostico;
    private boolean prioritario;

    public Paciente(String nome, String email, String senha, LocalDate dataNascimento) {
        super(nome, email, senha, dataNascimento);
        this.diagnostico = "";
        this.prioritario = false;
    }

    public Paciente() {
    }
    
    public List<Consulta> getHistorico_consulta() {
        return historico_consulta;
    }

    public void setHistorico_consulta(List<Consulta> historico_consulta) {
        this.historico_consulta = historico_consulta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public boolean isPrioritario() {
        return prioritario;
    }

    public void setPrioritario(boolean prioritario) {
        this.prioritario = prioritario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
}
