import greenfoot.*;

/**
 * InfoPanel - painel com um título e várias linhas de texto.
 * Usado em "Como jogar", "Créditos" e no resumo da batalha.
 */
public class InfoPanel extends Actor
{
    private static final int PADDING = 22;
    private static final int TITLE_SIZE = 30;
    private static final int LINE_SIZE = 20;
    private static final int LINE_HEIGHT = 26;

    public InfoPanel(String title, String[] lines, int width)
    {
        GreenfootImage titleImg = TextUtil.text(title, TITLE_SIZE, Palette.HIGHLIGHT);
        int height = PADDING * 2 + titleImg.getHeight() + 14 + lines.length * LINE_HEIGHT;

        GreenfootImage img = UiArt.panel(width, height);
        TextUtil.drawCentered(img, titleImg, PADDING);

        int y = PADDING + titleImg.getHeight() + 14;
        for (String line : lines)
        {
            if (line.length() > 0)
            {
                GreenfootImage lineImg = TextUtil.text(line, LINE_SIZE, Palette.TEXT);
                TextUtil.drawCentered(img, lineImg, y);
            }
            y = y + LINE_HEIGHT;
        }

        setImage(img);
    }
}
