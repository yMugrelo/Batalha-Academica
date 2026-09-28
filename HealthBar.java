import greenfoot.*;

/**
 * HealthBar - barra de vida. Muda de cor conforme a vida cai:
 * verde (mais de 50%), amarela (25% a 50%) e vermelha (menos de 25%).
 */
public class HealthBar extends StatBar
{
    public HealthBar(int barWidth)
    {
        super("HP", barWidth);
    }

    protected Color fillColor(double ratio)
    {
        if (ratio > 0.5)
        {
            return Palette.HP_HIGH;
        }
        if (ratio > 0.25)
        {
            return Palette.HP_MID;
        }
        return Palette.HP_LOW;
    }
}
