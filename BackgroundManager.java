import greenfoot.*;

/**
 * BackgroundManager - escolhe e prepara o fundo de cada tela.
 *
 * Garante que todo fundo tenha exatamente o tamanho do mundo (800 x 600)
 * SEM deformar: se um PNG vier com outro tamanho, ele é ampliado ou
 * reduzido mantendo a proporção até cobrir a tela, e o que sobrar
 * nas bordas é cortado (igual a "cover" em sites).
 */
public class BackgroundManager
{
    /** Menu, seleção, como jogar e créditos. */
    public static GreenfootImage menu()
    {
        return fit(ImageLibrary.classroom(BaseWorld.WIDTH, BaseWorld.HEIGHT));
    }

    /** Arena de batalha de cada matéria. */
    public static GreenfootImage arena(SubjectType type)
    {
        return fit(ImageLibrary.arena(type, BaseWorld.WIDTH, BaseWorld.HEIGHT));
    }

    /** Tela de resultado: a arena da matéria, escurecida para destacar o texto. */
    public static GreenfootImage result(SubjectType type)
    {
        return UiArt.dimmed(arena(type), 140);
    }

    /**
     * Ajusta uma imagem ao tamanho do mundo sem deformar.
     */
    private static GreenfootImage fit(GreenfootImage img)
    {
        int width = BaseWorld.WIDTH;
        int height = BaseWorld.HEIGHT;

        if (img.getWidth() == width && img.getHeight() == height)
        {
            return img;   // já está no tamanho certo
        }

        // Escala que faz a imagem cobrir a tela inteira nas duas direções
        double scale = Math.max((double) width / img.getWidth(), (double) height / img.getHeight());
        int scaledWidth = (int) Math.ceil(img.getWidth() * scale);
        int scaledHeight = (int) Math.ceil(img.getHeight() * scale);
        img.scale(scaledWidth, scaledHeight);

        // Recorta o centro
        GreenfootImage result = new GreenfootImage(width, height);
        result.drawImage(img, (width - scaledWidth) / 2, (height - scaledHeight) / 2);
        return result;
    }
}
