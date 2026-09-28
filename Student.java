import greenfoot.*;   // só para Greenfoot.getRandomNumber (sorteio do crítico)

/**
 * Student - o personagem controlado pelo jogador (LÓGICA).
 *
 * Guarda os atributos do estudante (vida, energia, conhecimento e defesa)
 * e oferece métodos seguros para alterá-los. Nenhuma outra classe mexe
 * nos atributos diretamente: todas precisam usar estes métodos.
 *
 * Esta classe NÃO desenha nada: a aparência fica em PlayerSprite.
 */
public class Student
{
    // Efeitos das ações (o custo de energia de cada ação fica no enum PlayerAction).
    // São public para a interface poder mostrar os números sem repeti-los.
    public static final int STUDY_ENERGY_GAIN = 10;     // ESTUDAR: energia recuperada
    public static final int STUDY_KNOWLEDGE_GAIN = 3;   // ESTUDAR: conhecimento ganho
    public static final int KNOWLEDGE_MULTIPLIER = 2;   // USAR_CONHECIMENTO: dano = conhecimento x 2
    public static final int REST_HEALTH_GAIN = 20;      // DESCANSAR: vida recuperada
    public static final int REST_ENERGY_GAIN = 5;       // DESCANSAR: energia recuperada

    // Acerto crítico: chance (em %) de um ataque causar 50% a mais de força
    public static final int CRITICAL_CHANCE = 15;

    // Nome exibido acima do personagem
    private String nome;

    // Vida: se chegar a 0, o estudante perde a batalha
    private int maxHealth;     // vida máxima
    private int health;        // vida atual (entre 0 e maxHealth)

    // Energia: gasta para usar ações especiais
    private int maxEnergy;     // energia máxima
    private int energy;        // energia atual (entre 0 e maxEnergy)

    // Conhecimento: vai servir como "força de ataque" do estudante
    private int knowledge;

    // Defesa: reduz o dano recebido
    private int defense;

    // A última ação foi um acerto crítico? (usado nas mensagens e efeitos)
    private boolean lastActionCritical;

    /**
     * Construtor: cria um estudante com o nome informado
     * e com os valores iniciais dos atributos.
     */
    public Student(String nome)
    {
        this.nome = nome;

        this.maxHealth = 100;
        this.health = maxHealth;      // começa com a vida cheia

        this.maxEnergy = 50;
        this.energy = maxEnergy;      // começa com a energia cheia

        this.knowledge = 10;
        this.defense = 5;
        this.lastActionCritical = false;
    }

    // ----- Vida -----

    /**
     * Recebe um ataque. A defesa do estudante reduz o dano.
     * Devolve quanto de vida foi realmente perdido.
     */
    public int takeDamage(int damage)
    {
        int realDamage = damage - defense;

        // A defesa não pode "curar" o estudante: o dano mínimo é 0
        if (realDamage < 0)
        {
            realDamage = 0;
        }

        health = health - realDamage;

        // Regra: a vida nunca fica abaixo de 0
        if (health < 0)
        {
            health = 0;
        }

        return realDamage;
    }

    /**
     * Recupera vida (ex.: tomar um café, dormir bem).
     */
    public void heal(int amount)
    {
        // Valores negativos não fazem sentido aqui: ignoramos
        if (amount < 0)
        {
            return;
        }

        health = health + amount;

        // Regra: a vida nunca passa do máximo
        if (health > maxHealth)
        {
            health = maxHealth;
        }
    }

    /**
     * Diz se o estudante ainda pode lutar.
     */
    public boolean isDefeated()
    {
        return health == 0;
    }

    // ----- Energia -----

    /**
     * Tenta gastar energia.
     * Se tiver energia suficiente, gasta e devolve true.
     * Se não tiver, não gasta nada e devolve false.
     */
    public boolean spendEnergy(int amount)
    {
        if (amount < 0 || amount > energy)
        {
            return false;   // valor inválido ou energia insuficiente: nada muda
        }

        energy = energy - amount;
        return true;
    }

