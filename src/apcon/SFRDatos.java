package apcon;

public class SFRDatos
{
    private int intervalo;
    private int fallas;
    private int dc;
    private int dc2;
    private int obs;
    private int obsR;
    private double q;
    private double p;
    private double si;
    private double fmi;
    private double pmi;
    private double hi;
    private double fi;
    private double sifi;

    public SFRDatos()
    {

    }

    public SFRDatos(int i, int b, int c, int c2, int ob, int obr, double qq,
            double pp, double s, double fm, double pm, double h, double f, double sf)
    {
        setIntervalo(i);
        setFallas(b);
        setDC(c);
        setDC2(c2);
        setObs(ob);
        setObsR(obr);
        setQ(qq);
        setP(pp);
        setSi(s);
        setFmi(fm);
        setPmi(pm);
        setHi(h);
        setFi(f);
        setSiFi(sf);
    }

    public void setIntervalo(int i)
    {
        intervalo = i;
    }

    public int getIntervalo()
    {
        return intervalo;
    }
    
    public void setFallas(int fa)
    {
        fallas = fa;
    }

    public int getFallas()
    {
        return fallas;
    }

    public void setDC(int c)
    {
        dc = c;
    }

    public int getDC()
    {
        return dc;
    }

    public void setDC2(int c2)
    {
        dc2 = c2;
    }

    public int getDC2()
    {
        return dc2;
    }

    public void setObs(int ob)
    {
        obs = ob;
    }

    public int getObs()
    {
        return obs;
    }

    public void setObsR(int obr)
    {
        obsR = obr;
    }

    public int getObsR()
    {
        return obsR;
    }

    public void setQ(double qq)
    {
        q = qq;
    }

    public double getQ()
    {
        return q;
    }

    public void setP(double pp)
    {
        p = pp;
    }

    public double getP()
    {
        return p;
    }

    public void setSi(double s)
    {
        si = s;
    }

    public double getSi()
    {
        return si;
    }

    public void setFmi(double f)
    {
        fmi = f;
    }

    public double getFmi()
    {
        return fmi;
    }

    public void setPmi(double pm)
    {
        pmi = pm;
    }

    public double getPmi()
    {
        return pmi;
    }

    public void setHi(double h)
    {
        hi = h;
    }

    public double getHi()
    {
        return hi;
    }
 
    public void setFi(double f)
    {
        fi = f;
    }

    public double getFi()
    {
        return fi;
    }

    public void setSiFi(double sf)
    {
        sifi = sf;
    }

    public double getSiFi()
    {
        return sifi;
    }
}