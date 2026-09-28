import greenfoot.*;

/**
 * PlayerSprite - a APARÊNCIA do estudante na tela.
 *
 * A lógica (vida, energia...) fica no Student; esta classe só desenha.
 * As animações de ataque, dano e derrota entram na Etapa 3.
 */
public class PlayerSprite extends Actor
{
    public PlayerSprite()
    {
        setImage(UiArt.withShadow(ImageLibrary.player("idle"), 0));
    }
}
