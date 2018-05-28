package apcon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GestorGenerarGraficaSFR
{
    static String driver;
    static String url;
    static String username;
    static String password;
    private Connection connection;
    private Statement statement;
    private boolean connectedToDatabase = false;
    private Scanner input;
    private String idProy;
    private String config;
    private PreparedStatement selectDatos1 = null;
    private PreparedStatement selectDatos4 = null;
    private SFRDatos sfr;
    private XYSeries S = new XYSeries("Sobrevivencia");
    private XYSeries F = new XYSeries("Fallas");
    private XYSeries H = new XYSeries("Riesgo");
    private JFreeChart chart;

    /*
     El Constructor.
     */
    public GestorGenerarGraficaSFR() throws SQLException, ClassNotFoundException
    {
        setConfig();
    }

    /*
     El método setConfig(): coloca la configuración de la conexión a la base
     de datos
     */
    private void setConfig()
    {
        try
        {
            input = new Scanner(new File("C:/APCON/Recursos/APCONCONFIG.txt"));
            config = input.nextLine();
            driver = config;
            config = input.nextLine();
            url = config;
            config = input.nextLine();
            username = config;
            config = input.nextLine();
            password = config;
        }

        catch (FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

    /*
      conectarDB(): se encarga de generar la conexión con la Base de Datos.
    */
    private void conectarDB()
    {
        try
        {
            Class.forName(driver);

            connection = DriverManager.getConnection(url, username, password);

            statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
            connectedToDatabase = true;
        }

        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        catch (ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     El Métodod getProyectoCargado(): retorna el id del proyeccto cargado después de leerlo
     en el archivo dondede está guardado.
     */
    public String getProyectoCargado() 
    {
        try 
        {
            input = new Scanner(new File("C:/APCON/Recursos/APCONTEMP.txt"));
            idProy = input.nextLine();
            return idProy;
        }

        catch (FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }

    /*
     El método insertarQuery(): recibe un query de SQL y lo ejecuta.
     */
    public void insertarQuery(String inQuery)
    {
        try
        {
            conectarDB();
            if (!connectedToDatabase)
                throw new IllegalStateException ("Not connected to database");
            statement.executeUpdate(inQuery);
            desconectarDB();
        }

        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        finally
        {
             desconectarDB();
        }
    }

    /*
     El método procesarDaots(): genera la gráfica resultante del Análisis de
     Sobrevivencia, Falla y Riesgo.
     */
    public void procesarDatos(List <SFRDatos> results, Proyecto proy,
            String metodo)
    {

        sfr = new SFRDatos();
        int i = 0;
        int indice = results.size();
        int width=0, height=0;
        String imagen = "";

        if (metodo.equals("SFR"))
        {
            width = 799;
            height = 329;
            imagen = "SFRTemp.jpg";
        }

        if (metodo.equals("Comparar"))
        {
            width = 445;
            height = 380;
            imagen = "CompararTemp.jpg";
        }

        while ( i <= indice)
        {
            if (i!=0)sfr = results.get(i-1);
            else sfr = results.get(i);

            if (i!=0) S.add(sfr.getIntervalo(),sfr.getSi());
            else S.add(i, 1);

            if (i!=0) F.add(sfr.getIntervalo(),sfr.getFi());
            else F.add(i, 0);

            if (i!=0 ) H.add(sfr.getIntervalo()-0.5,sfr.getHi());

            i++;
        }// Fin del ciclo while

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(S);
        dataset.addSeries(F);
        dataset.addSeries(H);

        XYLineAndShapeRenderer renderer=new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesPaint(2, Color.MAGENTA);

        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        renderer.setSeriesShapesVisible(2, false);

        BasicStroke stroke = new BasicStroke (1.5f);

        renderer.setSeriesStroke(0, stroke);
        renderer.setSeriesStroke(1, stroke);
        renderer.setSeriesStroke(2, stroke);

        ValueAxis v=new NumberAxis("Intervalos");
        v.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        ValueAxis v1=new NumberAxis("Probabilidad");

        XYPlot plot = new XYPlot(dataset, v, v1, renderer);

        chart = new JFreeChart(proy.getProyecto(), plot);
        ChartFactory.createXYLineChart(proy.getProyecto(), "", "",dataset,
                PlotOrientation.VERTICAL,true,true,false);

        try
        {
            File file = new File("C:/APCON/Recursos/" + imagen);
            file.delete();
            ChartUtilities.saveChartAsJPEG(new File("C:/APCON/Recursos/" + imagen),
                    chart, width, height);
        }

        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error: "+e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     El método getSFRDatos(): recibe un query de SQL y lo ejecuta para solicitar
     todos datos del Análisis de Sobrevivencia, Falla y Riesgo, pertenecientes a un
     proyectos que se encuentren en la base de datos y coincidan con la condición
     impuesta en el query. Sequidamente inserta los resultados en un ArrayList
     de objetos tipo SFRDatos y retorna dichos resultados.
     */
    public List <SFRDatos> getSFRDatos(String p) throws SQLException
    {
        List <SFRDatos> results = null;
        ResultSet resultSet1 = null;

        try
        {
            conectarDB();
             
            selectDatos1 =
                    connection.prepareStatement("SELECT intervalo, nFallas, nDC, dC2, nObsTotales," +
                        "nObsRiesgo, qi, pi, si, ftp, pmi, hi, fi, sifi FROM APCON.proyecto_sfr " +
                        "WHERE Id_proy_sfr = ?");
            
            selectDatos1.setString(1, p);
            resultSet1 = selectDatos1.executeQuery();
            results = new ArrayList <SFRDatos>();

            while (resultSet1.next())
            {
                results.add(new SFRDatos(
                        resultSet1.getInt("intervalo"),
                        resultSet1.getInt("nFallas"),
                        resultSet1.getInt("nDC"),
                        resultSet1.getInt("dC2"),
                        resultSet1.getInt("nObsTotales"),
                        resultSet1.getInt("nObsRiesgo"),
                        resultSet1.getDouble("qi"),
                        resultSet1.getDouble("pi"),
                        resultSet1.getDouble("si"),
                        resultSet1.getDouble("ftp"),
                        resultSet1.getDouble("pmi"),
                        resultSet1.getDouble("hi"),
                        resultSet1.getDouble("fi"),
                        resultSet1.getDouble("sifi")));
            }
        }

        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: "+sqlException, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        finally
        {
            try
            {
                resultSet1.close();
            }

            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: "+sqlException, "ERROR", JOptionPane.ERROR_MESSAGE);
                desconectarDB();
            }
        }

        return results;
     }

    /*
     El método getProyecto(): recibe un query de SQL y lo ejecuta para solicitar
     los datos pertenencientes a un proyecto en específico que se encuentren en
     la base de datos y coincidan con la condición impuesta en el query.
     Sequidamente inserta los resultados en un ArrayList de objetos tipo
     Proyecto y retorna dichos resultados.
     */
    public List <Proyecto> getProyecto(String p) throws SQLException
    {
        List <Proyecto> results = null;
        ResultSet resultSet1 = null;

        try
        {
            conectarDB();
            
            selectDatos4 =
                    connection.prepareStatement("SELECT*FROM APCON.proyecto_datos WHERE Id_proy = ?");
            
            selectDatos4.setString(1, p);
            resultSet1 = selectDatos4.executeQuery();
            results = new ArrayList <Proyecto>();

            while (resultSet1.next())
            {
                results.add(new Proyecto(
                        resultSet1.getInt("Id_pers"),
                        resultSet1.getString("Nombre_proy"),
                        resultSet1.getString("descrip"),
                        resultSet1.getString("sist"),
                        resultSet1.getString("subsis"),
                        resultSet1.getString("equipo")));
            }
        }

        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: "+sqlException, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        finally
        {
            try
            {
                resultSet1.close();
            }

            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: "+sqlException, "ERROR", JOptionPane.ERROR_MESSAGE);
                desconectarDB();
            }
        }

        return results;
    }

    /*
     El método desconectarDB(): cierra la conexión con la base de datos.
     */
    public void desconectarDB()
    {
        if (connectedToDatabase)
        {
            try
            {
                statement.close();
                connection.close();
            }

            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: "+sqlException, "ERROR", JOptionPane.ERROR_MESSAGE);
            }

            finally
            {
                connectedToDatabase = false;
            }
        }
    }
}