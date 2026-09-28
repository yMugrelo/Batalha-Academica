import greenfoot.*;

/**
 * CharacterSprite - base das APARÊNCIAS animadas (estudante e matérias).
 *
 * É uma pequena máquina de estados de animação (AnimationState).
 * Tudo é contado em frames dentro do act(): nada de Thread.sleep
 * nem laços esperando. Cada frame calcula um deslocamento a partir
 * da posição "de casa" (onde o personagem foi colocado no mundo).
 *
 * As subclasses só dizem o que muda: imagens, direção do ataque,
 * como flutuar parado e como cair na derrota.
 */
public abstract class CharacterSprite extends Actor
{
    private static final int ATTACK_FRAMES = 24;
    private static final int HURT_FRAMES = 24;
    private static final int LUNGE_DISTANCE = 44;   // quanto avança no ataque
    private static final int SHAKE = 5;             // quanto treme ao levar dano

    private GreenfootImage idleImage;
    private GreenfootImage attackImage;
    private GreenfootImage hurtImage;
    private int facing;             // +1 = olha para a direita, -1 = para a esquerda

    private AnimationState state;
    private int frame;              // frames desde o começo do estado atual
    private int homeX;
    private int homeY;

    public CharacterSprite(GreenfootImage idle, GreenfootImage attack, GreenfootImage hurt, int facing)
    {
        this.idleImage = idle;
        this.attackImage = attack;
        this.hurtImage = hurt;
        this.facing = facing;
        this.state = AnimationState.IDLE;
        this.frame = 0;
        setImage(idleImage);
    }

    /**
     * Chamado pelo Greenfoot quando o ator entra no mundo:
     * guardamos a posição de casa.
     */
    protected void addedToWorld(World world)
    {
        homeX = getX();
        homeY = getY();
    }

    // ===================== Comandos =====================

    public void playAttack()
    {
        start(AnimationState.ATTACK, attackImage);
    }

    public void playHurt()
    {
        start(AnimationState.HURT, hurtImage);
    }

    public void playDefeat()
    {
        start(AnimationState.DEFEAT, getImage());
    }

    public void playCelebrate()
    {
        start(AnimationState.CELEBRATE, idleImage);
    }

    /**
     * Ocupado = no meio de um ataque ou de um dano.
     * (Derrota e comemoração não contam: podem durar para sempre.)
     */
    public boolean isBusy()
    {
        return state == AnimationState.ATTACK || state == AnimationState.HURT;
    }

    public AnimationState getState()
    {
        return state;
    }

    private void start(AnimationState newState, GreenfootImage image)
    {
        // Depois de derrotado, o personagem não volta a se mexer
        if (state == AnimationState.DEFEAT)
        {
            return;
        }
        state = newState;
        frame = 0;
        image.setTransparency(255);
        setImage(image);
    }

    // ===================== Ciclo =====================

    public void act()
    {
        frame++;

        switch (state)
        {
            case ATTACK:
                animateAttack();
                break;
            case HURT:
                animateHurt();
                break;
            case DEFEAT:
                animateDefeat(frame);
                break;
            case CELEBRATE:
                // Pulinhos: sobe e desce seguindo o seno
                int hop = (int) Math.round(Math.abs(Math.sin(frame * 0.14)) * 18);
                moveTo(0, -hop);
                break;
            default:
                moveTo(0, idleOffsetY(frame));
                break;
        }
    }

    /**
     * Avança na direção do inimigo e volta (curva de seno: 0 -> máximo -> 0).
     */
    private void animateAttack()
    {
        double progress = (double) frame / ATTACK_FRAMES;
        int dx = (int) Math.round(Math.sin(progress * Math.PI) * LUNGE_DISTANCE) * facing;
        moveTo(dx, 0);

        if (frame >= ATTACK_FRAMES)
        {
            backToIdle();
        }
    }

    /**
     * Treme para os lados e pisca (fica meio transparente a cada 3 frames).
     */
    private void animateHurt()
    {
        int dx = (frame / 2) % 2 == 0 ? SHAKE : -SHAKE;
        moveTo(dx, 0);
        getImage().setTransparency((frame / 3) % 2 == 0 ? 90 : 255);

        if (frame >= HURT_FRAMES)
        {
            getImage().setTransparency(255);
            backToIdle();
        }
    }

    private void backToIdle()
    {
        state = AnimationState.IDLE;
        frame = 0;
        setImage(idleImage);
        moveTo(0, 0);
    }

    /**
     * Coloca o ator na posição de casa + deslocamento.
     */
    protected void moveTo(int dx, int dy)
    {
        setLocation(homeX + dx, homeY + dy);
    }

    // ===================== O que cada personagem define =====================

    /** Deslocamento vertical quando parado (respirar, flutuar). */
    protected abstract int idleOffsetY(int frame);

    /** Animação de derrota, chamada a cada frame com o número do frame. */
    protected abstract void animateDefeat(int frame);
}
