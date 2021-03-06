package apcon;


import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * IURealizarAnalisisSFR.java
 *
 * Created on 04/05/2010, 10:43:58 AM
 */

/**
 *
 * @author Andres
 */
public class IURealizarAnalisisSFR extends javax.swing.JFrame {
    int row = 0;
    int x;
    int control1 = 0;
    int nIntervalos, nObsTotales;
    private DatosProyecto datosSFR;
    private List<DatosProyecto> results;
    private List<Proyecto>results1;
    private Proyecto proy = new Proyecto();
    private DefaultTableModel model;
    private GestorRealizarAnalisisSFR gestorSFR;

    /** Creates new form IURealizarAnalisisSFR */
    public IURealizarAnalisisSFR() throws SQLException, ClassNotFoundException, FileNotFoundException
    {
        gestorSFR = new GestorRealizarAnalisisSFR();
        int i = 0;
        results1 = gestorSFR.getProyecto(gestorSFR.getProyectoCargado());
        proy = results1.get(0);

        model = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return false;
            }
        };
        
        results = gestorSFR.getDatosProyecto(gestorSFR.getProyectoCargado());
        x = results.size();

        if(results.size()==0)
        {
            model.addColumn("Intervalo");
            model.addColumn("N. Fallas");
            model.addColumn("N. Datos Censurados");
          
            initComponents();
        }

        else
        {
            model.addColumn("Intervalo");
            model.addColumn("N. Fallas");
            model.addColumn("N. Datos Censurados");

            for (i=0; i<results.size();i++)
            {
                datosSFR = results.get(i);

                model.addRow(new Integer[] {
                    datosSFR.getIntervalo(),
                    datosSFR.getFallas(),
                    datosSFR.getDC()});
            }

            initComponents();
            datosSFR = results.get(0);
            jTextFieldNObs.setText(Integer.toString(datosSFR.getObs()));
            jTextFieldNObs.setEnabled(false);
            jTextFieldNIntervalos.setText(Integer.toString(results.size()));
            jTextFieldNIntervalos.setEnabled(false);
            jTextFieldIntervalo.setText(Integer.toString(i+1));
            jButtonModificar.setEnabled(true);
            jButtonModificarI.setEnabled(true);
            jButtonCargar.setEnabled(false);
            jButtonProcesar.setEnabled(true);            
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTablaDatos = new javax.swing.JPanel();
        jLabelNFallas = new javax.swing.JLabel();
        jLabelIntervalo = new javax.swing.JLabel();
        jLabelNDC = new javax.swing.JLabel();
        jTextFieldIntervalo = new javax.swing.JTextField();
        jTextFieldNFallas = new javax.swing.JTextField();
        jTextFieldNDC = new javax.swing.JTextField();
        jButtonInsertar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableSFR = new javax.swing.JTable(model);
        jButtonProcesar = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();
        jPanelNObs = new javax.swing.JPanel();
        jButtonCargar = new javax.swing.JButton();
        jLabelNObs = new javax.swing.JLabel();
        jTextFieldNObs = new javax.swing.JTextField();
        jLabelNDias = new javax.swing.JLabel();
        jTextFieldNIntervalos = new javax.swing.JTextField();
        jButtonModificarI = new javax.swing.JButton();
        jButtonCancelarI = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jButtonSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(proy.getProyecto());
        setName("fff"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(apcon.APCONApp.class).getContext().getResourceMap(IURealizarAnalisisSFR.class);
        jPanelTablaDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanelTablaDatos.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelTablaDatos.border.titleFont"), resourceMap.getColor("jPanelTablaDatos.border.titleColor"))); // NOI18N
        jPanelTablaDatos.setName("jPanelTablaDatos"); // NOI18N

        jLabelNFallas.setFont(resourceMap.getFont("jTextFieldNDC.font")); // NOI18N
        jLabelNFallas.setText(resourceMap.getString("jLabelNFallas.text")); // NOI18N
        jLabelNFallas.setName("jLabelNFallas"); // NOI18N

        jLabelIntervalo.setFont(resourceMap.getFont("jTextFieldNDC.font")); // NOI18N
        jLabelIntervalo.setText(resourceMap.getString("jLabelIntervalo.text")); // NOI18N
        jLabelIntervalo.setName("jLabelIntervalo"); // NOI18N

        jLabelNDC.setFont(resourceMap.getFont("jTextFieldNDC.font")); // NOI18N
        jLabelNDC.setText(resourceMap.getString("jLabelNDC.text")); // NOI18N
        jLabelNDC.setName("jLabelNDC"); // NOI18N

        jTextFieldIntervalo.setEditable(false);
        jTextFieldIntervalo.setFont(resourceMap.getFont("jTextFieldNDC.font")); // NOI18N
        jTextFieldIntervalo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldIntervalo.setText(resourceMap.getString("jTextFieldIntervalo.text")); // NOI18N
        jTextFieldIntervalo.setToolTipText(resourceMap.getString("jTextFieldIntervalo.toolTipText")); // NOI18N
        jTextFieldIntervalo.setName("jTextFieldIntervalo"); // NOI18N

        jTextFieldNFallas.setFont(resourceMap.getFont("jTextFieldNDC.font")); // NOI18N
        jTextFieldNFallas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldNFallas.setToolTipText(resourceMap.getString("jTextFieldNFallas.toolTipText")); // NOI18N
        jTextFieldNFallas.setEnabled(false);
        jTextFieldNFallas.setName("jTextFieldNFallas"); // NOI18N
        jTextFieldNFallas.setNextFocusableComponent(jTextFieldNDC);
        jTextFieldNFallas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNFallasActionPerformed(evt);
            }
        });
        jTextFieldNFallas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNFallasKeyTyped(evt);
            }
        });

        jTextFieldNDC.setFont(resourceMap.getFont("jTextFieldNDC.font")); // NOI18N
        jTextFieldNDC.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldNDC.setToolTipText(resourceMap.getString("jTextFieldNDC.toolTipText")); // NOI18N
        jTextFieldNDC.setEnabled(false);
        jTextFieldNDC.setName("jTextFieldNDC"); // NOI18N
        jTextFieldNDC.setNextFocusableComponent(jButtonInsertar);
        jTextFieldNDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNDCActionPerformed(evt);
            }
        });
        jTextFieldNDC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNDCKeyTyped(evt);
            }
        });

        jButtonInsertar.setFont(resourceMap.getFont("jTextFieldNDC.font")); // NOI18N
        jButtonInsertar.setText(resourceMap.getString("jButtonInsertar.text")); // NOI18N
        jButtonInsertar.setToolTipText(resourceMap.getString("jButtonInsertar.toolTipText")); // NOI18N
        jButtonInsertar.setEnabled(false);
        jButtonInsertar.setName("jButtonInsertar"); // NOI18N
        jButtonInsertar.setNextFocusableComponent(jTextFieldNFallas);
        jButtonInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonInsertar(evt);
            }
        });

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTableSFR.setFont(resourceMap.getFont("jTextFieldNDC.font")); // NOI18N
        jTableSFR.setToolTipText(resourceMap.getString("jTableSFR.toolTipText")); // NOI18N
        jTableSFR.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTableSFR.setName("jTableSFR"); // NOI18N
        jTableSFR.setRequestFocusEnabled(false);
        jTableSFR.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableSFR.getTableHeader().setReorderingAllowed(false);
        jTableSFR.setUpdateSelectionOnSort(false);
        jTableSFR.setVerifyInputWhenFocusTarget(false);
        jTableSFR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableSFRMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTableSFR);

        javax.swing.GroupLayout jPanelTablaDatosLayout = new javax.swing.GroupLayout(jPanelTablaDatos);
        jPanelTablaDatos.setLayout(jPanelTablaDatosLayout);
        jPanelTablaDatosLayout.setHorizontalGroup(
            jPanelTablaDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTablaDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTablaDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelTablaDatosLayout.createSequentialGroup()
                        .addGroup(jPanelTablaDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelIntervalo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelNFallas, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelNDC, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelTablaDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldIntervalo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(jTextFieldNFallas, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldNDC, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTablaDatosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonInsertar)
                        .addGap(125, 125, 125)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelTablaDatosLayout.setVerticalGroup(
            jPanelTablaDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTablaDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTablaDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .addGroup(jPanelTablaDatosLayout.createSequentialGroup()
                        .addGroup(jPanelTablaDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelIntervalo))
                        .addGap(8, 8, 8)
                        .addGroup(jPanelTablaDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldNFallas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNFallas))
                        .addGap(6, 6, 6)
                        .addGroup(jPanelTablaDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldNDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNDC))
                        .addGap(18, 18, 18)
                        .addComponent(jButtonInsertar)))
                .addContainerGap())
        );

        jButtonProcesar.setFont(resourceMap.getFont("jButtonProcesar.font")); // NOI18N
        jButtonProcesar.setText(resourceMap.getString("jButtonProcesar.text")); // NOI18N
        jButtonProcesar.setToolTipText(resourceMap.getString("jButtonProcesar.toolTipText")); // NOI18N
        jButtonProcesar.setEnabled(false);
        jButtonProcesar.setName("jButtonProcesar"); // NOI18N
        jButtonProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonProcesar(evt);
            }
        });

        jButtonSalir.setFont(resourceMap.getFont("jButtonProcesar.font")); // NOI18N
        jButtonSalir.setText(resourceMap.getString("jButtonSalir.text")); // NOI18N
        jButtonSalir.setToolTipText(resourceMap.getString("jButtonSalir.toolTipText")); // NOI18N
        jButtonSalir.setName("jButtonSalir"); // NOI18N
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalir(evt);
            }
        });

        jPanelNObs.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanelNObs.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelNObs.border.titleFont"), resourceMap.getColor("jPanelNObs.border.titleColor"))); // NOI18N
        jPanelNObs.setName("jPanelNObs"); // NOI18N

        jButtonCargar.setFont(resourceMap.getFont("jButtonCargar.font")); // NOI18N
        jButtonCargar.setText(resourceMap.getString("jButtonCargar.text")); // NOI18N
        jButtonCargar.setToolTipText(resourceMap.getString("jButtonCargar.toolTipText")); // NOI18N
        jButtonCargar.setAlignmentX(0.5F);
        jButtonCargar.setName("jButtonCargar"); // NOI18N
        jButtonCargar.setNextFocusableComponent(jTextFieldNFallas);
        jButtonCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCargar(evt);
            }
        });

        jLabelNObs.setFont(resourceMap.getFont("jButtonCargar.font")); // NOI18N
        jLabelNObs.setText(resourceMap.getString("jLabelNObs.text")); // NOI18N
        jLabelNObs.setName("jLabelNObs"); // NOI18N

        jTextFieldNObs.setFont(resourceMap.getFont("jButtonCargar.font")); // NOI18N
        jTextFieldNObs.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldNObs.setToolTipText(resourceMap.getString("jTextFieldNObs.toolTipText")); // NOI18N
        jTextFieldNObs.setName("jTextFieldNObs"); // NOI18N
        jTextFieldNObs.setNextFocusableComponent(jTextFieldNIntervalos);
        jTextFieldNObs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNObsActionPerformed(evt);
            }
        });
        jTextFieldNObs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNObsKeyTyped(evt);
            }
        });

        jLabelNDias.setFont(resourceMap.getFont("jButtonCargar.font")); // NOI18N
        jLabelNDias.setText(resourceMap.getString("jLabelNDias.text")); // NOI18N
        jLabelNDias.setName("jLabelNDias"); // NOI18N

        jTextFieldNIntervalos.setFont(resourceMap.getFont("jButtonCargar.font")); // NOI18N
        jTextFieldNIntervalos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldNIntervalos.setToolTipText(resourceMap.getString("jTextFieldNIntervalos.toolTipText")); // NOI18N
        jTextFieldNIntervalos.setName("jTextFieldNIntervalos"); // NOI18N
        jTextFieldNIntervalos.setNextFocusableComponent(jButtonCargar);
        jTextFieldNIntervalos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNIntervalosActionPerformed(evt);
            }
        });
        jTextFieldNIntervalos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNIntervalosKeyTyped(evt);
            }
        });

        jButtonModificarI.setFont(resourceMap.getFont("jButtonCargar.font")); // NOI18N
        jButtonModificarI.setText(resourceMap.getString("jButtonModificarI.text")); // NOI18N
        jButtonModificarI.setToolTipText(resourceMap.getString("jButtonModificarI.toolTipText")); // NOI18N
        jButtonModificarI.setEnabled(false);
        jButtonModificarI.setName("jButtonModificarI"); // NOI18N
        jButtonModificarI.setNextFocusableComponent(jTextFieldNObs);
        jButtonModificarI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarI(evt);
            }
        });

        jButtonCancelarI.setFont(resourceMap.getFont("jButtonCargar.font")); // NOI18N
        jButtonCancelarI.setText(resourceMap.getString("jButtonCancelarI.text")); // NOI18N
        jButtonCancelarI.setToolTipText(resourceMap.getString("jButtonCancelarI.toolTipText")); // NOI18N
        jButtonCancelarI.setEnabled(false);
        jButtonCancelarI.setName("jButtonCancelarI"); // NOI18N
        jButtonCancelarI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarI(evt);
            }
        });

        javax.swing.GroupLayout jPanelNObsLayout = new javax.swing.GroupLayout(jPanelNObs);
        jPanelNObs.setLayout(jPanelNObsLayout);
        jPanelNObsLayout.setHorizontalGroup(
            jPanelNObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNObsLayout.createSequentialGroup()
                .addContainerGap(81, Short.MAX_VALUE)
                .addGroup(jPanelNObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelNObsLayout.createSequentialGroup()
                        .addGroup(jPanelNObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelNDias)
                            .addComponent(jLabelNObs))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelNObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldNObs)
                            .addComponent(jTextFieldNIntervalos, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                        .addGap(149, 149, 149))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelNObsLayout.createSequentialGroup()
                        .addComponent(jButtonCargar, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonModificarI)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCancelarI)
                        .addGap(171, 171, 171))))
        );

        jPanelNObsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonCancelarI, jButtonModificarI});

        jPanelNObsLayout.setVerticalGroup(
            jPanelNObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNObsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNObs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNObs, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNIntervalos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNDias))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelNObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonModificarI)
                    .addComponent(jButtonCancelarI)
                    .addComponent(jButtonCargar))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanelNObsLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonCancelarI, jButtonCargar, jButtonModificarI});

        jButtonModificar.setFont(resourceMap.getFont("jButtonProcesar.font")); // NOI18N
        jButtonModificar.setText(resourceMap.getString("jButtonModificar.text")); // NOI18N
        jButtonModificar.setToolTipText(resourceMap.getString("jButtonModificar.toolTipText")); // NOI18N
        jButtonModificar.setEnabled(false);
        jButtonModificar.setName("jButtonModificar"); // NOI18N
        jButtonModificar.setNextFocusableComponent(jTextFieldNFallas);
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificar(evt);
            }
        });

        jButtonSalvar.setFont(resourceMap.getFont("jButtonProcesar.font")); // NOI18N
        jButtonSalvar.setText(resourceMap.getString("jButtonSalvar.text")); // NOI18N
        jButtonSalvar.setToolTipText(resourceMap.getString("jButtonSalvar.toolTipText")); // NOI18N
        jButtonSalvar.setEnabled(false);
        jButtonSalvar.setName("jButtonSalvar"); // NOI18N
        jButtonSalvar.setNextFocusableComponent(jButtonProcesar);
        jButtonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalvar(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelTablaDatos, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                        .addComponent(jButtonModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSalvar)
                        .addGap(8, 8, 8)
                        .addComponent(jButtonProcesar)
                        .addGap(5, 5, 5)
                        .addComponent(jButtonSalir))
                    .addComponent(jPanelNObs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonModificar, jButtonProcesar, jButtonSalir, jButtonSalvar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelNObs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelTablaDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSalir)
                    .addComponent(jButtonProcesar)
                    .addComponent(jButtonModificar)
                    .addComponent(jButtonSalvar))
                .addGap(20, 20, 20))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonModificar, jButtonProcesar, jButtonSalir, jButtonSalvar});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonInsertar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonInsertar
        try
        {
            int intervalo = Integer.parseInt(jTextFieldIntervalo.getText());
            int fallas = Integer.parseInt(jTextFieldNFallas.getText());
            int dc = Integer.parseInt(jTextFieldNDC.getText());

            model.setValueAt(intervalo, intervalo - 1, 0);
            model.setValueAt(fallas, intervalo -1 ,1);
            model.setValueAt(dc,intervalo-1,2);

            if (jButtonSalvar.isEnabled() == false) intervalo++;

            jTextFieldIntervalo.setText(String.format("%d", intervalo));
            jTextFieldNDC.setText("");
            jTextFieldNFallas.setText("");
            jButtonInsertar.transferFocus();


            if (intervalo > Integer.parseInt(jTextFieldNIntervalos.getText()))
            {
                JOptionPane.showMessageDialog(null, "Límite de observaciones alcanzado.",
                        "Aviso", JOptionPane.INFORMATION_MESSAGE);

                jButtonProcesar.setEnabled(true);
                jButtonInsertar.setEnabled(false);
                jTextFieldNFallas.setEnabled(false);
                jTextFieldNDC.setEnabled(false);
            }
        }

        catch ( NumberFormatException e )
        {         
            jTextFieldNFallas.setText("");
            jTextFieldNDC.setText("");
            JOptionPane.showMessageDialog(null, "Debe insertar valores en " +
                "todos los campos.");
            jButtonInsertar.transferFocus();
        }
}//GEN-LAST:event_botonInsertar

    private void botonProcesar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonProcesar
        String query;
        int intervalo = 1;
        int nFallas;
        int nDC;
        nObsTotales = Integer.parseInt(jTextFieldNObs.getText());
        results = new ArrayList <DatosProyecto>();

        while (intervalo <= model.getRowCount())
        {
            nFallas = ((Integer) model.getValueAt(intervalo-1, 1)).intValue();
            nDC = ((Integer) model.getValueAt(intervalo-1, 2)).intValue();
            results.add(new DatosProyecto(intervalo, nFallas, nDC, nObsTotales));
            intervalo++;
        }

        query = "DELETE FROM APCON.proyecto_sfr WHERE Id_proy_sfr = " + gestorSFR.getProyectoCargado();

        gestorSFR.insertarQuery(query);
        gestorSFR.ProcesarDatos(results);
       
        try
        {
            gestorSFR.showSFR2IU();
            dispose();
        }

        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        catch (ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        catch (FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
}//GEN-LAST:event_botonProcesar

    private void botonSalir(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalir
        model = new DefaultTableModel();
        jTableSFR.setModel(model);
        dispose();
}//GEN-LAST:event_botonSalir
   
    private void botonCargar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCargar
        try
        {          
            nObsTotales = Integer.parseInt(jTextFieldNObs.getText());           
            nIntervalos = Integer.parseInt(jTextFieldNIntervalos.getText());

            if (x == 0)
            {
                for (int i = 0; i<nIntervalos ; i++) model.addRow(new Object [] {});
                jTextFieldNObs.setEnabled(false);
                jTextFieldNIntervalos.setEnabled(false);
                jButtonCargar.setEnabled(false);
                jButtonInsertar.setEnabled(true);
                jTextFieldNFallas.setEnabled(true);
                jTextFieldNDC.setEnabled(true);
                jButtonModificar.setEnabled(true);
                jButtonCancelarI.setEnabled(false);
                jButtonModificarI.setEnabled(true);
                jTextFieldIntervalo.transferFocus();
                x=nIntervalos;
            }
            else if (nIntervalos>x)
            {
                for (int i = 0; i<nIntervalos-x; i++) model.addRow(new Object [] {});
                jTextFieldNObs.setEnabled(false);
                jTextFieldNIntervalos.setEnabled(false);
                jButtonCargar.setEnabled(false);
                jButtonInsertar.setEnabled(true);
                jTextFieldNFallas.setEnabled(true);
                jTextFieldNDC.setEnabled(true);
                jButtonModificar.setEnabled(true);
                jButtonCancelarI.setEnabled(false);
                jButtonModificarI.setEnabled(true);
                jTextFieldIntervalo.transferFocus();
                x=nIntervalos;
            }
            else
            {
                int i;
                for (i = x; i>nIntervalos; i--) model.removeRow(i-1);
                jTextFieldIntervalo.setText(Integer.toString(i+1));
                jTextFieldNObs.setEnabled(false);
                jTextFieldNIntervalos.setEnabled(false);
                jButtonCargar.setEnabled(false);
                jButtonModificar.setEnabled(true);
                jButtonCancelarI.setEnabled(false);
                jButtonModificarI.setEnabled(true);
                jTextFieldNFallas.setEnabled(false);
                jTextFieldNDC.setEnabled(false);
                jButtonInsertar.setEnabled(false);
                x=nIntervalos;
            }

            jButtonCargar.transferFocus();
        } 
        
        catch ( NumberFormatException e )
        {            
            jTextFieldNObs.setText("");
            jTextFieldNIntervalos.setText("");

            JOptionPane.showMessageDialog(null, "Debe insertar valores en " +
                    "todos los campos.");

            jButtonSalir.transferFocus();            
        }
}//GEN-LAST:event_botonCargar
  
    private void botonModificar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificar
        if (model.getValueAt(0, 0)==null)
        {
            JOptionPane.showMessageDialog(this, "Debe haber minimo una fila " +
                    "llena en la tabla.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        jButtonSalvar.setEnabled(true);
        jButtonModificar.setEnabled(false);
        jTextFieldNFallas.setEnabled(true);
        jTextFieldNDC.setEnabled(true);
        jButtonModificarI.setEnabled(false);
        jButtonProcesar.setEnabled(false);

        if (jButtonInsertar.isEnabled()==false)
            jButtonInsertar.setEnabled(true);

        control1 = Integer.parseInt(jTextFieldIntervalo.getText());

        jTextFieldNDC.setText("");
        jTextFieldNFallas.setText("");
        jButtonModificar.transferFocus();
    }//GEN-LAST:event_botonModificar

    private void botonSalvar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalvar
        if (control1 > nIntervalos)
            jButtonInsertar.setEnabled(false);

        jTextFieldIntervalo.setText(String.format("%d",control1));
        jButtonModificar.setEnabled(true);
        jButtonSalvar.setEnabled(false);
        jButtonModificarI.setEnabled(true);
        jButtonProcesar.setEnabled(true);
        jButtonSalvar.transferFocus();
    }//GEN-LAST:event_botonSalvar

    private void jTableSFRMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSFRMouseReleased
        if (jButtonSalvar.isEnabled()==true)
        {
            int y0 = 0;
            row = jTableSFR.getSelectedRow();

            if (model.getValueAt(row, 0)!=null)
                y0 = ((Integer) model.getValueAt(row, 0)).intValue();

            jTextFieldIntervalo.setText(String.format("%d",y0));
        }
        
    }//GEN-LAST:event_jTableSFRMouseReleased

    private void jTextFieldNIntervalosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNIntervalosKeyTyped
      gestorSFR.validacionCampoNum(evt);
      gestorSFR.validarTamaño(jTextFieldNIntervalos.getText(), 6, evt);
    }//GEN-LAST:event_jTextFieldNIntervalosKeyTyped

    private void jTextFieldNIntervalosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNIntervalosActionPerformed
        jTextFieldNIntervalos.transferFocus();
    }//GEN-LAST:event_jTextFieldNIntervalosActionPerformed

    private void jTextFieldNObsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNObsActionPerformed
        jTextFieldNObs.transferFocus();
    }//GEN-LAST:event_jTextFieldNObsActionPerformed

    private void jTextFieldNObsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNObsKeyTyped
        gestorSFR.validacionCampoNum(evt);
        gestorSFR.validarTamaño(jTextFieldNObs.getText(), 10, evt);
    }//GEN-LAST:event_jTextFieldNObsKeyTyped

    private void jTextFieldNFallasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNFallasKeyTyped
        gestorSFR.validacionCampoNum(evt);
        gestorSFR.validarTamaño(jTextFieldNFallas.getText(), 10, evt);
    }//GEN-LAST:event_jTextFieldNFallasKeyTyped

    private void jTextFieldNDCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNDCKeyTyped
        gestorSFR.validacionCampoNum(evt);
        gestorSFR.validarTamaño(jTextFieldNDC.getText(), 10, evt);
    }//GEN-LAST:event_jTextFieldNDCKeyTyped

    private void jTextFieldNFallasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNFallasActionPerformed
        jTextFieldNFallas.transferFocus();
    }//GEN-LAST:event_jTextFieldNFallasActionPerformed

    private void jTextFieldNDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNDCActionPerformed
        jTextFieldNDC.transferFocus();
    }//GEN-LAST:event_jTextFieldNDCActionPerformed

    private void botonModificarI(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarI
        jButtonModificarI.setEnabled(false);
        jButtonCancelarI.setEnabled(true);
        jTextFieldNObs.setEnabled(true);
        jTextFieldNIntervalos.setEnabled(true);
        nIntervalos = Integer.parseInt(jTextFieldNIntervalos.getText());
        nObsTotales = Integer.parseInt(jTextFieldNObs.getText());
        jButtonCargar.setEnabled(true);
        jButtonModificarI.transferFocus();
    }//GEN-LAST:event_botonModificarI

    private void botonCancelarI(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarI
        jTextFieldNObs.setText(Integer.toString(nObsTotales));
        jTextFieldNObs.setEnabled(false);
        jTextFieldNIntervalos.setText(Integer.toString(nIntervalos));
        jTextFieldNIntervalos.setEnabled(false);
        jButtonCancelarI.setEnabled(false);
        jButtonModificarI.setEnabled(true);
        jButtonCargar.setEnabled(false);
    }//GEN-LAST:event_botonCancelarI

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancelarI;
    private javax.swing.JButton jButtonCargar;
    private javax.swing.JButton jButtonInsertar;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonModificarI;
    private javax.swing.JButton jButtonProcesar;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JButton jButtonSalvar;
    private javax.swing.JLabel jLabelIntervalo;
    private javax.swing.JLabel jLabelNDC;
    private javax.swing.JLabel jLabelNDias;
    private javax.swing.JLabel jLabelNFallas;
    private javax.swing.JLabel jLabelNObs;
    private javax.swing.JPanel jPanelNObs;
    private javax.swing.JPanel jPanelTablaDatos;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableSFR;
    private javax.swing.JTextField jTextFieldIntervalo;
    private javax.swing.JTextField jTextFieldNDC;
    private javax.swing.JTextField jTextFieldNFallas;
    private javax.swing.JTextField jTextFieldNIntervalos;
    private javax.swing.JTextField jTextFieldNObs;
    // End of variables declaration//GEN-END:variables

}
