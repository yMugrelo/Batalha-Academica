import greenfoot.*;

/**
 * BattleWorld - a tela de BATALHA.
 *
 * Liga as peças:
 *   - lógica:        Student, Subject, TurnManager
 *   - apresentação:  PlayerSprite, BossSprite, MenuUI (ações)
 *
 * Controles: 1-4 escolhem a ação direto; SETAS + ENTER também funcionam.
 * ESC abandona a batalha e volta para a seleção.
 *
 * (Etapa 1: o HUD ainda usa showText. As barras chegam na Etapa 2.)
 */
public class BattleWorld extends BaseWorld
{
    // Quantos frames a tela espera depois do fim da batalha antes do resultado
    private static final int END_DELAY = 120;

    private SubjectType type;
    private Student student;
    private Subject subject;
    private TurnManager turnManager;

    private MenuUI actionMenu;
    private int endCounter;

    /**
     * Construtor sem parâmetros: permite abrir a batalha direto pelo
     * Greenfoot (botão direito > new BattleWorld()) para testar.
     */
    public BattleWorld()
    {
        this(SubjectType.CALCULUS);
    }

    public BattleWorld(SubjectType type)
    {
        super();
        this.type = type;
        this.student = new Student("Aluno");
        this.subject = type.create();
        this.turnManager = new TurnManager(student, subject);
        this.endCounter = 0;

        setBackground(ImageLibrary.arena(type, WIDTH, HEIGHT));
        addObject(new PlayerSprite(), 190, 330);
        addObject(new BossSprite(type), 600, 300);

        actionMenu = new MenuUI(actionLabels(), actionDetails(), 600, 30);
        addObject(actionMenu, 400, 526);   // 140 px de altura: ocupa de 456 a 596

        refresh();
    }

    // ===================== Entrada =====================

    protected void onKey(String key)
    {
        if (Keys.isBack(key))
        {
            GameManager.goToSelection();
            return;
        }

        // Batalha acabou: ENTER pula a espera e vai direto ao resultado
        if (turnManager.isBattleOver())
        {
            if (Keys.isConfirm(key))
            {
                finishBattle();
            }
            return;
        }

        if (turnManager.getState() != BattleState.PLAYER_TURN)
        {
            return;
        }

        if (Keys.isUp(key))
        {
            actionMenu.moveUp();
        }
        else if (Keys.isDown(key))
        {
            actionMenu.moveDown();
        }
        else if (Keys.isConfirm(key))
        {
            turnManager.playerAction(PlayerAction.values()[actionMenu.getSelected()]);
        }
        else
        {
            PlayerAction action = PlayerAction.fromKey(key);
            if (action != null)
            {
                actionMenu.setSelected(action.ordinal());
                turnManager.playerAction(action);
            }
        }
        refresh();
    }

    // ===================== Ciclo =====================

    protected void update()
    {
        turnManager.update();

        if (turnManager.isBattleOver())
        {
            endCounter++;
            if (endCounter >= END_DELAY)
            {
                finishBattle();
                return;
            }
        }
        refresh();
    }

    private void finishBattle()
    {
        GameManager.showResult(new BattleSummary(type, turnManager));
    }

    // ===================== Tela =====================

    /**
     * Atualiza textos e o estado do menu de ações.
     */
    private void refresh()
    {
        // HUD provisório (Etapa 2 troca por barras)
        showText("ALUNO   HP " + student.getHealth() + "/" + student.getMaxHealth(), 190, 28);
        showText("EN " + student.getEnergy() + "/" + student.getMaxEnergy()
                 + "   CON " + student.getKnowledge(), 190, 56);
        showText(subject.getName().toUpperCase(), 610, 28);
        showText("HP " + subject.getHealth() + "/" + subject.getMaxHealth()
                 + "   EN " + subject.getEnergy() + "/" + subject.getMaxEnergy(), 610, 56);

        // Mensagem do turno
        String status = "";
        if (turnManager.getState() == BattleState.ENEMY_TURN)
        {
            status = "Turno de " + subject.getName() + "...";
        }
        else if (turnManager.getState() == BattleState.VICTORY)
        {
            status = "VITÓRIA!";
        }
        else if (turnManager.getState() == BattleState.DEFEAT)
        {
            status = "DERROTA...";
        }
        showText(status, 400, 388);
        showText(turnManager.getMessage(), 400, 428);

        // Menu: só ativo no turno do jogador; ações sem energia ficam apagadas
        actionMenu.setActive(turnManager.getState() == BattleState.PLAYER_TURN);
        PlayerAction[] actions = PlayerAction.values();
        for (int i = 0; i < actions.length; i++)
        {
            actionMenu.setEnabled(i, student.canPerform(actions[i]));
        }
    }

    /**
     * "[1] Resolver Questão", ... montado a partir do enum.
     */
    private String[] actionLabels()
    {
        PlayerAction[] actions = PlayerAction.values();
        String[] labels = new String[actions.length];
        for (int i = 0; i < actions.length; i++)
        {
            labels[i] = "[" + actions[i].getKey() + "] " + actions[i].getLabel();
        }
        return labels;
    }

    /**
     * Custo e efeito de cada ação. Os números vêm do enum e das
     * constantes do Student: mudou lá, muda aqui sozinho.
     */
    private String[] actionDetails()
    {
        PlayerAction[] actions = PlayerAction.values();
        String[] details = new String[actions.length];
        for (int i = 0; i < actions.length; i++)
        {
            String cost = actions[i].getEnergyCost() > 0 ? "-" + actions[i].getEnergyCost() + " EN   " : "";
            details[i] = cost + effectOf(actions[i]);
        }
        return details;
    }

    private String effectOf(PlayerAction action)
    {
        switch (action)
        {
            case RESOLVER_QUESTAO:
                return "dano = conhecimento";
            case ESTUDAR:
                return "+" + Student.STUDY_ENERGY_GAIN + " EN  +" + Student.STUDY_KNOWLEDGE_GAIN + " CON";
            case USAR_CONHECIMENTO:
                return "dano x" + Student.KNOWLEDGE_MULTIPLIER;
            case DESCANSAR:
                return "+" + Student.REST_HEALTH_GAIN + " HP  +" + Student.REST_ENERGY_GAIN + " EN";
            default:
                return "";
        }
    }
}
