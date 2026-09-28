/**
 * BattleEvent - o registro de UMA ação que acabou de acontecer na batalha.
 *
 * O TurnManager cria um BattleEvent a cada ação (do jogador ou da matéria).
 * A interface (BattleUI) lê esse registro para decidir quais animações
 * e efeitos tocar. O evento só guarda dados: não muda nada na batalha.
 *
 * As "variações" (changes) são diferenças: -8 = perdeu 8, +20 = ganhou 20.
 */
public class BattleEvent
{
    private final boolean byPlayer;          // true = o aluno agiu; false = a matéria atacou
    private final PlayerAction action;       // ação do aluno (null se foi a matéria)
    private final String attackName;         // ataque da matéria (null se foi o aluno)
    private final boolean critical;
    private final int damage;                // dano do golpe (o mesmo número da mensagem)
    private final int studentHealthChange;
    private final int studentEnergyChange;
    private final int knowledgeChange;
    private final int subjectHealthChange;
    private final boolean finishingBlow;     // esta ação terminou a batalha?

    private BattleEvent(boolean byPlayer, PlayerAction action, String attackName, boolean critical,
                        int damage, int studentHealthChange, int studentEnergyChange, int knowledgeChange,
                        int subjectHealthChange, boolean finishingBlow)
    {
        this.byPlayer = byPlayer;
        this.action = action;
        this.attackName = attackName;
        this.critical = critical;
        this.damage = damage;
        this.studentHealthChange = studentHealthChange;
        this.studentEnergyChange = studentEnergyChange;
        this.knowledgeChange = knowledgeChange;
        this.subjectHealthChange = subjectHealthChange;
        this.finishingBlow = finishingBlow;
    }

    /** Evento de uma ação do aluno. */
    public static BattleEvent player(PlayerAction action, boolean critical, int damage,
                                     int studentHealthChange, int studentEnergyChange,
                                     int knowledgeChange, int subjectHealthChange,
                                     boolean finishingBlow)
    {
        return new BattleEvent(true, action, null, critical, damage, studentHealthChange, studentEnergyChange,
                               knowledgeChange, subjectHealthChange, finishingBlow);
    }

    /** Evento de um ataque da matéria. */
    public static BattleEvent subject(String attackName, int damage, int studentHealthChange,
                                      int studentEnergyChange, boolean finishingBlow)
    {
        return new BattleEvent(false, null, attackName, false, damage, studentHealthChange, studentEnergyChange,
                               0, 0, finishingBlow);
    }

    /** O aluno atacou? (Resolver Questão ou Usar Conhecimento) */
    public boolean isPlayerAttack()
    {
        return byPlayer && (action == PlayerAction.RESOLVER_QUESTAO
                            || action == PlayerAction.USAR_CONHECIMENTO);
    }

    public boolean isByPlayer()
    {
        return byPlayer;
    }

    public PlayerAction getAction()
    {
        return action;
    }

    public String getAttackName()
    {
        return attackName;
    }

    public boolean isCritical()
    {
        return critical;
    }

    public int getDamage()
    {
        return damage;
    }

    public int getStudentHealthChange()
    {
        return studentHealthChange;
    }

    public int getStudentEnergyChange()
    {
        return studentEnergyChange;
    }

    public int getKnowledgeChange()
    {
        return knowledgeChange;
    }

    public int getSubjectHealthChange()
    {
        return subjectHealthChange;
    }

    public boolean isFinishingBlow()
    {
        return finishingBlow;
    }
}
