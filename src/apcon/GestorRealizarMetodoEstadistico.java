package apcon;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GestorRealizarMetodoEstadistico
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
    private JFrame sfrIU;
    private JFrame compararIU;
    private JFrame weibullIU;
    private PreparedStatement selectDatos2 = null;
    private PreparedStatement selectDatos3 = null;
    private PreparedStatement selectDatos1 = null;
    private PreparedStatement selectDatos4 = null;

    /*
     El constructor.
     */
    public GestorRealizarMetodoEstadistico()
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
     El método showSFRIU(): genera la interfaz para del método de Sobrevivencia,
     Falla y Riesgo.
     */
    public void showSFRIU() throws SQLException, ClassNotFoundException, FileNotFoundException
    {
        JFrame mainFrame = APCONApp.getApplication().getMainFrame();
        sfrIU = new IURealizarAnalisisSFR();
        sfrIU.setLocationRelativeTo(mainFrame);
        APCONApp.getApplication().show(sfrIU);
     }

    /*
     El método showCompararIU(): genera la interfaz comparar ambos métodos
     estadísticos.
     */
    public void showCompararIU() throws SQLException, ClassNotFoundException, FileNotFoundException
    {
        JFrame mainFrame = APCONApp.getApplication().getMainFrame();
        compararIU = new IUCompararResultados();
        compararIU.setLocationRelativeTo(mainFrame);
        APCONApp.getApplication().show(compararIU);
     }

    /*
     El método showWeibullIU(): genera la interfaz para realizar la Distribución
     de Weibull.
     */
    public void showWeibullIU() throws SQLException, ClassNotFoundException, FileNotFoundException
    {
        JFrame mainFrame = APCONApp.getApplication().getMainFrame();
        weibullIU = new IURealizarDistWeibull();
        weibullIU.setLocationRelativeTo(mainFrame);
        APCONApp.getApplication().show(weibullIU);
    }

    /*
     El método getProyecto(): recibe un query de SQL y lo ejecuta para solicitar
     los datos pertenencientes a un proyecto en específico que se encuentren en
     la base de datos y coincidan con la condición impuesta en el query.
     Sequidamente inserta los resultados en un ArrayList de objetos tipo
     Proyecto y retorna dichos resultados.
     */
    public List <Person> getProyecto(String p) throws SQLException
    {
        List <Person> results = null;
        ResultSet resultSet1 = null;

        try
        {
            conectarDB();

            selectDatos4 =
                    connection.prepareStatement("SELECT*FROM APCON.proyecto_datos WHERE Id_proy = ?");

            selectDatos4.setString(1, p);
            resultSet1 = selectDatos4.executeQuery();
            results = new ArrayList <Person>();

            while (resultSet1.next())
            {
                results.add(new Person(
                        resultSet1.getInt("Id_pers"),
                        resultSet1.getString("Nombre_proy"),
                        resultSet1.getString("descrip"),
                        resultSet1.getString("sist"),
                        resultSet1.getString("subsis"),
                        resultSet1.getString("equipo"),
                        resultSet1.getBoolean("sfr"),
                        resultSet1.getBoolean("weibull")));
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
     El método getParametros(): recibe un query de SQL y lo ejecuta para solicitar
     los tres parámetros de la Distribución de Weibull, pertenecientes a un
     proyectos que se encuentren en la base de datos y coincidan con la condición
     impuesta en el query. Sequidamente inserta los resultados en un ArrayList
     de objetos tipo Parametros y retorna dichos resultados.
     */
    public List <Parametros> getParametros(String p) throws SQLException
    {
        List <Parametros> results = null;
        ResultSet resultSet1 = null;

        try
        {

            conectarDB();

            selectDatos2 =
                    connection.prepareStatement("SELECT conf_beta, conf_n, conf_gama FROM " +
                    "APCON.weibull_parametros WHERE Id_w_conf = ?");

            selectDatos2.setString(1, p);
            resultSet1 = selectDatos2.executeQuery();
            results = new ArrayList <Parametros>();

            while (resultSet1.next())
            {
                results.add(new Parametros(
                        resultSet1.getDouble("conf_beta"),
                        resultSet1.getDouble("conf_n"),
                        resultSet1.getDouble("conf_gama")));
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
     El método getDatosWeibull(): recibe un query de SQL y lo ejecuta para solicitar
     todos los tiempos entre fallas y fallas acumuladas, pertenecientes a un
     proyectos que se encuentren en la base de datos y coincidan con la condición
     impuesta en el query. Sequidamente inserta los resultados en un ArrayList
     de objetos tipo DatosWeibull y retorna dichos resultados.
     */
    public List <DatosWeibull> getDatosWeibull(String p) throws SQLException
    {
        List <DatosWeibull> results = null;
        ResultSet resultSet1 = null;

        try
        {
            conectarDB();

            selectDatos3 =
                    connection.prepareStatement("SELECT ob_weibull, tef, falla_ac, confiabilidad," +
                    "falla, tasa, densidad FROM APCON.proyecto_weibull WHERE Id_proy_weibull = ?");

            selectDatos3.setString(1, p);
            resultSet1 = selectDatos3.executeQuery();
            results = new ArrayList <DatosWeibull>();

            while (resultSet1.next())
            {
                results.add(new DatosWeibull(
                        resultSet1.getInt("ob_weibull"),
                        resultSet1.getDouble("tef"),
                        resultSet1.getDouble("falla_ac"),
                        resultSet1.getDouble("confiabilidad"),
                        resultSet1.getDouble("falla"),
                        resultSet1.getDouble("tasa"),
                        resultSet1.getDouble("densidad")));
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