import greenfoot.*;

/**
 * InfoWorld - tela de texto (COMO JOGAR, CRÉDITOS).
 * ESC ou ENTER voltam para o menu.
 */
public class InfoWorld extends BaseWorld
{
    public InfoWorld()
    {
        this("INFORMAÇÕES", new String[] { "" });
    }

    public InfoWorld(String title, String[] lines)
    {
        super();
        setBackground(ImageLibrary.classroom(WIDTH, HEIGHT));
        addObject(new InfoPanel(title, lines, 640), 400, 290);
        addObject(new Decoration(TextUtil.shadowText("ESC: voltar ao menu", 18, Palette.TEXT)), 400, 575);
    }

    /**
     * Tela "COMO JOGAR". Os números vêm do enum e do Student.
     */
    public static InfoWorld howToPlay()
    {
        return new InfoWorld("COMO JOGAR", new String[] {
            "Vença a matéria antes que ela vença você.",
            "",
            "[1] Resolver Questão: dano = conhecimento.",
            "[2] Estudar: +" + Student.STUDY_ENERGY_GAIN + " energia e +"
                + Student.STUDY_KNOWLEDGE_GAIN + " conhecimento.",
            "[3] Usar Conhecimento: dano x" + Student.KNOWLEDGE_MULTIPLIER
                + ", custa " + PlayerAction.USAR_CONHECIMENTO.getEnergyCost() + " de energia.",
            "[4] Descansar: +" + Student.REST_HEALTH_GAIN + " vida e +"
                + Student.REST_ENERGY_GAIN + " energia.",
            "",
            "Ataques podem ser CRÍTICOS (+50% de força).",
            "As matérias ficam mais fortes a cada turno:",
            "estude antes de enfrentar as mais difíceis!",
            "",
            "SETAS: navegar   ENTER/ESPAÇO: confirmar   ESC: voltar"
        });
    }

    /**
     * Tela "CRÉDITOS".
     */
    public static InfoWorld credits()
    {
        return new InfoWorld("CRÉDITOS", new String[] {
            "BATALHA ACADÊMICA",
            "",
            "Projeto de faculdade desenvolvido em Java",
            "com o Greenfoot.",
            "",
            "Programação e design: (seu nome aqui)",
            "Arte: provisória, desenhada em código",
            "",
            "Obrigado por jogar!"
        });
    }

    protected void onKey(String key)
    {
        if (Keys.isBack(key) || Keys.isConfirm(key))
        {
            GameManager.goToMenu();
        }
    }
}
