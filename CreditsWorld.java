import greenfoot.*;

/**
 * CreditsWorld - tela "CRÉDITOS".
 *
 * Mostra os créditos e, embaixo, o estudante e as quatro matérias
 * flutuando (cada uma num ritmo um pouco diferente).
 *
 * ESC, ENTER ou um clique voltam ao menu.
 */
public class CreditsWorld extends BaseWorld
{
    public CreditsWorld()
    {
        super();
        setBackground(BackgroundManager.menu());
        SoundManager.playMusic("menu");

        addObject(new InfoPanel("CRÉDITOS", new String[] {
            "BATALHA ACADÊMICA",
            "",
            "Projeto de faculdade desenvolvido em Java com o Greenfoot.",
            "",
            "Programação e ideia: Murilo Rosa de Paula",
            "Design e Sons: Felipe Henrique Santos Berberth",
            "",
            "Obrigado por jogar!"
        }, 620), 400, 190);

        // Desfile dos personagens
        addObject(new PlayerSprite(), 120, 470);
        SubjectType[] types = SubjectType.values();
        for (int i = 0; i < types.length; i++)
        {
            double speed = 0.06 + i * 0.012;   // ritmos diferentes: não flutuam juntos
            addObject(new FloatingDecoration(ImageLibrary.subjectIcon(types[i]), 6, speed),
                      270 + i * 140, 460);
        }

        addObject(new Decoration(TextUtil.shadowText("ESC ou CLIQUE: voltar ao menu", 16, Palette.TEXT)),
                  400, 584);
    }

    protected void onKey(String key)
    {
        if (Keys.isBack(key) || Keys.isConfirm(key))
        {
            GameManager.goToMenu(this);
        }
    }

    protected void handleMouse()
    {
        if (Greenfoot.mouseClicked(null))
        {
            GameManager.goToMenu(this);
        }
    }
}
