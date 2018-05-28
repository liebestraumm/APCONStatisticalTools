package apcon;

public class Parametros
{
    private double forma;
    private double escala;
    private double posicion;

    public Parametros(double beta, double n, double gamma)
    {
        setForma(beta);
        setEscala(n);
        setPosicion(gamma);
    }

    public void setForma(double beta)
    {
        forma = beta;
    }

    public double getForma()
    {
        return forma;
    }

    public void setEscala(double n)
    {
        escala = n;
    }

    public double getEscala()
    {
        return escala;
    }

    public void setPosicion(double gamma)
    {
        posicion = gamma;
    }

    public double getPosicion()
    {
        return posicion;
    }
}