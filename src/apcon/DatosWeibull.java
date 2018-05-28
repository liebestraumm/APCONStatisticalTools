package apcon;

public class DatosWeibull
{
    private int intervalo;
    private double tef;
    private double fallaAc;
    private double confiabilidad;
    private double falla;
    private double tasa;
    private double densidad;
    private double mtbf;
    private double desviacion;

    public DatosWeibull()
    {

    }

    public DatosWeibull(int i, double t, double fac)
    {
        setIntervalo(i);
        setTEF(t);
        setFallaAc(fac);
    }

    public DatosWeibull(int i, double t, double fac, double r, double fall,
            double ta, double den)
    {
        setIntervalo(i);
        setTEF(t);
        setFallaAc(fac);
        setConfiabilidad(r);
        setFalla(fall);
        setTasa(ta);
        setDensidad(den);
    }

     public DatosWeibull(double r, double fall, double ta, double den, double m, double des)
    {
        setConfiabilidad(r);
        setFalla(fall);
        setTasa(ta);
        setDensidad(den);
        setMTBF(m);
        setDesviacion(des);
    }

    public void setIntervalo(int i)
    {
        intervalo = i;
    }

    public int getIntervalo()
    {
        return intervalo;
    }

    public void setTEF(double t)
    {
        tef = t;
    }

    public double getTEF()
    {
        return tef;
    }

    public void setFallaAc(double fac)
    {
        fallaAc = fac;
    }

    public double getFallaAc()
    {
        return fallaAc;
    }

    public void setConfiabilidad(double r)
    {
        confiabilidad = r;
    }

    public double getConfiabilidad()
    {
        return confiabilidad;
    }

    public void setFalla(double fall)
    {
        falla = fall;
    }

    public double getFalla()
    {
        return falla;
    }

    public void setTasa(double ta)
    {
        tasa = ta;
    }

    public double getTasa()
    {
        return tasa;
    }

    public void setDensidad(double den)
    {
        densidad = den;
    }

    public double getDensidad()
    {
        return densidad;
    }

    public void setMTBF(double m)
    {
        mtbf = m;
    }

    public double getMTBF()
    {
        return mtbf;
    }

    public void setDesviacion(double des)
    {
        desviacion = des;
    }

    public double getDesviacion()
    {
        return desviacion;
    }
}