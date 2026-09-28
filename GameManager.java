import greenfoot.*;

/**
 * GameManager - controla o FLUXO entre as telas do jogo.
 *
 *   MENU -> SELEÇÃO -> BATALHA -> RESULTADO -> MENU
 *
 * Toda troca de tela passa por aqui. Assim nenhuma tela precisa
 * saber como a outra é construída, e se um dia quisermos uma
 * transição (ex.: escurecer a tela), mudamos só esta classe.
 *
 * Cada tela é um World diferente, trocado com Greenfoot.setWorld().
 * Os dados passam pelos construtores (ex.: a matéria escolhida), então
 * não existe estado "global" que possa sobrar depois de um Reset.
 */
public class GameManager
{
    public static void goToMenu()
    {
        Greenfoot.setWorld(new MyWorld());
    }

    public static void goToHowToPlay()
    {
        Greenfoot.setWorld(InfoWorld.howToPlay());
    }

    public static void goToCredits()
    {
        Greenfoot.setWorld(InfoWorld.credits());
    }

    public static void goToSelection()
    {
        Greenfoot.setWorld(new SelectWorld());
    }

    public static void startBattle(SubjectType type)
    {
        Greenfoot.setWorld(new BattleWorld(type));
    }

    public static void showResult(BattleSummary summary)
    {
        Greenfoot.setWorld(new ResultWorld(summary));
    }
}
