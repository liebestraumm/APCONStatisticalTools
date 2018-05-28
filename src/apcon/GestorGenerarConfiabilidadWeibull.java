package apcon;


import java.awt.event.KeyEvent;
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
import javax.swing.JOptionPane;

public class GestorGenerarConfiabilidadWeibull
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
    private PreparedStatement selectDatos2 = null;
    private PreparedStatement selectDatos3 = null;
    private PreparedStatement selectDatos4 = null;
    private PreparedStatement selectDatos5 = null;

    /*
     El constructor
     */
    public GestorGenerarConfiabilidadWeibull()
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
     El método calcularConfiabilidad(): retorna la probabilidad de confiabilidad
     para unos tiempos y parámetros dados.
     */
    public double calcularConfiabilidad(double variable, double posicion, double beta, double escala)
    {
        return Math.exp(-Math.pow((variable - posicion)/escala, beta));
    }

    /*
     El método calcularFalla(): retorna la probabilidad de falla para una
     probabilidad de confiabilidad dada.
     */
    public double calcularFalla(double conf)
    {
        return 1-conf;
    }

    /*
     El método calcularTasaFalla(): retorna el valor de la tasa de falla para
     un tiempo y parámetros dados.
    */
    public double calcularTasaFalla(double variable, double posicion, double beta, double escala)
    {
        return (beta/escala)*Math.pow(((variable - posicion)/escala), beta-1);
    }

    /*
     El método calcularDensidadFalla(): retorna el valor de la densidad de falla
     para uan tasa y una confiabilidad dada.
    */
    public double calcularDensidadFalla(double tasa, double conf)
    {
        return tasa*conf;
    }

    /*
     El método calcularDesviacionEstandar(): retorna el valor de la desviación
     estandar para los parámetros dados.
    */
    public double calcularDesviacionEstandar(double b, double escala)
    {
        return b*escala;
    }

    /*
     El método calcularMTBF(): retorna el valor de la media de tiempo entre fallas
     para para los parámetros dados.
    */
    public double calcularMTBF(double a, double posicion, double escala)
    {
        return (a*escala) + posicion;
    }

    /*
     El método interpolar(): realiza la interpolación con los parametros dados y
     retorna el valor resultante.
    */
    public double interpolar(double interX, double interXA, double interXB, double interYA, double interYB)
    {
        double interY;

        interY = interYA + ((interX-interXA)*((interYB-interYA)/(interXB-interXA)));

        return interY;
    }

    /*
     El Métodod getProyecto(): retorna el id del proyeccto cargado después de leerlo
     en el archivo denode es guardado.
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
     El método validacionCampoNum: recibe el caracter accionado en el teclado
     bloquea todos aquellos caracteres que no correspondan con el patrón numérico.
     */
    public void validacionCampoNum(KeyEvent event) {
        char caracter = event.getKeyChar();
        String xxx = Character.toString(caracter);

        // Verificar si la tecla pulsada no es un digito
        if(!xxx.matches("[0-9.]") && (caracter != KeyEvent.VK_BACK_SPACE))
        {
            event.consume();  // ignorar el evento de teclado
        }
    }

    /*
     El método validarTamaño(): verifica que una cadena no se exceda de un tamaño establecido.
     */
    public void validarTamaño(String cadena, int tamaño, KeyEvent evt)
    {
        if (cadena.length()>= tamaño) evt.consume();
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
     El método getLey(): recibe un query de SQL y lo ejecuta para solicitar
     los datos necesarios para realizar la interpolación necesariapara los cálculos
     de la media de tiempos entre falla y la desviación estandar. Sequidamente
     inserta los resultados en un ArrayList de objetos tipo Parametros y retorna
     dichos resultados.
     */
    public List <LeyNumerica> getLey(String p) throws SQLException
    {
        List <LeyNumerica> results = null;
        ResultSet resultSet1 = null;

        try
        {
            conectarDB();

            selectDatos4 =
                    connection.prepareStatement("SELECT a, b FROM APCON.ley_numerica WHERE beta = ?");

            selectDatos4.setString(1, p);
            resultSet1 = selectDatos4.executeQuery();
            results = new ArrayList <LeyNumerica>();

            while (resultSet1.next())
            {
                results.add(new LeyNumerica(
                        resultSet1.getDouble("a"),
                        resultSet1.getDouble("b")));
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

            selectDatos5 =
                    connection.prepareStatement("SELECT*FROM APCON.proyecto_datos WHERE Id_proy = ?");

            selectDatos5.setString(1, p);
            resultSet1 = selectDatos5.executeQuery();
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