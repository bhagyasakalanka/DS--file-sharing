/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse.cs4262.ui;

import org.uom.cse.cs4262.api.Cred;
import org.uom.cse.cs4262.api.NetworkDetails;
import org.uom.cse.cs4262.api.message.request.SearchReq;
import org.uom.cse.cs4262.controller.NodeOperationsImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class GUICopy extends JFrame {

    /**
     * Creates new form MainUI
     */

    private NodeOperationsImpl nodeOpsWS;

    private int sequenceNo;
    private int currentLogCount;
    // Variables declaration - do not modify
    private JMenuItem advLeave;
    private JMenuItem advRegister;
    private JMenuItem advUnregister;
    private JButton btnLeave;
    private JButton btnSearch;
    private JButton btnStop;
    private JButton btnUnregsiter;
    private JLabel jLabel1;
    private JLabel jLabel15;
    private JLabel jLabel18;
    private JLabel jLabel2;
    private JLabel jLabel20;
    private JLabel jLabel22;
    private JLabel jLabel3;
    private JLabel jLabel34;
    private JLabel jLabel38;
    private JLabel jLabel4;
    private JLabel jLabel42;
    private JLabel jLabel5;
    private JMenu jMenu2;
    private JMenu jMenu3;
    private JMenu jMenu4;

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
    private JMenuBar jMenuBar1;
    private JMenuBar jMenuBar2;
    private JPanel jPanel1;
    private JButton button1;
    private JPanel jPanel10;
    private JPanel jPanel11;
    private JPanel jPanel12;
    private JPanel jPanel13;
    private JPanel jPanel18;
    private JPanel jPanel2;
    private JPanel jPanel20;
    private JPanel jPanel22;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JScrollPane jScrollPane5;
    private JScrollPane jScrollPane6;
    private JSeparator jSeparator1;
    private JLabel lblAnsweredResponceCount;
    private JLabel lblAvgHopCountPerSearch;
    private JLabel lblAvgLatency;
    private JLabel lblForwardRequestCount;
    private JLabel lblReceivedRequestCount;
    private JLabel lblRequestSuccessRatio;
    private JLabel lblSearchRequestCount;
    private JList<String> lstMyFiles;
    private JMenuItem menuItemExit;
    private JMenu mnuFile;
    private JTable tblLog;
    private JTable tblRoutingTable;
    private JTable tblSearchResults;
    private JTable tblStatTable;
    private JTextField txtBS_IP;
    private JTextField txtBS_PORT;
    private JTextField txtMyIP;
    private JTextField txtMyPort;
    private JTextField txtSearchFile;
    private JTextField txtUsername;

    public GUICopy() {
        initComponents();
        initializeRoutingTable();
        InitializeNetworkDetailsTable();
        InitializeSearchResultsTable();
        initializeMyFileList();
        initializeLog();
    }

    public GUICopy(NodeOperationsImpl nodeOpsWS) {
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
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    updateLog();
                    updateRoutingTable();
                    updateSearchTable();
                    UpdateStatTable();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
        String header[] = new String[]{"Query", "Time Triggered", "Time Delivered", "Served IP Address", "Served Port", "Hop Count", "File","Download"};
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

        for (String newLog : nodeOpsWS.getNode().getFileList()) {
            ((DefaultListModel) lstMyFiles.getModel()).addElement(newLog);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jMenuBar2 = new JMenuBar();
        jMenu2 = new JMenu();
        jMenu3 = new JMenu();
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jScrollPane3 = new JScrollPane();
        tblRoutingTable = new JTable();
        jPanel4 = new JPanel();
        jLabel4 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        btnUnregsiter = new JButton();
        jLabel1 = new JLabel();
        txtUsername = new JTextField();
        txtBS_IP = new JTextField();
        txtMyPort = new JTextField();
        btnLeave = new JButton();
        txtMyIP = new JTextField();
        jLabel5 = new JLabel();
        txtBS_PORT = new JTextField();
        btnStop = new JButton();
        jPanel7 = new JPanel();
        jScrollPane1 = new JScrollPane();
        lstMyFiles = new JList<>();
        jPanel9 = new JPanel();
        jScrollPane5 = new JScrollPane();
        tblStatTable = new JTable();
        jSeparator1 = new JSeparator();
        jPanel3 = new JPanel();
        jPanel5 = new JPanel();
        txtSearchFile = new JTextField();
        btnSearch = new JButton();
        jScrollPane4 = new JScrollPane();
        tblSearchResults = new JTable();
        jPanel8 = new JPanel();
        jPanel6 = new JPanel();
        lblReceivedRequestCount = new JLabel();
        jLabel15 = new JLabel();
        jPanel10 = new JPanel();
        lblForwardRequestCount = new JLabel();
        jLabel18 = new JLabel();
        jPanel11 = new JPanel();
        lblAnsweredResponceCount = new JLabel();
        jLabel20 = new JLabel();
        jPanel12 = new JPanel();
        lblSearchRequestCount = new JLabel();
        jLabel22 = new JLabel();
        jPanel18 = new JPanel();
        lblAvgHopCountPerSearch = new JLabel();
        jLabel34 = new JLabel();
        jPanel20 = new JPanel();
        lblRequestSuccessRatio = new JLabel();
        jLabel38 = new JLabel();
        jPanel22 = new JPanel();
        lblAvgLatency = new JLabel();
        jLabel42 = new JLabel();
        jPanel13 = new JPanel();
        jScrollPane6 = new JScrollPane();
        tblLog = new JTable();
        jMenuBar1 = new JMenuBar();
        mnuFile = new JMenu();
        menuItemExit = new JMenuItem();
        jMenu4 = new JMenu();
        advRegister = new JMenuItem();
        advUnregister = new JMenuItem();
        advLeave = new JMenuItem();

        jMenu2.setText("File");
        jMenuBar2.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar2.add(jMenu3);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(850, 650));


        jPanel1.setPreferredSize(new Dimension(425, 350));
        jPanel1.setBackground(Color.DARK_GRAY);

        jPanel2.setBorder(BorderFactory.createTitledBorder(null, "Routing Table", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14))); // NOI18N
        jPanel2.setPreferredSize(new Dimension(400, 182));
        jPanel2.setBackground(Color.GRAY);
        jPanel5.setBackground(Color.GRAY);
        jPanel4.setBackground(Color.GRAY);
        jPanel13.setBackground(Color.GRAY);
        jPanel8.setBackground(Color.GRAY);
        jPanel7.setBackground(Color.GRAY);
        jPanel9.setBackground(Color.GRAY);
        jPanel3.setBackground(Color.DARK_GRAY);




        tblRoutingTable.setModel(new DefaultTableModel(
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

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
        );
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel4.setBorder(BorderFactory.createTitledBorder(null, "Node Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14))); // NOI18N
        jPanel4.setPreferredSize(new Dimension(400, 182));

        jLabel4.setText("Node Name");

        jLabel2.setText("Node IP Address");

        jLabel3.setText("Node Port");

        btnUnregsiter.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnUnregsiter.setText("Unregister");
        btnUnregsiter.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUnregsiter.setOpaque(true);
        btnUnregsiter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnregsiterActionPerformed(evt);
            }
        });

        jLabel1.setText("BS IP");

        btnLeave.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnLeave.setText("Leave");
        btnLeave.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
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

        btnStop.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnStop.setText("Stop");
        btnStop.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnStop.setOpaque(true);
        btnStop.addActionListener(evt -> btnStopActionPerformed(evt));

        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                                                .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addComponent(jLabel1, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtBS_IP, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtBS_PORT, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtMyIP, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtMyPort, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtUsername, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
                                .addGap(81, 81, 81))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(btnUnregsiter, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnLeave, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtBS_IP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtBS_PORT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtMyIP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtMyPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnStop)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnUnregsiter)
                                        .addComponent(btnLeave))
                                .addContainerGap())
        );

        jPanel7.setBorder(BorderFactory.createTitledBorder(null, "Local Files", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14))); // NOI18N

        lstMyFiles.setModel(new AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lstMyFiles);

        GroupLayout jPanel7Layout = new GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        );




        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel5, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel7, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel8, GroupLayout.PREFERRED_SIZE, 380, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))

        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel4, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                                        .addComponent(jPanel2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                        .addComponent(jPanel5, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel8, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                                        .addComponent(jPanel7, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );


        jPanel3.setPreferredSize(new Dimension(450, 400));
        jPanel3.setBackground(Color.DARK_GRAY);

        jPanel5.setBorder(BorderFactory.createTitledBorder(null, "File Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14))); // NOI18N
        jPanel5.setPreferredSize(new Dimension(400, 268));

        txtSearchFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchFileActionPerformed(evt);
            }
        });

        btnSearch.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        tblSearchResults.setModel(new DefaultTableModel(
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

        jPanel9.setBorder(BorderFactory.createTitledBorder(null, "Network Details Table", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14))); // NOI18N
        jPanel9.setPreferredSize(new Dimension(250, 100));
        tblStatTable.setModel(new DefaultTableModel(
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

        GroupLayout jPanel9Layout = new GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
                jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane5, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
                jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane5, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane4, GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(txtSearchFile)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtSearchFile, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jPanel8.setBorder(BorderFactory.createTitledBorder(null, "Performance", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14))); // NOI18N
        jPanel8.setPreferredSize(new Dimension(296, 100));

        lblReceivedRequestCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblReceivedRequestCount.setText("0");
        lblReceivedRequestCount.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        lblReceivedRequestCount.setPreferredSize(new Dimension(50, 16));

        jLabel15.setText("Received Request Count");
        jLabel15.setPreferredSize(new Dimension(119, 16));

        GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel15, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblReceivedRequestCount, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel15, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblReceivedRequestCount, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        jPanel6Layout.linkSize(SwingConstants.VERTICAL, new Component[] {jLabel15, lblReceivedRequestCount});

        lblForwardRequestCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblForwardRequestCount.setText("0");
        lblForwardRequestCount.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        lblForwardRequestCount.setPreferredSize(new Dimension(50, 16));

        jLabel18.setText("Forwarded Request Count");
        jLabel18.setPreferredSize(new Dimension(119, 16));

        GroupLayout jPanel10Layout = new GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
                jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel18, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblForwardRequestCount, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
                jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel18, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblForwardRequestCount, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        lblAnsweredResponceCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblAnsweredResponceCount.setText("0");
        lblAnsweredResponceCount.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        lblAnsweredResponceCount.setPreferredSize(new Dimension(50, 16));

        jLabel20.setText("Answered Responce Count");
        jLabel20.setPreferredSize(new Dimension(119, 16));

        GroupLayout jPanel11Layout = new GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
                jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel20, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblAnsweredResponceCount, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
                jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel20, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblAnsweredResponceCount, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        lblSearchRequestCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblSearchRequestCount.setText("0");
        lblSearchRequestCount.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        lblSearchRequestCount.setPreferredSize(new Dimension(50, 16));

        jLabel22.setText("Searched Request Count");
        jLabel22.setPreferredSize(new Dimension(119, 16));

        GroupLayout jPanel12Layout = new GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
                jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel22, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblSearchRequestCount, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
                jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel22, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblSearchRequestCount, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        lblAvgHopCountPerSearch.setHorizontalAlignment(SwingConstants.CENTER);
        lblAvgHopCountPerSearch.setText("0");
        lblAvgHopCountPerSearch.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        lblAvgHopCountPerSearch.setPreferredSize(new Dimension(50, 16));

        jLabel34.setText("Average Hop Count Per Search");
        jLabel34.setPreferredSize(new Dimension(119, 16));

        GroupLayout jPanel18Layout = new GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
                jPanel18Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel34, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblAvgHopCountPerSearch, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
                jPanel18Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGroup(jPanel18Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel34, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblAvgHopCountPerSearch, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        lblRequestSuccessRatio.setHorizontalAlignment(SwingConstants.CENTER);
        lblRequestSuccessRatio.setText("0");
        lblRequestSuccessRatio.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        lblRequestSuccessRatio.setPreferredSize(new Dimension(50, 16));

        jLabel38.setText("Request Success Ratio");
        jLabel38.setPreferredSize(new Dimension(119, 16));

        GroupLayout jPanel20Layout = new GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
                jPanel20Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel20Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel38, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblRequestSuccessRatio, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
                jPanel20Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel20Layout.createSequentialGroup()
                                .addGroup(jPanel20Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel38, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblRequestSuccessRatio, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        lblAvgLatency.setHorizontalAlignment(SwingConstants.CENTER);
        lblAvgLatency.setText("0");
        lblAvgLatency.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        lblAvgLatency.setPreferredSize(new Dimension(50, 16));

        jLabel42.setText("Average Latency Of Query Execution ");
        jLabel42.setPreferredSize(new Dimension(119, 16));

        GroupLayout jPanel22Layout = new GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
                jPanel22Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel22Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel42, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblAvgLatency, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
                jPanel22Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel22Layout.createSequentialGroup()
                                .addGroup(jPanel22Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel42, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblAvgLatency, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        GroupLayout jPanel8Layout = new GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
                jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel10, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel11, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel12, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel18, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel20, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel22, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
                jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel6, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel10, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel11, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel12, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel18, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel20, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel22, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(53, Short.MAX_VALUE))
        );

        jPanel13.setBorder(BorderFactory.createTitledBorder(null, "Logs", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14))); // NOI18N
        jPanel13.setPreferredSize(new Dimension(250, 100));

        tblLog.setModel(new DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Title 1"
                }
        ));
        tblLog.setShowHorizontalLines(false);
        jScrollPane6.setViewportView(tblLog);

        GroupLayout jPanel13Layout = new GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
                jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane6, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
                jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jScrollPane6, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel13, GroupLayout.PREFERRED_SIZE, 725, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel9, GroupLayout.DEFAULT_SIZE, 1500, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jPanel13, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                                        .addComponent(jPanel9, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
                                .addContainerGap())
        );



        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 1218, Short.MAX_VALUE)
                        .addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, 1218, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                                .addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
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
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
        });
    }


    public void updateRoutingTable() {
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
        LinkedHashMap<String, ArrayList<String>> searchResults = nodeOpsWS.getNode().getDetailsTable();
        List<NetworkDetails> networkDetails = nodeOpsWS.getNode().getNetworkDetailsTable();
        ((DefaultTableModel) tblSearchResults.getModel()).setRowCount(0);
        for (String key : searchResults.keySet()) {
            ((DefaultTableModel) tblSearchResults.getModel()).addRow(new Object[]{key, String.join(",", searchResults.get(key))});
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
            JButton jButton = new JButton("Download");
            jButton.setName("Download");
            jButton.setBackground(Color.DARK_GRAY);
            jButton.addActionListener(v ->{
                System.out.println("downloading");
            });

            tableModel.addRow(new Object[]{row.getQuery(), new SimpleDateFormat("HH:mm:ss").format(row.getTrigTime()), new SimpleDateFormat("HH:mm:ss").format(row.getDeliveryTime()), row.getServedNode().getNodeIp(), row.getServedNode().getNodePort(), row.getHopsNeeded(), filelist, jButton});
        }
    }


}
