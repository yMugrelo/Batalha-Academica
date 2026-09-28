import greenfoot.*;

/**
 * MenuUI - uma lista de opções navegável pelo teclado.
 *
 * Usada no menu principal, na tela de resultado e nas ações da batalha.
 * Cada opção pode estar:
 *   - selecionada   (fundo destacado e marcador ">")
 *   - disponível    (texto claro)
 *   - indisponível  (texto apagado; pode ser selecionada, mas não confirmada)
 *
 * O MenuUI só DESENHA e guarda qual opção está selecionada.
 * Quem decide o que cada opção faz é o World que o usa.
 */
public class MenuUI extends Actor
{
    private static final int PADDING = 12;
    private static final Color DETAIL_COLOR = new Color(190, 184, 206);
    private static final Color SELECTED_BAR = new Color(255, 204, 84, 50);

    private String[] labels;      // texto de cada opção
    private String[] details;     // texto menor à direita (custo, efeito); pode ser null
    private boolean[] enabled;    // quais opções podem ser confirmadas
    private int selected;         // índice da opção selecionada
    private int width;
    private int rowHeight;
    private boolean active;       // menu inativo fica todo apagado (ex.: turno do inimigo)

    public MenuUI(String[] labels, int width, int rowHeight)
    {
        this(labels, null, width, rowHeight);
    }

    public MenuUI(String[] labels, String[] details, int width, int rowHeight)
    {
        this.labels = labels;
        this.details = details;
        this.width = width;
        this.rowHeight = rowHeight;
        this.enabled = new boolean[labels.length];
        for (int i = 0; i < enabled.length; i++)
        {
            enabled[i] = true;
        }
        this.selected = 0;
        this.active = true;
        redraw();
    }

    // ===================== Navegação =====================

    public void moveUp()
    {
        selected = (selected - 1 + labels.length) % labels.length;   // dá a volta no topo
        redraw();
    }

    public void moveDown()
    {
        selected = (selected + 1) % labels.length;                   // dá a volta embaixo
        redraw();
    }

    public void setSelected(int index)
    {
        if (index >= 0 && index < labels.length)
        {
            selected = index;
            redraw();
        }
    }

    public int getSelected()
    {
        return selected;
    }

    public boolean isSelectedEnabled()
    {
        return enabled[selected];
    }

    // ===================== Estado das opções =====================

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
        int height = labels.length * rowHeight + PADDING * 2;
        GreenfootImage img = UiArt.panel(width, height);
        int labelSize = Math.max(17, rowHeight * 11 / 20);   // nunca menor que 17 (legibilidade)
        int detailSize = Math.max(15, rowHeight * 9 / 20);   // nunca menor que 15

        for (int i = 0; i < labels.length; i++)
        {
            int y = PADDING + i * rowHeight;
            boolean isSelected = active && i == selected;

            if (isSelected)
            {
                img.setColor(SELECTED_BAR);
                img.fillRect(8, y + 2, width - 16, rowHeight - 4);
                GreenfootImage marker = TextUtil.text(">", labelSize, Palette.HIGHLIGHT);
                img.drawImage(marker, 16, y + (rowHeight - marker.getHeight()) / 2);
            }

            // Cor do texto conforme o estado
            Color color = Palette.TEXT;
            if (!active || !enabled[i])
            {
                color = Palette.TEXT_DIM;
            }
            else if (isSelected)
            {
                color = Palette.HIGHLIGHT;
            }

            GreenfootImage label = TextUtil.text(labels[i], labelSize, color);
            img.drawImage(label, 36, y + (rowHeight - label.getHeight()) / 2);

            // Detalhe alinhado à direita
            if (details != null && details[i] != null)
            {
                Color detailColor = (active && enabled[i]) ? DETAIL_COLOR : Palette.TEXT_DIM;
                GreenfootImage detail = TextUtil.text(details[i], detailSize, detailColor);
                img.drawImage(detail, width - PADDING - 8 - detail.getWidth(),
                              y + (rowHeight - detail.getHeight()) / 2);
            }
        }

        setImage(img);
    }
}
