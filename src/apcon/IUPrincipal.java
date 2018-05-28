/*
 * IUPrincipal.java
 */

package apcon;

import java.awt.Desktop;
import java.io.IOException;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.sql.SQLException;
import javax.swing.JOptionPane;


/**
 * The application's main frame.
 */
public class IUPrincipal extends FrameView {

    public IUPrincipal(SingleFrameApplication app) throws ClassNotFoundException
            , SQLException
    {
        super(app);
        initComponents();

//         status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

//         connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = APCONApp.getApplication().getMainFrame();
            aboutBox = new APCONAboutBox(mainFrame);// ESTO MANDA UN PARAMETRO DE CUALQUIER NOMBRE A UN CONSTRUCTOR INTERNO DE JAVA PARA ABRIR,
            //EN ESTE CASO, EL ABOUTBOX. CON LOS DEMAS @ACTION FUNCIONA IGUAL
            aboutBox.setLocationRelativeTo(mainFrame);

        }
        APCONApp.getApplication().show(aboutBox);
    }

    @Action
    public void showCrearIU() throws SQLException, ClassNotFoundException
    {
        JFrame mainFrame = APCONApp.getApplication().getMainFrame();
        crearIU = new IUGestionarProyecto();
        crearIU.setLocationRelativeTo(mainFrame);
        APCONApp.getApplication().show(crearIU);
//        JFrame mainFrame = new JFrame();
//        crearIU = new IUGestionarProyecto();
//        crearIU.setLocationRelativeTo(mainFrame);
//        APCONApp.getApplication().show(crearIU);
    }

    @Action
    public void showBuscadorIU() throws SQLException, SQLException, ClassNotFoundException
    {
        JFrame mainFrame = APCONApp.getApplication().getMainFrame();
        buscarIU = new IUBusqueda();
        buscarIU.setLocationRelativeTo(mainFrame);
        APCONApp.getApplication().show(buscarIU);
    }

    @Action
    public void showSelectorIU() throws SQLException
    {
        JFrame mainFrame = APCONApp.getApplication().getMainFrame();
        selectorIU = new IURealizarMetodoEstadistico();
        selectorIU.setLocationRelativeTo(mainFrame);
        APCONApp.getApplication().show(selectorIU);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu menuArchivo = new javax.swing.JMenu();
        menuItemCrear = new javax.swing.JMenuItem();
        menuItemBuscar = new javax.swing.JMenuItem();
        javax.swing.JMenuItem menuItemSalir = new javax.swing.JMenuItem();
        menuHerramientas = new javax.swing.JMenu();
        menuItemRealizarMetodo = new javax.swing.JMenuItem();
        javax.swing.JMenu menuAyuda = new javax.swing.JMenu();
        menuItemTutorial = new javax.swing.JMenuItem();
        javax.swing.JMenuItem menuItemAcerca = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jProgressBar1 = new javax.swing.JProgressBar();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(apcon.APCONApp.class).getContext().getResourceMap(IUPrincipal.class);
        mainPanel.setBackground(resourceMap.getColor("mainPanel.background")); // NOI18N
        mainPanel.setMaximumSize(new java.awt.Dimension(1280, 720));
        mainPanel.setMinimumSize(new java.awt.Dimension(711, 349));
        mainPanel.setName("mainPanel"); // NOI18N

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(resourceMap.getIcon("jLabel1.icon")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setMinimumSize(new java.awt.Dimension(711, 349));
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(711, 349));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menuBar.setMinimumSize(new java.awt.Dimension(711, 21));
        menuBar.setName("menuBar"); // NOI18N

        menuArchivo.setText(resourceMap.getString("menuArchivo.text")); // NOI18N
        menuArchivo.setToolTipText(resourceMap.getString("menuArchivo.toolTipText")); // NOI18N
        menuArchivo.setName("menuArchivo"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(apcon.APCONApp.class).getContext().getActionMap(IUPrincipal.class, this);
        menuItemCrear.setAction(actionMap.get("showCrearIU")); // NOI18N
        menuItemCrear.setFont(resourceMap.getFont("menuItemCrear.font")); // NOI18N
        menuItemCrear.setText(resourceMap.getString("menuItemCrear.text")); // NOI18N
        menuItemCrear.setToolTipText(resourceMap.getString("menuItemCrear.toolTipText")); // NOI18N
        menuItemCrear.setName("menuItemCrear"); // NOI18N
        menuArchivo.add(menuItemCrear);

        menuItemBuscar.setAction(actionMap.get("showBuscadorIU")); // NOI18N
        menuItemBuscar.setFont(resourceMap.getFont("menuitemBuscar.font")); // NOI18N
        menuItemBuscar.setText(resourceMap.getString("menuitemBuscar.text")); // NOI18N
        menuItemBuscar.setToolTipText(resourceMap.getString("menuitemBuscar.toolTipText")); // NOI18N
        menuItemBuscar.setName("menuitemBuscar"); // NOI18N
        menuArchivo.add(menuItemBuscar);

        menuItemSalir.setAction(actionMap.get("quit")); // NOI18N
        menuItemSalir.setFont(resourceMap.getFont("menuItemSalir.font")); // NOI18N
        menuItemSalir.setText(resourceMap.getString("menuItemSalir.text")); // NOI18N
        menuItemSalir.setToolTipText(resourceMap.getString("menuItemSalir.toolTipText")); // NOI18N
        menuItemSalir.setName("menuItemSalir"); // NOI18N
        menuItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalir(evt);
            }
        });
        menuArchivo.add(menuItemSalir);

        menuBar.add(menuArchivo);
        menuArchivo.getAccessibleContext().setAccessibleName(resourceMap.getString("menuArchivo.AccessibleContext.accessibleName")); // NOI18N

        menuHerramientas.setAction(actionMap.get("showSelector")); // NOI18N
        menuHerramientas.setText(resourceMap.getString("menuHerramientas.text")); // NOI18N
        menuHerramientas.setToolTipText(resourceMap.getString("menuHerramientas.toolTipText")); // NOI18N
        menuHerramientas.setName("menuHerramientas"); // NOI18N

        menuItemRealizarMetodo.setAction(actionMap.get("showSelectorIU")); // NOI18N
        menuItemRealizarMetodo.setFont(resourceMap.getFont("menuItemRealizarMetodo.font")); // NOI18N
        menuItemRealizarMetodo.setText(resourceMap.getString("menuItemRealizarMetodo.text")); // NOI18N
        menuItemRealizarMetodo.setToolTipText(resourceMap.getString("menuItemRealizarMetodo.toolTipText")); // NOI18N
        menuItemRealizarMetodo.setName("menuItemRealizarMetodo"); // NOI18N
        menuHerramientas.add(menuItemRealizarMetodo);

        menuBar.add(menuHerramientas);

        menuAyuda.setText(resourceMap.getString("menuAyuda.text")); // NOI18N
        menuAyuda.setToolTipText(resourceMap.getString("menuAyuda.toolTipText")); // NOI18N
        menuAyuda.setName("menuAyuda"); // NOI18N

        menuItemTutorial.setFont(resourceMap.getFont("menuItemTutorial.font")); // NOI18N
        menuItemTutorial.setText(resourceMap.getString("menuItemTutorial.text")); // NOI18N
        menuItemTutorial.setToolTipText(resourceMap.getString("menuItemTutorial.toolTipText")); // NOI18N
        menuItemTutorial.setName("menuItemTutorial"); // NOI18N
        menuItemTutorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirTutorial(evt);
            }
        });
        menuAyuda.add(menuItemTutorial);

        menuItemAcerca.setAction(actionMap.get("showAboutBox")); // NOI18N
        menuItemAcerca.setFont(resourceMap.getFont("menuItemAcerca.font")); // NOI18N
        menuItemAcerca.setText(resourceMap.getString("menuItemAcerca.text")); // NOI18N
        menuItemAcerca.setToolTipText(resourceMap.getString("menuItemAcerca.toolTipText")); // NOI18N
        menuItemAcerca.setName("menuItemAcerca"); // NOI18N
        menuAyuda.add(menuItemAcerca);

        menuBar.add(menuAyuda);

        statusPanel.setMinimumSize(new java.awt.Dimension(711, 25));
        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 541, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jProgressBar1.setName("jProgressBar1"); // NOI18N

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void botonSalir(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalir
        System.exit(0);
}//GEN-LAST:event_botonSalir

    /*
     El metodo abrirTutorial(): abre el docimento del manual de usuario.
     */
    public void abrirTutorial()
    {
        File manual = new File("C:/APCON/Recursos/MANUAL DE USUARIO APCON.docx");

        try
        {
            Desktop.getDesktop().open(manual);
        }

        catch (IOException ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirTutorial(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirTutorial
        abrirTutorial();
    }//GEN-LAST:event_abrirTutorial

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuHerramientas;
    private javax.swing.JMenuItem menuItemBuscar;
    private javax.swing.JMenuItem menuItemCrear;
    private javax.swing.JMenuItem menuItemRealizarMetodo;
    private javax.swing.JMenuItem menuItemTutorial;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private JFrame buscarIU;
    private JFrame crearIU;
    private JFrame selectorIU;
    private JFrame r;
}
