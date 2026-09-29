import greenfoot.*;

/**
 * ResultWorld - tela de VITÓRIA ou DERROTA, com o resumo da batalha.
 */
public class ResultWorld extends BaseWorld
{
    private static final int CONFETTI_INTERVAL = 25;   // frames entre levas de confete

    private BattleSummary summary;
    private MenuUI menu;
    private int frame;

    public ResultWorld(BattleSummary summary)
    {
        super();
        this.summary = summary;
        setBackground(BackgroundManager.result(summary.getSubjectType()));

        if (summary.isVictory())
        {
            buildVictory();
            SoundManager.playEffect("victory");
        }
        else
        {
            buildDefeat();
            SoundManager.playEffect("defeat");
        }

        addObject(menu, 400, 520);

        // Textos e menu sempre por cima dos efeitos (confete)
        setLayers(MenuUI.class, InfoPanel.class, Decoration.class, EffectSprite.class);
        frame = 0;
    }

    /**
     * Na vitória, cai um pouco de confete de tempos em tempos.
     */
    protected void update()
    {
        frame++;
        if (summary.isVictory() && frame % CONFETTI_INTERVAL == 0)
        {
            Effects.confetti(this, 6);
        }
    }

    private void buildVictory()
    {
        addObject(new Decoration(TextUtil.shadowText("MATÉRIA CONCLUÍDA!", 48, Palette.HIGHLIGHT)), 400, 60);
        addObject(new Decoration(TextUtil.shadowText(
            "Você foi aprovado em " + summary.getSubjectName() + "!", 24, Palette.TEXT)), 400, 110);

        PlayerSprite player = new PlayerSprite();
        addObject(player, 170, 300);
        player.playCelebrate();
        Effects.victory(this, 170, 270);

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

        PlayerSprite player = new PlayerSprite();
        addObject(player, 220, 300);
        player.playDefeat();                                        // o aluno cai
        addObject(new BossSprite(summary.getSubjectType()), 580, 280);   // a matéria flutua, vitoriosa

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
            GameManager.goToMenu(this);
        }
        else if (Keys.isConfirm(key))
        {
            choose(menu.getSelected());
        }
    }

    protected void handleMouse()
    {
        int clicked = menu.getClickedIndex();
        if (clicked >= 0)
        {
            choose(clicked);
        }
    }

    private void choose(int option)
    {
        if (option == 1)
        {
            GameManager.goToMenu(this);
        }
        else if (summary.isVictory())
        {
            GameManager.goToSelection(this);                              // escolher outra matéria
        }
        else
        {
            GameManager.startBattle(this, summary.getSubjectType());      // revanche
        }
    }
}
