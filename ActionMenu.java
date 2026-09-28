import greenfoot.*;

/**
 * ActionMenu - as quatro ações do jogador, em uma grade 2 x 2.
 *
 *   ┌───────────────────────────┬───────────────────────────┐
 *   │ [1] RESOLVER QUESTÃO -5 EN│ [2] ESTUDAR               │
 *   │ dano = conhecimento       │ +10 EN  +3 CON            │
 *   ├───────────────────────────┼───────────────────────────┤
 *   │ [3] USAR CONHECIMENTO ... │ [4] DESCANSAR             │
 *   └───────────────────────────┴───────────────────────────┘
 *
 * Mostra claramente: ação selecionada (borda dourada), disponível
 * (texto claro) e indisponível (apagada, com "SEM ENERGIA").
 * Os números vêm do enum PlayerAction e das constantes do Student.
 */
public class ActionMenu extends Actor
{
    private static final int COLUMNS = 2;
    private static final int CELL_WIDTH = 384;
    private static final int CELL_HEIGHT = 50;
    private static final int GAP = 6;
    private static final int PADDING = 8;
    private static final Color SUBTITLE = new Color(190, 184, 206);

    private PlayerAction[] actions;
    private boolean[] enabled;
    private int selected;
    private boolean active;
    private boolean interactive;   // false = só exibição (tela "Como jogar")

    /** Menu da batalha (navegável por teclado e mouse). */
    public ActionMenu()
    {
        this(true);
    }

    /**
     * interactive = false cria uma versão só para exibir as ações,
     * sem seleção e sem responder ao mouse.
     */
    public ActionMenu(boolean interactive)
    {
        actions = PlayerAction.values();
        enabled = new boolean[actions.length];
        for (int i = 0; i < enabled.length; i++)
        {
            enabled[i] = true;
        }
        this.interactive = interactive;
        selected = interactive ? 0 : -1;
        active = true;
        redraw();
    }

    // ===================== Mouse =====================

    /**
     * Passar o mouse por cima de uma ação a seleciona.
     */
    public void act()
    {
        if (interactive && active && Greenfoot.mouseMoved(this))
        {
            int index = indexUnderMouse();
            if (index >= 0)
            {
                select(index);
            }
        }
    }

    /**
     * Ação clicada neste frame (null se nenhuma).
     */
    public PlayerAction getClickedAction()
    {
        if (!interactive || !active || !Greenfoot.mouseClicked(this))
        {
            return null;
        }
        int index = indexUnderMouse();
        if (index < 0)
        {
            return null;
        }
        select(index);
        return actions[index];
    }

