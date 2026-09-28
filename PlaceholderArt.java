import greenfoot.*;

/**
 * PlaceholderArt - arte PROVISÓRIA desenhada em código (pixel art).
 *
 * Enquanto os PNGs não existem, o ImageLibrary pede as imagens aqui.
 * Cada sprite é uma "grade" de caracteres: cada caractere vira um
 * quadradinho colorido (um "pixel" grande). Só usa fillRect, então
 * funciona igual no desktop e no navegador (HTML5).
 */
public class PlaceholderArt
{
    // ===================== Ferramenta de desenho =====================

    /**
     * Desenha uma grade de caracteres. Cada caractere de "keys" usa
     * a cor de mesma posição em "colors". Outros caracteres ficam transparentes.
     */
    private static GreenfootImage drawGrid(String[] grid, int block, String keys, Color[] colors)
    {
        int columns = grid[0].length();
        GreenfootImage img = new GreenfootImage(columns * block, grid.length * block);

        for (int y = 0; y < grid.length; y++)
        {
            for (int x = 0; x < columns; x++)
            {
                int index = keys.indexOf(grid[y].charAt(x));
                if (index >= 0)
                {
                    img.setColor(colors[index]);
                    img.fillRect(x * block, y * block, block, block);
                }
            }
        }
        return img;
    }

    // ===================== Estudante =====================

    private static final String[] PLAYER = {
        "....KKKKKKKK....",
        "...KHHHHHHHHK...",
        "..KHHHHHHHHHHK..",
        "..KHHHHHHHHHHK..",
        "..KHHSSSSSSHHK..",
        "..KHSSSSSSSSHK..",
        "..KHSWESSWESHK..",
        "...KSSSSSSSSK...",
        "...KSSSssSSSK...",
        "....KSSSSSSK....",
        ".....KKSSKK.....",
        "..KKBBBBBBBBKK..",
        "..KBBBYBBYBBBK..",
        ".KBBBBYBBYBBBBK.",
        ".KBbBBBBBBBBbBK.",
        ".KBbBBBBBBBBbBK.",
        ".KSKBBBBBBBBKSK.",
        ".KKKbbbbbbbbKKK.",
        "...KPPPPPPPPK...",
        "...KPPPppPPPK...",
        "...KPPPKKPPPK...",
        "...KpPPKKPPpK...",
        "...KOOOKKOOOK...",
        "..KOOOOK.KOOOOK."
    };

    public static GreenfootImage player(int block)
    {
        return drawGrid(PLAYER, block, "KHSsWEBbYPpO", new Color[] {
            Palette.INK, Palette.HAIR, Palette.SKIN, Palette.SKIN_SHADE,
            Palette.WHITE, Palette.INK, Palette.HOODIE, Palette.HOODIE_DARK,
            Palette.ACCENT, Palette.PANTS, Palette.PANTS_DARK, Palette.SHOES
        });
    }

    /**
     * Pose de ataque: o estudante estica o braço com um lápis gigante.
     * A imagem ganha margem dos DOIS lados para o centro não "pular"
     * quando o sprite troca de pose.
     */
    public static GreenfootImage playerAttack(int block)
    {
        GreenfootImage body = player(block);
        int pad = 5 * block;
        GreenfootImage img = new GreenfootImage(body.getWidth() + pad * 2, body.getHeight());
        img.drawImage(body, pad, 0);

        // Lápis saindo da mão direita (linha 16 da grade)
        int handX = pad + 14 * block;
        int handY = 16 * block;
        img.setColor(Palette.INK);                       // contorno
        img.fillRect(handX, handY - 2, 5 * block + 2, block + 4);
        img.setColor(Palette.ACCENT);                    // corpo amarelo
        img.fillRect(handX, handY, 4 * block, block);
        img.setColor(Palette.PAGE);                      // madeira apontada
        img.fillRect(handX + 4 * block, handY, block, block);
        return img;
    }

    // ===================== Efeitos =====================

    // Estrela de impacto (cada caractere vira um bloco)
    private static final String[] BURST = {
        "....Y....",
        "....Y....",
        ".Y..Y..Y.",
        "..YYOYY..",
        "YYYOWOYYY",
        "..YYOYY..",
        ".Y..Y..Y.",
        "....Y....",
        "....Y...."
    };

    /**
     * Estrela de impacto com a cor de fora e a cor do miolo escolhidas.
     */
    public static GreenfootImage burst(int block, Color outer, Color core)
    {
        return drawGrid(BURST, block, "YOW", new Color[] { outer, core, Palette.WHITE });
    }

    /**
     * Um pedacinho de confete.
     */
    public static GreenfootImage confetti(Color color)
    {
        GreenfootImage img = new GreenfootImage(8, 8);
        img.setColor(color);
        img.fillRect(0, 0, 8, 8);
        img.setColor(Palette.darker(color, 50));
        img.fillRect(0, 6, 8, 2);
        return img;
    }

