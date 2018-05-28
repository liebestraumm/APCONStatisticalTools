package apcon;



public class Person
{
    private int idProyecto;
    private String cedula;
    private String nombre;
    private String apellido;
    private String nombreProy;
    private String descripcion;
    private String planta;
    private String subSistema;
    private String equipo;
    private boolean sfr;
    private boolean weibull;

    public Person()
    {

    }

    public Person(int idProy, String proyect,
            String descrip, String pl, String sub, String eq, boolean s, boolean w)
    {
        setIDProyecto(idProy);
        setProyecto(proyect);
        setDescripcion(descrip);
        setPlanta(pl);
        setSubSistema(sub);
        setEquipo(eq);
        setSFR(s);
        setWeibull(w);
    }

    public Person(String ci, String name, String lastName, int idProy, String proyect,
            String descrip, String pl, String sub, String eq, boolean s, boolean w)
    {
        setIDProyecto(idProy);
        setCedula(ci);
        setNombre(name);
        setApellido(lastName);
        setProyecto(proyect);
        setDescripcion(descrip);
        setPlanta(pl);
        setSubSistema(sub);
        setEquipo(eq);
        setSFR(s);
        setWeibull(w);
    }
    
    public void setIDProyecto (int idProy)
    {
        idProyecto = idProy;
    }
    
    public int getIDProyecto ()
    {
        return idProyecto;
    }

    public void setCedula(String id)
    {
        cedula = id;
    }

    public String getCedula()
    {
        return cedula;
    }

    public void setNombre(String name)
    {
        nombre = name;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setApellido(String lastName)
    {
        apellido = lastName;
    }

    public String getApellido()
    {
        return apellido;
    }

    public void setProyecto(String proyect)
    {
        nombreProy = proyect;
    }

    public String getProyecto()
    {
        return nombreProy;
    }

    public void setDescripcion(String descrip)
    {
        descripcion = descrip;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setPlanta(String plant)
    {
        planta = plant;
    }

    public String getPlanta()
    {
        return planta;
    }

    public void setSubSistema(String subSys)
    {
        subSistema = subSys;
    }

    public String getSubSistema()
    {
        return subSistema;
    }

    public void setEquipo(String equip)
    {
        equipo = equip;
    }

    public String getEquipo()
    {
        return equipo;
    }

    public void setSFR(boolean s)
    {
        sfr = s;
    }

    public boolean getSFR()
    {
        return sfr;
    }

    public void setWeibull(boolean w)
    {
        weibull = w;
    }

    public boolean getWeibull()
    {
        return weibull;
    }
}