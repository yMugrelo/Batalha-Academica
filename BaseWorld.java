import greenfoot.*;

/**
 * BaseWorld - a base de TODAS as telas do jogo.
 *
 * Define o tamanho fixo (800 x 600) e o ciclo de cada frame:
 *   1) lê a tecla apertada (se houver) e entrega para onKey()
 *   2) verifica cliques do mouse em handleMouse()
 *   3) chama update() para animações e contadores
 *
 * Também faz as TRANSIÇÕES: goTo(próximaTela) escurece a tela atual,
 * troca de World e a próxima tela clareia. Durante a transição,
 * teclado e mouse são ignorados (evita cliques duplos).
 *
 * Nada aqui espera o jogador: se nenhuma tecla foi apertada, o frame
 * simplesmente segue. Isso mantém o jogo compatível com o HTML5.
 */
public abstract class BaseWorld extends World
{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private BaseWorld nextWorld;          // tela para onde estamos indo (null = nenhuma)
    private FadeTransition fadeOut;

    public BaseWorld()
    {
        super(WIDTH, HEIGHT, 1);
        setLayers();
    }

    /**
     * Chamado pelo Greenfoot a cada frame (enquanto o Run está ativo).
     */
    public void act()
    {
        String key = Greenfoot.getKey();   // lida sempre, para não "sobrar" tecla para a próxima tela

        // Primeira tecla/clique libera o áudio (exigência dos navegadores)
        if (key != null || Greenfoot.mouseClicked(null))
        {
            SoundManager.onUserInput();
        }

        // M liga/desliga o som em qualquer tela
        if (key != null && Keys.isMute(key))
        {
            SoundManager.toggleMute();
            key = null;
        }

        if (isTransitioning())
        {
            if (fadeOut.isFinished())
            {
                nextWorld.fadeIn();
                Greenfoot.setWorld(nextWorld);
                return;
            }
        }
        else
        {
            if (key != null)
            {
                onKey(key);
            }
            handleMouse();
        }
        update();
    }

    /** Greenfoot pausado (botão Pause): a música pausa junto. */
    public void stopped()
    {
        SoundManager.pause();
    }

    /** Greenfoot voltou a rodar (botão Run): a música continua. */
    public void started()
    {
        SoundManager.resume();
    }

    // ===================== Transições =====================

    /**
     * Vai para outra tela com uma transição suave.
     * Se já existe uma transição acontecendo, o pedido é ignorado.
     */
    public void goTo(BaseWorld next)
    {
        if (isTransitioning())
        {
            return;
        }
        nextWorld = next;
        fadeOut = new FadeTransition(WIDTH, HEIGHT, true);
        addObject(fadeOut, WIDTH / 2, HEIGHT / 2);
    }

    public boolean isTransitioning()
    {
        return nextWorld != null;
    }

    /** A tela começa preta e vai clareando. */
    private void fadeIn()
    {
        addObject(new FadeTransition(WIDTH, HEIGHT, false), WIDTH / 2, HEIGHT / 2);
    }

    /**
     * Define a ordem de desenho (primeiro = por cima). A transição
     * é colocada automaticamente acima de todas as outras camadas.
     */
    protected void setLayers(Class<?>... layers)
    {
        Class<?>[] all = new Class<?>[layers.length + 1];
        all[0] = FadeTransition.class;
        for (int i = 0; i < layers.length; i++)
        {
            all[i + 1] = layers[i];
        }
        setPaintOrder(all);
    }

    // ===================== Ganchos para as telas =====================

    /**
     * Cada tela decide o que fazer com a tecla.
     */
    protected abstract void onKey(String key);

    /**
     * Cliques do mouse. Por padrão não faz nada.
     */
    protected void handleMouse()
    {
    }

    /**
     * Para animações e contadores. Por padrão não faz nada.
     */
    protected void update()
    {
    }
}
