import greenfoot.*;

/**
 * FadeTransition - a camada preta das transições entre telas.
 *
 *   saída:   transparente -> preto   (a tela atual escurece)
 *   entrada: preto -> transparente   (a próxima tela aparece)
 *
 * Fica sempre por cima de tudo (o BaseWorld garante isso na ordem
 * de desenho). É contada em frames: não bloqueia nada.
 */
public class FadeTransition extends Actor
{
    public static final int FRAMES = 12;

    private boolean fadingOut;   // true = escurecendo; false = clareando
    private int age;

    public FadeTransition(int width, int height, boolean fadingOut)
    {
        GreenfootImage img = new GreenfootImage(width, height);
        img.setColor(Palette.INK);
        img.fillRect(0, 0, width, height);
        setImage(img);

        this.fadingOut = fadingOut;
        this.age = 0;
        img.setTransparency(fadingOut ? 0 : 255);
    }

    public void act()
    {
        if (isFinished())
        {
            if (!fadingOut)
            {
                getWorld().removeObject(this);   // entrada terminou: some
            }
            return;
        }

        age++;
        int alpha = 255 * age / FRAMES;
        getImage().setTransparency(fadingOut ? alpha : 255 - alpha);
    }

    public boolean isFinished()
    {
        return age >= FRAMES;
    }
}
