import greenfoot.*;

/**
 * DialogueBox - a caixa de mensagens na parte de baixo da batalha.
 *
 * - fundo semitransparente com borda (UiArt.panel)
 * - uma "etiqueta" no topo com o estado do turno (SEU TURNO, VITÓRIA...)
 * - até 2 linhas de texto, que aparecem letra por letra
 * - "CRÍTICO!" no começo da mensagem aparece em laranja
 *
 * O efeito de digitação é só visual e contado em frames:
 * não atrasa nem bloqueia a lógica da batalha.
 */
public class DialogueBox extends Actor
{
    private static final int TAG_HEIGHT = 22;     // etiqueta sobre a borda
    private static final int PANEL_HEIGHT = 64;
    private static final int TEXT_SIZE = 20;
    private static final int CHARS_PER_FRAME = 3;
    private static final String CRITICAL_PREFIX = "CRÍTICO!";

    private int width;
    private String tag;
    private Color tagColor;
    private String message;
    private int revealed;       // quantas letras já aparecem

    public DialogueBox(int width)
    {
        this.width = width;
        this.tag = "";
        this.tagColor = Palette.HIGHLIGHT;
        this.message = "";
        this.revealed = 0;
        redraw();
    }

    /**
     * Troca a mensagem. Se for a mesma de antes, nada acontece.
     */
    public void setMessage(String text)
    {
        if (!text.equals(message))
        {
            message = text;
            revealed = 0;
            redraw();
        }
    }

    /**
     * Troca a etiqueta de estado (ex.: "SEU TURNO").
     */
    public void setTag(String text, Color color)
    {
        if (!text.equals(tag) || !color.equals(tagColor))
        {
            tag = text;
            tagColor = color;
            redraw();
        }
    }

    public void act()
    {
        if (revealed < message.length())
        {
            revealed = Math.min(message.length(), revealed + CHARS_PER_FRAME);
            redraw();
        }
    }

    private void redraw()
    {
        GreenfootImage img = new GreenfootImage(width, TAG_HEIGHT / 2 + PANEL_HEIGHT);
        img.drawImage(UiArt.panel(width, PANEL_HEIGHT), 0, TAG_HEIGHT / 2);

        // Etiqueta com o estado do turno
        if (tag.length() > 0)
        {
            GreenfootImage tagText = TextUtil.text(tag, 15, Palette.INK);
            int tagWidth = tagText.getWidth() + 20;
            img.setColor(Palette.INK);
            img.fillRect(16, 0, tagWidth + 4, TAG_HEIGHT);
            img.setColor(tagColor);
            img.fillRect(18, 2, tagWidth, TAG_HEIGHT - 4);
            img.drawImage(tagText, 28, (TAG_HEIGHT - tagText.getHeight()) / 2);
        }

        // Texto (só a parte já revelada)
        String visible = message.substring(0, revealed);
        String[] lines = visible.split("\n");
        int y = TAG_HEIGHT / 2 + 14;
        for (String line : lines)
        {
            drawLine(img, line, 22, y);
            y = y + TEXT_SIZE + 4;
        }

        setImage(img);
    }

    /**
     * Desenha uma linha; se ela começa com "CRÍTICO!", essa parte fica laranja.
     */
    private void drawLine(GreenfootImage img, String line, int x, int y)
    {
        if (line.startsWith(CRITICAL_PREFIX))
        {
            GreenfootImage critical = TextUtil.text(CRITICAL_PREFIX, TEXT_SIZE, Palette.CRITICAL);
            img.drawImage(critical, x, y);
            x = x + critical.getWidth();
            line = line.substring(CRITICAL_PREFIX.length());
        }
        if (line.length() > 0)   // imagem de texto vazio pode dar erro
        {
            img.drawImage(TextUtil.text(line, TEXT_SIZE, Palette.TEXT), x, y);
        }
    }
}
