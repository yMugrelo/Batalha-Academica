/**
 * Keys - nomes das teclas que o jogo usa.
 *
 * O texto recebido vem de Greenfoot.getKey(), que devolve a última tecla
 * apertada (ou null). Ele é lido UMA vez por frame pelo BaseWorld, sem
 * nenhum laço esperando tecla: o jogo nunca fica "travado".
 */
public class Keys
{
    public static boolean isConfirm(String key)
    {
        return key.equals("enter") || key.equals("space");
    }

    public static boolean isBack(String key)
    {
        return key.equals("escape");
    }

    public static boolean isMute(String key)
    {
        return key.equals("m");
    }

    public static boolean isUp(String key)
    {
        return key.equals("up") || key.equals("w");
    }

    public static boolean isDown(String key)
    {
        return key.equals("down") || key.equals("s");
    }

    public static boolean isLeft(String key)
    {
        return key.equals("left") || key.equals("a");
    }

    public static boolean isRight(String key)
    {
        return key.equals("right") || key.equals("d");
    }
}
