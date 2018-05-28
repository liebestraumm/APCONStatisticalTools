package apcon;

import java.awt.Component;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Statement;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class GestorGestionarProyecto
{

    private String query1;
    private String query2;
    static String driver;
    static String url;
    static String username;
    static String password;
    private Connection connection=null;
    private Statement statement=null;
    private boolean connectedToDatabase = false;
    private Person person;
    private DefaultListModel lista;
    private List<Person> results;
    private Scanner input;
    private String config;
    private Formatter output;

    /*
     El constructor.
     */
    public GestorGestionarProyecto ()
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
      El método Crear(): se encarga de crear los proyectos nuevos, verificando 
      si que no se dupliquen cédulas o de ser deseaddo insertar el proyecto como
      parte de una cedula ya existente.
     */
    public void crear(Component parent, Person datos) throws SQLException, ClassNotFoundException
    {
        int x=1;
        String nombre = datos.getNombre();
        String apellido = datos.getApellido();
        String cedula = datos.getCedula();
        String proyecto = datos.getProyecto();
        String planta = datos.getPlanta();
        String subSis = datos.getSubSistema();
        String equip = datos.getEquipo();
        String descripcion = datos.getDescripcion();

        query1 = "INSERT INTO APCON.persona_datos " +
                    "(Cedula, Nombre, Apellido) VALUES " +
                    "('" + cedula + "','" + nombre + "','" + apellido + "')";

        query2 = "INSERT INTO APCON.proyecto_datos " +
                    "(Id_pers, Nombre_proy, descrip, equipo, sist, subsis) VALUES " +
                    "('" + cedula + "','" + proyecto + "', '" + descripcion +
                    "', '" + equip + "', '" + planta + "', '" + subSis + "')";

        results = getPersona("SELECT * FROM APCON.persona_datos INNER JOIN APCON.proyecto_datos ON " +
                "persona_datos.Cedula = proyecto_datos.Id_pers " +
                "WHERE Cedula = '" + cedula + "'");

        if (results.size()==0)
        {
            insertarQuery(query1);
            insertarQuery(query2);
        }
        else
        {
            Person p = results.get(0);

            x = JOptionPane.showConfirmDialog(parent, "Ya existe un usuario registrado" +
                    " para está cédula,\n¿Desea registrar este proyecto para el " +
                    "mismo usuario?\n\n    Nombre: " +p.getNombre()+ "\n    Apellido: "
                    +p.getApellido ()+ "\n    C.I: "+ p.getCedula()+ "\n\n",
                    "Mensage de Confirmación", JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,null);
            
            if (x==0)
            {
                  insertarQuery(query2);
            }
        }
    }

    /*
     El método salvar(): recibe un objeto de tipo Person y  ejecuta los queries
     de SQL pertnentes para guardar los cambios efectuados durante la
     modificación a los propiedades de un proyecto, tales como nombre del autor,
     descripción del proyecto, nombre del proyecto, etc.
     */
    public void salvar(Person x) throws SQLException
    {
        String querySalvar, querySalvar1;

        querySalvar = "UPDATE APCON.persona_datos SET Nombre= '" + x.getNombre() +
                "', Apellido= '" + x.getApellido() + "' WHERE Cedula= '" + x.getCedula() + "'";

        querySalvar1 = "UPDATE APCON.proyecto_datos SET Nombre_proy = '" + x.getProyecto() +
                "', descrip = '" + x.getDescripcion() + "', equipo = '" + x.getEquipo() +
                "', sist= '" + x.getPlanta() + "', subsis = '" + x.getSubSistema() +
                "' WHERE Id_proy = '" + x.getIDProyecto() + "'";

        insertarQuery(querySalvar);
        insertarQuery(querySalvar1);
    }

    /*
     El método buscar(): recibe un query de SQL para solicitar todos los proyectos
     que coincidan con la búsqueda solicitada por el usuario y retorna una lista
     con todas las coincidencias.
     */
    public DefaultListModel buscar(String query) throws SQLException, ClassNotFoundException
    {
         int xx=0;
         lista = new DefaultListModel();
         results = getPersona(query);

         while (xx < results.size())
         {
             person = results.get(xx);
             lista.addElement(person.getProyecto());
             xx++;
         }

         return lista;
    }

    /*
     El método seleccionarProyecto(): recibe el indice de la JList de la interfaz
     y retorna un objeto de la clase Person que incluira todos los datos para dicha
     entrada de la JList.
     */
    public Person seleccionarProyecto(int index)
    {
        person = results.get(index);
        return person;
    }

    /*
     El método cargar(); se encarga de guardar en un archivo de texto (.txt)la
     clave del proyecto que se va utilizar durante el uso de la aplicación.
     */
    public void cargar()
    {
        try
        {
            output = new Formatter("C:/APCON/Recursos/APCONTEMP.txt");
            output.format("%d \n", person.getIDProyecto());
            output.close();
        }

        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: "+ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     El método eliminar(): se encarga de borrar las entradas de la base de datos
     que coincidadn con el proyecto a ser eliminado elegido por el usuario,
     considerando en el proceso:

            - Sí es el único proyecto para un autor el autor se elimina junto
              con el proyecto.
            - Sí el usuario posee más de un proyecto sólo se procede a eliminar
              el proyecto seleccionado.
     */
    public void eliminar(int x) 
    {
         if (x==0)
         {
            String queryEliminar, queryEliminar1, queryEliminar2, queryEliminar3,
                    queryEliminar4;
            
            queryEliminar = "DELETE FROM APCON.proyecto_datos WHERE Id_proy = '" + person.getIDProyecto() + "'";
            queryEliminar1 = "DELETE FROM APCON.persona_datos WHERE Cedula = '" + person.getCedula() + "'";
            queryEliminar2 = "DELETE FROM APCON.proyecto_weibull WHERE Id_proy_weibull = '" + person.getIDProyecto() + "'";
            queryEliminar3 = "DELETE FROM APCON.weibull_parametros WHERE Id_w_conf = '" + person.getIDProyecto() + "'";
            queryEliminar4 = "DELETE FROM APCON.proyecto_sfr WHERE Id_proy_sfr = '" + person.getIDProyecto() + "'";
                         
            results = getPersona("SELECT*FROM APCON.persona_datos INNER JOIN APCON.proyecto_datos ON " +
                "persona_datos.Cedula = proyecto_datos.Id_pers " +
                " WHERE Id_pers = '" + person.getCedula() + "'");

            if (results.size()>1)
            {
                insertarQuery(queryEliminar4);  
                insertarQuery(queryEliminar3);
                insertarQuery(queryEliminar2);
                insertarQuery(queryEliminar);
                
                JOptionPane.showMessageDialog(null, "El proyecto \"" +
                    person.getProyecto() +  "\" ha sido eliminado.",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                insertarQuery(queryEliminar4);
                insertarQuery(queryEliminar3);
                insertarQuery(queryEliminar2);
                insertarQuery(queryEliminar);
                insertarQuery(queryEliminar1);
                
                JOptionPane.showMessageDialog(null, "El proyecto \"" +
                    person.getProyecto() +  "\" y el usuario de C.I: " + person.getCedula()
                    + " han sido eliminados.","Información", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /*
     El método validacionCampo: recibe el caracter accionado en el teclado, además
     del patrón que permite el campo donde se insertaran dichos caracteres y
     bloquea todos aquellos caracteres que no correspondan con el patrón.
     */
    public void validacionCampo(KeyEvent event, String patron)
    {
        char caracter = event.getKeyChar();
        String xxx = Character.toString(caracter);

        // Verificar si la tecla pulsada no es un digito
        if( !xxx.matches(patron) && (caracter != KeyEvent.VK_BACK_SPACE)
                && (caracter != KeyEvent.VK_SPACE))
        {
            event.consume();  // ignorar el evento de teclado
        }

        if (caracter == KeyEvent.VK_CONTROL)event.consume();
    }

    /*
     ValidarCampo se emplea para asegurarse de que el usuario inserte los datos
     en todo campo que dedba ser rellenado evitando que se inserte cadenas de
     espacios en blanco.
     */
    public int validarCampo(String cadena)
    {
        String espacios[]=cadena.split(" ");
        
        if(espacios.length>0 && !cadena.equals(""))
            return 1;
        else
            return 0;
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
     El método qetPersona(): recibe un query de SQL y lo executa para solicitar
     todas las personas y sus proyectos que se encuentren en la base de datos y
     coincidan con la condición impuesta en el query. Sequidamente inserta los
     resultados en un ArrayList de objetos tipo Person y retorna dichos resultados.
     */
    private List <Person> getPersona(String query) 
    {
        results = null;
        ResultSet resultSet1 = null;

        try
        {
            conectarDB();
            resultSet1 = statement.executeQuery(query);
            results = new ArrayList <Person>();

            while (resultSet1.next())
            {
                results.add(new Person(
                        resultSet1.getString("Cedula"),
                        resultSet1.getString("Nombre"),
                        resultSet1.getString("Apellido"),
                        resultSet1.getInt("Id_proy"),
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