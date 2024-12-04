/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.events.MouseEvent;

import controler.ControladorAgendamento;
import model.Consulta;
import model.Fisioterapeuta;
import model.Sistema;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author thiago
 */
public class Paciente extends javax.swing.JFrame {

    private model.Paciente paciente;

    private final Color DISPONIVEL = Color.GREEN;
    private final Color AGENDADO = Color.YELLOW;
    private final Color INDISPONIVEL_LIGHT = new Color(211, 211, 211);
    private final Color INDISPONIVEL_DARK = new Color(211, 211, 211);

    public void setPaciente(model.Paciente paciente) {
        this.paciente = paciente;
    }

    public void dev() { //TODO: remover isso aqui
        // Sistema sistema = Sistema.getInstance();
        
        // sistema.criarContaPaciente("Maria","maria.oliveira@exemplo.com","senha456", LocalDate.of(1995, 8, 22));
        // sistema.criarContaFisioterapeuta(12345, 100,"Carlos Silva","carlos.silva@exemplo.com","senha123",LocalDate.of(1985, 5, 10));
        
        // sistema.autenticar("carlos.silva@exemplo.com", "senha123");
        // sistema.adicionarHorariosLivres(LocalDateTime.of(2024, 12, 3, 19, 0));
        // sistema.adicionarHorariosLivres(LocalDateTime.of(2024, 12, 4, 19, 0));
        // sistema.adicionarHorariosLivres(LocalDateTime.of(2024, 12, 5, 19, 0));
        // sistema.adicionarHorariosLivres(LocalDateTime.of(2024, 12, 6, 19, 0));
        // sistema.adicionarHorariosLivres(LocalDateTime.of(2024, 12, 7, 19, 0));
        // sistema.adicionarHorariosLivres(LocalDateTime.of(2024, 12, 8, 19, 0));

        // this.paciente = (model.Paciente) sistema.autenticar("maria.oliveira@exemplo.com","senha456");
        // sistema.marcarConsulta(LocalDateTime.of(2024, 12, 3, 19, 0));
        // sistema.marcarConsulta(LocalDateTime.of(2024, 12, 4, 19, 0));
        // sistema.marcarConsulta(LocalDateTime.of(2024, 12, 5, 19, 0));
    }

    public void getConsultas() {
        Sistema sistema = Sistema.getInstance();

        //TODO: remover isso aqui
        DefaultListModel<String> listModel =  new DefaultListModel<>();
        ArrayList<Consulta> consultas = new ArrayList<>(sistema.listarConsultasPorPaciente(paciente.getNome()));

        listModel.clear();
        for (Consulta consulta : consultas) {
            listModel.addElement(consulta.toString());
        }
        listConsultas.setModel(listModel);
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
            data[rowIndex][col] = "indisponível"; // Inicialmente todos os horários são indisponíveis
            }
            rowIndex++;
        }
        
        // Preenchendo os horários livres
        Sistema sistema = Sistema.getInstance();
        ArrayList<LocalDateTime> horariosLivres = new ArrayList<>(sistema.listarHorariosLivres(paciente));
        for (LocalDateTime horario : horariosLivres) {
            int dayIndex = horario.getDayOfMonth() - LocalDate.now().getDayOfMonth() + 1;
            int hourIndex = horario.getHour() - 6;
            if (dayIndex >= 1 && dayIndex < 8 && hourIndex >= 0 && hourIndex < 17) {
            data[hourIndex][dayIndex] = "livre";
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
                case "agendado":
                    c.setBackground(AGENDADO);
                    c.setForeground(AGENDADO);
                    break;
                case "livre":
                if (row % 2 == 0) {
                    c.setBackground(new Color(255, 255, 255));
                    c.setForeground(new Color(255, 255, 255));
                } else {
                    c.setBackground(new Color(242, 242, 242));
                    c.setForeground(new Color(242, 242, 242));
                }
                break;
                default: // indisponível
                    if (row % 2 == 0) {
                        c.setBackground(INDISPONIVEL_LIGHT);
                        c.setForeground(INDISPONIVEL_LIGHT);
                    } else {
                        c.setBackground(INDISPONIVEL_DARK);
                        c.setForeground(INDISPONIVEL_DARK);
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

    //função para abriar a tela com os detalhes da consulta
    public void openConsulta(java.awt.event.MouseEvent e) {
            int index = listConsultas.getSelectedIndex();
            if (index >= 0) {
                Sistema sistema = Sistema.getInstance();
                ArrayList<Consulta> consultas = new ArrayList<>(sistema.listarConsultasPorPaciente(paciente.getNome()));
                Consulta consultaSelecionada = consultas.get(index);
                Consultas consultaDialog = new Consultas(this, true, consultaSelecionada);
                consultaDialog.setConsulta(consultaSelecionada);
                consultaDialog.setVisible(true);
                getConsultas();
            }
    }

    /**
     * Creates new form Fisioterapeuta
     */
    public Paciente() {
        initComponents();
        dev();
    }

    public Paciente(model.Paciente paciente) {
        this.paciente = paciente;
        initComponents();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listConsultas = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                Paciente.this.windowOpened(evt);
            }
        });

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Paciente.this.stateChanged(evt);
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

        jButton1.setText("Agendar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(142, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Agendar Consulta", jPanel2);

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
                handleJListClick(evt);
            }
        });
        jScrollPane2.setViewportView(listConsultas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(205, 205, 205)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(473, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Próximas Consultas", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_stateChanged
        JTabbedPane fonte = (JTabbedPane) evt.getSource();
        int index = fonte.getSelectedIndex();
        if (index == 0) {
            loadTable();
        }
        else if(index == 1){
            getConsultas();
        }
    }//GEN-LAST:event_stateChanged

    private void handleJListClick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_handleJListClick
        openConsulta(evt);
    }//GEN-LAST:event_handleJListClick

    private void handleTableClick(java.awt.event.MouseEvent e) {// GEN-FIRST:event_handleTableClick
        int row = tabela.rowAtPoint(e.getPoint());
        int col = tabela.columnAtPoint(e.getPoint());

        if (col > 0) {
            String currentStatus = (String) tabela.getValueAt(row, col);
            if (!(currentStatus.equals("agendado") || currentStatus.equals("indisponível"))) {
                String newStatus = currentStatus.equals("disponível") ? "livre" : "disponível";
                tabela.setValueAt(newStatus, row, col);
            }
        }
    }// GEN-LAST:event_handleTableClick

    private void windowOpened(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_windowOpened
        loadTable();
        getConsultas();
    }// GEN-LAST:event_windowOpened

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

                sistema.marcarConsulta(data);
                tabela.setValueAt("agendado", row, col);
            }
            }
        }
    }// GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Paciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Paciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Paciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Paciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Paciente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList<String> listConsultas;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
