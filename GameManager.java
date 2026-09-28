/**
 * GameManager - controla o FLUXO entre as telas do jogo.
 *
 *   MENU -> SELEÇÃO -> BATALHA -> RESULTADO -> MENU
 *
 * Toda troca de tela passa por aqui. Assim nenhuma tela precisa
 * saber como a outra é construída. Cada método recebe a tela atual
 * ("from") para fazer a transição (escurecer e clarear).
 *
 * Cada tela é um World diferente. Os dados passam pelos construtores
 * (ex.: a matéria escolhida), então não existe estado "global" que
 * possa sobrar depois de um Reset.
 */
public class GameManager
{
    public static void goToMenu(BaseWorld from)
    {
        from.goTo(new MyWorld());
    }

    public static void goToHowToPlay(BaseWorld from)
    {
        from.goTo(new HowToPlayWorld());
    }

    public static void goToCredits(BaseWorld from)
    {
        from.goTo(new CreditsWorld());
    }

    public static void goToSelection(BaseWorld from)
    {
        from.goTo(new SelectWorld());
    }

    public static void startBattle(BaseWorld from, SubjectType type)
    {
        from.goTo(new BattleWorld(type));
    }

    public static void showResult(BaseWorld from, BattleSummary summary)
    {
        from.goTo(new ResultWorld(summary));
    }
}
