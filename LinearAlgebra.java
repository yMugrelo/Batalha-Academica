/**
 * LinearAlgebra - segunda disciplina.
 */
public class LinearAlgebra extends Subject
{
    public LinearAlgebra()
    {
        //    nome              vida  força  defesa  dificuldade
        super("Álgebra Linear", 70,   11,    3,      2,
              "Matrizes, vetores e autovalores.");

        //                    nome            dano  custo  descrição
        addAttack(new Attack("Matriz",        0,    0,     "Uma matriz 5x5 cai na sua cabeça."));
        addAttack(new Attack("Determinante",  4,    3,     "Calcule sem calculadora!"));
        addAttack(new Attack("Autovalor",     9,    7,     "Seus problemas só se multiplicam."));
    }
}
