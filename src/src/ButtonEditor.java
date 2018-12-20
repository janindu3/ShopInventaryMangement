/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author savantis
 */
class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        int x=10;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            
            NewJFrame mm= new NewJFrame();
        
                int selectedRowIndex = mm.kk.getSelectedRow();
            
         
                
            JTextField filed1 = new JTextField("");
            JTextField filed2 = new JTextField("");
            JTextField filed3 = new JTextField("");
              
             Object[] fields = {
                "Name", filed1,
                "Distribute Price", filed2,
                "Sale Price", filed3
            };
            
            JOptionPane.showConfirmDialog(button, fields,"Update values",JOptionPane.OK_CANCEL_OPTION);

   

        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}