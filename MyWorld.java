import greenfoot.*;

/**
 * MyWorld - TELA INICIAL (menu principal).
 *
 * O nome continua "MyWorld" porque é o mundo que o Greenfoot abre
 * primeiro (configurado no projeto). Ele só monta o menu e repassa
 * cada escolha para o GameManager.
 */
public class MyWorld extends BaseWorld
{
    private static final String[] OPTIONS = { "JOGAR", "COMO JOGAR", "CRÉDITOS", "SAIR" };

    private MenuUI menu;
    private boolean exiting;    // true depois de escolher "SAIR"

    public MyWorld()
    {
        super();
        setBackground(BackgroundManager.menu());
        SoundManager.playMusic("menu");

        // Título flutuando devagar
        addObject(new FloatingDecoration(TextUtil.shadowText("BATALHA ACADÊMICA", 52, Palette.HIGHLIGHT),
                                         4, 0.05), 400, 135);
        addObject(new Decoration(TextUtil.shadowText("Sobreviva ao semestre.", 26, Palette.TEXT)), 400, 205);

        menu = new MenuUI(OPTIONS, 300, 44);
        addObject(menu, 400, 390);

        // Personagens decorativos
        addObject(new PlayerSprite(), 130, 455);
        addObject(new BossSprite(SubjectType.CALCULUS), 670, 440);

        addObject(new Decoration(TextUtil.shadowText(
            "SETAS ou MOUSE: navegar    ENTER, ESPAÇO ou CLIQUE: confirmar", 18, Palette.TEXT)), 400, 575);
        exiting = false;
    }

    protected void onKey(String key)
    {
        // Depois de "SAIR", qualquer tecla volta ao menu (útil no desktop após apertar Run de novo)
        if (exiting)
        {
            GameManager.goToMenu(this);
            return;
        }

        if (Keys.isUp(key))
        {
            menu.moveUp();
        }
        else if (Keys.isDown(key))
        {
            menu.moveDown();
        }
        else if (Keys.isConfirm(key))
        {
            choose(menu.getSelected());
        }
    }

    protected void handleMouse()
    {
        if (exiting)
        {
            if (Greenfoot.mouseClicked(null))   // clique em qualquer lugar
            {
                GameManager.goToMenu(this);
            }
            return;
        }

        int clicked = menu.getClickedIndex();
        if (clicked >= 0)
        {
            choose(clicked);
        }
    }

    private void choose(int option)
    {
        switch (option)
        {
            case 0:
                GameManager.goToSelection(this);
                break;
            case 1:
                GameManager.goToHowToPlay(this);
                break;
            case 2:
                GameManager.goToCredits(this);
                break;
            default:
                exit();
                break;
        }
    }

    /**
     * No navegador um jogo não pode fechar a aba, então "SAIR"
     * mostra uma despedida e pausa o jogo (Greenfoot.stop()).
     */
    private void exit()
    {
        exiting = true;
        removeObject(menu);
        addObject(new InfoPanel("ATÉ LOGO!", new String[] {
            "Obrigado por jogar.",
            "Aperte Run e uma tecla (ou clique) para voltar."
        }, 420), 400, 390);
        Greenfoot.stop();
    }
}
