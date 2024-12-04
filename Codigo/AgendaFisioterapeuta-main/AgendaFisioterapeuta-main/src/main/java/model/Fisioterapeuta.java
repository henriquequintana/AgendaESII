/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hugol
 */
public class Fisioterapeuta extends Usuario implements Serializable{
    private int nro_registro;
    private int valorConsujlta;
    private List<LocalDateTime> horarios_livres = new ArrayList<>();

    public Fisioterapeuta(int nro_registro, int valorConsujlta, String nome, String email, String senha, LocalDate dataNascimento) {
        super(nome, email, senha, dataNascimento);
        this.nro_registro = nro_registro;
        this.valorConsujlta = valorConsujlta;
    }

    public Fisioterapeuta() {
    }

    public int getNro_registro() {
        return nro_registro;
    }

    public void setNro_registro(int nro_registro) {
        this.nro_registro = nro_registro;
    }

    public int getValorConsujlta() {
        return valorConsujlta;
    }

    public void setValorConsujlta(int valorConsujlta) {
        this.valorConsujlta = valorConsujlta;
    }

    public List<LocalDateTime> getHorarios_livres() {
        return horarios_livres;
    }

    public void setHorarios_livres(List<LocalDateTime> horarios_livres) {
        this.horarios_livres = horarios_livres;
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
    
    public void remover_horario_livre(LocalDateTime data){
        horarios_livres.remove(data);
    }

}
