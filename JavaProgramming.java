/**
 * JavaProgramming - terceira disciplina.
 */
public class JavaProgramming extends Subject
{
    public JavaProgramming()
    {
        //    nome                   vida  força  defesa  dificuldade
        super("Programação em Java", 85,   12,    4,      3,
              "Classes, objetos e exceções.");

        //                    nome                    dano  custo  descrição
        addAttack(new Attack("NullPointerException",  1,    0,     "Você esqueceu de inicializar!"));

        // Infinite Loop é um ataque diferente: também drena 10 de energia do estudante
        addAttack(new EnergyDrainAttack("Infinite Loop", 0, 4,
                                        "while (true) consome sua energia.", 10));

        addAttack(new Attack("StackOverflowError",    10,   8,     "Recursão sem caso base!"));
    }
}