    /**
     * Qual célula está embaixo do mouse (-1 se for o espaço entre elas).
     */
    private int indexUnderMouse()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse == null)
        {
            return -1;
        }
        return indexAt(mouse.getX(), mouse.getY());
    }

    /**
     * Qual célula fica no ponto (x, y) do mundo (-1 se nenhuma).
     */
    public int indexAt(int x, int y)
    {
        int left = getX() - getImage().getWidth() / 2 + PADDING;
        int top = getY() - getImage().getHeight() / 2 + PADDING;
        if (x < left || y < top)
        {
            return -1;
        }
        int column = (x - left) / (CELL_WIDTH + GAP);
        int row = (y - top) / (CELL_HEIGHT + GAP);
        int index = row * COLUMNS + column;
        return (column < COLUMNS && index < actions.length) ? index : -1;
    }

    // ===================== Navegação na grade =====================

    public void moveLeft()
    {
        if (selected % COLUMNS > 0)
        {
            select(selected - 1);
        }
    }

    public void moveRight()
    {
        if (selected % COLUMNS < COLUMNS - 1 && selected + 1 < actions.length)
        {
            select(selected + 1);
        }
    }

    public void moveUp()
    {
        if (selected - COLUMNS >= 0)
        {
            select(selected - COLUMNS);
        }
    }

    public void moveDown()
    {
        if (selected + COLUMNS < actions.length)
        {
            select(selected + COLUMNS);
        }
    }

    public void select(int index)
    {
        if (index >= 0 && index < actions.length && index != selected)
        {
            selected = index;
            redraw();
        }
    }

    public PlayerAction getSelectedAction()
    {
        return actions[selected];
    }

    public int getSelected()
    {
        return selected;
    }

    public boolean isSelectedEnabled()
    {
        return enabled[selected];
    }

    // ===================== Estado =====================

    public void setEnabled(int index, boolean value)
    {
        if (enabled[index] != value)
        {
            enabled[index] = value;
            redraw();
        }
    }

    public void setActive(boolean value)
    {
        if (active != value)
        {
            active = value;
            redraw();
        }
    }

    // ===================== Desenho =====================

    private void redraw()
    {
        int rows = (actions.length + COLUMNS - 1) / COLUMNS;
        int width = PADDING * 2 + COLUMNS * CELL_WIDTH + (COLUMNS - 1) * GAP;
        int height = PADDING * 2 + rows * CELL_HEIGHT + (rows - 1) * GAP;
        GreenfootImage img = UiArt.panel(width, height);

        for (int i = 0; i < actions.length; i++)
        {
            int x = PADDING + (i % COLUMNS) * (CELL_WIDTH + GAP);
            int y = PADDING + (i / COLUMNS) * (CELL_HEIGHT + GAP);
            drawCell(img, i, x, y);
        }
        setImage(img);
    }

    private void drawCell(GreenfootImage img, int index, int x, int y)
    {
        PlayerAction action = actions[index];
        boolean isSelected = active && index == selected;
        boolean usable = active && enabled[index];

        // Fundo e borda da célula
        img.setColor(isSelected ? Palette.HIGHLIGHT : Palette.CELL_EDGE);
        img.fillRect(x, y, CELL_WIDTH, CELL_HEIGHT);
        img.setColor(Palette.CELL);
        int border = isSelected ? 3 : 1;
        img.fillRect(x + border, y + border, CELL_WIDTH - border * 2, CELL_HEIGHT - border * 2);

        // Título: "[1] RESOLVER QUESTÃO"
        Color titleColor = !usable ? Palette.TEXT_DIM : (isSelected ? Palette.HIGHLIGHT : Palette.TEXT);
        String title = "[" + action.getKey() + "] " + action.getLabel().toUpperCase();
        img.drawImage(TextUtil.text(title, 18, titleColor), x + 12, y + 6);

        // Custo de energia (à direita do título)
        if (action.getEnergyCost() > 0)
        {
            GreenfootImage cost = TextUtil.text("-" + action.getEnergyCost() + " EN", 16,
                                                usable ? Palette.ENERGY : Palette.TEXT_DIM);
            img.drawImage(cost, x + CELL_WIDTH - 12 - cost.getWidth(), y + 7);
        }

        // Efeito (linha de baixo)
        img.drawImage(TextUtil.text(effectOf(action), 15, usable ? SUBTITLE : Palette.TEXT_DIM),
                      x + 12, y + 28);

        // Aviso quando falta energia
        if (!enabled[index])
        {
            GreenfootImage warning = TextUtil.text("SEM ENERGIA", 14, Palette.HP);
            img.drawImage(warning, x + CELL_WIDTH - 12 - warning.getWidth(), y + 29);
        }
    }

    /**
     * Texto do efeito de cada ação, montado com os números do Student.
     */
    private String effectOf(PlayerAction action)
    {
        switch (action)
        {
            case RESOLVER_QUESTAO:
                return "Dano = conhecimento";
            case ESTUDAR:
                return "+" + Student.STUDY_ENERGY_GAIN + " EN   +" + Student.STUDY_KNOWLEDGE_GAIN + " CON";
            case USAR_CONHECIMENTO:
                return "Dano = conhecimento x" + Student.KNOWLEDGE_MULTIPLIER;
            case DESCANSAR:
                return "+" + Student.REST_HEALTH_GAIN + " HP   +" + Student.REST_ENERGY_GAIN + " EN";
            default:
                return "";
        }
    }
}
