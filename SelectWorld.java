import greenfoot.*;

/**
 * SelectWorld - tela "ESCOLHA SUA MATÉRIA".
 *
 * SETAS escolhem, ENTER confirma, 1-4 escolhem direto, ESC volta ao menu.
 */
public class SelectWorld extends BaseWorld
{
    private SubjectCard[] cards;
    private int selected;

    public SelectWorld()
    {
        super();
        setBackground(ImageLibrary.classroom(WIDTH, HEIGHT));

        addObject(new Decoration(TextUtil.shadowText("ESCOLHA SUA MATÉRIA", 40, Palette.HIGHLIGHT)), 400, 60);

        SubjectType[] types = SubjectType.values();
        cards = new SubjectCard[types.length];
        int gap = 12;
        int totalWidth = types.length * SubjectCard.WIDTH + (types.length - 1) * gap;
        int firstX = (WIDTH - totalWidth) / 2 + SubjectCard.WIDTH / 2;

        for (int i = 0; i < types.length; i++)
        {
            cards[i] = new SubjectCard(types[i], i + 1);
            addObject(cards[i], firstX + i * (SubjectCard.WIDTH + gap), 300);
        }

        addObject(new Decoration(TextUtil.shadowText(
            "SETAS: escolher    ENTER: batalhar    ESC: voltar", 18, Palette.TEXT)), 400, 575);

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
            GameManager.startBattle(cards[selected].getType());
        }
        else if (Keys.isBack(key))
        {
            GameManager.goToMenu();
        }
        else if (key.length() == 1 && key.charAt(0) >= '1' && key.charAt(0) <= '4')
        {
            // Atalho: número escolhe e já começa a batalha
            GameManager.startBattle(cards[key.charAt(0) - '1'].getType());
        }
    }

    /**
     * Destaca o card escolhido (e o "levanta" um pouco).
     */
    private void select(int index)
    {
        for (int i = 0; i < cards.length; i++)
        {
            boolean isSelected = i == index;
            cards[i].setSelected(isSelected);
            cards[i].setLocation(cards[i].getX(), isSelected ? 290 : 300);
        }
        selected = index;
    }
}
