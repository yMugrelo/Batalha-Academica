import greenfoot.*;

/**
 * BossSprite - a APARÊNCIA animada de uma matéria.
 *
 * Olha para a esquerda (ataca avançando para a esquerda).
 * Parada: flutua devagar.
 * Derrota: treme, afunda e desaparece.
 *
 * Existe só um PNG por matéria, então os estados são feitos com
 * movimento e transparência (a mesma imagem nos três primeiros estados).
 */
public class BossSprite extends CharacterSprite
{
    private static final int FLOAT_GAP = 18;
    private static final int FADE_FRAMES = 60;

    public BossSprite(SubjectType type)
    {
        super(image(type), image(type), image(type), -1);
    }

    private static GreenfootImage image(SubjectType type)
    {
        return UiArt.withShadow(ImageLibrary.subject(type), FLOAT_GAP);
    }

    protected int idleOffsetY(int frame)
    {
        return (int) Math.round(Math.sin(frame * 0.07) * 5);
    }

    protected void animateDefeat(int frame)
    {
        if (frame > FADE_FRAMES)
        {
            return;
        }
        double progress = (double) frame / FADE_FRAMES;
        int shake = (frame / 2) % 2 == 0 ? 3 : -3;
        moveTo(shake, (int) (progress * 50));
        getImage().setTransparency((int) (255 * (1 - progress)));
    }
}
