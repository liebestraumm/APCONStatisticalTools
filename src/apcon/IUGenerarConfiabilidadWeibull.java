/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * IUGenerarConfiabilidadWeibull.java
 *
 * Created on 05/07/2010, 10:57:52 AM
 */

package apcon;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andrés
 */
public class IUGenerarConfiabilidadWeibull extends javax.swing.JFrame {

    private double beta;
    private double escala;
    private double posicion;
    private double variable;
    private double conf;
    private double falla;
    private double f;
    private double tasa;
    private double ds;
    int y;
    private Parametros param;
    private List<DatosWeibull> results;
    private List<Parametros> results1;
    private List<LeyNumerica> results2, results3;
    private LeyNumerica ley, ley2;
    private DefaultTableModel model;
    private DatosWeibull weibull;
    private List<Proyecto>results4;
    private Proyecto proy = new Proyecto();
    private GestorGenerarConfiabilidadWeibull gestor;
    protected final NumberFormat numberFormatterObj = NumberFormat.getInstance();

    /** Creates new form IUGenerarConfiabilidadWeibull */
    public IUGenerarConfiabilidadWeibull() throws SQLException
    {
        gestor = new GestorGenerarConfiabilidadWeibull();
        setupNumberFmtObj(4);
        NumberFormat F = numberFormatterObj;
        String query;
        weibull = new DatosWeibull();
        results4 = gestor.getProyecto(gestor.getProyectoCargado());
        proy = results4.get(0);
        results = gestor.getDatosWeibull(gestor.getProyectoCargado());
        results1 = gestor.getParametros(gestor.getProyectoCargado());
        param = results1.get(0);

        if (results1.size()>0)
            for (int i=0; i<results.size(); i++)
            {
                weibull = results.get(i);
                variable = weibull.getTEF();
                posicion = param.getPosicion();
                escala = param.getEscala();
                beta = param.getForma();
                conf = gestor.calcularConfiabilidad(variable, posicion, beta, escala);
                falla = gestor.calcularFalla(conf);
                tasa = gestor.calcularTasaFalla(variable, posicion, beta, escala);
                f = gestor.calcularDensidadFalla(tasa, conf);

                query = "UPDATE APCON.proyecto_weibull SET confiabilidad='" + conf + "', falla='" +
                        falla + "', tasa='" + tasa + "', densidad='" + f + "' WHERE Id_proy_weibull='" +
                        gestor.getProyectoCargado() + "' AND ob_weibull='" + weibull.getIntervalo() + "'";
                        gestor.insertarQuery(query);
            }

        model = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return false;
            }
        };

        model.addColumn("i");
        model.addColumn("T.E.F.");
        model.addColumn("F(i)[%]");
        if (param.getPosicion()!=0)
            model.addColumn("T.E.F.-γ");
        model.addColumn("R(t)");
        model.addColumn("F(t)");
        model.addColumn("λ(t)");
        model.addColumn("f(t)");

        results = gestor.getDatosWeibull(gestor.getProyectoCargado());

        weibull=new DatosWeibull();

        if (results.size()>0)
            if(param.getPosicion()!=0)
            {
                for (int i=0; i<results.size();i++)
                {
                    weibull = results.get(i);

                    model.addRow(new Object[] {
                        weibull.getIntervalo(),
                        weibull.getTEF(),
                        F.format(weibull.getFallaAc()*100).replace(",", "."),
                        F.format(weibull.getTEF()-param.getPosicion()).replace(",", "."),
                        F.format(weibull.getConfiabilidad()).replace(",", "."),
                        F.format( weibull.getFalla()).replace(",", "."),
                        F.format( weibull.getTasa()).replace(",", "."),
                        F.format( weibull.getDensidad()).replace(",", ".")});
                }
            }
            else
            {
                for (int i=0; i<results.size();i++)
                {
                    weibull = results.get(i);

                    model.addRow(new Object[] {
                        weibull.getIntervalo(),
                        weibull.getTEF(),
                        F.format(weibull.getFallaAc()*100).replace(",", "."),
                        F.format(weibull.getConfiabilidad()).replace(",", "."),
                        F.format( weibull.getFalla()).replace(",", "."),
                        F.format( weibull.getTasa()).replace(",", "."),
                        F.format( weibull.getDensidad()).replace(",", ".")});
                }
            }

        initComponents();

        jTableWeibull.setModel(model);
        results1 = gestor.getParametros(gestor.getProyectoCargado());
        y = results1.size();

        if (y>0)
        {
            param = results1.get(0);
            jTextFieldBeta.setText(Double.toString(param.getForma()));
            jTextFieldEscala.setText(Double.toString(param.getEscala()));
            jTextFieldPosicion.setText(Double.toString(param.getPosicion()));
            jTextFieldEscala.setEditable(false);
            jTextFieldBeta.setEditable(false);
            jTextFieldPosicion.setEditable(false);
        }
        jTextFieldVariable.setFocusCycleRoot(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabelBeta = new javax.swing.JLabel();
        jLabelEscala = new javax.swing.JLabel();
        jLabelPosicion = new javax.swing.JLabel();
        jTextFieldBeta = new javax.swing.JTextField();
        jTextFieldEscala = new javax.swing.JTextField();
        jTextFieldPosicion = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabelConfiabilidad = new javax.swing.JLabel();
        jTextFieldConfiabilidad = new javax.swing.JTextField();
        jTextFieldFalla = new javax.swing.JTextField();
        jLabelFalla = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldDensidad = new javax.swing.JTextField();
        jLabelDensidad = new javax.swing.JLabel();
        jTextFieldTasa = new javax.swing.JTextField();
        jLabelTasa = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldDesviacion = new javax.swing.JTextField();
        jLabelDesviacion = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabelMTBF1 = new javax.swing.JLabel();
        jLabelMTBF2 = new javax.swing.JLabel();
        jTextFieldMTBF = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabelVariable = new javax.swing.JLabel();
        jTextFieldVariable = new javax.swing.JTextField();
        jButtonSalir = new javax.swing.JButton();
        jButtonProcesar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableWeibull = new javax.swing.JTable(model);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(apcon.APCONApp.class).getContext().getResourceMap(IUGenerarConfiabilidadWeibull.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(proy.getProyecto());
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanel1.border.titleFont"), resourceMap.getColor("jPanel1.border.titleColor"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jLabelBeta.setFont(resourceMap.getFont("jLabelBeta.font")); // NOI18N
        jLabelBeta.setText(resourceMap.getString("jLabelBeta.text")); // NOI18N
        jLabelBeta.setName("jLabelBeta"); // NOI18N

        jLabelEscala.setFont(resourceMap.getFont("jLabelBeta.font")); // NOI18N
        jLabelEscala.setText(resourceMap.getString("jLabelEscala.text")); // NOI18N
        jLabelEscala.setName("jLabelEscala"); // NOI18N

        jLabelPosicion.setFont(resourceMap.getFont("jLabelBeta.font")); // NOI18N
        jLabelPosicion.setText(resourceMap.getString("jLabelPosicion.text")); // NOI18N
        jLabelPosicion.setName("jLabelPosicion"); // NOI18N

        jTextFieldBeta.setFont(resourceMap.getFont("jTextFieldTasa.font")); // NOI18N
        jTextFieldBeta.setText(resourceMap.getString("jTextFieldBeta.text")); // NOI18N
        jTextFieldBeta.setName("jTextFieldBeta"); // NOI18N
        jTextFieldBeta.setNextFocusableComponent(jTextFieldEscala);
        jTextFieldBeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBetaActionPerformed(evt);
            }
        });
        jTextFieldBeta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldBetaKeyTyped(evt);
            }
        });

        jTextFieldEscala.setFont(resourceMap.getFont("jTextFieldTasa.font")); // NOI18N
        jTextFieldEscala.setText(resourceMap.getString("jTextFieldEscala.text")); // NOI18N
        jTextFieldEscala.setName("jTextFieldEscala"); // NOI18N
        jTextFieldEscala.setNextFocusableComponent(jTextFieldPosicion);
        jTextFieldEscala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEscalaActionPerformed(evt);
            }
        });
        jTextFieldEscala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldEscalaKeyTyped(evt);
            }
        });

        jTextFieldPosicion.setFont(resourceMap.getFont("jTextFieldTasa.font")); // NOI18N
        jTextFieldPosicion.setText(resourceMap.getString("jTextFieldPosicion.text")); // NOI18N
        jTextFieldPosicion.setName("jTextFieldPosicion"); // NOI18N
        jTextFieldPosicion.setNextFocusableComponent(jTextFieldVariable);
        jTextFieldPosicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPosicionActionPerformed(evt);
            }
        });
        jTextFieldPosicion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPosicionKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelBeta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldBeta, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelEscala)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldEscala, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPosicion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPosicion, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextFieldBeta, jTextFieldEscala});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBeta)
                    .addComponent(jTextFieldBeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEscala)
                    .addComponent(jTextFieldEscala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPosicion)
                    .addComponent(jTextFieldPosicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel2.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanel2.border.titleFont"), resourceMap.getColor("jPanel2.border.titleColor"))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabelConfiabilidad.setFont(resourceMap.getFont("jLabelBeta.font")); // NOI18N
        jLabelConfiabilidad.setText(resourceMap.getString("jLabelConfiabilidad.text")); // NOI18N
        jLabelConfiabilidad.setName("jLabelConfiabilidad"); // NOI18N

        jTextFieldConfiabilidad.setEditable(false);
        jTextFieldConfiabilidad.setFont(resourceMap.getFont("jTextFieldTasa.font")); // NOI18N
        jTextFieldConfiabilidad.setName("jTextFieldConfiabilidad"); // NOI18N

        jTextFieldFalla.setEditable(false);
        jTextFieldFalla.setFont(resourceMap.getFont("jTextFieldTasa.font")); // NOI18N
        jTextFieldFalla.setName("jTextFieldFalla"); // NOI18N

        jLabelFalla.setFont(resourceMap.getFont("jLabelBeta.font")); // NOI18N
        jLabelFalla.setText(resourceMap.getString("jLabelFalla.text")); // NOI18N
        jLabelFalla.setName("jLabelFalla"); // NOI18N

        jLabel9.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel10.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jTextFieldDensidad.setEditable(false);
        jTextFieldDensidad.setFont(resourceMap.getFont("jTextFieldTasa.font")); // NOI18N
        jTextFieldDensidad.setName("jTextFieldDensidad"); // NOI18N

        jLabelDensidad.setFont(resourceMap.getFont("jLabelBeta.font")); // NOI18N
        jLabelDensidad.setText(resourceMap.getString("jLabelDensidad.text")); // NOI18N
        jLabelDensidad.setName("jLabelDensidad"); // NOI18N

        jTextFieldTasa.setEditable(false);
        jTextFieldTasa.setFont(resourceMap.getFont("jTextFieldTasa.font")); // NOI18N
        jTextFieldTasa.setName("jTextFieldTasa"); // NOI18N

        jLabelTasa.setFont(resourceMap.getFont("jLabelBeta.font")); // NOI18N
        jLabelTasa.setText(resourceMap.getString("jLabelTasa.text")); // NOI18N
        jLabelTasa.setName("jLabelTasa"); // NOI18N

        jLabel13.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jTextFieldDesviacion.setEditable(false);
        jTextFieldDesviacion.setFont(resourceMap.getFont("jTextFieldTasa.font")); // NOI18N
        jTextFieldDesviacion.setName("jTextFieldDesviacion"); // NOI18N

        jLabelDesviacion.setFont(resourceMap.getFont("jLabelBeta.font")); // NOI18N
        jLabelDesviacion.setText(resourceMap.getString("jLabelDesviacion.text")); // NOI18N
        jLabelDesviacion.setName("jLabelDesviacion"); // NOI18N

        jLabel15.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jLabelMTBF1.setFont(resourceMap.getFont("jLabelMTBF1.font")); // NOI18N
        jLabelMTBF1.setText(resourceMap.getString("jLabelMTBF1.text")); // NOI18N
        jLabelMTBF1.setName("jLabelMTBF1"); // NOI18N

        jLabelMTBF2.setFont(resourceMap.getFont("jLabelMTBF2.font")); // NOI18N
        jLabelMTBF2.setText(resourceMap.getString("jLabelMTBF2.text")); // NOI18N
        jLabelMTBF2.setName("jLabelMTBF2"); // NOI18N

        jTextFieldMTBF.setEditable(false);
        jTextFieldMTBF.setFont(resourceMap.getFont("jTextFieldMTBF.font")); // NOI18N
        jTextFieldMTBF.setText(resourceMap.getString("jTextFieldMTBF.text")); // NOI18N
        jTextFieldMTBF.setToolTipText(resourceMap.getString("jTextFieldMTBF.toolTipText")); // NOI18N
        jTextFieldMTBF.setName("jTextFieldMTBF"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelConfiabilidad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldConfiabilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5)
                    .addComponent(jLabelMTBF1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelMTBF2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldMTBF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelDensidad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldDensidad, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelFalla)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldFalla, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelTasa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldTasa, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelDesviacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldDesviacion, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel15)
                    .addComponent(jLabel9))
                .addGap(116, 116, 116))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextFieldConfiabilidad, jTextFieldDensidad, jTextFieldDesviacion, jTextFieldFalla, jTextFieldMTBF, jTextFieldTasa});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelConfiabilidad)
                            .addComponent(jTextFieldConfiabilidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldDensidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDensidad))
                        .addGap(18, 18, 18)
                        .addComponent(jLabelMTBF1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelMTBF2)
                            .addComponent(jTextFieldMTBF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelFalla)
                            .addComponent(jTextFieldFalla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelTasa)
                            .addComponent(jTextFieldTasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelDesviacion)
                                    .addComponent(jTextFieldDesviacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel3.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanel3.border.titleFont"), resourceMap.getColor("jPanel3.border.titleColor"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        jLabelVariable.setFont(resourceMap.getFont("jLabelBeta.font")); // NOI18N
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabelVariable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldVariable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelVariable))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jButtonSalir.setFont(resourceMap.getFont("jButtonProcesar.font")); // NOI18N
        jButtonSalir.setText(resourceMap.getString("jButtonSalir.text")); // NOI18N
        jButtonSalir.setName("jButtonSalir"); // NOI18N
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalir(evt);
            }
        });

        jButtonProcesar.setFont(resourceMap.getFont("jButtonProcesar.font")); // NOI18N
        jButtonProcesar.setText(resourceMap.getString("jButtonProcesar.text")); // NOI18N
        jButtonProcesar.setName("jButtonProcesar"); // NOI18N
        jButtonProcesar.setNextFocusableComponent(jTextFieldVariable);
        jButtonProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonProcesar(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel4.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanel4.border.titleFont"), resourceMap.getColor("jPanel4.border.titleColor"))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonProcesar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSalir)
                    .addComponent(jButtonProcesar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                                Character.toString(betaString.charAt(1)) + k + 5;
                         if (k==9)
                             betamayor = Integer.toString(j+1)+".0";
                         else
                             betamayor = Character.toString(betaString.charAt(0)) +
                                Character.toString(betaString.charAt(1)) + (k+1);
                    }
                    else if(x>0 && x<5 )
                        {
                            betamenor = Character.toString(betaString.charAt(0)) +
                                    Character.toString(betaString.charAt(1))+ k;
                            betamayor = Character.toString(betaString.charAt(0)) +
                                    Character.toString(betaString.charAt(1)) + k + 5;
                        }
               try 
               {
                    betaA = Double.parseDouble(betamenor);
                    betaB = Double.parseDouble(betamayor);
                    
                    results2 = gestor.getLey(betamenor);
                    ley = results2.get(0);
                    results3 = gestor.getLey(betamayor);
                    ley2 = results3.get(0);

                    yA = gestor.interpolar(beta,betaA,betaB,ley.getA(),ley2.getA());
                    yB = gestor.interpolar(beta,betaA,betaB,ley.getB(),ley2.getB());
                    
                    MTBF = gestor.calcularMTBF(yA, posicion, escala);
                    conf = gestor.calcularConfiabilidad(variable, posicion, beta, escala);
                    falla = gestor.calcularFalla(conf);
                    tasa = gestor.calcularTasaFalla(variable, posicion, beta, escala);
                    f = gestor.calcularDensidadFalla(tasa, conf);
                    ds = gestor.calcularDesviacionEstandar(yB, escala);
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
                    
                    results2 = gestor.getLey(betamenor);
                    ley = results2.get(0);
                    results3 = gestor.getLey(betamayor);
                    ley2 = results3.get(0);

                    yA = gestor.interpolar(beta,betaA,betaB,ley.getA(),ley2.getA());
                    yB = gestor.interpolar(beta,betaA,betaB,ley.getB(),ley2.getB());
                    
                    MTBF = gestor.calcularMTBF(yA, posicion, escala);
                    conf = gestor.calcularConfiabilidad(variable, posicion, beta, escala);
                    falla = gestor.calcularFalla(conf);
                    tasa = gestor.calcularTasaFalla(variable, posicion, beta, escala);
                    f = gestor.calcularDensidadFalla(tasa, conf);
                    ds = gestor.calcularDesviacionEstandar(f, escala);
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
                    results2 = gestor.getLey(betaString);
                    ley = results2.get(0);
                    
                    MTBF = gestor.calcularMTBF(ley.getA() ,posicion, escala);
                    conf = gestor.calcularConfiabilidad(variable, posicion, beta, escala);
                    falla = gestor.calcularFalla(conf);
                    tasa = gestor.calcularTasaFalla(variable, posicion, beta, escala);
                    f = gestor.calcularDensidadFalla(tasa, conf);
                    ds = gestor.calcularDesviacionEstandar(ley.getB(), escala);
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
        jTextFieldConfiabilidad.setText(F.format(conf).replace(",", "."));
        jTextFieldFalla.setText(F.format(falla).replace(",", "."));

        setupNumberFmtObj(8);
        jTextFieldTasa.setText(F.format(tasa).replace(",", "."));
        jTextFieldDensidad.setText(F.format(f).replace(",", "."));

        setupNumberFmtObj(2);
        jTextFieldDesviacion.setText(F.format(ds).replace(",", "."));
        jTextFieldMTBF.setText(F.format(MTBF).replace(",", "."));
        jButtonProcesar.transferFocus();
    }//GEN-LAST:event_botonProcesar

    protected void setupNumberFmtObj(int k)
    {
        String patron = "0.";
        for (int j=0; j<k; j++)
            patron = patron + "#";
        if (this.numberFormatterObj instanceof DecimalFormat) {
            ((DecimalFormat) this.numberFormatterObj).applyPattern(patron);
        }
    }

    private void botonSalir(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalir
        jTextFieldBeta.setText("");
        jTextFieldEscala.setText("");
        jTextFieldPosicion.setText("");
        jTextFieldVariable.setText("");
        jTextFieldConfiabilidad.setText("");
        jTextFieldFalla.setText("");
        jTextFieldTasa.setText("");
        jTextFieldDensidad.setText("");
        jTextFieldDesviacion.setText("");
        dispose();
    }//GEN-LAST:event_botonSalir

    private void jTextFieldBetaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBetaKeyTyped
        gestor.validacionCampoNum(evt);
        gestor.validarTamaño(jTextFieldBeta.getText(),4,evt);
    }//GEN-LAST:event_jTextFieldBetaKeyTyped

    private void jTextFieldEscalaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEscalaKeyTyped
        gestor.validacionCampoNum(evt);
    }//GEN-LAST:event_jTextFieldEscalaKeyTyped

    private void jTextFieldPosicionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPosicionKeyTyped
        gestor.validacionCampoNum(evt);
    }//GEN-LAST:event_jTextFieldPosicionKeyTyped

    private void jTextFieldVariableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldVariableKeyTyped
        gestor.validacionCampoNum(evt);
        gestor.validarTamaño(jTextFieldVariable.getText(), 10, evt);
    }//GEN-LAST:event_jTextFieldVariableKeyTyped

    private void jTextFieldBetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBetaActionPerformed
        jTextFieldBeta.transferFocus();
    }//GEN-LAST:event_jTextFieldBetaActionPerformed

    private void jTextFieldEscalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEscalaActionPerformed
        jTextFieldEscala.transferFocus();
    }//GEN-LAST:event_jTextFieldEscalaActionPerformed

    private void jTextFieldPosicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPosicionActionPerformed
        jTextFieldPosicion.transferFocus();
    }//GEN-LAST:event_jTextFieldPosicionActionPerformed

    private void jTextFieldVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldVariableActionPerformed
        jTextFieldVariable.transferFocus();
    }//GEN-LAST:event_jTextFieldVariableActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonProcesar;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelBeta;
    private javax.swing.JLabel jLabelConfiabilidad;
    private javax.swing.JLabel jLabelDensidad;
    private javax.swing.JLabel jLabelDesviacion;
    private javax.swing.JLabel jLabelEscala;
    private javax.swing.JLabel jLabelFalla;
    private javax.swing.JLabel jLabelMTBF1;
    private javax.swing.JLabel jLabelMTBF2;
    private javax.swing.JLabel jLabelPosicion;
    private javax.swing.JLabel jLabelTasa;
    private javax.swing.JLabel jLabelVariable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableWeibull;
    private javax.swing.JTextField jTextFieldBeta;
    private javax.swing.JTextField jTextFieldConfiabilidad;
    private javax.swing.JTextField jTextFieldDensidad;
    private javax.swing.JTextField jTextFieldDesviacion;
    private javax.swing.JTextField jTextFieldEscala;
    private javax.swing.JTextField jTextFieldFalla;
    private javax.swing.JTextField jTextFieldMTBF;
    private javax.swing.JTextField jTextFieldPosicion;
    private javax.swing.JTextField jTextFieldTasa;
    private javax.swing.JTextField jTextFieldVariable;
    // End of variables declaration//GEN-END:variables

}
