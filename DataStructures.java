/**
 * DataStructures - quarta e última disciplina (a mais difícil).
 */
public class DataStructures extends Subject
{
    public DataStructures()
    {
        //    nome                  vida  força  defesa  dificuldade
        super("Estrutura de Dados", 100,  13,    5,      4,
              "Pilhas, filas, listas e árvores.");

        //                    nome           dano  custo  descrição
        addAttack(new Attack("Stack",        0,    0,     "A última tarefa chega primeiro."));
        addAttack(new Attack("Queue",        3,    3,     "Uma fila infinita de exercícios."));
        addAttack(new Attack("Linked List",  6,    5,     "Você perdeu o ponteiro do nó!"));
        addAttack(new Attack("Binary Tree",  10,   9,     "Uma árvore totalmente desbalanceada!"));
    }
}
