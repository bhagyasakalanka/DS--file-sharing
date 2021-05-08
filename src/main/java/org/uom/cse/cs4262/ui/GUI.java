/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse.cs4262.ui;

import org.apache.tomcat.jni.Time;
import org.uom.cse.cs4262.File;
import org.uom.cse.cs4262.api.Cred;
import org.uom.cse.cs4262.api.NetworkDetails;
import org.uom.cse.cs4262.api.message.request.DownloadReq;
import org.uom.cse.cs4262.api.message.request.SearchReq;
import org.uom.cse.cs4262.controller.NodeOperationsImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form MainUI
     */

    private NodeOperationsImpl nodeOpsWS;

    private int sequenceNo;
    private int currentLogCount;
    // Variables declaration - do not modify
    private javax.swing.JMenuItem advLeave;
    private javax.swing.JMenuItem advRegister;
    private javax.swing.JMenuItem advUnregister;
    private javax.swing.JButton btnLeave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnDownload;
    private javax.swing.JButton btnStop;
    private javax.swing.JButton btnUnregsiter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;

    //    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new MainUI().setVisible(true);
//            }
//        });
//    }
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private JButton button1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAnsweredResponceCount;
    private javax.swing.JLabel lblAvgHopCountPerSearch;
    private javax.swing.JLabel lblAvgLatency;
    private javax.swing.JLabel lblForwardRequestCount;
    private javax.swing.JLabel lblReceivedRequestCount;
    private javax.swing.JLabel lblRequestSuccessRatio;
    private javax.swing.JLabel lblSearchRequestCount;
    private javax.swing.JList<String> lstMyFiles;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JTable tblLog;
    private javax.swing.JTable tblRoutingTable;
    private javax.swing.JTable tblSearchResults;
    private javax.swing.JTable tblStatTable;
    private javax.swing.JTextField txtBS_IP;
    private javax.swing.JTextField txtBS_PORT;
    private javax.swing.JTextField txtMyIP;
    private javax.swing.JTextField txtMyPort;
    private javax.swing.JTextField txtSearchFile;
    private javax.swing.JTextField txtUsername;

    public GUI() {
        initComponents();
        initializeRoutingTable();
        InitializeNetworkDetailsTable();
        InitializeSearchResultsTable();
        initializeMyFileList();
        initializeLog();
    }

    public GUI(NodeOperationsImpl nodeOpsWS) {
        initComponents();
        this.nodeOpsWS = nodeOpsWS;
        sequenceNo = 0;
        currentLogCount = 0;
        initializeRoutingTable();
        InitializeNetworkDetailsTable();
        InitializeSearchResultsTable();
        initializeMyFileList();
        initializeLog();

        //lock user creadential fields
        txtBS_IP.setEnabled(false);
        txtBS_PORT.setEnabled(false);
        txtMyIP.setEnabled(false);
        txtMyPort.setEnabled(false);
        txtUsername.setEnabled(false);
        txtSearchFile.setEnabled(true);

        setUserDetails();
        // This works
        new Thread(() -> {
            int searchCount = 0;
            List<Integer> portList = Arrays.asList(new Integer[]{44411,44414,44419});
            while (true) {
                updateLog();
                updateRoutingTable();
                updateSearchTable();
                UpdateStatTable();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(searchCount==0) {
                    if(!portList.contains(nodeOpsWS.getNode().getCred().getNodePort())){
                        searchCount++;
                        break;
                    }
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = null;
                    try {
                        date = dateFormat.parse("23/04/2021 22:50:00");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(new Date().getTime() >= date.getTime()){
                        System.out.println("starting automate search");
                        automateSearch();
                        System.out.println("ended automate search");
                        searchCount++;
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        //Thread to update performance measurements
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    updatePerformanceMeasurements();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }).start();

        txtSearchFile.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    btnSearch.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        tblLog.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int lastIndex = tblLog.getRowCount() - 1;
                tblLog.changeSelection(lastIndex, 0, false, false);
            }
        });

        tblSearchResults.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int lastIndex = tblSearchResults.getRowCount() - 1;
                tblSearchResults.changeSelection(lastIndex, 0, false, false);
            }
        });

        tblStatTable.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int lastIndex = tblStatTable.getRowCount() - 1;
                tblStatTable.changeSelection(lastIndex, 0, false, false);
            }
        });
    }

    private void initializeRoutingTable() {
        DefaultTableModel tblModel = (DefaultTableModel) tblRoutingTable.getModel();
        String header[] = new String[]{"IP", "Port", "Username"};
        tblModel.setColumnIdentifiers(header);
        tblRoutingTable.setModel(tblModel);
        tblRoutingTable.getTableHeader().setBackground(Color.LIGHT_GRAY);


    }

    private void InitializeNetworkDetailsTable() {
        DefaultTableModel tblModel = (DefaultTableModel) tblStatTable.getModel();
        String header[] = new String[]{"Query", "Time Triggered", "Time Delivered", "Served IP Address", "Served Port", "Hop Count", "File"};
        tblModel.setColumnIdentifiers(header);
        tblStatTable.setModel(tblModel);

        float[] columnWidthPercentage = {15.0f, 14.0f, 14.0f, 11.0f, 11.0f, 6.0f, 22.0f,7.0f};
        int tW = tblStatTable.getWidth();
        TableColumn column;
        TableColumnModel jTableColumnModel = tblStatTable.getColumnModel();
        int cantCols = jTableColumnModel.getColumnCount();
        for (int i = 0; i < cantCols; i++) {
            column = jTableColumnModel.getColumn(i);
            int pWidth = Math.round(columnWidthPercentage[i] * tW);
            column.setPreferredWidth(pWidth);
        }
    }

    private void InitializeSearchResultsTable() {
        DefaultTableModel tblModel = (DefaultTableModel) tblSearchResults.getModel();
        String header[] = new String[]{"Query", "Matched Files"};
        tblModel.setColumnIdentifiers(header);
        tblSearchResults.setModel(tblModel);
        tblSearchResults.getTableHeader().setBackground(Color.LIGHT_GRAY);

        float[] columnWidthPercentage = {20.0f, 80.0f};
        int tW = tblSearchResults.getWidth();
        TableColumn column;
        TableColumnModel jTableColumnModel = tblSearchResults.getColumnModel();
        int cantCols = jTableColumnModel.getColumnCount();
        for (int i = 0; i < cantCols; i++) {
            column = jTableColumnModel.getColumn(i);
            int pWidth = Math.round(columnWidthPercentage[i] * tW);
            column.setPreferredWidth(pWidth);
        }
    }

    private void initializeMyFileList() {
        DefaultListModel model = new DefaultListModel<String>();
        lstMyFiles.setModel(model);
    }

    private void initializeLog() {
        DefaultTableModel tblModel = (DefaultTableModel) tblLog.getModel();
        String header[] = new String[]{""};
        tblModel.setColumnIdentifiers(header);
        tblLog.setModel(tblModel);
    }

    private void resetPerformanceMeasurements() {
        lblReceivedRequestCount.setText("0");
        lblForwardRequestCount.setText("0");
        lblAnsweredResponceCount.setText("0");
        lblSearchRequestCount.setText("0");
        lblAvgHopCountPerSearch.setText("0");
        lblRequestSuccessRatio.setText("0");
        lblAvgLatency.setText("0");
    }

    public void ResetAll() {
        initializeRoutingTable();
        InitializeNetworkDetailsTable();
        InitializeSearchResultsTable();
        initializeMyFileList();
        initializeLog();
        resetPerformanceMeasurements();
    }

    public void setUserDetails() {
        txtBS_IP.setText(nodeOpsWS.getNode().getBs().getNodeIp());
        txtBS_PORT.setText(String.valueOf(nodeOpsWS.getNode().getBs().getNodePort()));
        txtMyIP.setText(nodeOpsWS.getNode().getCred().getNodeIp());
        txtMyPort.setText(String.valueOf(nodeOpsWS.getNode().getCred().getNodePort()));
        txtUsername.setText(nodeOpsWS.getNode().getCred().getNodeName());

        nodeOpsWS.getNode().getFileList().forEach(fileName -> {
            File file = nodeOpsWS.getNode().getDownloadTable().get(fileName);
            ((DefaultListModel) lstMyFiles.getModel()).addElement(file.getFileDetails());
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRoutingTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnUnregsiter = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtBS_IP = new javax.swing.JTextField();
        txtMyPort = new javax.swing.JTextField();
        btnLeave = new javax.swing.JButton();
        txtMyIP = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtBS_PORT = new javax.swing.JTextField();
        btnStop = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstMyFiles = new javax.swing.JList<>();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblStatTable = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtSearchFile = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnDownload = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSearchResults = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lblReceivedRequestCount = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        lblForwardRequestCount = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        lblAnsweredResponceCount = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        lblSearchRequestCount = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        lblAvgHopCountPerSearch = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        lblRequestSuccessRatio = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        lblAvgLatency = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblLog = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        menuItemExit = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        advRegister = new javax.swing.JMenuItem();
        advUnregister = new javax.swing.JMenuItem();
        advLeave = new javax.swing.JMenuItem();

        jMenu2.setText("File");
        jMenuBar2.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar2.add(jMenu3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(850, 650));


        jPanel1.setPreferredSize(new java.awt.Dimension(425, 350));
        jPanel1.setBackground(Color.DARK_GRAY);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Routing Table", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 182));
        jPanel2.setBackground(Color.GRAY);
        jPanel5.setBackground(Color.GRAY);
        jPanel4.setBackground(Color.GRAY);
        jPanel13.setBackground(Color.GRAY);
        jPanel8.setBackground(Color.GRAY);
        jPanel7.setBackground(Color.GRAY);
        jPanel9.setBackground(Color.GRAY);
        jPanel3.setBackground(Color.DARK_GRAY);




        tblRoutingTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null}
                },
                new String [] {
                        "Title 1", "Title 2", "Title 3"
                }
        ));
        tblRoutingTable.setName("tblRoutingTable"); // NOI18N
        tblRoutingTable.setShowHorizontalLines(false);
        tblRoutingTable.setShowVerticalLines(false);
        jScrollPane3.setViewportView(tblRoutingTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
        );
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Node Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(400, 182));

        jLabel4.setText("Node Name");

        jLabel2.setText("Node IP Address");

        jLabel3.setText("Node Port");

        btnUnregsiter.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnUnregsiter.setText("Unregister");
        btnUnregsiter.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUnregsiter.setOpaque(true);
        btnUnregsiter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnregsiterActionPerformed(evt);
            }
        });

        jLabel1.setText("BS IP");

        btnLeave.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLeave.setText("Leave");
        btnLeave.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLeave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeaveActionPerformed(evt);
            }
        });

        txtMyIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMyIPActionPerformed(evt);
            }
        });

        jLabel5.setText("BS Port");

        txtBS_PORT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBS_PORTActionPerformed(evt);
            }
        });

        btnStop.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnStop.setText("Stop");
        btnStop.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnStop.setOpaque(true);
        btnStop.addActionListener(evt -> btnStopActionPerformed(evt));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtBS_IP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtBS_PORT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtMyIP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtMyPort, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(81, 81, 81))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(btnUnregsiter, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnLeave, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtBS_IP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtBS_PORT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtMyIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtMyPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnStop)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnUnregsiter)
                                        .addComponent(btnLeave))
                                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Local Files", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        lstMyFiles.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lstMyFiles);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        );




        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))

        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );


        jPanel3.setPreferredSize(new java.awt.Dimension(450, 400));
        jPanel3.setBackground(Color.DARK_GRAY);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "File Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(400, 268));

        txtSearchFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchFileActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        btnDownload.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnDownload.setText("Download");
        btnDownload.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnDownloadActionPerformed(evt);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });
        tblSearchResults.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null}
                },
                new String [] {
                        "Title 1", "Title 2"
                }
        ));
        tblSearchResults.setShowHorizontalLines(false);
        tblSearchResults.setShowVerticalLines(false);
        jScrollPane4.setViewportView(tblSearchResults);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Network Details Table", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel9.setPreferredSize(new java.awt.Dimension(250, 100));
        tblStatTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null}
                },
                new String [] {
                        "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
                }
        ));
        tblStatTable.setShowHorizontalLines(false);
        tblStatTable.setShowVerticalLines(false);
        tblStatTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        jScrollPane5.setViewportView(tblStatTable);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE-20)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(txtSearchFile)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(btnSearch, 0, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnDownload, GroupLayout.PREFERRED_SIZE, 150, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtSearchFile, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))


                                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnDownload, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Performance", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel8.setPreferredSize(new java.awt.Dimension(296, 100));

        lblReceivedRequestCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReceivedRequestCount.setText("0");
        lblReceivedRequestCount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblReceivedRequestCount.setPreferredSize(new java.awt.Dimension(50, 16));

        jLabel15.setText("Received Request Count");
        jLabel15.setPreferredSize(new java.awt.Dimension(119, 16));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblReceivedRequestCount, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblReceivedRequestCount, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel15, lblReceivedRequestCount});

        lblForwardRequestCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblForwardRequestCount.setText("0");
        lblForwardRequestCount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblForwardRequestCount.setPreferredSize(new java.awt.Dimension(50, 16));

        jLabel18.setText("Forwarded Request Count");
        jLabel18.setPreferredSize(new java.awt.Dimension(119, 16));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblForwardRequestCount, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblForwardRequestCount, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        lblAnsweredResponceCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnsweredResponceCount.setText("0");
        lblAnsweredResponceCount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblAnsweredResponceCount.setPreferredSize(new java.awt.Dimension(50, 16));

        jLabel20.setText("Answered Responce Count");
        jLabel20.setPreferredSize(new java.awt.Dimension(119, 16));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
                jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblAnsweredResponceCount, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
                jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblAnsweredResponceCount, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        lblSearchRequestCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSearchRequestCount.setText("0");
        lblSearchRequestCount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblSearchRequestCount.setPreferredSize(new java.awt.Dimension(50, 16));

        jLabel22.setText("Searched Request Count");
        jLabel22.setPreferredSize(new java.awt.Dimension(119, 16));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
                jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblSearchRequestCount, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
                jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblSearchRequestCount, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        lblAvgHopCountPerSearch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAvgHopCountPerSearch.setText("0");
        lblAvgHopCountPerSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblAvgHopCountPerSearch.setPreferredSize(new java.awt.Dimension(50, 16));

        jLabel34.setText("Average Hop Count Per Search");
        jLabel34.setPreferredSize(new java.awt.Dimension(119, 16));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
                jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblAvgHopCountPerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
                jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblAvgHopCountPerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        lblRequestSuccessRatio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRequestSuccessRatio.setText("0");
        lblRequestSuccessRatio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblRequestSuccessRatio.setPreferredSize(new java.awt.Dimension(50, 16));

        jLabel38.setText("Request Success Ratio");
        jLabel38.setPreferredSize(new java.awt.Dimension(119, 16));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
                jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel20Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblRequestSuccessRatio, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
                jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel20Layout.createSequentialGroup()
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblRequestSuccessRatio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        lblAvgLatency.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAvgLatency.setText("0");
        lblAvgLatency.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblAvgLatency.setPreferredSize(new java.awt.Dimension(50, 16));

        jLabel42.setText("Average Latency Of Query Execution ");
        jLabel42.setPreferredSize(new java.awt.Dimension(119, 16));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
                jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel22Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblAvgLatency, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
                jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel22Layout.createSequentialGroup()
                                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblAvgLatency, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(53, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Logs", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel13.setPreferredSize(new java.awt.Dimension(250, 100));

        tblLog.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Title 1"
                }
        ));
        tblLog.setShowHorizontalLines(false);
        jScrollPane6.setViewportView(tblLog);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
                jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
                jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 1500, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
                                .addContainerGap())
        );



        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1218, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1218, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void menuItemExitActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.dispose();
    }

    private void txtSearchFileActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {
        String query = txtSearchFile.getText().trim();
        if (!query.isEmpty()) {
            nodeOpsWS.logMe("Started searching for \"" + query + "\"...");
            SearchReq searchReq = new SearchReq(++sequenceNo, nodeOpsWS.getNode().getCred(), query, 0);
            List<String> mySearchResults = nodeOpsWS.checkFilesInFileList(searchReq.getFileName(), nodeOpsWS.getNode().getFileList());
            if (!nodeOpsWS.getNode().getDetailsTable().containsKey(query)) {
                nodeOpsWS.getNode().getDetailsTable().put(query, new ArrayList<>());
            }
            if (nodeOpsWS.getNode().getFileList().contains(searchReq.getFileName())) {
                nodeOpsWS.logMe("Exactly matching file is locally available!");
            } else {
                if (!mySearchResults.isEmpty()) {
                    nodeOpsWS.logMe("Partially matching file is locally available!");
                    nodeOpsWS.getNode().getDetailsTable().get(query).addAll(mySearchResults);
                }
                nodeOpsWS.triggerSearchRequest(searchReq);
                updateSearchTable();
            }
            txtSearchFile.setText("");
        }
    }

    private void btnDownloadActionPerformed(java.awt.event.ActionEvent evt) throws NoSuchAlgorithmException {
        String query = txtSearchFile.getText().trim();
        if (!query.isEmpty()) {
            nodeOpsWS.logMe("Started downloading for \"" + query + "\"...");
//            DownloadReq downloadReq = new DownloadReq(++sequenceNo, nodeOpsWS.getNode().getCred(), query, 0);
            DownloadReq downloadReq = new DownloadReq(nodeOpsWS.getNode().getCred(), query);
            List<String> myDownloadResult = nodeOpsWS.checkFilesInFileList(downloadReq.getFileName(), nodeOpsWS.getNode().getFileList());
            System.out.println("cached file list "+nodeOpsWS.getNode().getCachedFilesWithOriginalFileNameTable().size());
            List<NetworkDetails> cachedDownloadResults = nodeOpsWS.checkFilesInStatTableForOriginal(downloadReq.getFileName(),nodeOpsWS.getNode().getCachedFilesWithOriginalFileNameTable());
            System.out.println("cached download "+cachedDownloadResults.size());

            if (nodeOpsWS.getNode().getFileList().contains(downloadReq.getFileName())) {
                nodeOpsWS.logMe("Exactly matching file is locally available!");
                File file = nodeOpsWS.getNode().getDownloadTable().get(downloadReq.getFileName());
                nodeOpsWS.logMe("\t"+ file.getFileDetails());
            } else {
                if (!myDownloadResult.isEmpty()) {
                    nodeOpsWS.logMe("Partially matching file is locally available!");
                    File file = nodeOpsWS.getNode().getDownloadTable().get(myDownloadResult.get(0));
                    nodeOpsWS.logMe("\t"+ file.getFileDetails());
//                    nodeOpsWS.getNode().getDetailsTable().get(query).addAll(mySearchResults);
                }
                else if (!cachedDownloadResults.isEmpty()) {
                    nodeOpsWS.logMe("Cached file available");
                    File file = nodeOpsWS.getNode().getDownloadTable().get(cachedDownloadResults.get(0));
                    if(file == null) {
                        nodeOpsWS.triggerDownloadRequest(downloadReq, cachedDownloadResults.get(0).getServedNode());
                    }else {
                        nodeOpsWS.logMe("\t"+ file.getFileDetails());
                    }
                }
                else {
//                int index = nodeOpsWS.getNode().getNetworkDetailsTable().indexOf(query);
//                System.out.println("index: "+ index);
                    nodeOpsWS.logMe(downloadReq.getFileName()+ " is not available");
                }
            }
            System.out.println(query + " " + nodeOpsWS.getNode().getDownloadTable().get(query));
            txtSearchFile.setText("");
        }
    }


    private void advRegisterActionPerformed(java.awt.event.ActionEvent evt) {
        nodeOpsWS.register();
    }

    private void txtMyIPActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btnUnregsiterActionPerformed(java.awt.event.ActionEvent evt) {
//        btnUnregsiter.setEnabled(false);
        try {
            nodeOpsWS.unRegister();
            JOptionPane.showMessageDialog(this, "Unregistered!");
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(this, "Unregister failed: " + e1.getMessage());
//            btnUnregsiter.setEnabled(true);
            e1.printStackTrace();
        }
    }

    private void txtBS_PORTActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btnLeaveActionPerformed(java.awt.event.ActionEvent evt) {
        try {
//            btnLeave.setEnabled(false);
            nodeOpsWS.leave();
            JOptionPane.showMessageDialog(this, "Leave Successful!");
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(this, "Leave failed: " + e1.getMessage());
//            btnLeave.setEnabled(true);
            e1.printStackTrace();
        }
    }

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {
        btnUnregsiter.doClick();
        btnLeave.doClick();
    }

    private void advLeaveActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void advUnregisterActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    public void start() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
        });
    }


    public void updateRoutingTable() {
        synchronized (nodeOpsWS){
            DefaultTableModel dtm = (DefaultTableModel) tblRoutingTable.getModel();
            List<Cred> routingTable = nodeOpsWS.getNode().getRoutingTable();
            //Remove rows one by one from the end of the table
            for (int i = dtm.getRowCount() - 1; i >= 0; i--) {
                dtm.removeRow(i);
            }
            for (Cred routingTableRecord : routingTable) {
                dtm.addRow(new Object[]{routingTableRecord.getNodeIp(), routingTableRecord.getNodePort(), routingTableRecord.getNodeName()});
            }
        }
    }

    public void updateLog() {
        if (nodeOpsWS.isLogFlag()) {
            List<String> logList = nodeOpsWS.getLogDisplay();
            int newLogCount = logList.size() - currentLogCount;
            List<String> newLogs = logList.subList(logList.size() - newLogCount, logList.size());
            for (String newLog : newLogs) {
                ((DefaultTableModel) tblLog.getModel()).addRow(new Object[]{newLog});
            }

            //update self variables
            currentLogCount = logList.size();
            nodeOpsWS.setLogFlag(false);
        }
    }

    public void updatePerformanceMeasurements() {
        lblReceivedRequestCount.setText(String.valueOf(nodeOpsWS.getNode().getNumberReceivedQuery()));
        lblForwardRequestCount.setText(String.valueOf(nodeOpsWS.getNode().getNumberForwardedQuery()));
        lblAnsweredResponceCount.setText(String.valueOf(nodeOpsWS.getNode().getNumberAnsweredQuery()));
        lblSearchRequestCount.setText(String.valueOf(nodeOpsWS.getNode().getNumberSearchedQuery()));
        lblAvgHopCountPerSearch.setText(String.valueOf(nodeOpsWS.getNode().calcAverageHopCountPerSearch()));
        lblRequestSuccessRatio.setText(String.valueOf(nodeOpsWS.getNode().calcRequestSuccessRatio()));
        lblAvgLatency.setText(String.valueOf(nodeOpsWS.getNode().calcAverageLatency()));
    }

    public void updateSearchTable() {
        synchronized (nodeOpsWS){
            LinkedHashMap<String, ArrayList<String>> searchResults = nodeOpsWS.getNode().getDetailsTable();
            List<NetworkDetails> networkDetails = nodeOpsWS.getNode().getNetworkDetailsTable();
            ((DefaultTableModel) tblSearchResults.getModel()).setRowCount(0);
            for (String key : searchResults.keySet()) {
                ((DefaultTableModel) tblSearchResults.getModel()).addRow(new Object[]{key, String.join(",", searchResults.get(key))});
            }
        }
    }

    public void UpdateStatTable() {
        DefaultTableModel tableModel = (DefaultTableModel) tblStatTable.getModel();

        //remove previous records
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
        List<NetworkDetails> statTable = nodeOpsWS.getNode().getNetworkDetailsTable();
        for (int i = 0; i < statTable.size(); i++) {
            NetworkDetails row = statTable.get(i);
            String filelist = String.join(",", row.getFileList());
//            JButton jButton = new JButton("Download");
//            jButton.setName("Download");
//            jButton.setBackground(Color.DARK_GRAY);
//            jButton.addActionListener(v ->{
//                System.out.println("downloading");
//            });

            tableModel.addRow(new Object[]{row.getQuery(), new SimpleDateFormat("HH:mm:ss").format(row.getTrigTime()), new SimpleDateFormat("HH:mm:ss").format(row.getDeliveryTime()), row.getServedNode().getNodeIp(), row.getServedNode().getNodePort(), row.getHopsNeeded(), filelist});
        }
    }
    private static List<String> createFileList() {
        ArrayList<String> fileList = new ArrayList<>();
        fileList.add("Adventures of Tintin");
        fileList.add("Jack and Jill");
        fileList.add("Glee");
        fileList.add("The Vampire Diarie");
        fileList.add("King Arthur");
        fileList.add("Windows XP");
        fileList.add("Harry Potter");
        fileList.add("Kung Fu Panda");
        fileList.add("Lady Gaga");
        fileList.add("Twilight");
        fileList.add("Windows 8");
        fileList.add("Mission Impossible");
        fileList.add("Turn Up The Music");
        fileList.add("Super Mario");
        fileList.add("American Pickers");
        fileList.add("Microsoft Office 2010");
        fileList.add("Happy Feet");
        fileList.add("Modern Family");
        fileList.add("American Idol");
        fileList.add("Hacking for Dummies");
        Collections.shuffle(fileList);
        List<String> subFileList = fileList.subList(0, 5);
        return subFileList;
    }

    private void automateSearch() {
        List<String> searchQueryList = new ArrayList<>();
        for(int i=0; i<10; i++){
            List<String> arr =  createFileList();
            searchQueryList.addAll(arr);
        }
        System.out.println(searchQueryList.size());
        searchQueryList.forEach(query->{
            SearchReq searchReq = new SearchReq(++sequenceNo, nodeOpsWS.getNode().getCred(), query, 0);
            List<String> mySearchResults = nodeOpsWS.checkFilesInFileList(searchReq.getFileName(), nodeOpsWS.getNode().getFileList());
            if (!nodeOpsWS.getNode().getDetailsTable().containsKey(query)) {
                synchronized (nodeOpsWS){
                    nodeOpsWS.getNode().getDetailsTable().put(query, new ArrayList<>());
                }
            }
            if (nodeOpsWS.getNode().getFileList().contains(searchReq.getFileName())) {
                synchronized (nodeOpsWS){
                    nodeOpsWS.logMe("Exactly matching file is locally available!");
                }
            } else {
                if (!mySearchResults.isEmpty()) {
                    synchronized (nodeOpsWS) {
                        nodeOpsWS.logMe("Partially matching file is locally available!");
                        nodeOpsWS.getNode().getDetailsTable().get(query).addAll(mySearchResults);
                    }
                }
                synchronized (nodeOpsWS) {
                    nodeOpsWS.triggerSearchRequest(searchReq);
                }
                updateSearchTable();
            }
        });

    }





}
