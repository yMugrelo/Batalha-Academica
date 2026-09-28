import greenfoot.*;

/**
 * EnergyBar - barra de energia (sempre azul).
 */
public class EnergyBar extends StatBar
{
    public EnergyBar(int barWidth)
    {
        super("EN", barWidth);
    }

    protected Color fillColor(double ratio)
    {
        return Palette.ENERGY;
    }
}
