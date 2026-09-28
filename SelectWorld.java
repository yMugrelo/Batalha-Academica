import greenfoot.*;

/**
 * SelectWorld - tela "ESCOLHA SUA MATÉRIA".
 *
 * Teclado: SETAS escolhem, ENTER confirma, 1-4 escolhem direto, ESC volta.
 * Mouse: passar por cima escolhe, clicar começa a batalha.
 *
 * O ícone da matéria selecionada flutua; os outros ficam parados.
 */
public class SelectWorld extends BaseWorld
{
    private static final int CARD_Y = 300;
    private static final int LIFT = 10;   // quanto o card selecionado sobe

    private SubjectCard[] cards;
    private FloatingDecoration[] icons;
    private int selected;

    public SelectWorld()
    {
        super();
        setBackground(BackgroundManager.menu());
        SoundManager.playMusic("menu");
        setLayers(FloatingDecoration.class, SubjectCard.class);   // ícones por cima dos cards

        addObject(new Decoration(TextUtil.shadowText("ESCOLHA SUA MATÉRIA", 40, Palette.HIGHLIGHT)), 400, 60);

        SubjectType[] types = SubjectType.values();
        cards = new SubjectCard[types.length];
        icons = new FloatingDecoration[types.length];
        int gap = 12;
        int totalWidth = types.length * SubjectCard.WIDTH + (types.length - 1) * gap;
        int firstX = (WIDTH - totalWidth) / 2 + SubjectCard.WIDTH / 2;

        for (int i = 0; i < types.length; i++)
        {
            int x = firstX + i * (SubjectCard.WIDTH + gap);
            cards[i] = new SubjectCard(types[i], i + 1);
            addObject(cards[i], x, CARD_Y);

            icons[i] = new FloatingDecoration(ImageLibrary.subjectIcon(types[i]), 4, 0.09);
            addObject(icons[i], x, CARD_Y + SubjectCard.ICON_OFFSET_Y);
        }

        addObject(new Decoration(TextUtil.shadowText(
            "SETAS ou MOUSE: escolher    ENTER ou CLIQUE: batalhar    ESC: voltar", 18, Palette.TEXT)),
            400, 575);

        selected = -1;
        select(0);
    }

    protected void onKey(String key)
    {
        if (Keys.isLeft(key) || Keys.isUp(key))
        {
            select((selected - 1 + cards.length) % cards.length);
        }
        else if (Keys.isRight(key) || Keys.isDown(key))
        {
            select((selected + 1) % cards.length);
        }
        else if (Keys.isConfirm(key))
        {
            startBattle(selected);
        }
        else if (Keys.isBack(key))
        {
            GameManager.goToMenu(this);
        }
        else if (key.length() == 1 && key.charAt(0) >= '1' && key.charAt(0) <= '4')
        {
            // Atalho: número escolhe e já começa a batalha
            int index = key.charAt(0) - '1';
            select(index);
            startBattle(index);
        }
    }

    protected void handleMouse()
    {
        for (int i = 0; i < cards.length; i++)
        {
            // O ícone fica por cima do card, então verificamos os dois
            if (Greenfoot.mouseMoved(cards[i]) || Greenfoot.mouseMoved(icons[i]))
            {
                select(i);
            }
            if (Greenfoot.mouseClicked(cards[i]) || Greenfoot.mouseClicked(icons[i]))
            {
                select(i);
                startBattle(i);
            }
        }
    }

    private void startBattle(int index)
    {
        GameManager.startBattle(this, cards[index].getType());
    }

    /**
     * Destaca o card escolhido: borda dourada, sobe um pouco e o ícone flutua.
     */
    private void select(int index)
    {
        if (index == selected)
        {
            return;
        }
        for (int i = 0; i < cards.length; i++)
        {
            boolean isSelected = i == index;
            int y = isSelected ? CARD_Y - LIFT : CARD_Y;
            cards[i].setSelected(isSelected);
            cards[i].setLocation(cards[i].getX(), y);
            icons[i].setHome(cards[i].getX(), y + SubjectCard.ICON_OFFSET_Y);
            icons[i].setFloating(isSelected);
        }
        selected = index;
    }
}
