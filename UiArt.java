import greenfoot.*;

/**
 * UiArt - peças visuais da interface (painéis, sombras).
 *
 * Os cantos são "cortados" em degraus para combinar com o estilo pixel art.
 */
public class UiArt
{
    /**
     * Painel semitransparente com borda clara.
     */
    public static GreenfootImage panel(int width, int height)
    {
        return panel(width, height, Palette.PANEL_EDGE);
    }

    /**
     * Painel semitransparente com a cor de borda escolhida.
     */
    public static GreenfootImage panel(int width, int height, Color edge)
    {
        GreenfootImage img = new GreenfootImage(width, height);
        int w = width;
        int h = height;

        // Fundo semitransparente
        img.setColor(Palette.PANEL);
        img.fillRect(4, 4, w - 8, h - 8);

        // Borda clara (2 px)
        img.setColor(edge);
        img.fillRect(4, 2, w - 8, 2);
        img.fillRect(4, h - 4, w - 8, 2);
        img.fillRect(2, 4, 2, h - 8);
        img.fillRect(w - 4, 4, 2, h - 8);

        // Contorno escuro externo (2 px), com cantos em degrau
        img.setColor(Palette.INK);
        img.fillRect(4, 0, w - 8, 2);
        img.fillRect(4, h - 2, w - 8, 2);
        img.fillRect(0, 4, 2, h - 8);
        img.fillRect(w - 2, 4, 2, h - 8);
        img.fillRect(2, 2, 2, 2);
        img.fillRect(w - 4, 2, 2, 2);
        img.fillRect(2, h - 4, 2, 2);
        img.fillRect(w - 4, h - 4, 2, 2);

        return img;
    }

    /**
     * Escurece uma imagem inteira (ex.: fundo das telas de resultado,
     * para o texto ficar em primeiro plano).
     */
    public static GreenfootImage dimmed(GreenfootImage image, int alpha)
    {
        image.setColor(new Color(0, 0, 0, alpha));
        image.fillRect(0, 0, image.getWidth(), image.getHeight());
        return image;
    }

    /**
     * Sombra oval para colocar embaixo dos personagens.
     */
    public static GreenfootImage shadow(int width)
    {
        GreenfootImage img = new GreenfootImage(width, width / 4);
        img.setColor(Palette.SHADOW);
        img.fillOval(0, 0, width, width / 4);
        return img;
    }

    /**
     * Junta uma imagem de personagem com uma sombra embaixo dela.
     * gap = espaço entre os pés e a sombra (personagens que flutuam).
     */
    public static GreenfootImage withShadow(GreenfootImage sprite, int gap)
    {
        GreenfootImage shadow = shadow(sprite.getWidth() * 3 / 4);
        int height = sprite.getHeight() + gap + shadow.getHeight() / 2;
        GreenfootImage img = new GreenfootImage(sprite.getWidth(), height);

        img.drawImage(shadow, (sprite.getWidth() - shadow.getWidth()) / 2,
                      height - shadow.getHeight());
        img.drawImage(sprite, 0, 0);
        return img;
    }
}
