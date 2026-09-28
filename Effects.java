import greenfoot.*;

/**
 * Effects - cria os efeitos visuais do jogo.
 *
 * Cada método monta um ou mais EffectSprite/ScreenOverlay e coloca no mundo.
 * Os efeitos se removem sozinhos: quem chama não precisa se preocupar.
 */
public class Effects
{
    /** Estrela de impacto de um acerto normal. */
    public static void hit(World world, int x, int y)
    {
        world.addObject(new EffectSprite(ImageLibrary.effect("hit"), 14, 0, 0, 0), x, y);
    }

    /** Acerto crítico: estrela grande laranja, texto "CRÍTICO!" e um clarão na tela. */
    public static void critical(World world, int x, int y)
    {
        world.addObject(new EffectSprite(ImageLibrary.effect("critical"), 22, 0, 0, 0), x, y);
        // O texto fica ao lado do alvo (em cima dele já aparece o número de dano)
        world.addObject(new EffectSprite(TextUtil.shadowText("CRÍTICO!", 34, Palette.CRITICAL),
                                         45, 0, -0.5, 0), x - 160, y - 60);
        world.addObject(ScreenOverlay.flash(world, Palette.WHITE, 150, 10),
                        world.getWidth() / 2, world.getHeight() / 2);
    }

    /**
     * Número flutuante ("-8", "+20 HP"...) que sobe e desaparece.
     * O x recebe uma pequena variação aleatória para números seguidos
     * não ficarem exatamente em cima um do outro.
     */
    public static void floatingText(World world, int x, int y, String text, Color color, int size)
    {
        int jitter = Greenfoot.getRandomNumber(17) - 8;
        world.addObject(new EffectSprite(TextUtil.shadowText(text, size, color), 50, 0, -1.0, 0.01),
                        x + jitter, y);
    }

    /** Brilho pequeno (estudar, descansar). */
    public static void sparkle(World world, int x, int y, Color color)
    {
        world.addObject(new EffectSprite(PlaceholderArt.burst(4, color, Palette.WHITE), 18, 0, -0.6, 0),
                        x, y);
    }

    /** Vitória: estrela dourada grande e uma chuva de confete. */
    public static void victory(World world, int x, int y)
    {
        world.addObject(new EffectSprite(ImageLibrary.effect("victory"), 70, 0, -0.3, 0), x, y);
        confetti(world, 24);
    }

    /** Confete caindo do topo da tela em posições aleatórias. */
    public static void confetti(World world, int pieces)
    {
        Color[] colors = { Palette.ACCENT, Palette.HP_HIGH, Palette.ENERGY, Palette.KNOWLEDGE, Palette.HP };
        for (int i = 0; i < pieces; i++)
        {
            Color color = colors[Greenfoot.getRandomNumber(colors.length)];
            double dx = (Greenfoot.getRandomNumber(21) - 10) / 10.0;
            double dy = 1 + Greenfoot.getRandomNumber(20) / 10.0;
            int x = Greenfoot.getRandomNumber(world.getWidth());
            int y = Greenfoot.getRandomNumber(80);
            world.addObject(new EffectSprite(PlaceholderArt.confetti(color), 140, dx, dy, 0.02), x, y);
        }
    }

    /** Derrota: a tela escurece devagar. */
    public static void defeat(World world)
    {
        world.addObject(ScreenOverlay.dim(world, 110, 60), world.getWidth() / 2, world.getHeight() / 2);
    }
}
