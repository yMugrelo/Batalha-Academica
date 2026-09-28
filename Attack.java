/**
 * Attack - um ataque que uma disciplina pode usar.
 *
 * É uma classe Java comum (não é Actor): ela não aparece na tela,
 * só guarda os dados do ataque e sabe como aplicá-lo no estudante.
 *
 * Os atributos são "final": depois de criado, um ataque nunca muda.
 */
public class Attack
{
    private final String name;         // nome do ataque (ex.: "Integral")
    private final int damage;          // dano extra, somado à força da disciplina
    private final int energyCost;      // energia que a disciplina gasta para usar
    private final String description;  // frase exibida quando o ataque é usado

    public Attack(String name, int damage, int energyCost, String description)
    {
        this.name = name;
        this.damage = damage;
        this.energyCost = energyCost;
        this.description = description;
    }

    /**
     * Aplica o ataque no estudante.
     * attackPower é a força base da disciplina que está atacando.
     * Devolve o dano que o estudante realmente recebeu.
     *
     * Subclasses podem sobrescrever este método para criar efeitos extras.
     */
    public int use(int attackPower, Student target)
    {
        return target.takeDamage(attackPower + damage);
    }

    // ----- Getters -----

    public String getName()
    {
        return name;
    }

    public int getDamage()
    {
        return damage;
    }

    public int getEnergyCost()
    {
        return energyCost;
    }

    public String getDescription()
    {
        return description;
    }
}
