import greenfoot.*;

/**
 * BossSprite - a APARÊNCIA de uma matéria na tela.
 *
 * A lógica (vida, ataques...) fica no Subject; esta classe só desenha.
 * As matérias "flutuam": a sombra fica um pouco abaixo do livro.
 * As animações de ataque, dano e derrota entram na Etapa 3.
 */
public class BossSprite extends Actor
{
    private static final int FLOAT_GAP = 18;

    public BossSprite(SubjectType type)
    {
        setImage(UiArt.withShadow(ImageLibrary.subject(type), FLOAT_GAP));
    }
}
