/**
 * SubjectType - identifica cada uma das quatro matérias do jogo.
 *
 * Serve para as telas (seleção, batalha, resultado) saberem QUAL matéria
 * foi escolhida sem precisar guardar o objeto Subject inteiro, e para
 * criar uma batalha nova (create) sempre que necessário.
 */
public enum SubjectType
{
    CALCULUS,
    LINEAR_ALGEBRA,
    JAVA_PROGRAMMING,
    DATA_STRUCTURES;

    /**
     * Cria um objeto novo da matéria, com vida e energia cheias.
     */
    public Subject create()
    {
        switch (this)
        {
            case LINEAR_ALGEBRA:
                return new LinearAlgebra();
            case JAVA_PROGRAMMING:
                return new JavaProgramming();
            case DATA_STRUCTURES:
                return new DataStructures();
            default:
                return new Calculus();
        }
    }
}
