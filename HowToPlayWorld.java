import greenfoot.*;

/**
 * HowToPlayWorld - tela "COMO JOGAR".
 *
 * Reaproveita o ActionMenu (em modo só exibição) para mostrar as quatro
 * ações exatamente como aparecem na batalha. Os números vêm do código
 * (enum e constantes do Student): se uma regra mudar, esta tela acompanha.
 *
 * ESC, ENTER ou um clique voltam ao menu.
 */
public class HowToPlayWorld extends BaseWorld
{
    public HowToPlayWorld()
    {
        super();
        setBackground(BackgroundManager.menu());
        SoundManager.playMusic("menu");

        addObject(new Decoration(TextUtil.shadowText("COMO JOGAR", 40, Palette.HIGHLIGHT)), 400, 40);

        addObject(new InfoPanel("OBJETIVO", new String[] {
            "Vença a matéria antes que ela vença você.",
            "A matéria ganha energia a cada turno e usa ataques cada vez mais fortes.",
            "Seus ataques podem ser CRÍTICOS: +50% de força (" + Student.CRITICAL_CHANCE + "% de chance).",
            "Dica: estude antes de enfrentar as matérias mais difíceis!"
        }, 760), 400, 169);

        addObject(new ActionMenu(false), 400, 340);

        addObject(new InfoPanel("CONTROLES", new String[] {
            "1 a 4: escolher ação    SETAS ou MOUSE: navegar",
            "ENTER, ESPAÇO ou CLIQUE: confirmar    ESC: voltar    M: som"
        }, 760), 400, 490);

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