    /**
     * Recupera energia (ex.: descansar um turno).
     */
    public void recoverEnergy(int amount)
    {
        // Valores negativos não fazem sentido aqui: ignoramos
        if (amount < 0)
        {
            return;
        }

        energy = energy + amount;

        // Regra: a energia nunca passa do máximo
        if (energy > maxEnergy)
        {
            energy = maxEnergy;
        }
    }

    /**
     * Perde energia por causa de um ataque inimigo (ex.: Infinite Loop).
     * Diferente de spendEnergy: aqui não é uma escolha, então sempre acontece.
     */
    public void loseEnergy(int amount)
    {
        if (amount < 0)
        {
            return;
        }

        energy = energy - amount;

        // Regra: a energia nunca fica abaixo de 0
        if (energy < 0)
        {
            energy = 0;
        }
    }

    // ----- Conhecimento -----

    /**
     * Aumenta o conhecimento (ex.: ao estudar ou vencer uma disciplina).
     */
    public void increaseKnowledge(int amount)
    {
        // Valores negativos não fazem sentido aqui: ignoramos
        if (amount < 0)
        {
            return;
        }

        knowledge = knowledge + amount;
    }

    // ----- Ações de batalha (escolhidas pelo jogador) -----

    /**
     * O estudante tem energia suficiente para esta ação?
     */
    public boolean canPerform(PlayerAction action)
    {
        return energy >= action.getEnergyCost();
    }

    /**
     * Executa uma ação: primeiro paga o custo de energia, depois aplica o efeito.
     * Devolve o dano causado no inimigo (0 para ações que não atacam).
     * Quem chama deve verificar canPerform() antes.
     */
    public int performAction(PlayerAction action, Subject enemy)
    {
        lastActionCritical = false;

        // Segurança: sem energia, nada acontece
        if (!spendEnergy(action.getEnergyCost()))
        {
            return 0;
        }

        switch (action)
        {
            case ESTUDAR:
                study();
                return 0;

            case RESOLVER_QUESTAO:
                return solveQuestion(enemy);

            case USAR_CONHECIMENTO:
                return useKnowledge(enemy);

            case DESCANSAR:
                rest();
                return 0;

            default:
                return 0;
        }
    }

    /**
     * ESTUDAR: recupera energia e aumenta o conhecimento.
     */
    private void study()
    {
        recoverEnergy(STUDY_ENERGY_GAIN);
        increaseKnowledge(STUDY_KNOWLEDGE_GAIN);
    }

    /**
     * RESOLVER_QUESTAO: ataque normal, a força é o conhecimento.
     */
    private int solveQuestion(Subject enemy)
    {
        return strike(enemy, knowledge);
    }

    /**
     * USAR_CONHECIMENTO: ataque forte, a força é o dobro do conhecimento.
     */
    private int useKnowledge(Subject enemy)
    {
        return strike(enemy, knowledge * KNOWLEDGE_MULTIPLIER);
    }

    /**
     * Golpeia a disciplina com a força informada.
     * Sorteia o crítico: se acontecer, a força aumenta 50%.
     */
    private int strike(Subject enemy, int power)
    {
        lastActionCritical = Greenfoot.getRandomNumber(100) < CRITICAL_CHANCE;

        if (lastActionCritical)
        {
            power = power + power / 2;
        }

        return enemy.takeDamage(power);
    }

    /**
     * DESCANSAR: recupera vida e um pouco de energia.
     */
    private void rest()
    {
        heal(REST_HEALTH_GAIN);
        recoverEnergy(REST_ENERGY_GAIN);
    }

    // ----- Métodos de acesso (getters) -----

    public String getNome()
    {
        return nome;
    }

    public int getMaxHealth()
    {
        return maxHealth;
    }

    public int getHealth()
    {
        return health;
    }

    public int getMaxEnergy()
    {
        return maxEnergy;
    }

    public int getEnergy()
    {
        return energy;
    }

    public int getKnowledge()
    {
        return knowledge;
    }

    public int getDefense()
    {
        return defense;
    }

    public boolean wasLastActionCritical()
    {
        return lastActionCritical;
    }
}
