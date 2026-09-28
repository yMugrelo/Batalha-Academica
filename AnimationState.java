/**
 * AnimationState - o que um personagem está fazendo na tela.
 */
public enum AnimationState
{
    IDLE,        // parado (respirando / flutuando)
    ATTACK,      // avançando para atacar
    HURT,        // tremendo e piscando depois de levar dano
    DEFEAT,      // caindo / sumindo
    CELEBRATE    // pulando de alegria (vitória)
}
