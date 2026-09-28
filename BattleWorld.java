import greenfoot.*;

/**
 * BattleWorld - a tela de BATALHA.
 *
 * Esta classe só faz duas coisas:
 *   1) transforma teclas em ações da batalha (TurnManager)
 *   2) avisa o BattleUI a cada frame para a tela acompanhar a lógica
 *
 * Controles: 1-4 escolhem a ação direto; SETAS + ENTER/ESPAÇO também.
 * ESC abandona a batalha e volta para a seleção.
 */
public class BattleWorld extends BaseWorld
{
    // Quantos frames a tela espera depois do fim da batalha antes do resultado
    private static final int END_DELAY = 150;

    private SubjectType type;
    private Student student;
    private TurnManager turnManager;
    private BattleUI ui;
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
        Subject subject = type.create();
        this.turnManager = new TurnManager(student, subject);
        this.ui = new BattleUI(this, type, student, subject, turnManager);
        SoundManager.playMusic("battle");
        this.endCounter = 0;
    }

    // ===================== Entrada =====================

    protected void onKey(String key)
    {
        if (Keys.isBack(key))
        {
            GameManager.goToSelection(this);
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
            return;   // turno do inimigo: teclas são ignoradas
        }

        ActionMenu menu = ui.getActionMenu();
        if (Keys.isUp(key))
        {
            menu.moveUp();
        }
        else if (Keys.isDown(key))
        {
            menu.moveDown();
        }
        else if (Keys.isLeft(key))
        {
            menu.moveLeft();
        }
        else if (Keys.isRight(key))
        {
            menu.moveRight();
        }
        else if (ui.isBusy())
        {
            // Animação rodando: dá para mover a seleção, mas ainda não dá para agir
        }
        else if (Keys.isConfirm(key))
        {
            tryAction(menu.getSelectedAction());
        }
        else
        {
            PlayerAction action = PlayerAction.fromKey(key);
            if (action != null)
            {
                menu.select(action.ordinal());
                tryAction(action);
            }
        }
        ui.update();
    }

    /**
     * Clique numa ação do menu = escolher essa ação.
     */
    protected void handleMouse()
    {
        PlayerAction clicked = ui.getActionMenu().getClickedAction();
        if (clicked != null)
        {
            tryAction(clicked);
            ui.update();
        }
        else if (turnManager.isBattleOver() && Greenfoot.mouseClicked(null))
        {
            finishBattle();   // clique no fim da batalha pula a espera
        }
    }

    /**
     * Executa a ação só se for a vez do jogador e nenhuma animação estiver rodando.
     */
    private void tryAction(PlayerAction action)
    {
        if (turnManager.getState() == BattleState.PLAYER_TURN && !ui.isBusy())
        {
            turnManager.playerAction(action);
        }
    }

    // ===================== Ciclo =====================

    protected void update()
    {
        turnManager.update();
        ui.update();

        if (turnManager.isBattleOver())
        {
            endCounter++;
            if (endCounter >= END_DELAY)
            {
                finishBattle();
            }
        }
    }

    private void finishBattle()
    {
        GameManager.showResult(this, new BattleSummary(type, turnManager));
    }
}
