import greenfoot.*;   // só para Greenfoot.getRandomNumber (sorteio do ataque)
import java.util.ArrayList;

/**
 * Subject - a classe "mãe" de todas as disciplinas (inimigos) do jogo.
 *
 * Guarda tudo o que TODA disciplina tem em comum: nome, vida, força,
 * defesa, energia, a lista de ataques e os métodos de combate. Cada disciplina específica
 * (Calculus, LinearAlgebra...) herda desta classe e só informa
 * os seus próprios valores.
 *
 * É "abstract" porque não existe uma "disciplina genérica" no jogo.
 *
 * Esta classe é só LÓGICA: não desenha nada. A aparência fica em BossSprite.
 */
public abstract class Subject
{
    // Energia das disciplinas: começa em 0 e recupera um pouco a cada turno
    private static final int MAX_ENERGY = 10;
    private static final int ENERGY_PER_TURN = 3;

    private String name;        // nome exibido (ex.: "Cálculo")
    private int maxHealth;      // vida máxima
    private int health;         // vida atual (entre 0 e maxHealth)
    private int attackPower;    // força base, somada ao dano de cada ataque
    private int defense;        // reduz o dano recebido
    private int energy;         // energia atual, usada para pagar ataques mais fortes
    private int difficulty;     // 1 (mais fácil) a 4 (mais difícil)
    private String description; // frase curta mostrada na tela de seleção

    // Composição: uma disciplina TEM uma lista de ataques
    private ArrayList<Attack> attacks;

    /**
     * Construtor: chamado pelas subclasses através de super(...).
     */
    public Subject(String name, int maxHealth, int attackPower, int defense,
                   int difficulty, String description)
    {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;          // começa com a vida cheia
        this.attackPower = attackPower;
        this.defense = defense;
        this.energy = 0;
        this.attacks = new ArrayList<Attack>();   // começa vazia; as subclasses preenchem
        this.difficulty = difficulty;
        this.description = description;
    }

    // ----- Combate -----

    /**
     * Usado pelas subclasses (no construtor) para cadastrar seus ataques.
     * "protected": só a Subject e suas filhas podem chamar.
     */
    protected void addAttack(Attack attack)
    {
        attacks.add(attack);
    }

    /**
     * Sorteia um ataque entre os que a disciplina consegue pagar agora.
     */
    public Attack chooseAttack()
    {
        // Monta uma lista só com os ataques que cabem na energia atual
        ArrayList<Attack> available = new ArrayList<Attack>();
        for (Attack attack : attacks)
        {
            if (attack.getEnergyCost() <= energy)
            {
                available.add(attack);
            }
        }

        // Segurança: se nenhum couber, usa o primeiro ataque da lista
        if (available.isEmpty())
        {
            return attacks.get(0);
        }

        int index = Greenfoot.getRandomNumber(available.size());
        return available.get(index);
    }

    /**
     * Usa um ataque no estudante: paga a energia, aplica o ataque
     * e recupera um pouco de energia para o próximo turno.
     * Devolve o dano que o estudante realmente recebeu.
     */
    public int useAttack(Attack attack, Student target)
    {
        energy = energy - attack.getEnergyCost();
        if (energy < 0)
        {
            energy = 0;
        }

        // Polimorfismo: se for um EnergyDrainAttack, roda o use() dele
        int damage = attack.use(attackPower, target);

        energy = energy + ENERGY_PER_TURN;
        if (energy > MAX_ENERGY)
        {
            energy = MAX_ENERGY;
        }

        return damage;
    }

    /**
     * Recebe um ataque. A defesa da disciplina reduz o dano.
     * Devolve quanto de vida foi realmente perdido.
     */
    public int takeDamage(int damage)
    {
        int realDamage = damage - defense;

        // A defesa não pode "curar": o dano mínimo é 0
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
     * Recupera vida.
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
     * Diz se a disciplina já foi vencida.
     */
    public boolean isDefeated()
    {
        return health == 0;
    }

    // ----- Métodos de acesso (getters) -----

    public String getName()
    {
        return name;
    }

    public int getMaxHealth()
    {
        return maxHealth;
    }

    public int getHealth()
    {
        return health;
    }

    public int getAttackPower()
    {
        return attackPower;
    }

    public int getDefense()
    {
        return defense;
    }

    public int getEnergy()
    {
        return energy;
    }

    public int getMaxEnergy()
    {
        return MAX_ENERGY;
    }

    public int getDifficulty()
    {
        return difficulty;
    }

    public String getDescription()
    {
        return description;
    }
}
