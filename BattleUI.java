import greenfoot.*;

/**
 * BattleUI - toda a APRESENTAÇÃO da tela de batalha.
 *
 * Monta e atualiza: fundo, personagens, HUD, caixa de mensagens e
 * menu de ações. Lê o estado da lógica (Student, Subject, TurnManager)
 * mas NUNCA o altera: quem muda a batalha é o BattleWorld.
 *
 * Animações: a cada frame verifica se o TurnManager registrou um
 * BattleEvent novo. Se sim, quem agiu faz a animação de ataque e,
 * alguns frames depois (quando o golpe "chega"), acontece o impacto:
 * o alvo treme, aparecem os efeitos e as barras do HUD se atualizam.
 *
 * Layout (800 x 600):
 *     0 - 106   HUD (painéis do aluno e da matéria)
 *   106 - 392   arena (personagens)
 *   394 - 470   caixa de mensagens
 *   474 - 596   menu de ações
 */
public class BattleUI
{
    private static final int PLAYER_X = 190;
    private static final int PLAYER_Y = 316;
    private static final int BOSS_X = 600;
    private static final int BOSS_Y = 290;
    private static final int DIALOGUE_Y = 432;
    private static final int MENU_Y = 535;
    private static final int IMPACT_DELAY = 10;   // frames entre o início do ataque e o golpe

    private BaseWorld world;
    private SubjectType type;
    private Student student;
    private Subject subject;
    private TurnManager turnManager;

    private HUD hud;
    private DialogueBox dialogue;
    private ActionMenu actionMenu;
    private PlayerSprite playerSprite;
    private BossSprite bossSprite;

    private int seenEvents;           // quantos eventos já foram animados
    private BattleEvent pendingEvent; // evento esperando o momento do impacto
    private int impactCountdown;      // frames até o impacto (0 = nenhum pendente)

    public BattleUI(BaseWorld world, SubjectType type, Student student, Subject subject,
                    TurnManager turnManager)
    {
        this.world = world;
        this.type = type;
        this.student = student;
        this.subject = subject;
        this.turnManager = turnManager;

        world.setBackground(BackgroundManager.arena(type));
        playerSprite = new PlayerSprite();
        bossSprite = new BossSprite(type);
        world.addObject(playerSprite, PLAYER_X, PLAYER_Y);
        world.addObject(bossSprite, BOSS_X, BOSS_Y);

        hud = new HUD(world, student, subject);

        dialogue = new DialogueBox(world.getWidth() - 10);
        world.addObject(dialogue, world.getWidth() / 2, DIALOGUE_Y);

        actionMenu = new ActionMenu();
        world.addObject(actionMenu, world.getWidth() / 2, MENU_Y);

        // Ordem de desenho (primeiro da lista = por cima de todos):
        // interface > efeitos > escurecimento > personagens
        world.setLayers(DialogueBox.class, ActionMenu.class, StatBar.class, Decoration.class,
                        EffectSprite.class, ScreenOverlay.class,
                        PlayerSprite.class, BossSprite.class);

        seenEvents = turnManager.getEventCount();
        impactCountdown = 0;
        update();
    }

    /**
     * Chamado a cada frame: copia o estado da lógica para a tela.
     */
    public void update()
    {
        // Evento novo? Começa a animação de quem agiu
        if (turnManager.getEventCount() != seenEvents)
        {
            seenEvents = turnManager.getEventCount();
            startEvent(turnManager.getLastEvent());
        }

        // Contagem até o golpe chegar
        if (impactCountdown > 0)
        {
            impactCountdown--;
            if (impactCountdown == 0)
            {
                impact(pendingEvent);
            }
        }

        // O HUD só atualiza depois do impacto (a barra cai junto com o golpe)
        if (impactCountdown == 0)
        {
            hud.update();
        }

        BattleState state = turnManager.getState();
        dialogue.setMessage(turnManager.getMessage());
        dialogue.setTag(tagText(state), tagColor(state));

        // Menu: só ativo no turno do jogador e sem animação rodando
        actionMenu.setActive(state == BattleState.PLAYER_TURN && !isBusy());
        PlayerAction[] actions = PlayerAction.values();
        for (int i = 0; i < actions.length; i++)
        {
            actionMenu.setEnabled(i, student.canPerform(actions[i]));
        }
    }

    public ActionMenu getActionMenu()
    {
        return actionMenu;
    }

