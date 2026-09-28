/**
 * PlayerAction - as quatro ações que o jogador pode escolher no seu turno.
 *
 * Este enum não é só uma lista de nomes: cada ação também guarda
 * seus próprios dados (tecla, texto e custo de energia).
 * Assim, tudo o que "descreve" uma ação fica em um lugar só.
 */
public enum PlayerAction
{
    //                 tecla  texto na tela           custo de energia
    RESOLVER_QUESTAO  ("1",   "Resolver Questão",     5),
    ESTUDAR           ("2",   "Estudar",              0),
    USAR_CONHECIMENTO ("3",   "Usar Conhecimento",    20),
    DESCANSAR         ("4",   "Descansar",            0);

    private final String key;         // tecla que escolhe a ação
    private final String label;       // nome mostrado na tela
    private final int energyCost;     // energia gasta para usar a ação

    /**
     * Construtor do enum: chamado automaticamente uma vez para cada
     * valor da lista acima (ESTUDAR, RESOLVER_QUESTAO...).
     */
    PlayerAction(String key, String label, int energyCost)
    {
        this.key = key;
        this.label = label;
        this.energyCost = energyCost;
    }

    public String getKey()
    {
        return key;
    }

    public String getLabel()
    {
        return label;
    }

    public int getEnergyCost()
    {
        return energyCost;
    }

    /**
     * Procura a ação ligada a uma tecla.
     * Devolve null se a tecla não corresponder a nenhuma ação.
     */
    public static PlayerAction fromKey(String key)
    {
        // values() devolve todas as ações do enum, na ordem em que foram escritas
        for (PlayerAction action : values())
        {
            if (action.getKey().equals(key))
            {
                return action;
            }
        }
        return null;
    }
}