    // ===================== Matérias (livros-monstro) =====================

    // Corpo comum a todas as matérias: um livro vivo com olhos bravos.
    // A área "L" no meio da capa é a etiqueta onde vai o emblema da matéria.
    private static final String[] BOOK = {
        "..KKKKKKKKKKKKKKK...",
        ".KcCCCCCCCCCCCCCCK..",
        ".KcCCCCCCCCCCCCCCKP.",
        ".KcCAACCCCCCAACCCKP.",
        ".KcCCAACCCCAACCCCKP.",
        ".KcCWWWCCCCWWWCCCKP.",
        ".KcCWEWCCCCWEWCCCKP.",
        ".KcCWWWCCCCWWWCCCKP.",
        ".KcCCCCCCCCCCCCCCKP.",
        ".KcCCCCKKKKKKCCCCKP.",
        ".KcCCCCCCCCCCCCCCKP.",
        ".KcCCLLLLLLLLLLCCKP.",
        ".KcCCLLLLLLLLLLCCKP.",
        ".KcCCLLLLLLLLLLCCKP.",
        ".KcCCLLLLLLLLLLCCKP.",
        ".KcCCLLLLLLLLLLCCKP.",
        ".KcCCLLLLLLLLLLCCKP.",
        ".KcCCLLLLLLLLLLCCKP.",
        ".KcCCCCCCCCCCCCCCKP.",
        "..KKKKKKKKKKKKKKKKK."
    };

    // Emblemas de cada matéria (desenhados sobre a etiqueta)
    private static final String[] EMBLEM_CALCULUS = {      // integral
        "...MM",
        "..M..",
        "..M..",
        "..M..",
        "MM..."
    };
    private static final String[] EMBLEM_ALGEBRA = {       // matriz
        "MM...MM",
        "M.M.M.M",
        "M.....M",
        "M.M.M.M",
        "MM...MM"
    };
    private static final String[] EMBLEM_JAVA = {          // chaves { }
        ".M...M.",
        ".M...M.",
        "M.....M",
        ".M...M.",
        ".M...M."
    };
    private static final String[] EMBLEM_DATA = {          // árvore binária
        "..MMM..",
        "...M...",
        ".M...M.",
        "MMM.MMM",
        "......."
    };

    /**
     * Desenha o livro-monstro de uma matéria no tamanho de bloco pedido.
     */
    public static GreenfootImage subject(SubjectType type, int block)
    {
        Color main = Palette.subjectColor(type);
        Color shade = Palette.darker(main, 60);

        GreenfootImage img = drawGrid(BOOK, block, "KcCAWELP", new Color[] {
            Palette.INK, shade, main, Palette.INK,
            Palette.WHITE, Palette.INK, Palette.PAGE, Palette.PAGE
        });

        // Emblema centralizado na etiqueta (colunas 5 a 14, linhas 12 a 16)
        String[] emblem = emblemFor(type);
        GreenfootImage emblemImg = drawGrid(emblem, block, "M", new Color[] { shade });
        int labelX = 5 * block;
        int labelWidth = 10 * block;
        img.drawImage(emblemImg, labelX + (labelWidth - emblemImg.getWidth()) / 2, 12 * block);

        return img;
    }

    private static String[] emblemFor(SubjectType type)
    {
        switch (type)
        {
            case LINEAR_ALGEBRA:
                return EMBLEM_ALGEBRA;
            case JAVA_PROGRAMMING:
                return EMBLEM_JAVA;
            case DATA_STRUCTURES:
                return EMBLEM_DATA;
            default:
                return EMBLEM_CALCULUS;
        }
    }

    // ===================== Cenários =====================

    /**
     * Sala de aula genérica (menu e telas de informação).
     */
    public static GreenfootImage classroom(int width, int height)
    {
        GreenfootImage img = room(width, height, height * 2 / 3, new Color(58, 62, 88), new Color(92, 70, 56));
        board(img, 100, 60, width - 200, 200);
        return img;
    }

    /**
     * Arena de batalha de uma matéria: sala com a cor da matéria
     * e um quadro com desenhos do conteúdo.
     */
    public static GreenfootImage arena(SubjectType type, int width, int height)
    {
        // O chão começa mais alto que no menu: os personagens ficam de pé
        // sobre ele, acima da caixa de mensagens.
        GreenfootImage img = room(width, height, 340, Palette.roomColor(type), new Color(84, 66, 54));
        int bx = 230;
        int by = 128;   // abaixo do HUD
        int bw = width - 460;
        int bh = 128;
        board(img, bx, by, bw, bh);
        boardContent(img, type, bx, by, bw, bh);
        return img;
    }

