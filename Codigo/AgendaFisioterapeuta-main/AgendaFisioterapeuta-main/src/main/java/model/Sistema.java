package model;

import controler.ControladorAgendamento;
import controler.ControladorAutenticacao;
import controler.ControladorConsultas;
import controler.ControladorConta;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author hugol
 */
public class Sistema implements Serializable{
    private List<Fisioterapeuta> lista_fisioterapeutas = new ArrayList<>();
    private List<Paciente> lista_pacientes = new ArrayList<>();
    
    private List<Consulta> consultas_marcadas = new ArrayList<>();
    private List<Consulta> consultas_realizadas = new ArrayList<>();
    
    private ControladorConta c_conta = new ControladorConta();
    private ControladorAutenticacao c_autenticacao = new ControladorAutenticacao();
    private ControladorConsultas c_consultas = new ControladorConsultas();
    private ControladorAgendamento c_agendamento = new ControladorAgendamento();
    
    private Usuario usuarioLogado;
    
    // Caminhos para os arquivos
    private static final String FISIOTERAPEUTAS_FILE = "fisioterapeutas.ser";
    private static final String PACIENTES_FILE = "pacientes.ser";
    private static final String CONSULTAS_MARCADAS_FILE = "consultas_marcadas.ser";
    private static final String CONSULTAS_REALIZADAS_FILE = "consultas_realizadas.ser";

    private static Sistema instance;
    public Sistema() {
    }

    public static Sistema getInstance(){

        if (instance == null) {
            instance = new Sistema();
        }
        return instance;
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
            if (consulta.getPaciente().getEmail().equals(paciente.getEmail()) && 
                consulta.getFisioterapeuta().getEmail().equals(fisioterapeuta.getEmail()) && 
                consulta.getData().equals(data)) {
                return consulta; 
            }
        }
        return null;
    }

    public List<Consulta> listarConsultasPorPaciente(String nomePaciente) {
        Paciente paciente = buscarPacientePorNome(nomePaciente);
        List<Consulta> consultasPaciente = new ArrayList<>();
        for (Consulta consulta : consultas_marcadas) {
            if (consulta.getPaciente().getEmail().equals(paciente.getEmail())) {
                System.out.println(consulta.toString());
                consultasPaciente.add(consulta);
            }
        }
        return consultasPaciente;
    }

    public List<Consulta> listarConsultasRealizadasPorPaciente(String nomePaciente) {
        Paciente paciente = buscarPacientePorNome(nomePaciente);
        List<Consulta> consultasPaciente = new ArrayList<>();
        for (Consulta consulta : consultas_realizadas) {
            if (consulta.getPaciente().getEmail().equals(paciente.getEmail())) {
                consultasPaciente.add(consulta);
            }
        }
        return consultasPaciente;
    }
    
    public List<Consulta> listarConsultasPorFisioterapeuta(String nomeFisioterapeuta) {
        Fisioterapeuta fisioterapeuta = buscarFisioterapeutaPorNome(nomeFisioterapeuta);
        List<Consulta> consultasFisioterapeuta = new ArrayList<>();
        for (Consulta consulta : consultas_marcadas) {
            if (consulta.getFisioterapeuta().getEmail().equals(fisioterapeuta.getEmail())) {
                consultasFisioterapeuta.add(consulta);
            }
        }
        return consultasFisioterapeuta;
    }

    public List<Paciente> listarPacientesPorFisioterapeuta(Fisioterapeuta fisioterapeuta) {
        List<Paciente> pacientes = new ArrayList<>();
        for (Consulta consulta : consultas_realizadas) {
            if (consulta.getFisioterapeuta().getEmail().equals(fisioterapeuta.getEmail())) {
                Paciente paciente = consulta.getPaciente();
            if (!pacientes.contains(paciente)) {
                pacientes.add(paciente);
            }
            }
        }
        return pacientes;
    }

    public void cancelarConsulta(Consulta consulta) {
        if (consulta.getData().isBefore(LocalDateTime.now().plusDays(2))) {
            throw new IllegalArgumentException("Consultas só podem ser canceladas com mais de 2 dias de antecedência.");
        }
        boolean status = consultas_marcadas.remove(consulta);
        if (status) {
            consulta.getFisioterapeuta().getHorarios_livres().add(consulta.getData());
        }
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

    public List<LocalDateTime> listarHorariosLivres(Paciente paciente) {
        Set<LocalDateTime> horariosLivres = new TreeSet<>();
        for (Fisioterapeuta fisioterapeuta : lista_fisioterapeutas) {
            for (LocalDateTime horario : fisioterapeuta.getHorarios_livres()) {
                if (paciente.isPrioritario() == false && horario.getHour() > 10) {//pacientes sem prioridade só podem marcar consulta depois das 10h
                    horariosLivres.add(horario);
                }
                else{
                    horariosLivres.add(horario);
                }
            }
        }
        return new ArrayList<>(horariosLivres);
    }
    
    public void marcarConsulta(LocalDateTime data){
        Paciente p = (Paciente) usuarioLogado;

        String nomeFisioterapeuta = null;
        for (Fisioterapeuta fisioterapeuta : lista_fisioterapeutas) {
            if (fisioterapeuta.getHorarios_livres().contains(data)) {
                nomeFisioterapeuta = fisioterapeuta.getNome();
                break;
            }
        }
        if (nomeFisioterapeuta == null) {
            throw new IllegalArgumentException("Nenhum fisioterapeuta disponível para a data e hora selecionada.");
        }
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
    
    // Método para salvar os dados
    public void salvarDados() {
        salvarLista(FISIOTERAPEUTAS_FILE, lista_fisioterapeutas);
        salvarLista(PACIENTES_FILE, lista_pacientes);
        salvarLista(CONSULTAS_MARCADAS_FILE, consultas_marcadas);
        salvarLista(CONSULTAS_REALIZADAS_FILE, consultas_realizadas);
    }

    // Método genérico para salvar uma lista
    private <T> void salvarLista(String filePath, List<T> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    // Método para carregar os dados
    @SuppressWarnings("unchecked")
    public void carregarDados() {
        lista_fisioterapeutas = carregarLista(FISIOTERAPEUTAS_FILE);
        lista_pacientes = carregarLista(PACIENTES_FILE);
        consultas_marcadas = carregarLista(CONSULTAS_MARCADAS_FILE);
        consultas_realizadas = carregarLista(CONSULTAS_REALIZADAS_FILE);
    }

    // Método genérico para carregar uma lista
    private <T> List<T> carregarLista(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar os dados: " + e.getMessage());
            return new ArrayList<>(); // Retorna uma lista vazia se der erro
        }
    }
}
