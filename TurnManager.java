/**
 * TurnManager - controla a ordem dos turnos de UMA batalha.
 *
 * Ele é uma máquina de estados: guarda o estado atual (BattleState)
 * e decide para qual estado ir depois de cada ação.
 *
 * Ele NÃO desenha nada e NÃO lê o teclado: isso é trabalho do BattleWorld.
 * Ele também não calcula dano: isso é trabalho do Student e do Subject.
 */
public class TurnManager
{
    // Quantos "act()" esperar antes do inimigo atacar (uma pequena pausa)
    private static final int ENEMY_DELAY = 40;

    private Student student;       // o jogador
    private Subject enemy;         // a disciplina enfrentada nesta batalha
    private BattleState state;     // estado atual da máquina de estados
    private String message;        // última coisa que aconteceu (para mostrar na tela)
    private int enemyWaitCounter;  // conta a pausa antes do ataque do inimigo

    // Estatísticas da batalha (usadas no resumo da tela de resultado)
    private int turns;             // quantas ações o jogador realizou
    private int damageDealt;       // dano total causado na disciplina
    private int damageTaken;       // dano total recebido
    private int criticalHits;      // quantos acertos críticos
    private int startKnowledge;    // conhecimento no início (para calcular o ganho)

    /**
     * Cria uma batalha entre o estudante e uma disciplina.
     * A batalha sempre começa no turno do jogador.
     */
    public TurnManager(Student student, Subject enemy)
    {
        this.student = student;
        this.enemy = enemy;
        this.state = BattleState.PLAYER_TURN;
        this.message = "Uma prova de " + enemy.getName() + " apareceu!";
        this.enemyWaitCounter = 0;
        this.startKnowledge = student.getKnowledge();
    }

    // ----- Turno do jogador -----

    /**
     * Chamado quando o jogador escolhe uma ação.
     * Só funciona durante o PLAYER_TURN.
     */
    public void playerAction(PlayerAction action)
    {
        // Fora do turno do jogador, ignoramos a escolha
        if (state != BattleState.PLAYER_TURN)
        {
            return;
        }

        // 1) Executa a ação
        boolean actionDone = executePlayerAction(action);

        // Se a ação não aconteceu (ex.: sem energia), o turno continua do jogador
        if (!actionDone)
        {
            return;
        }

        // 2) Verifica se o inimigo foi derrotado
        if (enemy.isDefeated())
        {
            state = BattleState.VICTORY;
            message = "Você passou em " + enemy.getName() + "!";
        }
        else
        {
            // 3) Se não, passa a vez para o inimigo
            state = BattleState.ENEMY_TURN;
            enemyWaitCounter = 0;
        }
    }

    /**
     * Executa a ação escolhida e escreve a mensagem do que aconteceu.
     * Devolve true se a ação foi realizada.
     */
    private boolean executePlayerAction(PlayerAction action)
    {
        // Sem energia suficiente, a ação não acontece e o jogador escolhe outra
        if (!student.canPerform(action))
        {
            message = "Energia insuficiente para " + action.getLabel() + "!";
            return false;
        }

        // O Student sabe COMO fazer a ação; o TurnManager só pede
        int enemyHealthBefore = enemy.getHealth();
        int damage = student.performAction(action, enemy);
        message = describeAction(action, damage);

        // Estatísticas
        turns++;
        damageDealt = damageDealt + (enemyHealthBefore - enemy.getHealth());   // só a vida que realmente saiu
        if (student.wasLastActionCritical())
        {
            criticalHits++;
            message = "CRÍTICO! " + message;
        }

        return true;
    }

    /**
     * Monta o texto que descreve o que a ação fez.
     */
    private String describeAction(PlayerAction action, int damage)
    {
        switch (action)
        {
            case ESTUDAR:
                return "Você estudou! Conhecimento: " + student.getKnowledge() + ".";

            case RESOLVER_QUESTAO:
                return "Você resolveu uma questão: " + damage + " de dano.";

            case USAR_CONHECIMENTO:
                return "Você usou todo o seu conhecimento: " + damage + " de dano!";

            case DESCANSAR:
                return "Você descansou e recuperou vida e energia.";

            default:
                return "";
        }
    }

    // ----- Turno do inimigo -----

    /**
     * Deve ser chamado a cada act() do mundo.
     * Durante o ENEMY_TURN, espera um pouco e então o inimigo ataca.
     */
    public void update()
    {
        if (state == BattleState.ENEMY_TURN)
        {
            enemyWaitCounter++;

            if (enemyWaitCounter >= ENEMY_DELAY)
            {
                enemyTurn();
            }
        }
    }

    /**
     * O inimigo ataca e depois verificamos se o jogador foi derrotado.
     */
    private void enemyTurn()
    {
        // 4) Inimigo escolhe um ataque e usa
        Attack attack = enemy.chooseAttack();
        int studentHealthBefore = student.getHealth();
        int damage = enemy.useAttack(attack, student);
        damageTaken = damageTaken + (studentHealthBefore - student.getHealth());

        // Duas linhas: o que aconteceu + a descrição do ataque
        message = enemy.getName() + " usou " + attack.getName() + "! (-" + damage + " de vida)\n"
                  + "\"" + attack.getDescription() + "\"";

        // 5) Verifica se o jogador foi derrotado
        if (student.isDefeated())
        {
            state = BattleState.DEFEAT;
        }
        else
        {
            // 6) Volta para o turno do jogador
            state = BattleState.PLAYER_TURN;
        }
    }

    // ----- Consultas -----

    public BattleState getState()
    {
        return state;
    }

    public String getMessage()
    {
        return message;
    }

    public Subject getEnemy()
    {
        return enemy;
    }

    public int getTurns()
    {
        return turns;
    }

    public int getDamageDealt()
    {
        return damageDealt;
    }

    public int getDamageTaken()
    {
        return damageTaken;
    }

    public int getCriticalHits()
    {
        return criticalHits;
    }

    public int getKnowledgeGained()
    {
        return student.getKnowledge() - startKnowledge;
    }

    /**
     * A batalha acabou? (vitória ou derrota)
     */
    public boolean isBattleOver()
    {
        return state == BattleState.VICTORY || state == BattleState.DEFEAT;
    }
}
