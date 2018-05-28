/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * IUCompararResultados.java
 *
 * Created on 15/07/2010, 09:27:53 AM
 */

package apcon;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author Andrés
 */
public class IUCompararResultados extends javax.swing.JFrame {

    private GestorGenerarConfiabilidadWeibull weibull;
    private List<Parametros> results;
    private List<DatosWeibull> results1;
    private List<SFRDatos> results2;
    private List<Proyecto> results3;
    private List<LeyNumerica> results4, results5;
    private LeyNumerica ley, ley2;
    private DatosWeibull dWeibull;
    private SFRDatos dSFR;
    private Parametros param;
    private Proyecto proy;
    private DefaultTableModel modelWeibull, modelSFR;
    private ImageIcon grafica;
    protected final NumberFormat numberFormatterObj = NumberFormat.getInstance();
    private double beta;
    private double escala;
    private double posicion;
    private double variable;
    private double conf;
    private double falla;
    private double f;
    private double tasa;
    private double ds;
    private GestorCompararResultados comparar;

    /** Creates new form IUCompararResultados */
    public IUCompararResultados() throws SQLException, ClassNotFoundException, FileNotFoundException {
        setupNumberFmtObj(4);
        NumberFormat F = numberFormatterObj;
        comparar = new GestorCompararResultados();
        dSFR = new SFRDatos();
        results3 = comparar.getProyecto(comparar.getProyectoCargado());
        proy = results3.get(0);
        results = comparar.getParametros(comparar.getProyectoCargado());
        param = results.get(0);
        results2 = comparar.getSFRDatos(comparar.getProyectoCargado());
        initComponents();

        modelSFR = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return false;
            }
        };

        modelSFR.addColumn("i");
        modelSFR.addColumn("d(i)");
        modelSFR.addColumn("c(i)");
        modelSFR.addColumn("n'(i)");
        modelSFR.addColumn("n(i)");
        modelSFR.addColumn("S(i)");
        modelSFR.addColumn("h(i)");
        modelSFR.addColumn("F(i)");

        for (int i=0; i<results2.size();i++)
            {
                dSFR = results2.get(i);

                modelSFR.addRow(new Object[] {
                    dSFR.getIntervalo(),
                    dSFR.getFallas(),
                    dSFR.getDC(),
                    dSFR.getObs(),
                    dSFR.getObsR(),
                    F.format(dSFR.getSi()).replace(",", "."),
                    F.format(dSFR.getHi()).replace(",", "."),
                    F.format(dSFR.getFi()).replace(",", ".")});

            }
        
        jTableSFR.setModel(modelSFR);

        results1 = comparar.getDatosWeibull(comparar.getProyectoCargado());
        dWeibull = new DatosWeibull();

        if (results.size()>0)
            for (int i=0; i<results1.size(); i++)
            {
                dWeibull = results1.get(i);
                int intervalo = dWeibull.getIntervalo();
                variable = dWeibull.getTEF();
                posicion = param.getPosicion();
                escala = param.getEscala();
                beta = param.getForma();

                dWeibull = comparar.obtenerCalculos(variable, posicion, beta, escala,0, 0);

                conf = dWeibull.getConfiabilidad();
                falla = dWeibull.getFalla();
                tasa = dWeibull.getTasa();
                f = dWeibull.getDensidad();
                
                String query = "UPDATE APCON.proyecto_weibull SET confiabilidad='" + conf + "', falla='" +
                        falla + "', tasa='" + tasa + "', densidad='" + f + "' WHERE Id_proy_weibull='" +
                        comparar.getProyectoCargado() + "' AND ob_weibull='" + intervalo + "'";
                
                comparar.insertarQuery(query);
            }

        modelWeibull = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return false;
            }
        };

        modelWeibull.addColumn("i");
        modelWeibull.addColumn("T.E.F.");
        modelWeibull.addColumn("F(i)[%]");
        if (param.getPosicion()!=0)
            modelWeibull.addColumn("T.E.F.-γ");
        modelWeibull.addColumn("R(t)");
        modelWeibull.addColumn("F(t)");
        modelWeibull.addColumn("λ(t)");
        modelWeibull.addColumn("f(t)");

        results1 = comparar.getDatosWeibull(comparar.getProyectoCargado());
        dWeibull = new DatosWeibull();

        if (results1.size()>0)
            if (param.getPosicion()!=0)
            {
                for (int i=0;i<results1.size();i++)
                {
                    dWeibull = results1.get(i);
                    modelWeibull.addRow(new Object[] {
                        dWeibull.getIntervalo(),
                        dWeibull.getTEF(),
                        F.format(dWeibull.getFallaAc()*100).replace(",", "."),
                        F.format(dWeibull.getTEF()-param.getPosicion()).replace(",", "."),
                        F.format(dWeibull.getConfiabilidad()).replace(",", "."),
                        F.format(dWeibull.getFalla()).replace(",", "."),
                        F.format(dWeibull.getTasa()).replace(",", "."),
                        F.format(dWeibull.getDensidad()).replace(",", ".")});
                }
            }
            else
            {
            for (int i=0;i<results1.size();i++)
                {
                    dWeibull = results1.get(i);
                    modelWeibull.addRow(new Object[] {
                        dWeibull.getIntervalo(),
                        dWeibull.getTEF(),
                        F.format(dWeibull.getFallaAc()*100).replace(",", "."),
                        F.format(dWeibull.getConfiabilidad()).replace(",", "."),
                        F.format(dWeibull.getFalla()).replace(",", "."),
                        F.format(dWeibull.getTasa()).replace(",", "."),
                        F.format(dWeibull.getDensidad()).replace(",", ".")});
                }
            }
        
        jTableWeibull.setModel(modelWeibull);
        jTextFieldBeta.setText(Double.toString(param.getForma()));
        jTextFieldEscala.setText(Double.toString(param.getEscala()));
        jTextFieldPosicion.setText(Double.toString(param.getPosicion()));
        jTextFieldEscala.setEditable(false);
        jTextFieldBeta.setEditable(false);
        jTextFieldPosicion.setEditable(false);
        jTextFieldVariable.setFocusCycleRoot(true);
        comparar.generarGrafica(results2, proy);
        grafica = new ImageIcon("C:/APCON/Recursos/CompararTemp.jpg");
        jLabelGrafica.setText("");
        jLabelGrafica.setIcon(grafica);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelBeta = new javax.swing.JLabel();
        jTextFieldBeta = new javax.swing.JTextField();
        jLabelEscala = new javax.swing.JLabel();
        jTextFieldEscala = new javax.swing.JTextField();
        jLabelPosicion = new javax.swing.JLabel();
        jLabelConfiabilidad = new javax.swing.JLabel();
        jTextFieldConfiabilidad = new javax.swing.JTextField();
        jLabelFalla = new javax.swing.JLabel();
        jTextFieldFalla = new javax.swing.JTextField();
        jLabelDensidad = new javax.swing.JLabel();
        jTextFieldDensidad = new javax.swing.JTextField();
        jLabelTasa = new javax.swing.JLabel();
        jTextFieldTasa = new javax.swing.JTextField();
        jLabelMTBF = new javax.swing.JLabel();
        jTextFieldMTBF = new javax.swing.JTextField();
        jLabelDS = new javax.swing.JLabel();
        jTextFieldDS = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableWeibull = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabelVariable = new javax.swing.JLabel();
        jTextFieldVariable = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jTextFieldPosicion = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabelGrafica = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableSFR = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jButtonSalir = new javax.swing.JButton();
        jButtonProcesar = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(apcon.APCONApp.class).getContext().getResourceMap(IUCompararResultados.class);
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(proy.getProyecto());
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanel1.border.titleFont"), resourceMap.getColor("jPanel1.border.titleColor"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jLabelBeta.setFont(resourceMap.getFont("jLabelBeta.font")); // NOI18N
        jLabelBeta.setText(resourceMap.getString("jLabelBeta.text")); // NOI18N
        jLabelBeta.setName("jLabelBeta"); // NOI18N

        jTextFieldBeta.setFont(resourceMap.getFont("jTextFieldBeta.font")); // NOI18N
        jTextFieldBeta.setText(resourceMap.getString("jTextFieldBeta.text")); // NOI18N
        jTextFieldBeta.setName("jTextFieldBeta"); // NOI18N

        jLabelEscala.setFont(resourceMap.getFont("jLabelEscala.font")); // NOI18N
        jLabelEscala.setText(resourceMap.getString("jLabelEscala.text")); // NOI18N
        jLabelEscala.setName("jLabelEscala"); // NOI18N

        jTextFieldEscala.setFont(resourceMap.getFont("jTextFieldEscala.font")); // NOI18N
        jTextFieldEscala.setText(resourceMap.getString("jTextFieldEscala.text")); // NOI18N
        jTextFieldEscala.setName("jTextFieldEscala"); // NOI18N

        jLabelPosicion.setFont(resourceMap.getFont("jLabelPosicion.font")); // NOI18N
        jLabelPosicion.setText(resourceMap.getString("jLabelPosicion.text")); // NOI18N
        jLabelPosicion.setName("jLabelPosicion"); // NOI18N

        jLabelConfiabilidad.setFont(resourceMap.getFont("jLabelConfiabilidad.font")); // NOI18N
        jLabelConfiabilidad.setText(resourceMap.getString("jLabelConfiabilidad.text")); // NOI18N
        jLabelConfiabilidad.setName("jLabelConfiabilidad"); // NOI18N

        jTextFieldConfiabilidad.setEditable(false);
        jTextFieldConfiabilidad.setFont(resourceMap.getFont("jTextFieldTasa.font")); // NOI18N
        jTextFieldConfiabilidad.setText(resourceMap.getString("jTextFieldConfiabilidad.text")); // NOI18N
        jTextFieldConfiabilidad.setName("jTextFieldConfiabilidad"); // NOI18N

        jLabelFalla.setFont(resourceMap.getFont("jLabelFalla.font")); // NOI18N
        jLabelFalla.setText(resourceMap.getString("jLabelFalla.text")); // NOI18N
        jLabelFalla.setName("jLabelFalla"); // NOI18N

        jTextFieldFalla.setEditable(false);
        jTextFieldFalla.setFont(resourceMap.getFont("jTextFieldTasa.font")); // NOI18N
        jTextFieldFalla.setText(resourceMap.getString("jTextFieldFalla.text")); // NOI18N
        jTextFieldFalla.setName("jTextFieldFalla"); // NOI18N

        jLabelDensidad.setFont(resourceMap.getFont("jLabelDensidad.font")); // NOI18N
        jLabelDensidad.setText(resourceMap.getString("jLabelDensidad.text")); // NOI18N
        jLabelDensidad.setName("jLabelDensidad"); // NOI18N

        jTextFieldDensidad.setEditable(false);
        jTextFieldDensidad.setFont(resourceMap.getFont("jTextFieldTasa.font")); // NOI18N
        jTextFieldDensidad.setText(resourceMap.getString("jTextFieldDensidad.text")); // NOI18N
        jTextFieldDensidad.setName("jTextFieldDensidad"); // NOI18N

        jLabelTasa.setFont(resourceMap.getFont("jLabelTasa.font")); // NOI18N
        jLabelTasa.setText(resourceMap.getString("jLabelTasa.text")); // NOI18N
        jLabelTasa.setName("jLabelTasa"); // NOI18N

        jTextFieldTasa.setEditable(false);
        jTextFieldTasa.setFont(resourceMap.getFont("jTextFieldTasa.font")); // NOI18N
        jTextFieldTasa.setText(resourceMap.getString("jTextFieldTasa.text")); // NOI18N
        jTextFieldTasa.setName("jTextFieldTasa"); // NOI18N

        jLabelMTBF.setFont(resourceMap.getFont("jLabelMTBF.font")); // NOI18N
        jLabelMTBF.setText(resourceMap.getString("jLabelMTBF.text")); // NOI18N
        jLabelMTBF.setName("jLabelMTBF"); // NOI18N

        jTextFieldMTBF.setEditable(false);
        jTextFieldMTBF.setFont(resourceMap.getFont("jTextFieldTasa.font")); // NOI18N
        jTextFieldMTBF.setText(resourceMap.getString("jTextFieldMTBF.text")); // NOI18N
        jTextFieldMTBF.setName("jTextFieldMTBF"); // NOI18N

        jLabelDS.setFont(resourceMap.getFont("jLabelDS.font")); // NOI18N
        jLabelDS.setText(resourceMap.getString("jLabelDS.text")); // NOI18N
        jLabelDS.setName("jLabelDS"); // NOI18N

        jTextFieldDS.setEditable(false);
        jTextFieldDS.setFont(resourceMap.getFont("jTextFieldTasa.font")); // NOI18N
        jTextFieldDS.setText(resourceMap.getString("jTextFieldDS.text")); // NOI18N
        jTextFieldDS.setName("jTextFieldDS"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTableWeibull.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTableWeibull.setName("jTableWeibull"); // NOI18N
        jTableWeibull.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTableWeibull);

        jLabel11.setFont(resourceMap.getFont("jLabel11.font")); // NOI18N
        jLabel11.setForeground(resourceMap.getColor("jLabel11.foreground")); // NOI18N
        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel13.setFont(resourceMap.getFont("jLabel13.font")); // NOI18N
        jLabel13.setForeground(resourceMap.getColor("jLabel13.foreground")); // NOI18N
        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel14.setFont(resourceMap.getFont("jLabel14.font")); // NOI18N
        jLabel14.setForeground(resourceMap.getColor("jLabel14.foreground")); // NOI18N
        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jLabelVariable.setFont(resourceMap.getFont("jLabelVariable.font")); // NOI18N
        jLabelVariable.setText(resourceMap.getString("jLabelVariable.text")); // NOI18N
        jLabelVariable.setName("jLabelVariable"); // NOI18N

        jTextFieldVariable.setFont(resourceMap.getFont("jTextFieldTasa.font")); // NOI18N
        jTextFieldVariable.setText(resourceMap.getString("jTextFieldVariable.text")); // NOI18N
        jTextFieldVariable.setName("jTextFieldVariable"); // NOI18N
        jTextFieldVariable.setNextFocusableComponent(jButtonProcesar);
        jTextFieldVariable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldVariableActionPerformed(evt);
            }
        });
        jTextFieldVariable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldVariableKeyTyped(evt);
            }
        });

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setForeground(resourceMap.getColor("jLabel1.foreground")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        jSeparator2.setName("jSeparator2"); // NOI18N

        jSeparator3.setName("jSeparator3"); // NOI18N

        jTextFieldPosicion.setFont(resourceMap.getFont("jTextFieldPosicion.font")); // NOI18N
        jTextFieldPosicion.setText(resourceMap.getString("jTextFieldPosicion.text")); // NOI18N
        jTextFieldPosicion.setName("jTextFieldPosicion"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelVariable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldVariable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(361, Short.MAX_VALUE))
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelConfiabilidad)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldConfiabilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelMTBF)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldMTBF, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelTasa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldTasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelDS)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldDS, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelFalla)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldFalla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelDensidad)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldDensidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(199, 199, 199))
            .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jLabel11)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelBeta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldBeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                        .addComponent(jLabelEscala)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldEscala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jLabelPosicion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPosicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextFieldBeta, jTextFieldConfiabilidad, jTextFieldDensidad, jTextFieldEscala, jTextFieldFalla, jTextFieldPosicion, jTextFieldTasa, jTextFieldVariable});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextFieldDS, jTextFieldMTBF});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelBeta)
                        .addComponent(jTextFieldBeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelEscala)
                        .addComponent(jTextFieldEscala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPosicion)
                    .addComponent(jTextFieldPosicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelConfiabilidad)
                            .addComponent(jTextFieldConfiabilidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelMTBF)
                            .addComponent(jTextFieldMTBF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelFalla)
                            .addComponent(jTextFieldFalla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelDensidad)
                            .addComponent(jTextFieldDensidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTasa)
                            .addComponent(jTextFieldTasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelDS)
                            .addComponent(jTextFieldDS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldVariable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelVariable))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel2.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanel2.border.titleFont"), resourceMap.getColor("jPanel2.border.titleColor"))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        jLabelGrafica.setFont(resourceMap.getFont("jLabelGrafica.font")); // NOI18N
        jLabelGrafica.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGrafica.setText(resourceMap.getString("jLabelGrafica.text")); // NOI18N
        jLabelGrafica.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jLabelGrafica.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jLabelGrafica.border.titleFont"), resourceMap.getColor("jLabelGrafica.border.titleColor"))); // NOI18N
        jLabelGrafica.setName("jLabelGrafica"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTableSFR.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTableSFR.setName("jTableSFR"); // NOI18N
        jTableSFR.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jTableSFR);

        jLabel12.setFont(resourceMap.getFont("jLabel12.font")); // NOI18N
        jLabel12.setForeground(resourceMap.getColor("jLabel12.foreground")); // NOI18N
        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelGrafica, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonSalir.setFont(resourceMap.getFont("jButtonSalir.font")); // NOI18N
        jButtonSalir.setText(resourceMap.getString("jButtonSalir.text")); // NOI18N
        jButtonSalir.setName("jButtonSalir"); // NOI18N
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalir(evt);
            }
        });

        jButtonProcesar.setFont(resourceMap.getFont("jButton2.font")); // NOI18N
        jButtonProcesar.setText(resourceMap.getString("jButtonProcesar.text")); // NOI18N
        jButtonProcesar.setName("jButtonProcesar"); // NOI18N
        jButtonProcesar.setNextFocusableComponent(jTextFieldVariable);
        jButtonProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonProcesar(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(297, 297, 297)
                        .addComponent(jButtonProcesar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonProcesar, jButtonSalir});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonSalir)
                            .addComponent(jButtonProcesar))
                        .addGap(15, 15, 15))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonSalir(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalir
        jLabelGrafica.setIcon(null);
        grafica = new ImageIcon();
        dispose();
    }//GEN-LAST:event_botonSalir

    private void botonProcesar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonProcesar
        String betaString, string3, string2, string1;
        String betamenor = null;
        String betamayor = null;
        char caracter1,caracter2, caracter3;
        double betaA, betaB;
        double yA = 0;
        double yB = 0;
        int j =0, k=0, x=0;
        double MTBF = 0;
        DatosWeibull wei = new DatosWeibull();

        try
        {
            beta = Double.parseDouble(jTextFieldBeta.getText());

            if (beta>6.9 || beta<0.2)
            {
                JOptionPane.showMessageDialog(this, "Forma(β) debe ser un valor" +
                        " entre 0.2 y 6.9", "Error.", JOptionPane.WARNING_MESSAGE);
                return;
            }

            escala = Double.parseDouble(jTextFieldEscala.getText());
            posicion = Double.parseDouble(jTextFieldPosicion.getText());
            variable = Double.parseDouble(jTextFieldVariable.getText());
            betaString = Double.toString(beta);

            if (betaString.length()>3)
            {
                caracter1 = betaString.charAt(0);
                string1 = Character.toString(caracter1);
                caracter2 = betaString.charAt(2);
                string2 = Character.toString(caracter2);
                caracter3 = betaString.charAt(3);
                string3 = Character.toString(caracter3);
                j = Integer.parseInt(string1);
                k = Integer.parseInt(string2);
                x = Integer.parseInt(string3);
            }

            if (betaString.length()>3 && x!=5 && x!=0 && beta <2.0)
            {
                if (x>5 && x<=9)
                    {
                         betamenor = Character.toString(betaString.charAt(0)) +
                                Character.toString(betaString.charAt(1))+ k + 5;
                         if (k==9)
                             betamayor = Integer.toString(j+1)+".0";
                         else
                             betamayor = Character.toString(betaString.charAt(0)) +
                                Character.toString(betaString.charAt(1)) + (k+1);
                    }
                    else if(x>0 && x<5 )
                        {
                            betamenor = Character.toString(betaString.charAt(0)) +
                                    Character.toString(betaString.charAt(1)) + k;
                            betamayor = Character.toString(betaString.charAt(0)) +
                                    Character.toString(betaString.charAt(1))+ k + 5;
                        }
               try
               {
                    betaA = Double.parseDouble(betamenor);
                    betaB = Double.parseDouble(betamayor);

                    results4 = comparar.getLey(betamenor);
                    ley = results4.get(0);
                    results5 = comparar.getLey(betamayor);
                    ley2 = results5.get(0);

                    yA = comparar.interpolar(beta,betaA,betaB,ley.getA(),ley2.getA());
                    yB = comparar.interpolar(beta,betaA,betaB,ley.getB(),ley2.getB());

                    wei = comparar.obtenerCalculos(variable, posicion, beta, escala, yA, yB);
                    MTBF = wei.getMTBF();
                    conf = wei.getConfiabilidad();
                    falla = wei.getFalla();
                    tasa = wei.getTasa();
                    f = wei.getDensidad();
                    ds = wei.getDesviacion();
               }

               catch (SQLException ex)
               {
                    JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
               }
            }
            else if (betaString.length()>3 && beta >2.0)
            {
                betamenor = Character.toString(betaString.charAt(0)) +
                        Character.toString(betaString.charAt(1)) + k;

                if (k==9)
                    betamayor = Integer.toString(j+1) + ".0";
                else
                    betamayor = Character.toString(betaString.charAt(0)) +
                        Character.toString(betaString.charAt(1)) + (k+1);

              try
              {
                    betaA = Double.parseDouble(betamenor);
                    betaB = Double.parseDouble(betamayor);

                    results4 = comparar.getLey(betamenor);
                    ley = results4.get(0);
                    results5 = comparar.getLey(betamayor);
                    ley2 = results5.get(0);

                    yA = comparar.interpolar(beta,betaA,betaB,ley.getA(),ley2.getA());
                    yB = comparar.interpolar(beta,betaA,betaB,ley.getB(),ley2.getB());

                    wei = comparar.obtenerCalculos(variable, posicion, beta, escala, yA, yB);
                    MTBF = wei.getMTBF();
                    conf = wei.getConfiabilidad();
                    falla = wei.getFalla();
                    tasa = wei.getTasa();
                    f = wei.getDensidad();
                    ds = wei.getDesviacion();
                }

                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                try
                {
                    results4 = comparar.getLey(betaString);
                    ley = results4.get(0);

                    wei = comparar.obtenerCalculos(variable, posicion, beta, escala, ley.getA(), ley.getB());
                    MTBF = wei.getMTBF();
                    conf = wei.getConfiabilidad();
                    falla = wei.getFalla();
                    tasa = wei.getTasa();
                    f = wei.getDensidad();
                    ds = wei.getDesviacion();
                }

                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        catch (NumberFormatException numberFormatException)
        {
            JOptionPane.showMessageDialog(this, "Debe insertar valores para cada" +
                    " parámetro y la variable.", "Error.", JOptionPane.WARNING_MESSAGE);
            return;
        }

        NumberFormat F = numberFormatterObj;

        setupNumberFmtObj(4);
        jTextFieldConfiabilidad.setText(F.format(conf).replace(",","."));
        jTextFieldFalla.setText(F.format(falla).replace(",","."));

        setupNumberFmtObj(8);
        jTextFieldTasa.setText(F.format(tasa).replace(",","."));
        jTextFieldDensidad.setText(F.format(f).replace(",","."));

        setupNumberFmtObj(2);
        jTextFieldDS.setText(F.format(ds).replace(",","."));
        jTextFieldMTBF.setText(F.format(MTBF).replace(",","."));
        jButtonProcesar.transferFocus();

    }//GEN-LAST:event_botonProcesar

    private void jTextFieldVariableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldVariableKeyTyped
        comparar.validacionCampoNum(evt);
        comparar.validarTamaño(jTextFieldVariable.getText(), 10, evt);
    }//GEN-LAST:event_jTextFieldVariableKeyTyped

    private void jTextFieldVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldVariableActionPerformed
        jTextFieldVariable.transferFocus();
    }//GEN-LAST:event_jTextFieldVariableActionPerformed

    protected void setupNumberFmtObj(int k)
    {
        String patron = "0.";
        for (int j=0; j<k; j++)
            patron = patron + "#";
        if (this.numberFormatterObj instanceof DecimalFormat) {
            ((DecimalFormat) this.numberFormatterObj).applyPattern(patron);
        }
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonProcesar;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabelBeta;
    private javax.swing.JLabel jLabelConfiabilidad;
    private javax.swing.JLabel jLabelDS;
    private javax.swing.JLabel jLabelDensidad;
    private javax.swing.JLabel jLabelEscala;
    private javax.swing.JLabel jLabelFalla;
    private javax.swing.JLabel jLabelGrafica;
    private javax.swing.JLabel jLabelMTBF;
    private javax.swing.JLabel jLabelPosicion;
    private javax.swing.JLabel jLabelTasa;
    private javax.swing.JLabel jLabelVariable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTableSFR;
    private javax.swing.JTable jTableWeibull;
    private javax.swing.JTextField jTextFieldBeta;
    private javax.swing.JTextField jTextFieldConfiabilidad;
    private javax.swing.JTextField jTextFieldDS;
    private javax.swing.JTextField jTextFieldDensidad;
    private javax.swing.JTextField jTextFieldEscala;
    private javax.swing.JTextField jTextFieldFalla;
    private javax.swing.JTextField jTextFieldMTBF;
    private javax.swing.JTextField jTextFieldPosicion;
    private javax.swing.JTextField jTextFieldTasa;
    private javax.swing.JTextField jTextFieldVariable;
    // End of variables declaration//GEN-END:variables
}