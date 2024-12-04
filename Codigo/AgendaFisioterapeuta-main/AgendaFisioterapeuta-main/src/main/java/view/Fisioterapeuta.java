/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controler.ControladorAgendamento;
import model.Consulta;
import model.Paciente;
import model.Sistema;

import java.awt.Color;
import java.awt.Component;

/**
 *
 * @author thiago
 */
public class Fisioterapeuta extends javax.swing.JFrame {

    private model.Fisioterapeuta fisioterapeuta;
    private static final Color DISPONIVEL = Color.GREEN;
    private static final Color INDISPONIVEL = Color.LIGHT_GRAY;


    public void dev() { //TODO: remover isso aqui
        // Sistema sistema = Sistema.getInstance();
        
        // sistema.criarContaPaciente("Maria","maria.oliveira@exemplo.com","senha456", LocalDate.of(1995, 8, 22));
        // sistema.criarContaFisioterapeuta(12345, 100,"Carlos Silva","carlos.silva@exemplo.com","senha123",LocalDate.of(1985, 5, 10));

        // this.fisioterapeuta = (model.Fisioterapeuta)sistema.autenticar("carlos.silva@exemplo.com", "senha123");
        // sistema.adicionarHorariosLivres(LocalDateTime.of(2024, 12, 3, 19, 0));
        // sistema.adicionarHorariosLivres(LocalDateTime.of(2024, 12, 4, 19, 0));
        // sistema.adicionarHorariosLivres(LocalDateTime.of(2024, 12, 5, 19, 0));
        // sistema.adicionarHorariosLivres(LocalDateTime.of(2024, 12, 6, 19, 0));
        // sistema.adicionarHorariosLivres(LocalDateTime.of(2024, 12, 7, 19, 0));
        // sistema.adicionarHorariosLivres(LocalDateTime.of(2024, 12, 8, 19, 0));

        // sistema.autenticar("maria.oliveira@exemplo.com","senha456");
        // sistema.marcarConsulta(LocalDateTime.of(2024, 12, 3, 19, 0));
        // sistema.marcarConsulta(LocalDateTime.of(2024, 12, 4, 19, 0));
        // sistema.marcarConsulta(LocalDateTime.of(2024, 12, 5, 19, 0));

        // this.fisioterapeuta = (model.Fisioterapeuta)sistema.autenticar("carlos.silva@exemplo.com", "senha123");
    }

    public void setFisioterapeuta(model.Fisioterapeuta fisioterapeuta) {
        this.fisioterapeuta = fisioterapeuta;
    }

    public void loadTable() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        String[] columnNames = new String[8];
        columnNames[0] = "Horário";
        for (int i = 1; i < 8; i++) {
            columnNames[i] = LocalDate.now().plusDays(i - 1).format(formatter);
        }

        // Dados da tabela
        String[][] data = new String[17][8]; // 17 horários (6h às 22h) + 1 para o horário
        int rowIndex = 0;

        for (int hour = 6; hour <= 22; hour++) {
            data[rowIndex][0] = hour + ":00"; // Preenchendo a coluna "Horário"
            for (int col = 1; col < 8; col++) {
                data[rowIndex][col] = "indisponível"; // Deixando os dias como indisponível por padrão
            }
            rowIndex++;
        }

        // Preencher horários disponíveis do fisioterapeuta logado
        ArrayList<LocalDateTime> horariosLivres = new ArrayList<>(fisioterapeuta.getHorarios_livres());
        for (LocalDateTime horario : horariosLivres) {
            int hour = horario.getHour();
            int dayIndex = horario.getDayOfMonth() - LocalDate.now().getDayOfMonth() + 1;
            if (dayIndex >= 1 && dayIndex < 8) {
            data[hour - 6][dayIndex] = "disponível";
            }
        }

        // Modelo de tabela
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

        tabela.setModel(tableModel);
        tabela.setRowHeight(30);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(80);

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Alternar cores das linhas
            if (!isSelected) {
                if (row % 2 == 1) {
                    c.setBackground(Color.LIGHT_GRAY);
                } else {
                    c.setBackground(Color.WHITE);
                }
            }

            if (column > 0) { // Não aplica cores na coluna de horários
                String status = (String) value;
                switch (status) {
                case "disponível":
                    c.setBackground(DISPONIVEL);
                    c.setForeground(DISPONIVEL);
                    break;
                default:
                if (row % 2 == 0) {
                    c.setBackground(new Color(255, 255, 255));
                    c.setForeground(new Color(255, 255, 255));
                } else {
                    c.setBackground(new Color(242, 242, 242));
                    c.setForeground(new Color(242, 242, 242));
                }
                break;
            }
            } else {
                c.setBackground(table.getBackground());
                c.setForeground(table.getForeground());
            }

