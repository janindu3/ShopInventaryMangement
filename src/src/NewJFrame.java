/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import jo.Connection;
import jo.Database;
import java.awt.event.ActionListener; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author savantis
 */
public class NewJFrame extends javax.swing.JFrame{

    DefaultTableModel dftm;
    JTable kk;
    int id;

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {

        this.setAlwaysOnTop(true);
        this.setResizable(true);
        this.setVisible(true);

        initComponents();
        
         errorMsg.setForeground(Color.RED);
          errorMsg.setVisible(false);
        
        item.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                String txt = item.getText();

              
                validateInput();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                validateInput();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                validateInput();
            }
        });
        
          dprice.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
               
                dPriceValidateInput();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                dPriceValidateInput();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                dPriceValidateInput();
            }
        });
          
         sprice.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {

                sPriceValidateInput();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                sPriceValidateInput();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                sPriceValidateInput();
            }
        });
         
    

        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = (int) tk.getScreenSize().getWidth();
        int ySize = (int) tk.getScreenSize().getHeight();
        this.setSize(xSize, ySize);
        searchAndFillTable();

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                int i = jTable1.getSelectedRow();

                if (i >= 0) {

                    dftm.setValueAt(item.getText(), i, 0);
                    dftm.setValueAt(dprice.getText(), i, 1);
                    dftm.setValueAt(sprice.getText(), i, 2);

                    try {
                        Database db = Connection.openConnection(myDb);
                        ArrayList<Items> itmDetailss = (ArrayList<Items>) db.getList("Items");

                        if (itmDetailss != null) {

                            id = itmDetailss.get(i).getId();

                        } else {

                            id = 1;
                        }

                        Items itms = new Items(id, item.getText(), Double.parseDouble(dprice.getText()), Double.parseDouble(sprice.getText()));

                        ArrayList<Items> itmDetails = (ArrayList<Items>) db.getList("Items");

                        if (itmDetails == null) {
                            itmDetails = new ArrayList<>();
                            db.addList("Items", itmDetails);
                        }
                        itmDetails.set(i, itms);

                        Connection.save();

                    } catch (IOException ex) {
                        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {

                }

            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                int i = jTable1.getSelectedRow();

                if (i >= 0) {

//                    dftm.setValueAt(item.getText(), i, 0);
//                    dftm.setValueAt(dprice.getText(), i, 1);
//                    dftm.setValueAt(sprice.getText(), i, 2);
                    try {
                        Database db = Connection.openConnection(myDb);
                        ArrayList<Items> itmDetailss = (ArrayList<Items>) db.getList("Items");

                        if (itmDetailss != null) {

                            id = itmDetailss.get(i).getId();

                        } else {

                            id = 1;
                        }

                        Items itms = new Items(id, item.getText(), Double.parseDouble(dprice.getText()), Double.parseDouble(sprice.getText()));

                        ArrayList<Items> itmDetails = (ArrayList<Items>) db.getList("Items");

                        if (itmDetails == null) {
                            itmDetails = new ArrayList<>();
                            db.addList("Items", itmDetails);
                        }
                        itmDetails.remove(i);

                        Connection.save();

                    } catch (IOException ex) {
                        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {

                }

            }
        });

    }
    
    private  void validateInput() {
        String text = item.getText();
        String pattern="^([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        
        
        if (text.length() == 0) {
            errorMsg.setText("Item name can not be empty");
            errorMsg.setVisible(true);
            saveButton.setEnabled(false);
        } else {
            errorMsg.setVisible(false);
              saveButton.setEnabled(true);
        }
 
    }
    
        private  void dPriceValidateInput() {
        String dpricetext = dprice.getText();
        String pattern="[0-9]+([,.][0-9]{1,2})?";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(dpricetext);
       
         if (dpricetext.length() == 0) {
             dPriceError.setText("This field can not be empty");
             dPriceError.setForeground(Color.RED);
             dPriceError.setVisible(true);
             saveButton.setEnabled(false);
         
         }else if (m.matches()) {
                
                dPriceError.setVisible(false);
                       saveButton.setEnabled(true);
            } else {
                
                dPriceError.setText("Not a Valied format");
                dPriceError.setForeground(Color.RED);
                dPriceError.setVisible(true);
                   saveButton.setEnabled(false);
                
            }
    }

    private void sPriceValidateInput() {
        String spricetext = sprice.getText();
        String pattern = "[0-9]+([,.][0-9]{1,2})?";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(spricetext);

        if (spricetext.length() == 0) {
             sPriceError.setText("This field can not be empty");
             sPriceError.setForeground(Color.RED);
             sPriceError.setVisible(true);
             saveButton.setEnabled(false);
         
         }else if (m.matches()) {

            sPriceError.setVisible(false);
            saveButton.setEnabled(true);
        } else {

            sPriceError.setText("Not a Valied format");
            sPriceError.setForeground(Color.RED);
            sPriceError.setVisible(true);
            saveButton.setEnabled(false);

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        item = new javax.swing.JTextField();
        dprice = new javax.swing.JTextField();
        sprice = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        search_item = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        updateButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        errorMsg = new javax.swing.JLabel();
        dPriceError = new javax.swing.JLabel();
        sPriceError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Good Item");

        jLabel2.setText("Distribute Price");

        jLabel3.setText("Sale Price");

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Name");

        jButton3.setText("Search");

        search_item.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_itemKeyReleased(evt);
            }
        });

        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel5.setText("SELL TO THE SHOP");

        updateButton.setText("Update");
        updateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateButtonMouseClicked(evt);
            }
        });

        deleteButton.setText("Delete");

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Distributed Price", "Sale Price", "Id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        errorMsg.setText("jLabel6");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(281, 281, 281)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(81, 81, 81))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(updateButton)
                                .addGap(26, 26, 26)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sPriceError, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(deleteButton)
                                    .addGap(33, 33, 33)
                                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(item, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(dprice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                                .addComponent(sprice, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(errorMsg)
                                .addComponent(dPriceError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(62, 62, 62)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(99, 99, 99)
                                .addComponent(search_item, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(101, 101, 101)
                                .addComponent(jButton3))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(148, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(search_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(errorMsg)
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(dprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(dPriceError, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addComponent(sPriceError, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(saveButton)
                                .addComponent(deleteButton))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton2)
                                .addComponent(updateButton))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 181, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        new Main().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void search_itemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_itemKeyReleased
        // TODO add your handling code here:
        searchAndFillTable();
    }//GEN-LAST:event_search_itemKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        clearM();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:

        try {

            Database db = Connection.openConnection(myDb);

            ArrayList<Items> itmDetailss = (ArrayList<Items>) db.getList("Items");

            if (itmDetailss != null) {

                id = itmDetailss.get(itmDetailss.size() - 1).getId() + 1;

            } else {

                id = 1;
            }

            Items itms = new Items(id, item.getText(), Double.parseDouble(dprice.getText()), Double.parseDouble(sprice.getText()));

            ArrayList<Items> itmDetails = (ArrayList<Items>) db.getList("Items");

            if (itmDetails == null) {
                itmDetails = new ArrayList<>();
                db.addList("Items", itmDetails);
            }
            itmDetails.add(itms);

            Connection.save();

        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        searchAndFillTable();
        clearM();

    }//GEN-LAST:event_saveButtonActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int select = jTable1.getSelectedRow();

        item.setText(dftm.getValueAt(select, 0).toString());

        dprice.setText(dftm.getValueAt(select, 1).toString());

        sprice.setText(dftm.getValueAt(select, 2).toString());

        saveButton.setEnabled(false);
        updateButton.setEnabled(true);
        deleteButton.setEnabled(true);

    }//GEN-LAST:event_jTable1MouseClicked

    private void updateButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateButtonMouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_updateButtonMouseClicked

    private void searchAndFillTable() {

//               Items itms=new Items(item.getText(),Double.parseDouble(dprice.getText()),Double.parseDouble(sprice.getText()));
//        
        try {

            dftm = (DefaultTableModel) jTable1.getModel();
            dftm.setRowCount(0);
            kk = new JTable();
            kk = jTable1;
            jTable1.setModel(dftm);

            int column = 0;
            int row = jTable1.getSelectedRow();
//            String value = jTable1.getModel().getValueAt(1, column).toString();

            Database db = Connection.openConnection(myDb);

            ArrayList<Items> itmDetails = (ArrayList<Items>) db.getList("Items");
            String searchName = search_item.getText();
            if (itmDetails != null) {
                for (Items item : itmDetails) {
                    if (item.getItem().toLowerCase().startsWith(searchName.toLowerCase())) {
                        dftm.addRow(new Object[]{item.getItem(), item.getDistributerPrice(), item.getSalePrice(), item.getId()});
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static File myDb = new File("myDb");

    private void clearM() {
        item.setText("");
        dprice.setText("");
        sprice.setText("");

        saveButton.setEnabled(true);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

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
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dPriceError;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField dprice;
    private javax.swing.JLabel errorMsg;
    private javax.swing.JTextField item;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel sPriceError;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField search_item;
    private javax.swing.JTextField sprice;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables

   
}
