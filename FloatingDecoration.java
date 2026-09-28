import greenfoot.*;

/**
 * FloatingDecoration - uma imagem que flutua (sobe e desce devagar).
 * Usada no título do menu e nos ícones das matérias na seleção.
 */
public class FloatingDecoration extends Decoration
{
    private int amplitude;     // quantos pixels sobe/desce
    private double speed;      // velocidade da oscilação
    private boolean floating;
    private int frame;
    private int homeX;
    private int homeY;

    public FloatingDecoration(GreenfootImage image, int amplitude, double speed)
    {
        super(image);
        this.amplitude = amplitude;
        this.speed = speed;
        this.floating = true;
        this.frame = 0;
    }

    protected void addedToWorld(World world)
    {
        homeX = getX();
        homeY = getY();
    }

    /** Liga ou desliga a flutuação (desligada, fica parada em casa). */
    public void setFloating(boolean value)
    {
        floating = value;
    }

    /** Muda a posição de casa (ex.: quando o card selecionado sobe). */
    public void setHome(int x, int y)
    {
        homeX = x;
        homeY = y;
        setLocation(x, y);
    }

    public void act()
    {
        if (floating)
        {
            frame++;
            int dy = (int) Math.round(Math.sin(frame * speed) * amplitude);
            setLocation(homeX, homeY + dy);
        }
        else
        {
            setLocation(homeX, homeY);
        }
    }
}