    /**
     * Parede com painéis, rodapé e piso quadriculado.
     */
    private static GreenfootImage room(int width, int height, int floorY, Color wall, Color floor)
    {
        GreenfootImage img = new GreenfootImage(width, height);

        // Parede
        img.setColor(wall);
        img.fillRect(0, 0, width, floorY);

        // Painéis verticais na parede
        img.setColor(Palette.darker(wall, 14));
        for (int x = 0; x < width; x += 100)
        {
            img.fillRect(x, 0, 4, floorY);
        }

        // Faixa superior e rodapé
        img.setColor(Palette.darker(wall, 28));
        img.fillRect(0, 0, width, 12);
        img.fillRect(0, floorY - 40, width, 40);
        img.setColor(Palette.darker(wall, 44));
        img.fillRect(0, floorY - 44, width, 4);

        // Piso quadriculado
        Color floorLight = floor;
        Color floorDark = Palette.darker(floor, 16);
        int tile = 50;
        for (int y = floorY; y < height; y += tile)
        {
            for (int x = 0; x < width; x += tile)
            {
                boolean light = ((x / tile) + (y / tile)) % 2 == 0;
                img.setColor(light ? floorLight : floorDark);
                img.fillRect(x, y, tile, tile);
            }
        }

        // Linha que separa parede e chão
        img.setColor(Palette.INK);
        img.fillRect(0, floorY, width, 4);

        return img;
    }

    /**
     * Quadro-negro com moldura de madeira.
     */
    private static void board(GreenfootImage img, int x, int y, int w, int h)
    {
        img.setColor(Palette.INK);
        img.fillRect(x - 12, y - 12, w + 24, h + 24);
        img.setColor(Palette.BOARD_EDGE);
        img.fillRect(x - 8, y - 8, w + 16, h + 16);
        img.setColor(Palette.BOARD);
        img.fillRect(x, y, w, h);

        // Bandeja de giz
        img.setColor(Palette.darker(Palette.BOARD_EDGE, 20));
        img.fillRect(x - 8, y + h + 8, w + 16, 8);
    }

    /**
     * Desenhos em giz no quadro, diferentes para cada matéria.
     */
    private static void boardContent(GreenfootImage img, SubjectType type, int x, int y, int w, int h)
    {
        img.setColor(Palette.CHALK);

        switch (type)
        {
            case CALCULUS:
                // Eixos e uma curva (feita com pontinhos grandes)
                img.fillRect(x + 20, y + h - 30, w / 2 - 20, 3);
                img.fillRect(x + 30, y + 15, 3, h - 40);
                for (int i = 0; i < w / 2 - 40; i += 4)
                {
                    int cy = y + h / 2 + (int) (Math.sin(i / 14.0) * 30);
                    img.fillRect(x + 34 + i, cy, 4, 4);
                }
                img.drawImage(TextUtil.text("f'(x) = lim", 22, Palette.CHALK), x + w / 2 + 20, y + 22);
                img.drawImage(TextUtil.text("∫ f(x) dx", 22, Palette.CHALK), x + w / 2 + 20, y + 72);
                break;

            case LINEAR_ALGEBRA:
                img.drawImage(TextUtil.text("[ 2  1 ]", 24, Palette.CHALK), x + 30, y + 28);
                img.drawImage(TextUtil.text("[ 0  3 ]", 24, Palette.CHALK), x + 30, y + 63);
                img.drawImage(TextUtil.text("det(A) = 6", 22, Palette.CHALK), x + w / 2 + 10, y + 24);
                img.drawImage(TextUtil.text("Av = λv", 22, Palette.CHALK), x + w / 2 + 10, y + 72);
                break;

            case JAVA_PROGRAMMING:
                img.drawImage(TextUtil.text("public class Aluno {", 20, Palette.CHALK), x + 25, y + 18);
                img.drawImage(TextUtil.text("    int nota = 10;", 20, Palette.CHALK), x + 25, y + 50);
                img.drawImage(TextUtil.text("}", 20, Palette.CHALK), x + 25, y + 82);
                break;

            default:
                // Árvore binária: nós e ligações
                int cx = x + w / 2;
                int[][] nodes = { {cx, y + 24}, {cx - 90, y + 62}, {cx + 90, y + 62},
                                  {cx - 130, y + 100}, {cx - 50, y + 100}, {cx + 50, y + 100} };
                int[][] edges = { {0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5} };
                for (int[] e : edges)
                {
                    img.drawLine(nodes[e[0]][0], nodes[e[0]][1], nodes[e[1]][0], nodes[e[1]][1]);
                    img.drawLine(nodes[e[0]][0] + 1, nodes[e[0]][1], nodes[e[1]][0] + 1, nodes[e[1]][1]);
                }
                for (int[] n : nodes)
                {
                    img.setColor(Palette.CHALK);
                    img.fillRect(n[0] - 10, n[1] - 10, 20, 20);
                    img.setColor(Palette.BOARD);
                    img.fillRect(n[0] - 6, n[1] - 6, 12, 12);
                }
                break;
        }
    }
}
