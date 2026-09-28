import greenfoot.*;

/**
 * TextUtil - funções de ajuda para criar imagens de texto.
 *
 * Usa sempre o construtor GreenfootImage(texto, tamanho, cor, fundo),
 * que funciona no desktop e no navegador (não depende de fontes instaladas).
 */
public class TextUtil
{
    /**
     * Texto simples com fundo transparente.
     */
    public static GreenfootImage text(String text, int size, Color color)
    {
        return new GreenfootImage(text, size, color, Palette.TRANSPARENT);
    }

    /**
     * Texto com uma sombra escura deslocada, para ficar legível sobre qualquer fundo.
     */
    public static GreenfootImage shadowText(String text, int size, Color color)
    {
        GreenfootImage front = text(text, size, color);
        GreenfootImage back = text(text, size, Palette.INK);
        int offset = Math.max(2, size / 16);

        GreenfootImage img = new GreenfootImage(front.getWidth() + offset, front.getHeight() + offset);
        img.drawImage(back, offset, offset);
        img.drawImage(front, 0, 0);
        return img;
    }

    /**
     * Quebra um texto em linhas de no máximo maxChars caracteres,
     * sem cortar palavras. Devolve o texto com "\n" entre as linhas.
     */
    public static String wrap(String text, int maxChars)
    {
        String[] words = text.split(" ");
        String result = "";
        String line = "";

        for (String word : words)
        {
            if (line.length() == 0)
            {
                line = word;
            }
            else if (line.length() + 1 + word.length() <= maxChars)
            {
                line = line + " " + word;
            }
            else
            {
                result = result + line + "\n";
                line = word;
            }
        }

        return result + line;
    }

    /**
     * Desenha uma imagem centralizada horizontalmente dentro de outra.
     */
    public static void drawCentered(GreenfootImage target, GreenfootImage img, int y)
    {
        target.drawImage(img, (target.getWidth() - img.getWidth()) / 2, y);
    }
}
