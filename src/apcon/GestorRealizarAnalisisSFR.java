package apcon;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class GestorRealizarAnalisisSFR
{
    static String driver;
    static String url;
    static String username;
    static String password;
    private Connection connection;
    private Statement statement;
    private boolean connectedToDatabase = false;
    private JFrame sfr2IU;
    private Scanner input;
    private String idProy;
    private String config;
    private DatosProyecto datosProy;
    int intervalo, nObsTotales, nObsTotalesAux, nFallasAux, nDCAux;
    double siAux = 0;
    int  control = 0;
    int nFallas;
    int nDC;
    private PreparedStatement selectDatos2 = null;
    private PreparedStatement selectDatos4 = null;

    /*
     El constructor.
     */
    public GestorRealizarAnalisisSFR() throws ClassNotFoundException, SQLException, FileNotFoundException
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
     El método getProyectoCargado(): retorna el id del proyeccto cargado después de leerlo
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
     El método ProcesarDatos(): recibe una lista con todos los valores insertados
     por el usuarios y que son necesarios para la realiazión de modelo estadístico
     no paramétrico (Sobrevivencia, Fallas, y Riesgo). Con estos valores realiza
     los cálculos pertinentes y los iserta en la tabla de la base de datos.
     */
    public void ProcesarDatos(List <DatosProyecto> results)
    {
        conectarDB();
        
        datosProy = results.get(0);
        nObsTotales = datosProy.getObs();
        intervalo = datosProy.getIntervalo();

        while (intervalo <= results.size())
        {
            datosProy = results.get(intervalo-1);
            
            nFallas = datosProy.getFallas();
            nDC = datosProy.getDC();
        
            int dc2=0;
            int nObsRiesgo;

            if ( nDC%2 == 0 )
                dc2 = (nDC / 2);
            else
                if ( nDC%2 != 0 && control == 0)
                {
                    dc2 =  (nDC / 2);
                    control = 1;
                } else
                    if ( nDC%2 != 0 && control == 1)
                    {
                        dc2 = (nDC / 2 + 1);
                        control = 0;
                    }	//Fin Sentencia if anidada

            /* if para calculo del número de elementos y el número de elementos
                    expuesto al riesgo*/
            if ( intervalo == 1 )
                nObsRiesgo = nObsTotales - dc2;
            else
            {
                nObsTotales = nObsTotalesAux - ( nFallasAux + nDCAux);
                nObsRiesgo = nObsTotales - dc2;
            }// fin sentencia if

            nObsTotalesAux = nObsTotales;
            nFallasAux = nFallas;
            nDCAux = nDC;

            double qi,pi,si,ftp,pmi,hi,fi;

            //Calclulo del estimador de propabilidad de falla
            qi = (double) (nFallas/(double)(nObsTotales-dc2));

            //Calculo de estimador de probabilidad de sobrevivencia
            pi = 1 - qi;

            //Estimador de función de sobrevivencia  en el intervalo
            if ( intervalo == 1 )
                si = pi;
            else
                si = pi * siAux;

            //Estimador de f.t.p
            if ( intervalo == 1 )
                ftp = ( 1 - si );
            else
                ftp = (siAux - si);

            siAux = si;

            //Estimador de falla en el intervalo
            if ( intervalo == 1 )
                pmi = ( 1 + si ) / 2;
            else
                pmi = (siAux + si ) / 2;

            //estimador de riesgo
            hi = ftp / pmi;

            // estimador de falla
            fi = 1 - si;

            double sifi = si + fi;

            String query1;

            query1 = "INSERT INTO APCON.proyecto_sfr " +
                    "(Id_proy_sfr, intervalo, nFallas, nDC, dC2, nObsTotales, " +
                    "nObsRiesgo, qi, pi, si, ftp, pmi, hi, fi, sifi) VALUES " +
                    "('" + idProy + "','" + intervalo + "','" + nFallas +
                    "','" + nDC + "','" + dc2 + "','" + nObsTotales + "','" + nObsRiesgo + "','"
                    + qi + "','" + pi + "','" + si + "','" + ftp + "','" + pmi +
                    "','" + hi +"','" + fi + "','" + sifi +"')";

            insertarQuery(query1);
            intervalo ++;
        }

        String query1 = "UPDATE APCON.proyecto_datos SET sfr = true WHERE Id_proy = " + getProyectoCargado();

        insertarQuery(query1);
        desconectarDB();
    }

    /*
     El método showSFR2IU(): genera la interfaz para seleccionar el método
     a realizar.
     */
    public void showSFR2IU() throws SQLException, ClassNotFoundException, FileNotFoundException
    {
        JFrame mainFrame = APCONApp.getApplication().getMainFrame();
        sfr2IU = new IUGenerarGraficaSFR();
        sfr2IU.setLocationRelativeTo(mainFrame);
        APCONApp.getApplication().show(sfr2IU);
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
     El método getDatosProyecto(): recibe un query de SQL y lo ejecuta para solicitar
     todos los datos iniciales de la metodología de SFR, pertenecientes a un
     proyectos que se encuentren en la base de datos y coincidan con la condición
     impuesta en el query. Sequidamente inserta los resultados en un ArrayList
     de objetos tipo DatosProyecto y retorna dichos resultados.
     */
    public List <DatosProyecto> getDatosProyecto(String p) throws SQLException
    {
        List <DatosProyecto> results = null;
        ResultSet resultSet1 = null;

        try
        {
            conectarDB();

            selectDatos2 =
                    connection.prepareStatement("SELECT intervalo, nFallas, nDC, nObsTotales " +
                    "FROM APCON.proyecto_sfr WHERE Id_proy_sfr = ?");

            selectDatos2.setString(1, p);
            resultSet1 = selectDatos2.executeQuery();
            results = new ArrayList <DatosProyecto>();

            while (resultSet1.next())
            {
                results.add(new DatosProyecto(
                        resultSet1.getInt("intervalo"),
                        resultSet1.getInt("nFallas"),
                        resultSet1.getInt("nDC"),
                        resultSet1.getInt("nObsTotales")));
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
     El método validacionCampoNum: recibe el caracter accionado en el teclado y
     bloquea todos aquellos caracteres que no correspondan con números.
     */
    public void validacionCampoNum(KeyEvent event) {
        char caracter = event.getKeyChar();
        String xxx = Character.toString(caracter);

        // Verificar si la tecla pulsada no es un digito
        if(!xxx.matches("[0-9]") && (caracter != KeyEvent.VK_BACK_SPACE))
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