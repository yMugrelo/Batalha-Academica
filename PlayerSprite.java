import greenfoot.*;

/**
 * PlayerSprite - a APARÊNCIA animada do estudante.
 *
 * Olha para a direita (ataca avançando para a direita).
 * Parado: "respira" (sobe e desce 1-2 pixels).
 * Derrota: tomba para trás e fica apagado.
 */
public class PlayerSprite extends CharacterSprite
{
    private static final int SHADOW_WIDTH = 72;
    private static final int FALL_FRAMES = 20;

    private GreenfootImage defeatImage;
    private boolean rotateOnDefeat;   // a arte provisória "tomba"; um PNG de derrota já vem deitado

    public PlayerSprite()
    {
        super(UiArt.withShadow(ImageLibrary.player("idle"), 0, SHADOW_WIDTH),
              UiArt.withShadow(ImageLibrary.player("attack"), 0, SHADOW_WIDTH),
              UiArt.withShadow(ImageLibrary.player("hurt"), 0, SHADOW_WIDTH),
              1);
        defeatImage = ImageLibrary.player("defeat");
        rotateOnDefeat = !ImageLibrary.isAvailable("player/player_defeat.png");
    }

    protected int idleOffsetY(int frame)
    {
        return (int) Math.round(Math.sin(frame * 0.08) * 1.5);
    }

    protected void animateDefeat(int frame)
    {
        if (frame == 1)
        {
            setImage(defeatImage);
        }

        if (rotateOnDefeat && frame <= FALL_FRAMES)
        {
            // Tomba para trás (gira até -90 graus) e desce um pouco
            double progress = (double) frame / FALL_FRAMES;
            setRotation((int) Math.round(-90 * progress));
            moveTo(-(int) (progress * 20), (int) (progress * 40));
        }

        if (frame == FALL_FRAMES)
        {
            getImage().setTransparency(170);   // fica "apagado"
        }
    }
}
