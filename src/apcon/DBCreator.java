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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import apcon.LeyNumerica;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrés
 */
public class DBCreator {

    static String driver;
    static String url;
    static String username;
    static String password;
    
    static final String dataBase = "Create Database IF NOT EXISTS APCON";
    
    static final String tablaPersona = "Create Table IF NOT EXISTS APCON.Persona_datos( " +
            "Cedula varchar(10), Nombre varchar(10), Apellido varchar(15)," +
            "PRIMARY KEY (Cedula))";
    
    static final String tablaProyecto = "Create Table IF NOT EXISTS APCON.Proyecto_datos( " +
            "Id_proy int(5) AUTO_INCREMENT, Id_pers varchar(10), Nombre_proy varchar(30)," +
            "descrip varchar(255), equipo varchar(15), sist varchar(15), subsis varchar(15)," +
            "sfr boolean DEFAULT false, weibull boolean DEFAULT false, PRIMARY KEY (Id_proy)," +
            "KEY Id_pers (Id_pers))";
    
    static final String tablaSFR = "Create Table IF NOT EXISTS APCON.Proyecto_sfr(Id_proy_sfr int(5)," +
            "Id_sfr int(5) AUTO_INCREMENT, intervalo int(5), nDC int(5), dC2 int(5), nObsTotales int(5)," +
            "nObsRiesgo int(5), qi float, pi float, si float, ftp float, pmi float, hi float, fi float," +
            "sifi float, nFallas int(5), PRIMARY KEY (Id_sfr), KEY Id_proy_sfr (Id_proy_sfr)," +
            "CONSTRAINT proyecto_sfr_ibfk_1 FOREIGN KEY (Id_proy_sfr) REFERENCES APCON.proyecto_datos (Id_proy))";

    static final String tablaWeibull = "Create Table IF NOT EXISTS APCON.Proyecto_weibull(Id_proy_weibull int(5)," +
            "Id_weibull int(5) AUTO_INCREMENT, ob_weibull int(5), tef float, falla_ac float, confiabilidad float," +
            "falla float, tasa float, densidad float, PRIMARY KEY (Id_weibull), KEY Id_proy_weibull (Id_proy_weibull)," +
            "CONSTRAINT proyecto_weibull_ibfk_1 FOREIGN KEY (Id_proy_weibull) REFERENCES " +
            "APCON.proyecto_datos (Id_proy) ON DELETE CASCADE)";

    static final String tablaParametros = "Create Table IF NOT EXISTS APCON.Weibull_parametros(Id_w_conf int(5)," +
            "Id_conf int(5) AUTO_INCREMENT, conf_n float, conf_beta float, conf_gama float, PRIMARY KEY (Id_conf)," +
            "KEY Id_w_conf (Id_w_conf), CONSTRAINT weibull_parametros_ibfk_1 FOREIGN KEY (Id_w_conf) " +
            "REFERENCES APCON.proyecto_datos (Id_proy))";

    static final String tablaLey = "Create Table IF NOT EXISTS APCON.Ley_numerica(beta varchar(4), a float, " +
            "b float, PRIMARY KEY (beta))";

    private Connection connection;
    private Statement statement;
    private boolean connectedToDatabase = false;
    private Scanner input;
    private String config;
    
    static final String beta[] = {"0.2","0.25","0.3","0.35","0.4","0.45","0.5","0.55","0.6","0.65","0.7","0.75","0.8",
    "0.85","0.9","0.95","1.0","1.05","1.1","1.15","1.2","1.25","1.3","1.35","1.4","1.45","1.5","1.55","1.6","1.65",
    "1.7","1.75","1.8","1.85","1.9","1.95","2.0","2.1","2.2","2.3","2.4","2.5","2.6","2.7","2.8","2.9","3.0","3.1",
    "3.2","3.3","3.4","3.5","3.6","3.7","3.8","3.9","4.0","4.1","4.2","4.3","4.4","4.5","4.6","4.7","4.8","4.9","5.0",
    "5.1","5.2","5.3","5.4","5.5","5.6","5.7","5.8","5.9","6.0","6.1","6.2","6.3","6.4","6.5","6.6","6.7","6.8","6.9"};
    
