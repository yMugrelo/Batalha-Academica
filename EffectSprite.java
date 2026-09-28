import greenfoot.*;

/**
 * EffectSprite - um efeito visual TEMPORÁRIO (estrela de impacto,
 * número de dano, confete...).
 *
 * Vive "lifetime" frames: a cada frame anda (dx, dy), sofre um pouco
 * de gravidade, vai ficando transparente e, no fim, se remove do mundo.
 */
public class EffectSprite extends Actor
{
    private int lifetime;
    private int age;
    private double x;
    private double y;
    private double dx;
    private double dy;
    private double gravity;

    public EffectSprite(GreenfootImage image, int lifetime, double dx, double dy, double gravity)
    {
        setImage(image);
        this.lifetime = lifetime;
        this.dx = dx;
        this.dy = dy;
        this.gravity = gravity;
        this.age = 0;
    }

    protected void addedToWorld(World world)
    {
        x = getX();
        y = getY();
    }

    public void act()
    {
        age++;
        if (age >= lifetime)
        {
            getWorld().removeObject(this);
            return;   // depois de removido, não pode mexer em mais nada
        }

        x = x + dx;
        y = y + dy;
        dy = dy + gravity;

        // O mundo tem bordas: em vez de "grudar" na borda, o efeito some
        World world = getWorld();
        if (x < 0 || x >= world.getWidth() || y < 0 || y >= world.getHeight())
        {
            world.removeObject(this);
            return;
        }
        setLocation((int) Math.round(x), (int) Math.round(y));

        // Some aos poucos no último terço da vida
        int fadeStart = lifetime * 2 / 3;
        if (age > fadeStart)
        {
            int alpha = 255 * (lifetime - age) / (lifetime - fadeStart);
            getImage().setTransparency(Math.max(0, alpha));
        }
    }
}
