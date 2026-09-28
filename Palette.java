import greenfoot.*;

/**
 * Palette - todas as cores do jogo em um só lugar.
 *
 * Usar sempre estas constantes (e nunca "new Color(...)" solto pelo código)
 * mantém o visual consistente em todas as telas.
 */
public class Palette
{
    // Interface
    public static final Color INK         = new Color(24, 20, 37);      // contornos e fundos escuros
    public static final Color PANEL       = new Color(28, 24, 48, 215); // painéis semitransparentes
    public static final Color PANEL_EDGE  = new Color(236, 214, 160);   // borda dos painéis
    public static final Color TEXT        = new Color(246, 240, 226);   // texto principal
    public static final Color TEXT_DIM    = new Color(128, 122, 146);   // texto indisponível
    public static final Color HIGHLIGHT   = new Color(255, 204, 84);    // seleção
    public static final Color SHADOW      = new Color(0, 0, 0, 150);    // sombras
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    // Atributos
    public static final Color HP          = new Color(222, 72, 72);
    public static final Color ENERGY      = new Color(72, 160, 232);
    public static final Color KNOWLEDGE   = new Color(156, 112, 232);
    public static final Color CRITICAL    = new Color(255, 150, 40);

    // Personagem
    public static final Color SKIN        = new Color(242, 192, 152);
    public static final Color SKIN_SHADE  = new Color(212, 150, 116);
    public static final Color HAIR        = new Color(72, 50, 42);
    public static final Color HOODIE      = new Color(58, 108, 200);
    public static final Color HOODIE_DARK = new Color(40, 78, 158);
    public static final Color PANTS       = new Color(50, 54, 82);
    public static final Color PANTS_DARK  = new Color(36, 38, 60);
    public static final Color SHOES       = new Color(32, 30, 38);
    public static final Color WHITE       = new Color(250, 250, 245);
    public static final Color ACCENT      = new Color(250, 204, 84);

    // Cenário
    public static final Color PAGE        = new Color(246, 238, 214);
    public static final Color BOARD       = new Color(34, 72, 56);
    public static final Color BOARD_EDGE  = new Color(96, 66, 40);
    public static final Color CHALK       = new Color(226, 236, 226);

    /**
     * Cor principal de cada matéria.
     */
    public static Color subjectColor(SubjectType type)
    {
        switch (type)
        {
            case LINEAR_ALGEBRA:
                return new Color(128, 80, 200);
            case JAVA_PROGRAMMING:
                return new Color(232, 138, 44);
            case DATA_STRUCTURES:
                return new Color(52, 158, 104);
            default:
                return new Color(204, 70, 70);
        }
    }

    /**
     * Cor da parede da arena de cada matéria (mais escura e discreta).
     */
    public static Color roomColor(SubjectType type)
    {
        switch (type)
        {
            case LINEAR_ALGEBRA:
                return new Color(62, 48, 92);
            case JAVA_PROGRAMMING:
                return new Color(40, 46, 62);
            case DATA_STRUCTURES:
                return new Color(38, 70, 60);
            default:
                return new Color(84, 52, 58);
        }
    }

    /**
     * Versão mais escura de uma cor (para sombras e contornos).
     */
    public static Color darker(Color c, int amount)
    {
        return new Color(Math.max(0, c.getRed() - amount),
                         Math.max(0, c.getGreen() - amount),
                         Math.max(0, c.getBlue() - amount));
    }
}
