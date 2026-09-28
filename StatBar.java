import greenfoot.*;

/**
 * StatBar - uma barra de atributo (vida, energia...) com rótulo e valor.
 *
 *   HP [##########------]  60/100
 *
 * A barra se atualiza sozinha: quem usa só chama setValue(atual, máximo).
 * Quando o valor cai, a parte perdida fica clara por um instante e vai
 * "esvaziando" aos poucos (o rastro do dano). Tudo é contado em frames,
 * sem pausas, então funciona no HTML5.
 *
 * É abstrata: cada tipo de barra decide a sua cor (fillColor).
 */
public abstract class StatBar extends Actor
{
    private static final int LABEL_WIDTH = 34;
    private static final int VALUE_WIDTH = 72;
    private static final int BAR_HEIGHT = 14;
    private static final int HEIGHT = 20;

    private String label;
    private int barWidth;
    private int value;          // valor real
    private int max;
    private double shown;       // valor desenhado (vai se aproximando do real)
    private boolean dirty;      // precisa redesenhar?

    public StatBar(String label, int barWidth)
    {
        this.label = label;
        this.barWidth = barWidth;
        this.value = 0;
        this.max = 1;
        this.shown = 0;
        this.dirty = true;
        redraw();
    }

    /**
     * Cor do preenchimento para a proporção atual (0.0 a 1.0).
     */
    protected abstract Color fillColor(double ratio);

    /**
     * Informa o valor atual. Só marca para redesenhar se algo mudou.
     */
    public void setValue(int value, int max)
    {
        if (value != this.value || max != this.max)
        {
            this.value = value;
            this.max = Math.max(1, max);
            dirty = true;
        }
    }

    /**
     * Coloca a barra direto no valor, sem animação (usado ao criar a tela).
     */
    public void snapTo(int value, int max)
    {
        setValue(value, max);
        shown = this.value;
        redraw();
    }

    public void act()
    {
        // Aproxima o valor desenhado do valor real (mais rápido quando a diferença é grande)
        if (shown != value)
        {
            double step = Math.max(0.5, Math.abs(value - shown) / 6.0);
            if (shown > value)
            {
                shown = Math.max(value, shown - step);
            }
            else
            {
                shown = Math.min(value, shown + step);
            }
            dirty = true;
        }

        if (dirty)
        {
            redraw();
        }
    }

    public int getValue()
    {
        return value;
    }

    private void redraw()
    {
        GreenfootImage img = new GreenfootImage(LABEL_WIDTH + barWidth + VALUE_WIDTH, HEIGHT);
        int barX = LABEL_WIDTH;
        int barY = (HEIGHT - BAR_HEIGHT) / 2;
        double ratio = (double) value / max;

        // Rótulo
        GreenfootImage labelImg = TextUtil.text(label, 16, Palette.TEXT);
        img.drawImage(labelImg, 0, (HEIGHT - labelImg.getHeight()) / 2);

        // Moldura e fundo vazio
        img.setColor(Palette.INK);
        img.fillRect(barX, barY, barWidth, BAR_HEIGHT);
        img.setColor(Palette.BAR_BACK);
        img.fillRect(barX + 2, barY + 2, barWidth - 4, BAR_HEIGHT - 4);

        int inner = barWidth - 4;
        int realWidth = (int) Math.round(inner * Math.min(value, max) / (double) max);
        int shownWidth = (int) Math.round(inner * Math.min(shown, max) / max);

        // Rastro do dano: parte que acabou de ser perdida
        if (shownWidth > realWidth)
        {
            img.setColor(Palette.BAR_GHOST);
            img.fillRect(barX + 2, barY + 2, shownWidth, BAR_HEIGHT - 4);
        }

        // Preenchimento (ao ganhar valor, cresce junto com a animação)
        int fillWidth = Math.min(realWidth, shownWidth);
        Color color = fillColor(ratio);
        img.setColor(color);
        img.fillRect(barX + 2, barY + 2, fillWidth, BAR_HEIGHT - 4);

        // Brilho na parte de cima (estilo pixel art)
        img.setColor(Palette.lighter(color, 40));
        img.fillRect(barX + 2, barY + 2, fillWidth, 2);

        // Valor numérico
        GreenfootImage valueImg = TextUtil.text(value + "/" + max, 16, Palette.TEXT);
        img.drawImage(valueImg, barX + barWidth + 8, (HEIGHT - valueImg.getHeight()) / 2);

        setImage(img);
        dirty = false;
    }
}
