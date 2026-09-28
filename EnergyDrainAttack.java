/**
 * EnergyDrainAttack - um ataque que, além do dano, drena a energia do estudante.
 *
 * Herda tudo de Attack e só muda o que acontece em use().
 */
public class EnergyDrainAttack extends Attack
{
    private final int energyDrain;   // quanto de energia o estudante perde

    public EnergyDrainAttack(String name, int damage, int energyCost,
                             String description, int energyDrain)
    {
        super(name, damage, energyCost, description);
        this.energyDrain = energyDrain;
    }

    /**
     * Primeiro drena a energia, depois causa o dano normal
     * reaproveitando o use() da classe mãe (super.use).
     */
    @Override
    public int use(int attackPower, Student target)
    {
        target.loseEnergy(energyDrain);
        return super.use(attackPower, target);
    }
}