    /**
     * Alguma animação importante ainda está rodando?
     * (O BattleWorld espera terminar antes de aceitar a próxima ação.)
     */
    public boolean isBusy()
    {
        return impactCountdown > 0 || playerSprite.isBusy() || bossSprite.isBusy();
    }

    // ===================== Animações dos eventos =====================

    /**
     * Começo de um evento: ataques esperam o golpe chegar;
     * estudar e descansar acontecem na hora.
     */
    private void startEvent(BattleEvent event)
    {
        if (event.isPlayerAttack())
        {
            playerSprite.playAttack();
            waitForImpact(event);
        }
        else if (!event.isByPlayer())
        {
            bossSprite.playAttack();
            waitForImpact(event);
        }
        else
        {
            impact(event);
        }
    }

    private void waitForImpact(BattleEvent event)
    {
        pendingEvent = event;
        impactCountdown = IMPACT_DELAY;
    }

    /**
     * O momento do golpe (ou do efeito de estudar/descansar).
     */
    private void impact(BattleEvent event)
    {
        int playerTop = PLAYER_Y - 95;
        int bossTop = BOSS_Y - 100;

        if (event.isPlayerAttack())
        {
            SoundManager.playEffect("attack");
            bossSprite.playHurt();
            int damage = event.getDamage();   // mesmo número da mensagem
            if (event.isCritical())
            {
                Effects.critical(world, BOSS_X, BOSS_Y - 20);
                Effects.floatingText(world, BOSS_X, bossTop, "-" + damage, Palette.CRITICAL, 38);
            }
            else
            {
                Effects.hit(world, BOSS_X, BOSS_Y - 20);
                Effects.floatingText(world, BOSS_X, bossTop, "-" + damage, Palette.TEXT, 32);
            }
            if (event.isFinishingBlow())
            {
                bossSprite.playDefeat();
                playerSprite.playCelebrate();
                SoundManager.stopMusic();
                SoundManager.playEffect("victory");
                Effects.victory(world, PLAYER_X, PLAYER_Y - 30);
            }
        }
        else if (event.isByPlayer())
        {
            // Estudar ou descansar: mostra os ganhos acima do aluno
            Effects.sparkle(world, PLAYER_X, PLAYER_Y - 40, Palette.KNOWLEDGE);
            showGain(event.getStudentHealthChange(), " HP", Palette.HP_HIGH, playerTop);
            showGain(event.getStudentEnergyChange(), " EN", Palette.ENERGY, playerTop - 26);
            showGain(event.getKnowledgeChange(), " CON", Palette.KNOWLEDGE, playerTop - 52);
        }
        else
        {
            // A matéria atacou o aluno
            SoundManager.playEffect("attack");
            playerSprite.playHurt();
            Effects.hit(world, PLAYER_X, PLAYER_Y - 20);
            Effects.floatingText(world, PLAYER_X, playerTop,
                                 "-" + event.getDamage(), Palette.HP_LOW, 32);
            if (event.getStudentEnergyChange() < 0)
            {
                Effects.floatingText(world, PLAYER_X, playerTop - 30,
                                     event.getStudentEnergyChange() + " EN", Palette.ENERGY, 24);
            }
            if (event.isFinishingBlow())
            {
                playerSprite.playDefeat();
                Effects.defeat(world);
                SoundManager.stopMusic();
                SoundManager.playEffect("defeat");
            }
        }
    }

    /**
     * Número "+10 EN" flutuando, só se o valor realmente mudou.
     */
    private void showGain(int change, String suffix, Color color, int y)
    {
        if (change > 0)
        {
            Effects.floatingText(world, PLAYER_X, y, "+" + change + suffix, color, 24);
        }
    }

    private String tagText(BattleState state)
    {
        switch (state)
        {
            case PLAYER_TURN:
                return "SEU TURNO";
            case ENEMY_TURN:
                return "TURNO DE " + subject.getName().toUpperCase();
            case VICTORY:
                return "VITÓRIA!  (ENTER para continuar)";
            default:
                return "REPROVADO!  (ENTER para continuar)";
        }
    }

    private Color tagColor(BattleState state)
    {
        switch (state)
        {
            case PLAYER_TURN:
                return Palette.HIGHLIGHT;
            case ENEMY_TURN:
                return Palette.subjectColor(type);
            case VICTORY:
                return Palette.HP_HIGH;
            default:
                return Palette.HP_LOW;
        }
    }
}
