import greenfoot.*;

/**
 * ScreenOverlay - uma camada colorida do tamanho da tela.
 *
 * Dois usos:
 *   - flash: começa forte e some (impacto de acerto crítico)
 *   - dim:   começa invisível e escurece até um limite (derrota)
 */
public class ScreenOverlay extends Actor
{
    private int startAlpha;
    private int endAlpha;
    private int frames;
    private int age;
    private boolean removeAtEnd;

    private ScreenOverlay(int width, int height, Color color, int startAlpha, int endAlpha,
                          int frames, boolean removeAtEnd)
    {
        GreenfootImage img = new GreenfootImage(width, height);
        img.setColor(color);
        img.fillRect(0, 0, width, height);
        setImage(img);

        this.startAlpha = startAlpha;
        this.endAlpha = endAlpha;
        this.frames = frames;
        this.removeAtEnd = removeAtEnd;
        this.age = 0;
        img.setTransparency(startAlpha);
    }

    /** Clarão rápido que desaparece. */
    public static ScreenOverlay flash(World world, Color color, int alpha, int frames)
    {
        return new ScreenOverlay(world.getWidth(), world.getHeight(), color, alpha, 0, frames, true);
    }

    /** Escurece aos poucos e fica. */
    public static ScreenOverlay dim(World world, int alpha, int frames)
    {
        return new ScreenOverlay(world.getWidth(), world.getHeight(), Palette.INK, 0, alpha, frames, false);
    }

    public void act()
    {
        if (age >= frames)
        {
            if (removeAtEnd)
            {
                getWorld().removeObject(this);
            }
            return;
        }

        age++;
        int alpha = startAlpha + (endAlpha - startAlpha) * age / frames;
        getImage().setTransparency(alpha);
    }
}
