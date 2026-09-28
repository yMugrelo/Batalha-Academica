import greenfoot.*;

/**
 * SubjectCard - o "card" de uma matéria na tela de seleção.
 * Mostra ícone, nome, dificuldade e uma descrição curta.
 */
public class SubjectCard extends Actor
{
    public static final int WIDTH = 180;
    public static final int HEIGHT = 330;

    private SubjectType type;
    private int number;         // 1 a 4 (tecla de atalho)
    private boolean selected;

    public SubjectCard(SubjectType type, int number)
    {
        this.type = type;
        this.number = number;
        this.selected = false;
        redraw();
    }

    public void setSelected(boolean value)
    {
        selected = value;
        redraw();
    }

    public SubjectType getType()
    {
        return type;
    }

    private void redraw()
    {
        // Um Subject temporário só para ler nome, dificuldade e descrição
        Subject info = type.create();
        Color main = Palette.subjectColor(type);

        GreenfootImage img = UiArt.panel(WIDTH, HEIGHT, selected ? Palette.HIGHLIGHT : Palette.PANEL_EDGE);

        // Número do atalho
        img.drawImage(TextUtil.text("[" + number + "]", 18,
                      selected ? Palette.HIGHLIGHT : Palette.TEXT_DIM), 12, 10);

        // Ícone sobre um fundo com a cor da matéria
        img.setColor(Palette.darker(main, 90));
        img.fillRect(30, 34, WIDTH - 60, 104);
        GreenfootImage icon = ImageLibrary.subjectIcon(type);
        TextUtil.drawCentered(img, icon, 34 + (104 - icon.getHeight()) / 2);

        // Nome (pode ocupar 2 linhas)
        GreenfootImage name = TextUtil.text(TextUtil.wrap(info.getName(), 13), 22,
                                            selected ? Palette.HIGHLIGHT : Palette.TEXT);
        TextUtil.drawCentered(img, name, 150);

        // Dificuldade: 4 quadradinhos, preenchidos conforme o nível
        GreenfootImage label = TextUtil.text("DIFICULDADE", 14, Palette.TEXT_DIM);
        TextUtil.drawCentered(img, label, 212);
        int pipsX = (WIDTH - (4 * 18 - 6)) / 2;
        for (int i = 0; i < 4; i++)
        {
            img.setColor(i < info.getDifficulty() ? Palette.HP : Palette.darker(Palette.TEXT_DIM, 50));
            img.fillRect(pipsX + i * 18, 232, 12, 12);
        }

        // Descrição
        GreenfootImage description = TextUtil.text(TextUtil.wrap(info.getDescription(), 18), 16,
                                                   Palette.TEXT);
        TextUtil.drawCentered(img, description, 260);

        setImage(img);
    }
}
