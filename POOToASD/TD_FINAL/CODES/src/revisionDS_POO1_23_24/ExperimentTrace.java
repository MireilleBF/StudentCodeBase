package revisionDS_POO1_23_24;

import java.util.*;

/**
 * Classe qui représente une trace d'expérimentation.
 * Une trace d'expérimentation est une pile de résultats d'expérimentation.
 * Les résultats sont empilés dans l'ordre où ils sont obtenus.
 * Les résultats sont retirés de la pile dans l'ordre inverse de leur obtention.
 *
 */
public class ExperimentTrace {

    private final Stack<Result> experimentStack = new Stack<>();

    /**
     * Ajoute un résultat d'expérimentation à la trace.
     *
     * @param result le résultat à ajouter.
     * @throws ExperimentTraceException si le résultat n'est pas valide.
     */
    public void addExperimentResult(Result result) throws ExperimentTraceException {
        if (isValid(result)) {
            experimentStack.push(result);
        } else {
            throw new ExperimentTraceException("Résultat d'expérimentation non valide");
        }
    }

    /**
     * Un résultat est valide s'il n'est pas null et si son code est inférieur à DANGER.
     *
     * @param result
     * @return true si le résultat est valide, false sinon.
     */
    private boolean isValid(Result result) {
        return ((result != null) && (result.getCode() < Result.DANGER));
    }

    /*
     * Retire de la trace tous les résultats qui sont égaux à un résultat donné.
     */
    public void removeMatchingResults(Result targetResult) {
        //List<Result> retainedResults = new ArrayList<>();
        //je choisis de rester sur une pile, mais je pourrais utiliser une liste comme ci-dessus
        Stack<Result> retainedResults = new Stack<>();

        while (!experimentStack.isEmpty()) {
            Result result = experimentStack.pop();
            if (!result.equals(targetResult)) {
                retainedResults.push(result);
            }
        }

        // Remettre les résultats retenus dans la pile tout en conservant l'ordre d'origine.
        //Collections.reverse(retainedResults);
        //experimentStack.addAll(retainedResults);
        while (!retainedResults.isEmpty()) {
            experimentStack.push(retainedResults.pop());
        }

    }

    /**
     * Retourne la liste des résultats d'expérimentation dans l'ordre inverse de leur obtention.
     * La pile est vidée.
     *
     * @return la liste des résultats d'expérimentation dans l'ordre inverse de leur obtention.
     */
    public List<Result> getExperimentResults() {
        List<Result> res = new ArrayList<>(experimentStack);
        experimentStack.clear();
        Collections.reverse(res);
        return res;
    }

    public Result peekLastResult() {
        return experimentStack.peek(); // throws EmptyStackException if stack is empty
    }

    public List<Result> getResultCopy() {
        List<Result> res = new ArrayList<>(experimentStack);
        Collections.reverse(res);
        return res;
    }

    public void removeDuplicatedResults() {
        List<Result> temp = this.getResultCopy();
        LinkedHashSet<Result> uniqueResultsSet = new LinkedHashSet<>(temp);
        experimentStack.clear();
        experimentStack.addAll(uniqueResultsSet);
        Collections.reverse(experimentStack);
    }
}