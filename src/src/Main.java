/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import jo.Connection;
import jo.Database;
import rojerusan.RSPanelsSlider;
import static src.NewJFrame.myDb;
import static src.SellToShop.date;

/**
 *
 * @author savantis
 */
public class Main extends javax.swing.JFrame {

    DefaultTableModel dftm;
    DefaultTableModel dftm1;
    DefaultTableModel dftm2;

    int id;
    int id1;

    double todayRevenue, totalRevenue, todayCost, todayProfitFromCheck,todayProfitFromCash, totalProfitFromCheck,totalProfitFromCash = 0.0;
    double unitSalePrice, unitPerchasePrice;

    boolean isProductExist=true;
    double availableAmount;
    /**
     * Creates new form NewJFrame1
     */
    public Main() {

        this.setResizable(false);
        this.setVisible(true);
        initComponents();
        goodItemErrorMsg.setForeground(Color.RED);
        goodItemErrorMsg.setVisible(true);

        distributePriceErrorMsg.setForeground(Color.RED);
        distributePriceErrorMsg.setVisible(true);

        salePriceErrorMsg.setForeground(Color.RED);
        salePriceErrorMsg.setVisible(true);

        ownerNameErrorMsg.setForeground(Color.RED);
        ownerNameErrorMsg.setVisible(true);

        ownerIdErrorMsg.setForeground(Color.RED);
        ownerIdErrorMsg.setVisible(true);

        paymentAmountErrorMsg.setForeground(Color.RED);
        paymentAmountErrorMsg.setVisible(true);

        goodAmountErrorMsg.setForeground(Color.RED);
        goodAmountErrorMsg.setVisible(true);
        
        gAmountErrorMsg.setForeground(Color.RED);
        gAmountErrorMsg.setVisible(true);

        bill_date.setDateFormatString("MM-dd-yyyy");
        reportDateChooser.setDateFormatString("MM-dd-yyyy");
        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePackage/1482186325_Cart.png")));

        try {

            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            date = new Date();

            bill_date.setDate(date);
            reportDateChooser.setDate(date);

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        updateButton1.setEnabled(false);
        deleteButton1.setEnabled(false);

        this.setLocationRelativeTo(this);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                int i = jTable1.getSelectedRow();

                if (i >= 0) {

                    dftm.setValueAt(item.getText(), i, 0);
                    dftm.setValueAt(dprice.getText(), i, 1);
                    dftm.setValueAt(sprice.getText(), i, 2);
                    dftm.setValueAt(gAmount.getText()+" "+good_unit1.getSelectedItem(), i, 4);

                    try {
                        Database db = Connection.openConnection(myDb);
                        ArrayList<Items> itmDetailss = (ArrayList<Items>) db.getList("Items");

                        if (itmDetailss != null) {

                            id = itmDetailss.get(i).getId();

                        } else {

                            id = 1;
                        }

                        Items itms = new Items(id, item.getText(), Double.parseDouble(dprice.getText()), Double.parseDouble(sprice.getText())
                                ,Double.parseDouble(gAmount.getText()),(String) good_unit1.getSelectedItem());

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

        updateButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                int i = sell_shop_table.getSelectedRow();

                if (i >= 0) {

                    dftm1.setValueAt(ownerName.getText(), i, 0);
                    dftm1.setValueAt(ownerId.getText(), i, 1);
                    dftm1.setValueAt(paymentMethod.getSelectedItem(), i, 2);
                    dftm1.setValueAt(paymentAmount.getText(), i, 3);
                    dftm1.setValueAt(bill_date.getDate(), i, 4);
                    dftm1.setValueAt(goodItem.getSelectedItem(), i, 5);
                    dftm1.setValueAt(goodAmount.getText(), i, 6);
                    dftm1.setValueAt(good_unit.getSelectedItem(), i, 7);

                    try {
                        Database db = Connection.openConnection(shopDb);
                        ArrayList<SellShopModel> itmDetailss = (ArrayList<SellShopModel>) db.getList("ShopItems");

                        if (itmDetailss != null) {

                            id1 = itmDetailss.get(i).getUid();

                        } else {

                            id1 = 1;
                        }

                        String d1 = ((JTextField) bill_date.getDateEditor().getUiComponent()).getText();

                        SellShopModel sellShopModel = new SellShopModel(id1, ownerName.getText(),
                                ownerId.getText(), (String) paymentMethod.getSelectedItem(),
                                Long.parseLong(paymentAmount.getText()),
                                d1,
                                (String) goodItem.getSelectedItem(),
                                Long.parseLong(goodAmount.getText()), (String) good_unit.getSelectedItem());

                        ArrayList<SellShopModel> itmDetails = (ArrayList<SellShopModel>) db.getList("ShopItems");

                        if (itmDetails == null) {
                            itmDetails = new ArrayList<>();
                            db.addList("Items", itmDetails);
                        }
                        itmDetails.set(i, sellShopModel);

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

                    try {
                        Database db = Connection.openConnection(myDb);
                        ArrayList<Items> itmDetailss = (ArrayList<Items>) db.getList("Items");

                        if (itmDetailss != null) {

                            id = itmDetailss.get(i).getId();

                        } else {

                            id = 1;
                        }

                        Items itms = new Items(id, item.getText(), Double.parseDouble(dprice.getText()), Double.parseDouble(sprice.getText()),Double.parseDouble(gAmount.getText()),(String) good_unit1.getSelectedItem());

                        ArrayList<Items> itmDetails = (ArrayList<Items>) db.getList("Items");

                        if (itmDetails == null) {
                            itmDetails = new ArrayList<>();
                            db.addList("Items", itmDetails);
                        }
                        itmDetails.remove(i);

                        Connection.save();
                        searchAndFillGoodTable();

                    } catch (IOException ex) {
                        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {

                }

            }
        });

        deleteButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                int i = sell_shop_table.getSelectedRow();

                if (i >= 0) {

                    try {

                        Database db = Connection.openConnection(shopDb);
                        ArrayList<SellShopModel> itmDetailss = (ArrayList<SellShopModel>) db.getList("ShopItems");

                        if (itmDetailss != null) {

                            id1 = itmDetailss.get(i).getUid();

                        } else {

                            id1 = 1;
                        }

                        String d1 = ((JTextField) bill_date.getDateEditor().getUiComponent()).getText();

                        SellShopModel sellShopModel = new SellShopModel(id1, ownerName.getText(),
                                ownerId.getText(), (String) paymentMethod.getSelectedItem(),
                                Long.parseLong(paymentAmount.getText()),
                                d1,
                                (String) goodItem.getSelectedItem(),
                                Long.parseLong(goodAmount.getText()), (String) good_unit.getSelectedItem());

                        ArrayList<SellShopModel> itmDetails = (ArrayList<SellShopModel>) db.getList("ShopItems");

                        if (itmDetails == null) {
                            itmDetails = new ArrayList<>();
                            db.addList("Items", itmDetails);
                        }
                        itmDetails.remove(i);

                        Connection.save();
                        searchAndFillSellToShopTable();

                    } catch (IOException ex) {
                        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {

                }

            }
        });

//        setLayout(new GridBagLayout());
//        
//        GridBagConstraints gbc = new GridBagConstraints();
//        
////        gbc.anchor = GridBagConstraints.CENTER;
//        gbc.weightx = 0.5;
//        gbc.weighty = 0.5;
//
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//
//        add(detailButton, gbc);
//
////        gbc.anchor = GridBagConstraints.CENTER;
//        gbc.weightx = 0.5;
//        gbc.weighty = 0.5;
//
//        gbc.gridx = 0;
//        gbc.gridy = 8;
//
//        add(seelToShopButton, gbc);
//        
////        jPanel1.add(jButton1, gbc);
//        
//        Toolkit tk=Toolkit.getDefaultToolkit();
//        int xSize=(int)tk.getScreenSize().getWidth();
//        int ySize=(int)tk.getScreenSize().getHeight();
//        this.setSize(xSize,ySize);
  
        Database db;
        try {
            db = Connection.openConnection(NewJFrame.myDb);

            ArrayList<Items> itmDetails = (ArrayList<Items>) db.getList("Items");
           

            if (itmDetails!=null) {

                for (Items item : itmDetails) {

                    goodItem.addItem(item.getItem());

                }

            }else{
            
                System.out.println("no data");
            
            }
         
        } catch (IOException ex) {
            Logger.getLogger(SellToShop.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SellToShop.class.getName()).log(Level.SEVERE, null, ex);
        }

        searchAndFillGoodTable();
        searchAndFillSellToShopTable();

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
 
        ownerName.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {

                ownerNamevalidateInput();

            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                ownerNamevalidateInput();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                ownerNamevalidateInput();
            }
        });
          
          
        ownerId.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {

                ownerIdValidateInput();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                ownerIdValidateInput();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                ownerIdValidateInput();
            }
        });

             
        paymentAmount.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {

                paymentAmountValidateInput();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                paymentAmountValidateInput();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                paymentAmountValidateInput();
            }
        });
        
        
          goodAmount.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {

                goodAmountValidateInput();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                goodAmountValidateInput();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                goodAmountValidateInput();
            }
        });
          
               gAmount.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {

                gAmountValidateInput();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                gAmountValidateInput();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                gAmountValidateInput();
            }
        });
    }

    private void validateInput() {

        String text = item.getText();

        if (text.length() != 0) {
            goodItemErrorMsg.setVisible(false);
            saveButton.setEnabled(true);
        }

    }
    
     private void ownerNamevalidateInput() {

        String text = ownerName.getText();

        if (text.length() != 0) {
            ownerNameErrorMsg.setVisible(false);
            saveButton1.setEnabled(true);
        }

    }

    private void dPriceValidateInput() {
        String dpricetext = dprice.getText();
        String pattern = "[0-9]+([,.][0-9]{1,2})?";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(dpricetext);

        if (m.matches()) {

            distributePriceErrorMsg.setVisible(false);
            saveButton.setEnabled(true);
        } else if (dpricetext.equals("")) {

        } else {

            distributePriceErrorMsg.setText("Not a Valied format");

            distributePriceErrorMsg.setVisible(true);
            saveButton.setEnabled(false);

        }
    }
    
       private void ownerIdValidateInput() {
           
        String ownerIdtext = ownerId.getText();
        String pattern = "^\\d{9}[V|v|x|X]$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(ownerIdtext);

        if (m.matches()) {

            ownerIdErrorMsg.setVisible(false);
            saveButton1.setEnabled(true);
            
        } else if (ownerIdtext.equals("")) {

        } else {

            ownerIdErrorMsg.setText("Not a Valied format");

            ownerIdErrorMsg.setVisible(true);
            saveButton1.setEnabled(false);

        }
    }
       
          private void paymentAmountValidateInput() {
           
        String paymentAmountext = paymentAmount.getText();
        String pattern = "(?<=\\s|^)\\d+(?=\\s|$)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(paymentAmountext);

        if (m.matches()) {

            paymentAmountErrorMsg.setVisible(false);
            saveButton1.setEnabled(true);
        } else if (paymentAmountext.equals("")) {

        } else {

            paymentAmountErrorMsg.setText("Not a Valied format");

            paymentAmountErrorMsg.setVisible(true);
            saveButton1.setEnabled(false);

        }
    }
          
        private void goodAmountValidateInput() {
           
        String goodAmounttext = goodAmount.getText();
        String pattern = "[0-9]+([,.][0-9]{1,2})?";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(goodAmounttext);

//        try {
//
//            Database dbitem = Connection.openConnection(myDb);
//            ArrayList<Items> itmDetails = (ArrayList<Items>) dbitem.getList("Items");
//            String selectedItem = goodItem.getSelectedItem().toString();
//
//            if (itmDetails != null && selectedItem != null) {
//
//                for (Items item : itmDetails) {
//
//                    if (item.getItem().toLowerCase().equals(selectedItem.toLowerCase())) {
//
//                        availableAmount = item.getgAmount();
//
//                    }
//                }
//            }
//
//        } catch (IOException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }

        if (m.matches()) {

            goodAmountErrorMsg.setVisible(false);
            saveButton1.setEnabled(true);
        } else if (goodAmounttext.equals("")) {

        } else {

            goodAmountErrorMsg.setText("Not a Valied format");

            goodAmountErrorMsg.setVisible(true);
            saveButton1.setEnabled(false);

        }
        
//        if (!goodAmount.getText().equals("")) {
//            if (availableAmount < Double.parseDouble(goodAmount.getText())) {
//                goodAmountErrorMsg.setText("stock does not have enoush amount");
//
//                goodAmountErrorMsg.setVisible(true);
//                saveButton1.setEnabled(false);
//            }
//        }
    }
 
    private void gAmountValidateInput() {

        String gAmounttext = gAmount.getText();
        String pattern = "[0-9]+([,.][0-9]{1,2})?";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(gAmounttext);

        if (m.matches()) {

            gAmountErrorMsg.setVisible(false);
            saveButton1.setEnabled(true);
        } else if (gAmounttext.equals("")) {

        } else {

            gAmountErrorMsg.setText("Not a Valied format");

            gAmountErrorMsg.setVisible(true);
            saveButton1.setEnabled(false);

        }
    }

    private void sPriceValidateInput() {
        String spricetext = sprice.getText();
        String pattern = "[0-9]+([,.][0-9]{1,2})?";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(spricetext);

        if (m.matches()) {

            salePriceErrorMsg.setVisible(false);
            saveButton.setEnabled(true);
        } else if (spricetext.equals("")) {

        } else {

            salePriceErrorMsg.setText("Not a Valied format");
            salePriceErrorMsg.setForeground(Color.RED);
            salePriceErrorMsg.setVisible(true);
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
        jPanel2 = new javax.swing.JPanel();
        btn3 = new rojerusan.RSButtonIconD();
        rSPanelImage1 = new rojerusan.RSPanelImage();
        btn1 = new rojerusan.RSButtonIconD();
        btn2 = new rojerusan.RSButtonIconD();
        btn4 = new rojerusan.RSButtonIconD();
        rSPanelsSlider1 = new rojerusan.RSPanelsSlider();
        pnl1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        item = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        dprice = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        sprice = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        search_item = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        saveButton = new javax.swing.JButton();
        goodItemErrorMsg = new java.awt.Label();
        distributePriceErrorMsg = new java.awt.Label();
        salePriceErrorMsg = new java.awt.Label();
        gAmount = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        gAmountErrorMsg = new java.awt.Label();
        jTextField2 = new javax.swing.JTextField();
        good_unit1 = new javax.swing.JComboBox<>();
        pnl2 = new javax.swing.JPanel();
        goodName = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        ownerName = new javax.swing.JTextField();
        ownerId = new javax.swing.JTextField();
        paymentMethod = new javax.swing.JComboBox<>();
        paymentAmount = new javax.swing.JTextField();
        bill_date = new com.toedter.calendar.JDateChooser();
        goodAmount = new javax.swing.JTextField();
        goodItem = new javax.swing.JComboBox<>();
        saveButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        sell_shop_table = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        good_unit = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        paymentAmountValidation = new javax.swing.JLabel();
        updateButton1 = new javax.swing.JButton();
        deleteButton1 = new javax.swing.JButton();
        searchText1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        ownerNameErrorMsg = new java.awt.Label();
        ownerIdErrorMsg = new java.awt.Label();
        paymentAmountErrorMsg = new java.awt.Label();
        goodAmountErrorMsg = new java.awt.Label();
        pnl3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnl4 = new javax.swing.JPanel();
        tprofitFromCheck = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        repTable = new javax.swing.JTable();
        btnTs = new javax.swing.JButton();
        reportDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        tprofitFromCash = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(60, 60, 170));

        btn3.setText("Button");
        btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rSPanelImage1Layout = new javax.swing.GroupLayout(rSPanelImage1);
        rSPanelImage1.setLayout(rSPanelImage1Layout);
        rSPanelImage1Layout.setHorizontalGroup(
            rSPanelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        rSPanelImage1Layout.setVerticalGroup(
            rSPanelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        btn1.setText("Good Details");
        btn1.setSelected(true);
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });

        btn2.setText("Sell To Shop");
        btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2ActionPerformed(evt);
            }
        });

        btn4.setText("Report");
        btn4.setName("btn4"); // NOI18N
        btn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(rSPanelImage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(rSPanelImage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        rSPanelsSlider1.setBackground(new java.awt.Color(244, 246, 247));

        pnl1.setName("pnl1"); // NOI18N

        jLabel4.setText("Good Item");

        jLabel6.setText("Distribute Price");

        jLabel7.setText("Sale Price");

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        updateButton.setText("Update");
        updateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateButtonMouseClicked(evt);
            }
        });
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        jLabel8.setText("SEARCH BY GOOD NAME");

        search_item.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_itemKeyReleased(evt);
            }
        });

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Distributed Price", "Sale Price", "Id", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true
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

        saveButton.setText("Save");
        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePackage/1482186415_restart-1.png")));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        jLabel15.setText("Amount");

        jTextField2.setText(":");

        good_unit1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kg", "g", "L", "ml" }));

        javax.swing.GroupLayout pnl1Layout = new javax.swing.GroupLayout(pnl1);
        pnl1.setLayout(pnl1Layout);
        pnl1Layout.setHorizontalGroup(
            pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(updateButton)
                        .addGap(18, 18, 18)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(pnl1Layout.createSequentialGroup()
                        .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnl1Layout.createSequentialGroup()
                                .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(goodItemErrorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(item, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dprice, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(distributePriceErrorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(salePriceErrorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(gAmountErrorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sprice, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44))
                            .addGroup(pnl1Layout.createSequentialGroup()
                                .addComponent(gAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(good_unit1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))))
                .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(75, 75, 75)
                        .addComponent(search_item, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(199, Short.MAX_VALUE))
        );
        pnl1Layout.setVerticalGroup(
            pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl1Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl1Layout.createSequentialGroup()
                        .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(goodItemErrorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(distributePriceErrorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(sprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(salePriceErrorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(gAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(good_unit1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addComponent(gAmountErrorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(updateButton)
                            .addComponent(jButton2)
                            .addComponent(deleteButton)
                            .addComponent(saveButton))
                        .addGap(130, 130, 130))
                    .addGroup(pnl1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(77, Short.MAX_VALUE))))
        );

        rSPanelsSlider1.add(pnl1, "card2");

        pnl2.setName("pnl2"); // NOI18N

        jLabel1.setText("Owner Name :");

        jLabel2.setText("Owner Id:");

        jLabel9.setText("Payment Method");

        jLabel10.setText("Payment Amount");

        jLabel11.setText("Date");

        jLabel12.setText("Select The Good");

        jLabel13.setText("Amount Of  The Good");

        paymentMethod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Check" }));
        paymentMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentMethodActionPerformed(evt);
            }
        });

        goodItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goodItemActionPerformed(evt);
            }
        });

        saveButton1.setText("Save");
        saveButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButton1ActionPerformed(evt);
            }
        });

        sell_shop_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Owner Name", "Owner Id", "Payment Method", "Payment Amount", "Date", "Selected Good", "Good Amount", "uuid"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        sell_shop_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sell_shop_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(sell_shop_table);

        jButton4.setText("Clear");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        good_unit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kg", "g", "L", "ml" }));

        jTextField1.setText(":");

        updateButton1.setText("Update");

        deleteButton1.setText("Delete");
        deleteButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButton1ActionPerformed(evt);
            }
        });

        searchText1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchText1KeyReleased(evt);
            }
        });

        jLabel14.setText("SEARCH BY OWNER NAME");

        javax.swing.GroupLayout goodNameLayout = new javax.swing.GroupLayout(goodName);
        goodName.setLayout(goodNameLayout);
        goodNameLayout.setHorizontalGroup(
            goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(goodNameLayout.createSequentialGroup()
                .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, goodNameLayout.createSequentialGroup()
                        .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(goodNameLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, goodNameLayout.createSequentialGroup()
                                .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel1))
                                .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(goodNameLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(paymentAmountErrorMsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(goodNameLayout.createSequentialGroup()
                                                .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(goodNameLayout.createSequentialGroup()
                                                        .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                            .addComponent(goodAmount, javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(goodItem, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(bill_date, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(paymentAmount, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(good_unit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(goodAmountErrorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addGroup(goodNameLayout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ownerId, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(goodNameLayout.createSequentialGroup()
                                                .addComponent(ownerName, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(ownerNameErrorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(ownerIdErrorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(paymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 12, Short.MAX_VALUE)))))
                        .addGap(113, 113, 113))
                    .addGroup(goodNameLayout.createSequentialGroup()
                        .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(goodNameLayout.createSequentialGroup()
                                .addGap(206, 206, 206)
                                .addComponent(paymentAmountValidation))
                            .addGroup(goodNameLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(deleteButton1)
                                .addGap(126, 126, 126)
                                .addComponent(updateButton1)
                                .addGap(43, 43, 43)
                                .addComponent(saveButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(goodNameLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(133, 133, 133)
                        .addComponent(searchText1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6))
        );
        goodNameLayout.setVerticalGroup(
            goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(goodNameLayout.createSequentialGroup()
                .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(goodNameLayout.createSequentialGroup()
                        .addContainerGap(71, Short.MAX_VALUE)
                        .addComponent(ownerNameErrorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))
                    .addGroup(goodNameLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(ownerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)))
                .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ownerId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ownerIdErrorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(paymentAmountValidation)
                .addGap(3, 3, 3)
                .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(paymentAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(paymentAmountErrorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bill_date, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(goodItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(51, 51, 51)
                .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(goodAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(good_unit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(goodAmountErrorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(updateButton1)
                    .addComponent(deleteButton1)
                    .addComponent(saveButton1)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, goodNameLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(goodNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(searchText1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl2Layout = new javax.swing.GroupLayout(pnl2);
        pnl2.setLayout(pnl2Layout);
        pnl2Layout.setHorizontalGroup(
            pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl2Layout.createSequentialGroup()
                .addComponent(goodName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnl2Layout.setVerticalGroup(
            pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl2Layout.createSequentialGroup()
                .addComponent(goodName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        rSPanelsSlider1.add(pnl2, "card3");

        pnl3.setName("pnl3"); // NOI18N

        jLabel3.setText("PANEL 3");

        javax.swing.GroupLayout pnl3Layout = new javax.swing.GroupLayout(pnl3);
        pnl3.setLayout(pnl3Layout);
        pnl3Layout.setHorizontalGroup(
            pnl3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
        );
        pnl3Layout.setVerticalGroup(
            pnl3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl3Layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(299, Short.MAX_VALUE))
        );

        rSPanelsSlider1.add(pnl3, "card4");

        pnl4.setName("pnl4"); // NOI18N

        tprofitFromCheck.setBackground(new java.awt.Color(225, 36, 154));
        tprofitFromCheck.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        tprofitFromCheck.setForeground(new java.awt.Color(191, 28, 28));
        tprofitFromCheck.setText("0");

        repTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Product ID", "Quantity", "Date", "Profit"
            }
        ));
        jScrollPane3.setViewportView(repTable);

        btnTs.setFont(new java.awt.Font("Segoe UI", 3, 11)); // NOI18N
        btnTs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePackage/todays sale.png"))); // NOI18N
        btnTs.setText("Today's Sale");
        btnTs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTsActionPerformed(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(225, 36, 154));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(191, 28, 28));
        jLabel16.setText("Data Record");

        jLabel17.setBackground(new java.awt.Color(225, 36, 154));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(191, 28, 28));
        jLabel17.setText("=");

        jLabel18.setBackground(new java.awt.Color(225, 36, 154));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(191, 28, 28));
        jLabel18.setText("TOTAL PROFIT FROM CASH");

        jLabel19.setBackground(new java.awt.Color(225, 36, 154));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(191, 28, 28));
        jLabel19.setText("TOTAL PROFIT FROM CHECK");

        jLabel20.setBackground(new java.awt.Color(225, 36, 154));
        jLabel20.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(191, 28, 28));
        jLabel20.setText("=");

        tprofitFromCash.setBackground(new java.awt.Color(225, 36, 154));
        tprofitFromCash.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        tprofitFromCash.setForeground(new java.awt.Color(191, 28, 28));
        tprofitFromCash.setText("0");

        javax.swing.GroupLayout pnl4Layout = new javax.swing.GroupLayout(pnl4);
        pnl4.setLayout(pnl4Layout);
        pnl4Layout.setHorizontalGroup(
            pnl4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl4Layout.createSequentialGroup()
                .addGap(247, 247, 247)
                .addGroup(pnl4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 753, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl4Layout.createSequentialGroup()
                        .addGroup(pnl4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl4Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(34, 34, 34)
                                .addGroup(pnl4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel20)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl4Layout.createSequentialGroup()
                                .addGap(179, 179, 179)
                                .addComponent(reportDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(180, 180, 180)
                                .addComponent(btnTs)))
                        .addGap(53, 53, 53)
                        .addGroup(pnl4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tprofitFromCash)
                            .addComponent(tprofitFromCheck))))
                .addContainerGap(223, Short.MAX_VALUE))
            .addGroup(pnl4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl4Layout.createSequentialGroup()
                    .addGap(377, 377, 377)
                    .addComponent(jLabel16)
                    .addContainerGap(596, Short.MAX_VALUE)))
        );
        pnl4Layout.setVerticalGroup(
            pnl4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl4Layout.createSequentialGroup()
                .addContainerGap(131, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(pnl4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel17)
                    .addComponent(tprofitFromCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel20)
                    .addComponent(tprofitFromCash))
                .addGap(24, 24, 24)
                .addGroup(pnl4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(reportDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTs))
                .addGap(39, 39, 39))
            .addGroup(pnl4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl4Layout.createSequentialGroup()
                    .addGap(56, 56, 56)
                    .addComponent(jLabel16)
                    .addContainerGap(584, Short.MAX_VALUE)))
        );

        rSPanelsSlider1.add(pnl4, "card5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rSPanelsSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(rSPanelsSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 2, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed

        if (!this.btn2.isSelected()) {

            this.btn1.setSelected(false);
            this.btn2.setSelected(true);
            this.btn3.setSelected(false);
            this.btn4.setSelected(false);

            rSPanelsSlider1.setPanelSlider(20, pnl2, RSPanelsSlider.DIRECT.RIGHT);

        } else {

            System.out.println("select btn2");
        }

    }//GEN-LAST:event_btn2ActionPerformed

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3ActionPerformed

        if (!this.btn3.isSelected()) {

            this.btn1.setSelected(false);
            this.btn2.setSelected(false);
            this.btn3.setSelected(true);
            this.btn4.setSelected(false);

            rSPanelsSlider1.setPanelSlider(20, pnl3, RSPanelsSlider.DIRECT.RIGHT);

            System.out.println("not select btn2");
        } else {

            System.out.println("select btn2");
        }
    }//GEN-LAST:event_btn3ActionPerformed

    private void searchAndFillGoodTable() {

        try {

            dftm = (DefaultTableModel) jTable1.getModel();
            dftm.setRowCount(0);

            jTable1.setModel(dftm);

            JTableHeader header = jTable1.getTableHeader();
            header.setBackground(Color.BLUE);

            JTableHeader header1 = sell_shop_table.getTableHeader();
            header1.setBackground(Color.BLUE);

            int column = 0;
            int row = jTable1.getSelectedRow();

            Database db = Connection.openConnection(myDb);

            ArrayList<Items> itmDetails = (ArrayList<Items>) db.getList("Items");
            String searchName = search_item.getText();
            if (itmDetails != null) {
                for (Items item : itmDetails) {
                    if (item.getItem().toLowerCase().startsWith(searchName.toLowerCase())) {
                        dftm.addRow(new Object[]{item.getItem(), item.getDistributerPrice(), item.getSalePrice(), item.getId(),item.getgAmount()+" "+item.getGoodUnit()});
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void searchAndFillSellToShopTable() {

        try {

            dftm1 = (DefaultTableModel) sell_shop_table.getModel();
            dftm1.setRowCount(0);
            Database db = Connection.openConnection(shopDb);

            ArrayList<SellShopModel> itmDetails = (ArrayList<SellShopModel>) db.getList("ShopItems");

            String searchName = searchText1.getText();

            if (itmDetails != null) {
                for (SellShopModel item : itmDetails) {
                    if (item.getOwnername().toLowerCase().startsWith(searchName.toLowerCase())) {
                        dftm1.addRow(new Object[]{item.getOwnername(), item.getId(), item.getPaymentMethod(), item.getPaymentAmount(),
                            item.getDate(), item.getGoodName(), item.getGoodAmount() + " " + item.getGoodUnit(), item.getUid()});
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void clearM() {
        item.setText("");
        dprice.setText("");
        sprice.setText("");
        gAmount.setText("");

        saveButton.setEnabled(true);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    private void clearModel() {

        date = new Date();
        bill_date.setDate(date);
        ownerName.setText("");
        ownerId.setText("");
        paymentAmount.setText("");

        goodAmount.setText("");

        saveButton1.setEnabled(true);
        updateButton1.setEnabled(false);
        deleteButton1.setEnabled(false);
    }


    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        clearM();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void updateButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateButtonMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_updateButtonMouseClicked

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

            if (!item.getText().equals("") && !dprice.getText().equals("") && !sprice.getText().equals("")&&!gAmount.getText().equals("")) {

                for (Items itm : itmDetailss) {
 
                    if (itm.getItem().equals(item.getText().toUpperCase())) {
                        isProductExist=false;
                        break;
                    }
                    

                }
                
                 
                if(isProductExist) {
                    
                    Items itms = new Items(id, item.getText().toUpperCase(), Double.parseDouble(dprice.getText()), Double.parseDouble(sprice.getText()),
                            Double.parseDouble(gAmount.getText()), (String) good_unit1.getSelectedItem());

                    ArrayList<Items> itmDetails = (ArrayList<Items>) db.getList("Items");

                    if (itmDetails == null) {
                        itmDetails = new ArrayList<>();
                        db.addList("Items", itmDetails);
                    }
                    itmDetails.add(itms);

                    Connection.save();
           

                } else {
                    isProductExist=true;
                    JOptionPane.showMessageDialog(item,"Product is already exist");
                    
                }
             
            } else {

                if (item.getText().equals("")) {
                    goodItemErrorMsg.setVisible(true);
                    goodItemErrorMsg.setText("Item name can not be empty");
                }

                if (dprice.getText().equals("")) {
                    distributePriceErrorMsg.setVisible(true);
                    distributePriceErrorMsg.setText("distribute price can not be empty");
                }

                if (sprice.getText().equals("")) {
                    salePriceErrorMsg.setVisible(true);
                    salePriceErrorMsg.setText("sale price can not be empty");
                }

                if (gAmount.getText().equals("")) {
                    gAmountErrorMsg.setVisible(true);
                    gAmountErrorMsg.setText("Product amount can not be empty");
                }


            }

        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        searchAndFillGoodTable();
        clearM();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void search_itemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_itemKeyReleased
        // TODO add your handling code here:
        searchAndFillGoodTable();
    }//GEN-LAST:event_search_itemKeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int select = jTable1.getSelectedRow();

        item.setText(dftm.getValueAt(select, 0).toString());

        dprice.setText(dftm.getValueAt(select, 1).toString());

        sprice.setText(dftm.getValueAt(select, 2).toString());
  
        String[] splited = dftm.getValueAt(select,4).toString().split("\\s+");
        gAmount.setText(splited[0]);

        good_unit1.setSelectedItem(splited[1]);
        

        saveButton.setEnabled(false);
        updateButton.setEnabled(true);
        deleteButton.setEnabled(true);
    }//GEN-LAST:event_jTable1MouseClicked


    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_updateButtonActionPerformed

    private void paymentMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentMethodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paymentMethodActionPerformed

    private void saveButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButton1ActionPerformed

        // TODO add your handling code here:
        try {

            Database db = Connection.openConnection(shopDb);

            ArrayList<SellShopModel> shopItmDetailsss = (ArrayList<SellShopModel>) db.getList("ShopItems");

            if (shopItmDetailsss != null) {

                id1 = shopItmDetailsss.get(shopItmDetailsss.size() - 1).getUid() + 1;

            } else {

                id1 = 1;
            }
  
            if(!ownerName.getText().equals("")&&!ownerId.getText().equals("")&&!paymentAmount.getText().equals("")&&!goodAmount.getText().equals("")){
           
                String d1 = ((JTextField) bill_date.getDateEditor().getUiComponent()).getText();

                SellShopModel sellShopModel = new SellShopModel(id1, ownerName.getText(),
                        ownerId.getText(), (String) paymentMethod.getSelectedItem(),
                        Long.parseLong(paymentAmount.getText()),
                        d1,
                        (String) goodItem.getSelectedItem(),
                        Long.parseLong(goodAmount.getText()), (String) good_unit.getSelectedItem());

                ArrayList<SellShopModel> itmDetails = (ArrayList<SellShopModel>) db.getList("ShopItems");

                if (itmDetails == null) {
                    itmDetails = new ArrayList<>();
                    db.addList("ShopItems", itmDetails);
                }
                itmDetails.add(sellShopModel);
                clearModel();
                Connection.save();
              
            }else {

                if (ownerName.getText().equals("")) {
                    ownerNameErrorMsg.setVisible(true);
                    ownerNameErrorMsg.setText("owner name can not be empty");
                }

                if (ownerId.getText().equals("")) {
                    ownerIdErrorMsg.setVisible(true);
                    ownerIdErrorMsg.setText("owner id can not be empty");
                }

                if (paymentAmount.getText().equals("")) {
                    paymentAmountErrorMsg.setVisible(true);
                    paymentAmountErrorMsg.setText("payment amount can not be empty");
                }
                
                  if (goodAmount.getText().equals("")) {
                    goodAmountErrorMsg.setVisible(true);
                    goodAmountErrorMsg.setText("good amount can not be empty");
                }


            }

        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        searchAndFillSellToShopTable();
    }//GEN-LAST:event_saveButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        clearModel();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void goodItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goodItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_goodItemActionPerformed

    private void sell_shop_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sell_shop_tableMouseClicked
        // TODO add your handling code here:

        updateButton1.setEnabled(true);
        deleteButton1.setEnabled(true);
        saveButton1.setEnabled(false);

        int select = sell_shop_table.getSelectedRow();

        ownerName.setText(dftm1.getValueAt(select, 0).toString());

        ownerId.setText(dftm1.getValueAt(select, 1).toString());

        paymentMethod.setSelectedItem(dftm1.getValueAt(select, 2).toString());

        paymentAmount.setText(dftm1.getValueAt(select, 3).toString());
        date.setDate(Integer.parseInt(dftm1.getValueAt(select, 3).toString()));

        goodItem.setSelectedItem(dftm1.getValueAt(select, 5).toString());

        String[] splited = dftm1.getValueAt(select, 6).toString().split("\\s+");
        goodAmount.setText(splited[0]);

        good_unit.setSelectedItem(splited[1]);

        saveButton.setEnabled(false);
        updateButton.setEnabled(true);
        deleteButton.setEnabled(true);
    }//GEN-LAST:event_sell_shop_tableMouseClicked

    private void searchText1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchText1KeyReleased
        // TODO add your handling code here:
        searchAndFillSellToShopTable();
    }//GEN-LAST:event_searchText1KeyReleased

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        // TODO add your handling code here:

        if (!this.btn1.isSelected()) {

            this.btn1.setSelected(true);
            this.btn2.setSelected(false);
            this.btn3.setSelected(false);
            this.btn4.setSelected(false);
            rSPanelsSlider1.setPanelSlider(20, pnl1, RSPanelsSlider.DIRECT.RIGHT);

            System.out.println("not select btn1");
        } else {

            System.out.println("select btn1");
        }

    }//GEN-LAST:event_btn1ActionPerformed

    private void deleteButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteButton1ActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void btn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4ActionPerformed
        // TODO add your handling code here:
        if (!this.btn4.isSelected()) {

            this.btn1.setSelected(false);
            this.btn2.setSelected(false);
            this.btn3.setSelected(false);
            this.btn4.setSelected(true);

            rSPanelsSlider1.setPanelSlider(20, pnl4, RSPanelsSlider.DIRECT.RIGHT);

            System.out.println("not select btn1");
        } else {

            System.out.println("select btn1");
        }
    }//GEN-LAST:event_btn4ActionPerformed

    private void btnTsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTsActionPerformed

        date = new Date();

        dftm2 = (DefaultTableModel) repTable.getModel();
        dftm2.setRowCount(0);

        try {
            Database db = Connection.openConnection(shopDb);
            ArrayList<SellShopModel> itmDetails = (ArrayList<SellShopModel>) db.getList("ShopItems");

            if (itmDetails != null) {

                for (SellShopModel item : itmDetails) {

                    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

                    String date3 = dateFormat.format(reportDateChooser.getDate());

                    Date date2 = dateFormat.parse(date3);

                    String dateStr1 = item.getDate();

                    Date date21 = dateFormat.parse(dateStr1);

                    if (date2.compareTo(date21) == 0 && item.getPaymentMethod().equals("Check")) {

                        try {
                            Database dbt = Connection.openConnection(myDb);

                            ArrayList<Items> itmDetailst = (ArrayList<Items>) dbt.getList("Items");

                            if (itmDetails != null) {
                                for (Items itemt : itmDetailst) {
                                    if (itemt.getItem().toLowerCase().equals(item.getGoodName().toLowerCase())) {
                                        unitSalePrice = itemt.getSalePrice();
                                        unitPerchasePrice = itemt.getDistributerPrice();

                                    }
                                }
                            }
                        } catch (Exception e) {

                        }

                        
                        todayRevenue = todayRevenue + unitSalePrice * item.getGoodAmount();
                        todayCost = todayCost + unitPerchasePrice * item.getGoodAmount();
                        todayProfitFromCheck = todayRevenue - todayCost;

                        dftm2.addRow(new Object[]{item.getGoodName(), item.getGoodAmount(),
                            item.getDate(), todayProfitFromCheck+"(CHECk)"});

                    } else if(date2.compareTo(date21) == 0 && item.getPaymentMethod().equals("Cash")) {
                        
                        
                        try {
                            Database dbt = Connection.openConnection(myDb);

                            ArrayList<Items> itmDetailst = (ArrayList<Items>) dbt.getList("Items");

                            if (itmDetails != null) {
                                for (Items itemt : itmDetailst) {
                                    if (itemt.getItem().toLowerCase().equals(item.getGoodName().toLowerCase())) {
                                        unitSalePrice = itemt.getSalePrice();
                                        unitPerchasePrice = itemt.getDistributerPrice();

                                    }
                                }
                            }
                        } catch (Exception e) {

                        }

                        
                        todayRevenue = todayRevenue + unitSalePrice * item.getGoodAmount();
                        todayCost = todayCost + unitPerchasePrice * item.getGoodAmount();
                        todayProfitFromCash = todayRevenue - todayCost;

                        dftm2.addRow(new Object[]{item.getGoodName(), item.getGoodAmount(),
                            item.getDate(), todayProfitFromCash+"(CASH)"});
                        
                        
                        
                        System.out.println("not work" + reportDateChooser.getDate().compareTo(date21));
                    }

                    totalProfitFromCheck = totalProfitFromCheck + todayProfitFromCheck;
                    tprofitFromCheck.setText(Double.toString(totalProfitFromCheck));
                    
                    totalProfitFromCash = totalProfitFromCash + todayProfitFromCash;
                    tprofitFromCash.setText(Double.toString(totalProfitFromCash));
                    
                    todayRevenue = 0;
                    todayCost = 0;
                    todayProfitFromCheck = 0;
                    todayProfitFromCash=0;
                }
                totalProfitFromCheck = 0;
                totalProfitFromCash=0;
            }

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnTsActionPerformed

    /**
     * @param args the command line arguments
     */
    File shopDb = new File("nn");

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        try {
            //here you can put the selected theme class name in JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser bill_date;
    private rojerusan.RSButtonIconD btn1;
    private rojerusan.RSButtonIconD btn2;
    private rojerusan.RSButtonIconD btn3;
    private rojerusan.RSButtonIconD btn4;
    private javax.swing.JButton btnTs;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton deleteButton1;
    private java.awt.Label distributePriceErrorMsg;
    private javax.swing.JTextField dprice;
    private javax.swing.JTextField gAmount;
    private java.awt.Label gAmountErrorMsg;
    private javax.swing.JTextField goodAmount;
    private java.awt.Label goodAmountErrorMsg;
    private javax.swing.JComboBox<String> goodItem;
    private java.awt.Label goodItemErrorMsg;
    private javax.swing.JPanel goodName;
    private javax.swing.JComboBox<String> good_unit;
    private javax.swing.JComboBox<String> good_unit1;
    private javax.swing.JTextField item;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField ownerId;
    private java.awt.Label ownerIdErrorMsg;
    private javax.swing.JTextField ownerName;
    private java.awt.Label ownerNameErrorMsg;
    private javax.swing.JTextField paymentAmount;
    private java.awt.Label paymentAmountErrorMsg;
    private javax.swing.JLabel paymentAmountValidation;
    private javax.swing.JComboBox<String> paymentMethod;
    private javax.swing.JPanel pnl1;
    private javax.swing.JPanel pnl2;
    private javax.swing.JPanel pnl3;
    private javax.swing.JPanel pnl4;
    private rojerusan.RSPanelImage rSPanelImage1;
    private rojerusan.RSPanelsSlider rSPanelsSlider1;
    private javax.swing.JTable repTable;
    private com.toedter.calendar.JDateChooser reportDateChooser;
    private java.awt.Label salePriceErrorMsg;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton saveButton1;
    private javax.swing.JTextField searchText1;
    private javax.swing.JTextField search_item;
    private javax.swing.JTable sell_shop_table;
    private javax.swing.JTextField sprice;
    private javax.swing.JLabel tprofitFromCash;
    private javax.swing.JLabel tprofitFromCheck;
    private javax.swing.JButton updateButton;
    private javax.swing.JButton updateButton1;
    // End of variables declaration//GEN-END:variables
}
