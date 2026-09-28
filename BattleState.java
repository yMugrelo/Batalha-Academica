/**
 * BattleState - os estados possíveis de uma batalha.
 *
 * Um enum é um tipo com uma lista FIXA de valores.
 * Uma batalha está sempre em exatamente UM destes estados.
 */
public enum BattleState
{
    PLAYER_TURN,   // o jogador está escolhendo uma ação
    ENEMY_TURN,    // a disciplina vai atacar
    VICTORY,       // a disciplina foi derrotada (fim da batalha)
    DEFEAT         // o estudante foi derrotado (fim da batalha)
}
