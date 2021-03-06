/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * IUGestionarProyecto.java
 *
 * Created on 08/04/2010, 11:09:57 AM
 */

package apcon;

import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Andres
 */
public class IUGestionarProyecto extends javax.swing.JFrame {

    private GestorGestionarProyecto gGestionar;

    /** Creates new form IUGestionarProyecto */
    public IUGestionarProyecto() throws SQLException, ClassNotFoundException
    {
        gGestionar = new GestorGestionarProyecto();
        initComponents();        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelPropiedades = new javax.swing.JPanel();
        jLabelNombreAutor = new javax.swing.JLabel();
        jLabelIDAutor = new javax.swing.JLabel();
        jLabelApellidoAutor = new javax.swing.JLabel();
        jLabelNombreProyecto = new javax.swing.JLabel();
        jPanelDescripcion = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPaneDescripcion = new javax.swing.JTextPane();
        jTextFieldNombreAutor = new javax.swing.JTextField();
        jTextFieldIDAutor = new javax.swing.JTextField();
        jTextFieldNombreProyecto = new javax.swing.JTextField();
        jButtonCrear = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jTextFieldApellidoAutor = new javax.swing.JTextField();
        jLabelPlanta = new javax.swing.JLabel();
        jLabelSubSistema = new javax.swing.JLabel();
        jLabelEquipo = new javax.swing.JLabel();
        jTextFieldPlanta = new javax.swing.JTextField();
        jTextFieldSubSistema = new javax.swing.JTextField();
        jTextFieldEquipo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("Form"); // NOI18N
        setResizable(false);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(apcon.APCONApp.class).getContext().getResourceMap(IUGestionarProyecto.class);
        jPanelPropiedades.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanelPropiedades.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelPropiedades.border.titleFont"), resourceMap.getColor("jPanelPropiedades.border.titleColor"))); // NOI18N
        jPanelPropiedades.setName("jPanelPropiedades"); // NOI18N

        jLabelNombreAutor.setFont(resourceMap.getFont("jLabelEquipo.font")); // NOI18N
        jLabelNombreAutor.setText(resourceMap.getString("jLabelNombreAutor.text")); // NOI18N
        jLabelNombreAutor.setName("jLabelNombreAutor"); // NOI18N

        jLabelIDAutor.setFont(resourceMap.getFont("jLabelEquipo.font")); // NOI18N
        jLabelIDAutor.setText(resourceMap.getString("jLabelIDAutor.text")); // NOI18N
        jLabelIDAutor.setName("jLabelIDAutor"); // NOI18N

        jLabelApellidoAutor.setFont(resourceMap.getFont("jLabelEquipo.font")); // NOI18N
        jLabelApellidoAutor.setText(resourceMap.getString("jLabelApellidoAutor.text")); // NOI18N
        jLabelApellidoAutor.setName("jLabelApellidoAutor"); // NOI18N

        jLabelNombreProyecto.setFont(resourceMap.getFont("jLabelEquipo.font")); // NOI18N
        jLabelNombreProyecto.setText(resourceMap.getString("jLabelNombreProyecto.text")); // NOI18N
        jLabelNombreProyecto.setName("jLabelNombreProyecto"); // NOI18N

        jPanelDescripcion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanelDescripcion.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelDescripcion.border.titleFont"), resourceMap.getColor("jPanelDescripcion.border.titleColor"))); // NOI18N
        jPanelDescripcion.setName("jPanelDescripcion"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextPaneDescripcion.setFont(resourceMap.getFont("jTextFieldEquipo.font")); // NOI18N
        jTextPaneDescripcion.setToolTipText(resourceMap.getString("jTextPaneDescripcion.toolTipText")); // NOI18N
        jTextPaneDescripcion.setName("jTextPaneDescripcion"); // NOI18N
        jTextPaneDescripcion.setNextFocusableComponent(jButtonCrear);
        jTextPaneDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextPaneDescripcionKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTextPaneDescripcion);

        javax.swing.GroupLayout jPanelDescripcionLayout = new javax.swing.GroupLayout(jPanelDescripcion);
        jPanelDescripcion.setLayout(jPanelDescripcionLayout);
        jPanelDescripcionLayout.setHorizontalGroup(
            jPanelDescripcionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDescripcionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelDescripcionLayout.setVerticalGroup(
            jPanelDescripcionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDescripcionLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTextFieldNombreAutor.setFont(resourceMap.getFont("jTextFieldEquipo.font")); // NOI18N
        jTextFieldNombreAutor.setToolTipText(resourceMap.getString("jTextFieldNombreAutor.toolTipText")); // NOI18N
        jTextFieldNombreAutor.setName("jTextFieldNombreAutor"); // NOI18N
        jTextFieldNombreAutor.setNextFocusableComponent(jTextFieldApellidoAutor);
        jTextFieldNombreAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreAutorActionPerformed(evt);
            }
        });
        jTextFieldNombreAutor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNombreAutorKeyTyped(evt);
            }
        });

        jTextFieldIDAutor.setFont(resourceMap.getFont("jTextFieldEquipo.font")); // NOI18N
        jTextFieldIDAutor.setToolTipText(resourceMap.getString("jTextFieldIDAutor.toolTipText")); // NOI18N
        jTextFieldIDAutor.setName("jTextFieldIDAutor"); // NOI18N
        jTextFieldIDAutor.setNextFocusableComponent(jTextFieldNombreProyecto);
        jTextFieldIDAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIDAutorActionPerformed(evt);
            }
        });
        jTextFieldIDAutor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldIDAutorKeyTyped(evt);
            }
        });

        jTextFieldNombreProyecto.setFont(resourceMap.getFont("jTextFieldEquipo.font")); // NOI18N
        jTextFieldNombreProyecto.setToolTipText(resourceMap.getString("jTextFieldNombreProyecto.toolTipText")); // NOI18N
        jTextFieldNombreProyecto.setName("jTextFieldNombreProyecto"); // NOI18N
        jTextFieldNombreProyecto.setNextFocusableComponent(jTextFieldPlanta);
        jTextFieldNombreProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreProyectoActionPerformed(evt);
            }
        });
        jTextFieldNombreProyecto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNombreProyectoKeyTyped(evt);
            }
        });

        jButtonCrear.setFont(resourceMap.getFont("jButtonCrear.font")); // NOI18N
        jButtonCrear.setText(resourceMap.getString("jButtonCrear.text")); // NOI18N
        jButtonCrear.setToolTipText(resourceMap.getString("jButtonCrear.toolTipText")); // NOI18N
        jButtonCrear.setName("jButtonCrear"); // NOI18N
        jButtonCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCrear(evt);
            }
        });

        jButtonCancelar.setFont(resourceMap.getFont("jButtonCrear.font")); // NOI18N
        jButtonCancelar.setText(resourceMap.getString("jButtonCancelar.text")); // NOI18N
        jButtonCancelar.setToolTipText(resourceMap.getString("jButtonCancelar.toolTipText")); // NOI18N
        jButtonCancelar.setName("jButtonCancelar"); // NOI18N
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelar(evt);
            }
        });

        jTextFieldApellidoAutor.setFont(resourceMap.getFont("jTextFieldEquipo.font")); // NOI18N
        jTextFieldApellidoAutor.setToolTipText(resourceMap.getString("jTextFieldApellidoAutor.toolTipText")); // NOI18N
        jTextFieldApellidoAutor.setName("jTextFieldApellidoAutor"); // NOI18N
        jTextFieldApellidoAutor.setNextFocusableComponent(jTextFieldIDAutor);
        jTextFieldApellidoAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldApellidoAutorActionPerformed(evt);
            }
        });
        jTextFieldApellidoAutor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldApellidoAutorKeyTyped(evt);
            }
        });

        jLabelPlanta.setFont(resourceMap.getFont("jLabelEquipo.font")); // NOI18N
        jLabelPlanta.setText(resourceMap.getString("jLabelPlanta.text")); // NOI18N
        jLabelPlanta.setName("jLabelPlanta"); // NOI18N

        jLabelSubSistema.setFont(resourceMap.getFont("jLabelEquipo.font")); // NOI18N
        jLabelSubSistema.setText(resourceMap.getString("jLabelSubSistema.text")); // NOI18N
        jLabelSubSistema.setName("jLabelSubSistema"); // NOI18N

        jLabelEquipo.setFont(resourceMap.getFont("jLabelEquipo.font")); // NOI18N
        jLabelEquipo.setText(resourceMap.getString("jLabelEquipo.text")); // NOI18N
        jLabelEquipo.setName("jLabelEquipo"); // NOI18N

        jTextFieldPlanta.setFont(resourceMap.getFont("jTextFieldEquipo.font")); // NOI18N
        jTextFieldPlanta.setText(resourceMap.getString("jTextFieldPlanta.text")); // NOI18N
        jTextFieldPlanta.setToolTipText(resourceMap.getString("jTextFieldPlanta.toolTipText")); // NOI18N
        jTextFieldPlanta.setName("jTextFieldPlanta"); // NOI18N
        jTextFieldPlanta.setNextFocusableComponent(jTextFieldSubSistema);
        jTextFieldPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPlantaActionPerformed(evt);
            }
        });
        jTextFieldPlanta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPlantaKeyTyped(evt);
            }
        });

        jTextFieldSubSistema.setFont(resourceMap.getFont("jTextFieldEquipo.font")); // NOI18N
        jTextFieldSubSistema.setText(resourceMap.getString("jTextFieldSubSistema.text")); // NOI18N
        jTextFieldSubSistema.setToolTipText(resourceMap.getString("jTextFieldSubSistema.toolTipText")); // NOI18N
        jTextFieldSubSistema.setName("jTextFieldSubSistema"); // NOI18N
        jTextFieldSubSistema.setNextFocusableComponent(jTextFieldEquipo);
        jTextFieldSubSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSubSistemaActionPerformed(evt);
            }
        });
        jTextFieldSubSistema.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldSubSistemaKeyTyped(evt);
            }
        });

        jTextFieldEquipo.setFont(resourceMap.getFont("jTextFieldEquipo.font")); // NOI18N
        jTextFieldEquipo.setText(resourceMap.getString("jTextFieldEquipo.text")); // NOI18N
        jTextFieldEquipo.setToolTipText(resourceMap.getString("jTextFieldEquipo.toolTipText")); // NOI18N
        jTextFieldEquipo.setName("jTextFieldEquipo"); // NOI18N
        jTextFieldEquipo.setNextFocusableComponent(jTextPaneDescripcion);
        jTextFieldEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEquipoActionPerformed(evt);
            }
        });
        jTextFieldEquipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldEquipoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanelPropiedadesLayout = new javax.swing.GroupLayout(jPanelPropiedades);
        jPanelPropiedades.setLayout(jPanelPropiedadesLayout);
        jPanelPropiedadesLayout.setHorizontalGroup(
            jPanelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPropiedadesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPropiedadesLayout.createSequentialGroup()
                        .addGroup(jPanelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelNombreProyecto)
                            .addComponent(jLabelIDAutor)
                            .addComponent(jLabelApellidoAutor)
                            .addComponent(jLabelNombreAutor)
                            .addComponent(jLabelPlanta)
                            .addComponent(jLabelSubSistema)
                            .addComponent(jLabelEquipo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldApellidoAutor)
                            .addComponent(jTextFieldNombreAutor, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .addComponent(jTextFieldIDAutor, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .addComponent(jTextFieldNombreProyecto, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .addComponent(jTextFieldPlanta)
                            .addComponent(jTextFieldSubSistema)
                            .addComponent(jTextFieldEquipo)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPropiedadesLayout.createSequentialGroup()
                        .addComponent(jButtonCrear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonCancelar))
                    .addComponent(jPanelDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanelPropiedadesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonCancelar, jButtonCrear});

        jPanelPropiedadesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextFieldApellidoAutor, jTextFieldIDAutor, jTextFieldNombreAutor, jTextFieldNombreProyecto});

        jPanelPropiedadesLayout.setVerticalGroup(
            jPanelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPropiedadesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNombreAutor)
                    .addComponent(jTextFieldNombreAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldApellidoAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelApellidoAutor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldIDAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelIDAutor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNombreProyecto)
                    .addComponent(jTextFieldNombreProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPlanta)
                    .addComponent(jTextFieldPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSubSistema)
                    .addComponent(jTextFieldSubSistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEquipo)
                    .addComponent(jTextFieldEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(jPanelDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancelar)
                    .addComponent(jButtonCrear))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelPropiedades, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelPropiedades, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonCancelar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelar
        jTextFieldNombreAutor.setText("");
        jTextFieldApellidoAutor.setText("");
        jTextFieldIDAutor.setText("");
        jTextFieldNombreProyecto.setText("");
        jTextFieldPlanta.setText("");
        jTextFieldSubSistema.setText("");
        jTextFieldEquipo.setText("");
        jTextPaneDescripcion.setText("");
        dispose();
}//GEN-LAST:event_botonCancelar

    private void botonCrear(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCrear
        if (gGestionar.validarCampo(jTextFieldNombreAutor.getText()) == 0||
                gGestionar.validarCampo(jTextFieldNombreProyecto.getText()) == 0 ||
                gGestionar.validarCampo(jTextFieldIDAutor.getText()) == 0 ||
                gGestionar.validarCampo(jTextFieldPlanta.getText()) == 0 ||
                gGestionar.validarCampo(jTextFieldApellidoAutor.getText()) == 0 ||
                gGestionar.validarCampo(jTextFieldSubSistema.getText()) == 0 ||
                gGestionar.validarCampo(jTextFieldEquipo.getText()) == 0 ||
                gGestionar.validarCampo(jTextPaneDescripcion.getText()) == 0)
        {
            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos.");
            jButtonCancelar.transferFocus();
            return;
        }
        
        if (jTextFieldIDAutor.getText().length() < 7)
        {
            JOptionPane.showMessageDialog(this, "La cédula debe tener entre 7 y 8 dígitos.");
            return;
        }
            
        Person person = new Person();
        person.setNombre(jTextFieldNombreAutor.getText());
        person.setApellido(jTextFieldApellidoAutor.getText());
        person.setCedula(jTextFieldIDAutor.getText());
        person.setProyecto(jTextFieldNombreProyecto.getText());
        person.setPlanta(jTextFieldPlanta.getText());
        person.setSubSistema(jTextFieldSubSistema.getText());
        person.setEquipo(jTextFieldEquipo.getText());
        person.setDescripcion(jTextPaneDescripcion.getText());
        try
        {
            gGestionar.crear(this,person);
        }

        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        catch (ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        finally
        {
            jTextFieldNombreAutor.setText("");
            jTextFieldApellidoAutor.setText("");
            jTextFieldIDAutor.setText("");
            jTextFieldNombreProyecto.setText("");
            jTextFieldPlanta.setText("");
            jTextFieldSubSistema.setText("");
            jTextFieldEquipo.setText("");
            jTextPaneDescripcion.setText("");
            dispose();
        }
    }//GEN-LAST:event_botonCrear

    private void jTextFieldNombreAutorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNombreAutorKeyTyped
        gGestionar.validacionCampo(evt, "[á-úÁ-Úa-zA-Z]");
        gGestionar.validarTamaño(jTextFieldNombreAutor.getText(), 10, evt);
    }//GEN-LAST:event_jTextFieldNombreAutorKeyTyped

    private void jTextFieldNombreAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreAutorActionPerformed
        jTextFieldNombreAutor.transferFocus();
    }//GEN-LAST:event_jTextFieldNombreAutorActionPerformed

    private void jTextFieldApellidoAutorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldApellidoAutorKeyTyped
        gGestionar.validacionCampo(evt, "[á-úÁ-Úa-zA-Z]");
        gGestionar.validarTamaño(jTextFieldApellidoAutor.getText(), 15, evt);
    }//GEN-LAST:event_jTextFieldApellidoAutorKeyTyped

    private void jTextFieldApellidoAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldApellidoAutorActionPerformed
        jTextFieldApellidoAutor.transferFocus();
    }//GEN-LAST:event_jTextFieldApellidoAutorActionPerformed

    private void jTextFieldIDAutorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldIDAutorKeyTyped
        gGestionar.validacionCampo(evt, "[0-9]");
        char caracter = evt.getKeyChar();
        if (caracter == KeyEvent.VK_SPACE) evt.consume();
        gGestionar.validarTamaño(jTextFieldIDAutor.getText(), 8, evt);
    }//GEN-LAST:event_jTextFieldIDAutorKeyTyped

    private void jTextFieldIDAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIDAutorActionPerformed
        jTextFieldIDAutor.transferFocus();
    }//GEN-LAST:event_jTextFieldIDAutorActionPerformed

    private void jTextFieldNombreProyectoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNombreProyectoKeyTyped
        gGestionar.validacionCampo(evt, "[-0-9á-úÁ-Úa-zA-Z]");
        gGestionar.validarTamaño(jTextFieldNombreProyecto.getText(), 30, evt);
    }//GEN-LAST:event_jTextFieldNombreProyectoKeyTyped

    private void jTextFieldNombreProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreProyectoActionPerformed
        jTextFieldNombreProyecto.transferFocus();
    }//GEN-LAST:event_jTextFieldNombreProyectoActionPerformed

    private void jTextFieldPlantaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPlantaKeyTyped
        gGestionar.validacionCampo(evt, "[-0-9á-úÁ-Úa-zA-Z]");
        gGestionar.validarTamaño(jTextFieldPlanta.getText(), 15, evt);
    }//GEN-LAST:event_jTextFieldPlantaKeyTyped

    private void jTextFieldPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPlantaActionPerformed
        jTextFieldPlanta.transferFocus();
    }//GEN-LAST:event_jTextFieldPlantaActionPerformed

    private void jTextFieldSubSistemaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSubSistemaKeyTyped
        gGestionar.validacionCampo(evt, "[-0-9á-úÁ-Úa-zA-Z]");
        gGestionar.validarTamaño(jTextFieldSubSistema.getText(), 15, evt);
    }//GEN-LAST:event_jTextFieldSubSistemaKeyTyped

    private void jTextFieldSubSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSubSistemaActionPerformed
        jTextFieldSubSistema.transferFocus();
    }//GEN-LAST:event_jTextFieldSubSistemaActionPerformed

    private void jTextFieldEquipoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEquipoKeyTyped
        gGestionar.validacionCampo(evt, "[-0-9á-úÁ-Úa-zA-Z]");
        gGestionar.validarTamaño(jTextFieldEquipo.getText(), 15, evt);
    }//GEN-LAST:event_jTextFieldEquipoKeyTyped

    private void jTextFieldEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEquipoActionPerformed
        jTextFieldEquipo.transferFocus();
    }//GEN-LAST:event_jTextFieldEquipoActionPerformed

    private void jTextPaneDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPaneDescripcionKeyTyped
        gGestionar.validacionCampo(evt, "[,-.:-;0-9á-úÁ-Úa-zA-Z(-)]");
        char enter = evt.getKeyChar();
        if (enter == KeyEvent.VK_ENTER) jTextPaneDescripcion.transferFocus();
        gGestionar.validarTamaño(jTextPaneDescripcion.getText(), 255, evt);
    }//GEN-LAST:event_jTextPaneDescripcionKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonCrear;
    private javax.swing.JLabel jLabelApellidoAutor;
    private javax.swing.JLabel jLabelEquipo;
    private javax.swing.JLabel jLabelIDAutor;
    private javax.swing.JLabel jLabelNombreAutor;
    private javax.swing.JLabel jLabelNombreProyecto;
    private javax.swing.JLabel jLabelPlanta;
    private javax.swing.JLabel jLabelSubSistema;
    private javax.swing.JPanel jPanelDescripcion;
    private javax.swing.JPanel jPanelPropiedades;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldApellidoAutor;
    private javax.swing.JTextField jTextFieldEquipo;
    private javax.swing.JTextField jTextFieldIDAutor;
    private javax.swing.JTextField jTextFieldNombreAutor;
    private javax.swing.JTextField jTextFieldNombreProyecto;
    private javax.swing.JTextField jTextFieldPlanta;
    private javax.swing.JTextField jTextFieldSubSistema;
    private javax.swing.JTextPane jTextPaneDescripcion;
    // End of variables declaration//GEN-END:variables

}
