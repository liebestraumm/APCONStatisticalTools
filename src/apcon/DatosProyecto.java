package apcon;

public class DatosProyecto
{
    private int intervalo;
    private int fallas;
    private int datosCensurados;
    private int observaciones;

    public DatosProyecto()
    {

    }

    public DatosProyecto(int i, int f, int dc, int obs)
    {
        setIntervalo(i);
        setFallas(f);
        setDC(dc);
        setObs(obs);
    }

    public void setIntervalo(int i)
    {
        intervalo = i;
    }

    public int getIntervalo()
    {
        return intervalo;
    }

    public void setFallas(int f)
    {
        fallas = f;
    }

    public int getFallas()
    {
        return fallas;
    }

    public void setDC(int dc)
    {
        datosCensurados = dc;
    }

    public int getDC()
    {
        return datosCensurados;
    }

    public void setObs (int ob)
    {
        observaciones = ob;
    }

    public int getObs ()
    {
        return observaciones;
    }
}