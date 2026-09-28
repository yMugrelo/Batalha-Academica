import greenfoot.*;

/**
 * ImageLibrary - o ÚNICO lugar do jogo que carrega imagens.
 *
 * Para cada imagem ele decide:
 *   - se o PNG existe no projeto  -> carrega o PNG (caminho relativo à pasta images/)
 *   - se ainda não existe         -> usa a arte provisória do PlaceholderArt
 *
 * Por que uma lista (AVAILABLE) em vez de "tentar carregar e ver se dá erro"?
 * No desktop, um arquivo ausente gera exceção; no navegador (HTML5) o
 * comportamento pode ser diferente. Com a lista, só pedimos arquivos que
 * sabemos que existem — funciona igual nos dois lugares.
 *
 * QUANDO ADICIONAR UM PNG: coloque o arquivo na pasta certa e acrescente
 * o caminho dele em AVAILABLE.
 */
public class ImageLibrary
{
    /** PNGs que realmente existem dentro de images/. */
    private static final String[] AVAILABLE = {
        // Exemplo: "player/player_idle.png",
    };

    /** Todos os PNGs que o jogo sabe usar (para o relatório de ausentes). */
    private static final String[] EXPECTED = {
        "player/player_idle.png",
        "player/player_attack.png",
        "player/player_hurt.png",
        "player/player_defeat.png",
        "subjects/calculus.png",
        "subjects/linear_algebra.png",
        "subjects/java_programming.png",
        "subjects/data_structures.png",
        "backgrounds/classroom.png",
        "backgrounds/calculus_room.png",
        "backgrounds/algebra_room.png",
        "backgrounds/java_lab.png",
        "backgrounds/data_structures_room.png",
        "backgrounds/final_exam_room.png",
        "effects/hit.png",
        "effects/critical.png",
        "effects/victory.png"
    };

    // Tamanho do "pixel" da arte provisória
    private static final int PLAYER_BLOCK = 6;   // estudante: 96 x 144
    private static final int BOSS_BLOCK = 7;     // matéria: 140 x 140
    private static final int ICON_BLOCK = 4;     // ícone da matéria: 80 x 80

    // ===================== Consultas =====================

    public static boolean isAvailable(String path)
    {
        for (String available : AVAILABLE)
        {
            if (available.equals(path))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Mostra no Terminal do Greenfoot quais assets ainda faltam.
     * Para usar: clique com o botão direito na classe ImageLibrary
     * e escolha printMissingAssets().
     */
    public static void printMissingAssets()
    {
        for (String path : EXPECTED)
        {
            if (!isAvailable(path))
            {
                System.out.println("Asset ausente: images/" + path);
            }
        }
    }

    // ===================== Imagens =====================

    /**
     * Estudante. pose: "idle", "attack", "hurt" ou "defeat".
     * (Nesta etapa a arte provisória só tem a pose parada.)
     */
    public static GreenfootImage player(String pose)
    {
        String path = "player/player_" + pose + ".png";
        if (isAvailable(path))
        {
            return new GreenfootImage(path);
        }
        return PlaceholderArt.player(PLAYER_BLOCK);
    }

    /**
     * Sprite da matéria em tamanho de batalha.
     */
    public static GreenfootImage subject(SubjectType type)
    {
        String path = subjectPath(type);
        if (isAvailable(path))
        {
            return new GreenfootImage(path);
        }
        return PlaceholderArt.subject(type, BOSS_BLOCK);
    }

    /**
     * Sprite pequeno da matéria (cards da tela de seleção).
     */
    public static GreenfootImage subjectIcon(SubjectType type)
    {
        String path = subjectPath(type);
        if (isAvailable(path))
        {
            // PNG real: reduzimos para caber no card
            GreenfootImage img = new GreenfootImage(path);
            img.scale(80, 80 * img.getHeight() / img.getWidth());
            return img;
        }
        // Arte provisória: desenhada direto no tamanho pequeno (sem borrar os pixels)
        return PlaceholderArt.subject(type, ICON_BLOCK);
    }

    /**
     * Fundo da tela de menu / informações.
     */
    public static GreenfootImage classroom(int width, int height)
    {
        String path = "backgrounds/classroom.png";
        if (isAvailable(path))
        {
            return new GreenfootImage(path);
        }
        return PlaceholderArt.classroom(width, height);
    }

    /**
     * Arena de batalha de uma matéria.
     */
    public static GreenfootImage arena(SubjectType type, int width, int height)
    {
        String path = arenaPath(type);
        if (isAvailable(path))
        {
            return new GreenfootImage(path);
        }
        return PlaceholderArt.arena(type, width, height);
    }

    // ===================== Caminhos =====================

    private static String subjectPath(SubjectType type)
    {
        switch (type)
        {
            case LINEAR_ALGEBRA:
                return "subjects/linear_algebra.png";
            case JAVA_PROGRAMMING:
                return "subjects/java_programming.png";
            case DATA_STRUCTURES:
                return "subjects/data_structures.png";
            default:
                return "subjects/calculus.png";
        }
    }

    private static String arenaPath(SubjectType type)
    {
        switch (type)
        {
            case LINEAR_ALGEBRA:
                return "backgrounds/algebra_room.png";
            case JAVA_PROGRAMMING:
                return "backgrounds/java_lab.png";
            case DATA_STRUCTURES:
                return "backgrounds/data_structures_room.png";
            default:
                return "backgrounds/calculus_room.png";
        }
    }
}
