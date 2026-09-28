/**
 * Calculus - primeira disciplina (a mais fácil).
 */
public class Calculus extends Subject
{
    public Calculus()
    {
        //    nome       vida  força  defesa  dificuldade
        super("Cálculo", 60,   10,    2,      1,
              "Limites, derivadas e integrais.");

        //                    nome        dano  custo  descrição
        addAttack(new Attack("Limite",    0,    0,     "Seu tempo livre tende a zero."));
        addAttack(new Attack("Derivada",  4,    3,     "A taxa de variação do seu desespero."));
        addAttack(new Attack("Integral",  8,    6,     "Soma toda a área do seu sofrimento."));
    }
}
