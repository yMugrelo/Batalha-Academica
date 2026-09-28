import greenfoot.*;

/**
 * ResultWorld - tela de VITÓRIA ou DERROTA, com o resumo da batalha.
 */
public class ResultWorld extends BaseWorld
{
    private BattleSummary summary;
    private MenuUI menu;

    public ResultWorld(BattleSummary summary)
    {
        super();
        this.summary = summary;
        setBackground(UiArt.dimmed(ImageLibrary.arena(summary.getSubjectType(), WIDTH, HEIGHT), 140));

        if (summary.isVictory())
        {
            buildVictory();
        }
        else
        {
            buildDefeat();
        }

        addObject(menu, 400, 520);
    }

    private void buildVictory()
    {
        addObject(new Decoration(TextUtil.shadowText("MATÉRIA CONCLUÍDA!", 48, Palette.HIGHLIGHT)), 400, 60);
        addObject(new Decoration(TextUtil.shadowText(
            "Você foi aprovado em " + summary.getSubjectName() + "!", 24, Palette.TEXT)), 400, 110);

        addObject(new PlayerSprite(), 170, 300);
        addObject(new InfoPanel("RESUMO DA BATALHA", new String[] {
            "Turnos: " + summary.getTurns(),
            "Dano causado: " + summary.getDamageDealt(),
            "Dano recebido: " + summary.getDamageTaken(),
            "Acertos críticos: " + summary.getCriticalHits(),
            "Conhecimento obtido: +" + summary.getKnowledgeGained()
        }, 380), 520, 285);

        menu = new MenuUI(new String[] { "JOGAR NOVAMENTE", "MENU PRINCIPAL" }, 320, 44);
    }

    private void buildDefeat()
    {
        addObject(new Decoration(TextUtil.shadowText("VOCÊ REPROVOU!", 48, Palette.HP)), 400, 60);
        addObject(new Decoration(TextUtil.shadowText(
            summary.getSubjectName() + " venceu desta vez. Estude e tente de novo!", 22, Palette.TEXT)),
            400, 110);

        addObject(new PlayerSprite(), 220, 300);
        addObject(new BossSprite(summary.getSubjectType()), 580, 280);

        menu = new MenuUI(new String[] { "TENTAR NOVAMENTE", "MENU PRINCIPAL" }, 320, 44);
    }

    protected void onKey(String key)
    {
        if (Keys.isUp(key))
        {
            menu.moveUp();
        }
        else if (Keys.isDown(key))
        {
            menu.moveDown();
        }
        else if (Keys.isBack(key))
        {
            GameManager.goToMenu();
        }
        else if (Keys.isConfirm(key))
        {
            if (menu.getSelected() == 1)
            {
                GameManager.goToMenu();
            }
            else if (summary.isVictory())
            {
                GameManager.goToSelection();          // escolher outra matéria
            }
            else
            {
                GameManager.startBattle(summary.getSubjectType());   // revanche
            }
        }
    }
}
