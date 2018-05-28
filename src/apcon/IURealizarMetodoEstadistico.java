/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * IURealizarMetodoEstadistico.java
 *
 * Created on 08/04/2010, 03:27:30 PM
 */

package apcon;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Andres
 */
public class IURealizarMetodoEstadistico extends JFrame {
    private List<Person> results2;
    private Person proy = new Person();
    private GestorRealizarMetodoEstadistico selector;
    private DefaultComboBoxModel combo;
    
    /** Creates new form IURealizarMetodoEstadistico */
    public IURealizarMetodoEstadistico() throws SQLException {
        
        selector = new GestorRealizarMetodoEstadistico();
        combo = new DefaultComboBoxModel();

        combo.addElement("...");
        combo.addElement("Distribución de Weibull.");
        combo.addElement("Análisis de Sobrevivencia, Falla y Riesgo.");
        combo.addElement("Comparar Métodos.");
            
        initComponents();

        jComboBoxSelector.setModel(combo);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelSeleccionMetodo = new javax.swing.JPanel();
        jLabelSeleccionMetodo = new javax.swing.JLabel();
        jComboBoxSelector = new javax.swing.JComboBox();
        jLabelComparar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButtonAceptar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(getTitle());
        setName("Form"); // NOI18N
        setResizable(false);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(apcon.APCONApp.class).getContext().getResourceMap(IURealizarMetodoEstadistico.class);
        jPanelSeleccionMetodo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanelSeleccionMetodo.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelSeleccionMetodo.border.titleFont"), resourceMap.getColor("jPanelSeleccionMetodo.border.titleColor"))); // NOI18N
        jPanelSeleccionMetodo.setName("jPanelSeleccionMetodo"); // NOI18N

        jLabelSeleccionMetodo.setFont(resourceMap.getFont("jLabelComparar.font")); // NOI18N
        jLabelSeleccionMetodo.setText(resourceMap.getString("jLabelSeleccionMetodo.text")); // NOI18N
        jLabelSeleccionMetodo.setName("jLabelSeleccionMetodo"); // NOI18N

        jComboBoxSelector.setFont(resourceMap.getFont("jComboBoxSelector.font")); // NOI18N
        jComboBoxSelector.setName("jComboBoxSelector"); // NOI18N
        jComboBoxSelector.setNextFocusableComponent(jButtonAceptar);
        jComboBoxSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSelectorActionPerformed(evt);
            }
        });
        jComboBoxSelector.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jComboBoxSelectorKeyTyped(evt);
            }
        });

        jLabelComparar.setFont(resourceMap.getFont("jLabelComparar.font")); // NOI18N
        jLabelComparar.setText(resourceMap.getString("jLabelComparar.text")); // NOI18N
        jLabelComparar.setName("jLabelComparar"); // NOI18N

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout jPanelSeleccionMetodoLayout = new javax.swing.GroupLayout(jPanelSeleccionMetodo);
        jPanelSeleccionMetodo.setLayout(jPanelSeleccionMetodoLayout);
        jPanelSeleccionMetodoLayout.setHorizontalGroup(
            jPanelSeleccionMetodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSeleccionMetodoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSeleccionMetodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSeleccionMetodo)
                    .addComponent(jComboBoxSelector, 0, 491, Short.MAX_VALUE)
                    .addGroup(jPanelSeleccionMetodoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelComparar)))
                .addContainerGap())
        );
        jPanelSeleccionMetodoLayout.setVerticalGroup(
            jPanelSeleccionMetodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSeleccionMetodoLayout.createSequentialGroup()
                .addComponent(jLabelSeleccionMetodo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSeleccionMetodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelComparar))
                .addGap(13, 13, 13)
                .addComponent(jComboBoxSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelSeleccionMetodo.getAccessibleContext().setAccessibleName(resourceMap.getString("jLabelSeleccionMetodo.AccessibleContext.accessibleName")); // NOI18N

        jButtonAceptar.setFont(resourceMap.getFont("jLabelSeleccionMetodo.font")); // NOI18N
        jButtonAceptar.setText(resourceMap.getString("jButtonAceptar.text")); // NOI18N
        jButtonAceptar.setToolTipText(resourceMap.getString("jButtonAceptar.toolTipText")); // NOI18N
        jButtonAceptar.setName("jButtonAceptar"); // NOI18N
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptar(evt);
            }
        });

        jButtonCancelar.setFont(resourceMap.getFont("jLabelSeleccionMetodo.font")); // NOI18N
        jButtonCancelar.setText(resourceMap.getString("jButtonCancelar.text")); // NOI18N
        jButtonCancelar.setToolTipText(resourceMap.getString("jButtonCancelar.toolTipText")); // NOI18N
        jButtonCancelar.setName("jButtonCancelar"); // NOI18N
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelar(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelSeleccionMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(339, Short.MAX_VALUE)
                .addComponent(jButtonAceptar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCancelar)
                .addGap(32, 32, 32))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonAceptar, jButtonCancelar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelSeleccionMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancelar)
                    .addComponent(jButtonAceptar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public String getTitle()
    {
        try
        {
            results2 = selector.getProyecto(selector.getProyectoCargado());
        } 
        
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        proy = results2.get(0);
        return proy.getProyecto();
    }
    private void botonCancelar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelar
        dispose();
}//GEN-LAST:event_botonCancelar

    private void botonAceptar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptar
        if (combo.getSelectedItem().equals("..."))
        {
            JOptionPane.showMessageDialog(this, "Seleccione un método.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try
        {
            results2 = selector.getProyecto(selector.getProyectoCargado());
            proy = results2.get(0);
        }
        
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        
        if(combo.getSelectedItem().equals("Comparar Métodos."))
            if (proy.getSFR()==true && proy.getWeibull()==true)
            {
                try
                {
                    combo.setSelectedItem("...");
                    selector.showCompararIU();
                    dispose();
                }

                catch (SQLException ex)
                {
                   JOptionPane.showMessageDialog(this, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
                }

                catch (ClassNotFoundException ex)
                {
                   JOptionPane.showMessageDialog(this, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
                }

                catch (FileNotFoundException ex)
                {
                   JOptionPane.showMessageDialog(this, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                if (proy.getSFR()==false)
                    JOptionPane.showMessageDialog(this, "El método \"Análisis de Sobrevivencia, Falla y Riesgo\" no ha sido realizado previamente.\n" +
                            "Realice el método y vuelva a intentar.", "ERROR", JOptionPane.INFORMATION_MESSAGE);

                if (proy.getWeibull()==false)
                    JOptionPane.showMessageDialog(this, "El método \"Distribución de Weibull\" no ha sido realizado previamente.\n" +
                            "Realice el método y vuelva a intentar.", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
        
        if (combo.getSelectedItem().equals("Análisis de Sobrevivencia, Falla y Riesgo."))
           try 
           {
               combo.setSelectedItem("...");
               selector.showSFRIU();
               dispose();        
           }
           
           catch (SQLException ex)
           {
              JOptionPane.showMessageDialog(this, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
           }

           catch (ClassNotFoundException ex)
           {
              JOptionPane.showMessageDialog(this, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
           }

           catch (FileNotFoundException ex)
           {
              JOptionPane.showMessageDialog(this, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
           }

        if(combo.getSelectedItem().equals("Distribución de Weibull."))
           try
           {
               combo.setSelectedItem("...");
               selector.showWeibullIU();
               dispose();
           }

           catch (SQLException ex)
           {
              JOptionPane.showMessageDialog(this, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
           }

           catch (ClassNotFoundException ex)
           {
              JOptionPane.showMessageDialog(this, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
           }

           catch (FileNotFoundException ex)
           {
              JOptionPane.showMessageDialog(this, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
           }
    }//GEN-LAST:event_botonAceptar

    private void jComboBoxSelectorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxSelectorKeyTyped
        char caracter = evt.getKeyChar();

        if(caracter == KeyEvent.VK_ENTER )
            jComboBoxSelector.transferFocus();
}//GEN-LAST:event_jComboBoxSelectorKeyTyped

    private void jComboBoxSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSelectorActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jComboBoxSelectorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAceptar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JComboBox jComboBoxSelector;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelComparar;
    private javax.swing.JLabel jLabelSeleccionMetodo;
    private javax.swing.JPanel jPanelSeleccionMetodo;
    // End of variables declaration//GEN-END:variables

}