    static final double a[] = {120.0,24.0,9.2605,5.0291,3.3234,2.4786,2.0,1.7024,1.5046,1.3663,1.2638,1.1906,1.133,
    1.088,1.0552,1.0234,1.0,0.9803,0.9649,0.9517,0.9407,0.9314,0.9236,0.917,0.9114,0.9067,0.9027,0.8994,0.8966,
    0.8942,0.8922,0.8906,0.8893,0.8882,0.8874,0.8867,0.8862,0.8857,0.8856,0.8859,0.8865,0.8873,0.8882,0.8893,
    0.8905,0.8917,0.893,0.8943,0.8957,0.897,0.8984,0.8997,0.9011,0.9025,0.9038,0.9051,0.9064,0.9077,0.9089,0.9102,
    0.9114,0.9126,0.9137,0.9149,0.916,0.9171,0.9182,0.9192,0.9202,0.9213,0.9222,0.9232,0.9241,0.9251,0.926,0.9269,
    0.9277,0.9286,0.9294,0.9302,0.931,0.9318,0.9325,0.9333,0.934,0.9347};

    static final double b[] = {1901.0,199.0,50.08,19.98,10.44,6.46,4.47,3.35,2.65,2.18,1.85,1.61,1.43,1.29,1.77,1.08,
    1.0,0.934,0.878,0.83,0.787,0.75,0.716,0.687,0.66,0.635,0.613,0.593,0.574,0.556,0.54,0.525,0.511,0.498,0.486,
    0.474,0.463,0.443,0.425,0.409,0.393,0.38,0.367,0.355,0.344,0.334,0.325,0.316,0.307,0.299,0.292,0.285,0.278,0.272,
    0.266,0.26,0.254,0.249,0.244,0.239,0.235,0.23,0.226,0.222,0.218,0.214,0.21,0.207,0.203,0.2,0.197,0.194,0.191,
    0.186,0.185,0.183,0.18,0.177,0.175,0.172,0.17,0.168,0.166,0.163,0.161,0.16};

    public DBCreator()
    {
        setConfig();
        crearDB();
        crearTablaPersona();
        crearTablaProyecto();
        crearTablaSFR();
        crearTablaWeibull();
        crearTablaParametros();
        crearTablaLey();
        cargarLey();
    }

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

    private void crearDB()
    {
        insertarQuery(dataBase);
    }

    private void crearTablaPersona()
    {
        insertarQuery(tablaPersona);
    }

    private void crearTablaProyecto()
    {
        insertarQuery(tablaProyecto);
    }
    
    private void crearTablaSFR()
    {
        insertarQuery(tablaSFR);
    }

    private void crearTablaWeibull()
    {
        insertarQuery(tablaWeibull);
    }

    private void crearTablaParametros()
    {
        insertarQuery(tablaParametros);
    }

    private void crearTablaLey()
    {
        insertarQuery(tablaLey);
    }
    
    private void cargarLey()
    {
        try
        {
            List<LeyNumerica> results = getLey("6.9");
            if (results.size() == 0) {
                for (int i = 0; i < 86; i++) {
                    String leyNumerica = "INSERT INTO APCON.Ley_numerica (beta, a, b) Values (" + beta[i] + "," + a[i] + "," + b[i] + ")";
                    insertarQuery(leyNumerica);
                }
            }
        } 
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
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

            PreparedStatement selectDatos4 =
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

            desconectarDB();
        }
        return results;
    }
    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
////
////        DBCreator creador = new DBCreator();
////
////        creador.setConfig();
////        creador.crearDB();
////        creador.crearTablaPersona();
////        creador.crearTablaProyecto();
////        creador.crearTablaSFR();
////        creador.crearTablaWeibull();
////        creador.crearTablaParametros();
////        creador.crearTablaLey();
////        creador.cargarLey();
//    }
}
