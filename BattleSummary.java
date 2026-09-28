/**
 * BattleSummary - o resultado de uma batalha que acabou.
 *
 * É criado pelo BattleWorld quando a batalha termina e entregue
 * para a tela de resultado. Só guarda dados (não muda depois de criado).
 */
public class BattleSummary
{
    private final SubjectType subjectType;
    private final String subjectName;
    private final boolean victory;
    private final int turns;
    private final int damageDealt;
    private final int damageTaken;
    private final int criticalHits;
    private final int knowledgeGained;

    /**
     * Copia os números do TurnManager no momento em que a batalha acaba.
     */
    public BattleSummary(SubjectType subjectType, TurnManager turnManager)
    {
        this.subjectType = subjectType;
        this.subjectName = turnManager.getEnemy().getName();
        this.victory = turnManager.getState() == BattleState.VICTORY;
        this.turns = turnManager.getTurns();
        this.damageDealt = turnManager.getDamageDealt();
        this.damageTaken = turnManager.getDamageTaken();
        this.criticalHits = turnManager.getCriticalHits();
        this.knowledgeGained = turnManager.getKnowledgeGained();
    }

    public SubjectType getSubjectType()
    {
        return subjectType;
    }

    public String getSubjectName()
    {
        return subjectName;
    }

    public boolean isVictory()
    {
        return victory;
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
        return knowledgeGained;
    }
}
