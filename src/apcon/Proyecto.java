package apcon;



public class Proyecto
{
    private int idProyecto;
    private String nombreProy;
    private String descripcion;
    private String planta;
    private String subSistema;
    private String equipo;


    public Proyecto()
    {

    }

    public Proyecto(int idProy, String proyect,
            String descrip, String pl, String sub, String eq )
    {
        setIDProyecto(idProy);
        setProyecto(proyect);
        setDescripcion(descrip);
        setPlanta(pl);
        setSubSistema(sub);
        setEquipo(eq);
    }

    public void setIDProyecto (int idProy)
    {
        idProyecto = idProy;
    }

    public int getIDProyecto ()
    {
        return idProyecto;
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
}