import greenfoot.*;

/**
 * BaseWorld - a base de TODAS as telas do jogo.
 *
 * Define o tamanho fixo (800 x 600) e o ciclo de cada frame:
 *   1) lê a tecla apertada (se houver) e entrega para onKey()
 *   2) chama update() para animações e contadores
 *
 * Nada aqui espera o jogador: se nenhuma tecla foi apertada, o frame
 * simplesmente segue. Isso mantém o jogo compatível com o HTML5.
 */
public abstract class BaseWorld extends World
{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public BaseWorld()
    {
        super(WIDTH, HEIGHT, 1);
    }

    /**
     * Chamado pelo Greenfoot a cada frame (enquanto o Run está ativo).
     */
    public void act()
    {
        String key = Greenfoot.getKey();   // null se nada foi apertado
        if (key != null)
        {
            onKey(key);
        }
        update();
    }

    /**
     * Cada tela decide o que fazer com a tecla.
     */
    protected abstract void onKey(String key);

    /**
     * Para animações e contadores. Por padrão não faz nada.
     */
    protected void update()
    {
    }
}