            return c;
            }
        });
    }

    public void getConsultas() {
        Sistema sistema = Sistema.getInstance();

        //TODO: remover isso aqui
        DefaultListModel<String> listModel =  new DefaultListModel<>();
        ArrayList<Consulta> consultas = new ArrayList<>(sistema.listarConsultasPorFisioterapeuta(fisioterapeuta.getNome()));
        
        listModel.clear();
        for (Consulta consulta : consultas) {
            listModel.addElement(consulta.toString());
        }
        listConsultas.setModel(listModel);
    }

    public void getPaciente() {
        Sistema sistema = Sistema.getInstance();
        ArrayList<model.Paciente> pacientes = new ArrayList<>(sistema.listarPacientesPorFisioterapeuta(fisioterapeuta));
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.clear();
        for (model.Paciente paciente : pacientes) {
            listModel.addElement(paciente.getNome());
        }
        listPacientes.setModel(listModel);
    }

    //função para abriar a tela com os detalhes da consulta
    public void openConsulta(java.awt.event.MouseEvent e) {
        int index = listConsultas.getSelectedIndex();
        if (index >= 0) {
            Sistema sistema = Sistema.getInstance();
            ArrayList<Consulta> consultas = new ArrayList<>(sistema.listarConsultasPorFisioterapeuta(fisioterapeuta.getNome()));
            Consulta consultaSelecionada = consultas.get(index);
            ConsultasFisioterapeuta consultaDialog = new ConsultasFisioterapeuta(this, true, consultaSelecionada);
            consultaDialog.setConsulta(consultaSelecionada);
            consultaDialog.setVisible(true);
            getConsultas();
        }
}

public void openPaciente(java.awt.event.MouseEvent e) {
    int index = listPacientes.getSelectedIndex();
    if (index >= 0) {
        Sistema sistema = Sistema.getInstance();
        ArrayList<Paciente> pacientes = new ArrayList<>(sistema.listarPacientesPorFisioterapeuta(fisioterapeuta));
        Paciente pacienteSelecionado = pacientes.get(index);
        Diagnotico diagnotico = new Diagnotico(this, true);
        diagnotico.setPaciente(pacienteSelecionado);
        diagnotico.setVisible(true);
        getPaciente();
    }
}
    /**
     * Creates new form Fisioterapeuta
     */
    public Fisioterapeuta() {
        initComponents();
    }
    
    public Fisioterapeuta(model.Fisioterapeuta fisioterapeuta) {
        this.fisioterapeuta = fisioterapeuta;
        initComponents();
        loadTable();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listConsultas = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listPacientes = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                windowOpended(evt);
            }
        });

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Fisioterapeuta.this.stateChanged(evt);
            }
        });

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                handleTableClick(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);

        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(24, 24, 24))
        );

        jTabbedPane1.addTab("Disponibilidade", jPanel1);

        listConsultas.setFont(new java.awt.Font("Liberation Sans", 0, 16)); // NOI18N
        listConsultas.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "test", "test", "test" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listConsultas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listConsultas.setFixedCellHeight(30);
        listConsultas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listConsultashandleJListClick(evt);
            }
        });
        jScrollPane2.setViewportView(listConsultas);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(223, 223, 223)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(267, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        jTabbedPane1.addTab("Consultas", jPanel2);

        listPacientes.setFont(new java.awt.Font("Liberation Sans", 0, 16)); // NOI18N
        listPacientes.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "test", "test", "test" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listPacientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listPacientes.setFixedCellHeight(30);
        listPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listPacienteshandleJListClick2(evt);
            }
        });
        jScrollPane3.setViewportView(listPacientes);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(223, 223, 223)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(267, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pacientes", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void windowOpended(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_windowOpended
        //TODO
    }//GEN-LAST:event_windowOpended

    private void handleTableClick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_handleTableClick
        int row = tabela.rowAtPoint(evt.getPoint());
        int col = tabela.columnAtPoint(evt.getPoint());

        if (col > 0) {
            String currentStatus = (String) tabela.getValueAt(row, col);
            String newStatus = currentStatus.equals("disponível") ? "indisponível" : "disponível";
            tabela.setValueAt(newStatus, row, col);
        }
    }//GEN-LAST:event_handleTableClick

    private void listConsultashandleJListClick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listConsultashandleJListClick
        openConsulta(evt);
    }//GEN-LAST:event_listConsultashandleJListClick

    private void stateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_stateChanged
        JTabbedPane fonte = (JTabbedPane) evt.getSource();
         int index = fonte.getSelectedIndex();
        if (index == 0) {
            loadTable();
        }
        else if(index == 1){
            getConsultas();
        }
        else if(index == 2){
            getPaciente();
        }
    }//GEN-LAST:event_stateChanged

    private void listPacienteshandleJListClick2(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listPacienteshandleJListClick2
        openPaciente(evt);
    }//GEN-LAST:event_listPacienteshandleJListClick2

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        int rowCount = tabela.getRowCount();
        int colCount = tabela.getColumnCount();
        Sistema sistema = Sistema.getInstance();

        for (int row = 0; row < rowCount; row++) {
            for (int col = 1; col < colCount; col++) {
            String status = (String) tabela.getValueAt(row, col);
            if ("disponível".equals(status)) {
                int hour = 6 + row;
                String dateStr = tabela.getColumnName(col);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(dateStr + "/" + LocalDate.now().getYear(), formatter);
                LocalDateTime data = date.atTime(hour, 0);

                sistema.adicionarHorariosLivres(data);
            }
            }
        }
    }// GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Fisioterapeuta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fisioterapeuta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fisioterapeuta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fisioterapeuta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Fisioterapeuta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList<String> listConsultas;
    private javax.swing.JList<String> listPacientes;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
