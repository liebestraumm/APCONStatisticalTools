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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GestorRealizarDistWeibull
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
    private int j; //utilizada como contador en los métodos de Estimacion de parámetros
    private double X =0, Y=0;// Utilizadas en los métodos de estimacíon de parámetros.
    private PreparedStatement selectDatos2 = null;
    private PreparedStatement selectDatos3 = null;
    private PreparedStatement selectDatos4 = null;
    private JFrame weibullcalIU;

    /*
     El constructor.
     */
    public GestorRealizarDistWeibull()
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
     El método showWeibullCalIU(): genera la interfaz para seleccionar el método
     a realizar.
     */
    public void showWeibullCalIU() throws SQLException, ClassNotFoundException, FileNotFoundException
    {
        JFrame mainFrame = APCONApp.getApplication().getMainFrame();
        weibullcalIU = new IUGenerarConfiabilidadWeibull();
        weibullcalIU.setLocationRelativeTo(mainFrame);
        APCONApp.getApplication().show(weibullcalIU);
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
     El método ordenar(): recibe un arreglo y lo ordena de menor a mayor.
     */
    public  double[] Ordenar(double[] x)
    {
        double aux;

        for (int i = 1; i < x.length; i++)
        {
            for (int k = x.length- 1; k >= i; k--)
            {
                if(x[k] < x[k-1])
                {
                    aux = x[k];
                    x[k] = x[k-1];
                    x[k-1]= aux;
                }
            }
        }
        return x;
    }

    public double Estimar(double[] x)
    {
        double valor;
        double primero;
        double segundo;
        double tercero;
        int i=0;
        int j=0;
        int k=0;

        i = x.length;

        primero = x[((i/2)/2)-1];
        segundo = x[i-((i/2)/2)];
        
        return
    }
    /*
     El método fallaAcumulada(): recibe la cantidad de tiempo entre fallas
     considerados y calcula la falla acumulada que le correspponde a cada uno.
     */
    public double[] fallaAcumulada(int n)
    {
        double [] acumulada = new double [n];
        int inter;

        for (int i = 0; i<n; i++)
        {
            inter = i+1;
            if (n <= 20)
                acumulada[i] = (double) (inter - 0.3)/ (double)(n + 0.4);
            else if (n > 20 && n <=50)
                acumulada[i] = (double) inter/ (double)(n + 1);
            else if (n> 50)
                acumulada[i] = (double) inter/(double) n;
        }

        return acumulada;
    }

    /*
     El método CalX(): calcula la media de valores de X, necesario para la
     estimacion de parámetros de forma analítica.
     */
    public double CalX(int n)
    {
        X=0;

        for (j=1;j<=n;j++)
        {
            X = X + Math.log(Math.log((1/(1-(j/(double)(n+1))))));
        }

        return X/n;
    }

    /*
     El método CalY(): calcula la media de valores de Y, necesario para la
     estimacion de parámetros de forma analítica.
     */
    public double CalY(double []x, int n)
    {
        Y=0;

        for (j=1;j<=n;j++)
           Y = Y + Math.log(x[j-1]);

        return Y/n;
    }

    /*
     El método CalBeta(): estima el valor de Beta, necesario para la realización
     de la Distribución de Weibull.
     */
    public double CalBeta(int n, double []x)
    {
        double temp1=0,temp2=0,temp3=0,temp4=0;

        for (j=1;j<=n;j++)
        {
            temp1 = temp1 + (Math.log(x[j-1])*Math.log(Math.log(1/(1-(j/(double)(n+1))))));

            temp2 = temp2 + Math.log(x[j-1]);

            temp3 = temp3 + Math.log(Math.log(1/(1-(j/(double)(n+1)))));

            temp4 = temp4 + Math.pow(Math.log(x[j-1]), 2);
        }

        return ((n*temp1)-(temp3*temp2))/((n*temp4) - Math.pow(temp2, 2));
    }

    /*
     El método CalEscala(): estima el valor del parámetro de escala, necesario
     para la realización de la Distribución de Weibull.
     */
    public double CalEscala(double Y, double X, double beta)
    {
        return Math.exp(Y-(X/beta));
    }

    /*
     El método validacionCampoChar(): recibe el caracter accionado en el teclado, además
     del patrón que permite el campo donde se insertaran dichos caracteres y
     bloquea todos aquellos caracteres que no correspondan con el patrón.
     */
    public void validacionCampoChar(KeyEvent event, String patron)
    {
        char caracter = event.getKeyChar();
        String xxx = Character.toString(caracter);

        // Verificar si la tecla pulsada no es un digito
        if( !xxx.matches(patron) && (caracter != KeyEvent.VK_BACK_SPACE)
                && (caracter != KeyEvent.VK_SPACE))
        {
            event.consume();  // ignorar el evento de teclado
        }
    }

    /*
     validarTamaño es un método que comprueba el tamaño de los string ingresados
     y evita que se excedan de los tamaños predefinidos.
     */
    public void validarTamaño(String cadena, int tamaño, KeyEvent evt)
    {
        if (cadena.length()>= tamaño) evt.consume();
    }

    /*
     El método ProcesarDatos(): carga en la base de datos los tiempos entre
     fallas, la falla acumulada y el intrevalo al que estos pertenecen.
     */
    public void ProcesarDatos(List <DatosWeibull> results)
    {
        String query1;
        String query;
        int intervalo = 0;
        double tef = 0;
        double fallaAc = 0;
        DatosWeibull dw;

        query = "DELETE FROM APCON.proyecto_weibull WHERE Id_proy_weibull = '" +
                getProyectoCargado() + "'";

        insertarQuery(query);

        dw = results.get(0);
        intervalo = dw.getIntervalo();

        while (intervalo <= results.size())

        {
            dw = results.get(intervalo-1);
            tef = dw.getTEF();
            fallaAc = dw.getFallaAc();

            query1 = "INSERT INTO APCON.proyecto_weibull " +
                    "(Id_proy_weibull,ob_weibull, tef, falla_ac) VALUES " +
                    "('" + getProyectoCargado() + "','" + intervalo + "','" + tef +
                    "','" + fallaAc + "')";

            insertarQuery(query1);
            intervalo ++;
        }
    }

    /*
     El método CargarParametros(): recibe los parametros de la distribución de
     Weibull y los carga en la base de datos.
     */
    public void CargarParametros (double beta, double n, double gamma)
    {
        String eliminar, query;

        eliminar = "DELETE FROM APCON.weibull_parametros WHERE Id_w_conf = " + getProyectoCargado();

        query = "INSERT INTO APCON.weibull_parametros (Id_w_conf, conf_n, conf_beta, conf_gama) VALUES ('"
                + getProyectoCargado() + "','" + n + "','" + beta + "','" + gamma + "')";

        insertarQuery(eliminar);
        insertarQuery(query);

        query = "UPDATE APCON.proyecto_datos SET weibull = true WHERE Id_proy = " + getProyectoCargado();
        insertarQuery(query);
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
                    connection.prepareStatement("SELECT ob_weibull, tef, falla_ac FROM "
                    + "APCON.proyecto_weibull WHERE Id_proy_weibull = ?");

            selectDatos3.setString(1, p);
            resultSet1 = selectDatos3.executeQuery();
            results = new ArrayList <DatosWeibull>();

            while (resultSet1.next())
            {
                results.add(new DatosWeibull(
                        resultSet1.getInt("ob_weibull"),
                        resultSet1.getDouble("tef"),
                        resultSet1.getDouble("falla_ac")));
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